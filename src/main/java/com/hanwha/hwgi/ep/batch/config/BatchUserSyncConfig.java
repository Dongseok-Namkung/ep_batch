package com.hanwha.hwgi.ep.batch.config;

import javax.sql.DataSource;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.database.BeanPropertyItemSqlParameterSourceProvider;
import org.springframework.batch.item.database.JdbcBatchItemWriter;
import org.springframework.batch.item.database.JdbcCursorItemReader;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.BeanPropertyRowMapper;

import com.hanwha.hwgi.ep.batch.constants.BatchQuery;
import com.hanwha.hwgi.ep.batch.listener.JobCompletionNotificationListener;
import com.hanwha.hwgi.ep.batch.processor.UserItemProcessor;
import com.hanwha.hwgi.ep.batch.vo.User;

/**
 * 
 *============================================================================
 * @PROEJCT  : 포탈 프로젝트
 * @NAME     : 배치설정 BatchConfiguration
 * @DESC     : 배치설정파일
 * @DESC     : 실행순서: userItemReader -> processor -> userItemWriter
 * @DESC     : 관련테이블: BATCH_JOB_INSTANCE, BATCH_JOB_EXECUTION, BATCH_JOB_EXECUTION_PARAMS, BATCH_STEP_EXECUTION, BATCH_STEP_EXECUTION_CONTEXT, BATCH_JOB_EXECUTION_CONTEXT
 * @author   : NKDS
 *============================================================================
 *    DATE     		AUTHOR              DESCRIPTION
 *============================================================================
 * 2018. 3. 8.   	NKDS          	최초작성
 *
 *============================================================================
 */
@Configuration
@EnableBatchProcessing
public class BatchUserSyncConfig {

    @Autowired
    private JobBuilderFactory jobBuilderFactory;

    @Autowired
    private StepBuilderFactory stepBuilderFactory;

    @Autowired
    private DataSource dataSource;
    
	/**
	 * 
	 * @Method Name  : SAM_STF에서 변경데이터 가져오기
	 * @Method 설명 : SAM_STF에서 변경데이터를 가지고 온다. 
	 * @Method override : 
	 * @작성자   : nkds
	 * @작성일   : 2018. 3. 8. 
	 * @변경이력  :
	 * @param 
	 * @return ItemReader
	 * @throws Exception
	 */
    @Bean
    ItemReader<User> userItemReader() {
        JdbcCursorItemReader<User> databaseReader = new JdbcCursorItemReader<>();

        databaseReader.setDataSource(dataSource);
        databaseReader.setSql(BatchQuery.QUERY_SEL_USER);
        databaseReader.setRowMapper(new BeanPropertyRowMapper<>(User.class));
 
        return databaseReader;
    }

    /**
	 * 
	 * @Method Name  : Reader에서 읽어온 User 객체로 처리할 메소드 정의 (UserItemProcessor)
	 * @Method 설명 : Reader에서 읽어온 User 객체가 넘어간다.
	 * @Method override : 
	 * @작성자   : nkds
	 * @작성일   : 2018. 3. 8. 
	 * @변경이력  :
	 * @param 
	 * @return UserItemProcessor
	 * @throws Exception
	 */
    @Bean
    public UserItemProcessor processor() {
        return new UserItemProcessor();
    }

    /**
	 * 
	 * @Method Name  : UserItemProcessor 에서처리한 User 객체를 DB에 저장
	 * @Method 설명 : UserItemProcessor 에서처리한 User 객체를 DB에 저장한다.
	 * @Method override : 
	 * @작성자   : nkds
	 * @작성일   : 2018. 3. 8. 
	 * @변경이력  :
	 * @param 
	 * @return UserItemProcessor
	 * @throws Exception
	 */
    @Bean
    public JdbcBatchItemWriter<User> userItemWriter() {
    	
        JdbcBatchItemWriter<User> writer = new JdbcBatchItemWriter<User>();
        writer.setItemSqlParameterSourceProvider(new BeanPropertyItemSqlParameterSourceProvider<User>());
        writer.setSql(BatchQuery.QUERY_PUT_USER);
        writer.setDataSource(dataSource);
        return writer;
    }
    
    /**
	 * 
	 * @Method Name  : SAM_STF User 동기화 Job 설정
	 * @Method 설명 : SAM_STF User 동기화 Job 설정
	 * @Method override : 
	 * @작성자   : nkds
	 * @작성일   : 2018. 3. 8. 
	 * @변경이력  :
	 * @param 
	 * @return Job
	 * @throws Exception
	 */
    @Bean
    public Job importUserJob(JobCompletionNotificationListener listener) {
        return jobBuilderFactory.get("importUserJob")
                .incrementer(new RunIdIncrementer())
                .listener(listener)
                .flow(SynchronizeUserStep())
                .end()
                .build();
    }

    /**
	 * 
	 * @Method Name  : SAM_STF User 동기화 Step 설정
	 * @Method 설명 : SAM_STF User 동기화 Step 설정
	 * @Method override : 
	 * @작성자   : nkds
	 * @작성일   : 2018. 3. 8. 
	 * @변경이력  :
	 * @param 
	 * @return Step
	 * @throws Exception
	 */
    @Bean
    public Step SynchronizeUserStep() {
        return stepBuilderFactory.get("step1")
                .<User, User> chunk(10)
                .reader(userItemReader())
                .processor(processor())
                .writer(userItemWriter())
                .build();
    }
}