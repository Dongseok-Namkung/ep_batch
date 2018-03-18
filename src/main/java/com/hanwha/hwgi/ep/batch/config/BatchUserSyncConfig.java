package com.hanwha.hwgi.ep.batch.config;

import javax.annotation.Resource;

import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.batch.MyBatisBatchItemWriter;
import org.mybatis.spring.batch.MyBatisCursorItemReader;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.ItemWriter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.hanwha.hwgi.ep.batch.listener.JobCompletionNotificationListener;
import com.hanwha.hwgi.ep.batch.processor.UserItemProcessor;
import com.hanwha.hwgi.ep.batch.vo.UserVO;

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
	private static final Logger log = LoggerFactory.getLogger(BatchUserSyncConfig.class);
    @Autowired
    private JobBuilderFactory jobBuilderFactory;

    @Autowired
    private StepBuilderFactory stepBuilderFactory;

//    @Resource(name = "primaryDataSource")
//    private DataSource primaryDataSource;
//    
//    @Resource(name = "secondaryDataSource")
//    private DataSource secondaryDataSource;
    
    @Resource(name = "sqlSessionFactoryPrimary")
    private SqlSessionFactory sqlSessionFactoryPrimary;
    
    @Resource(name = "sqlSessionFactorySecondary")
    private SqlSessionFactory sqlSessionFactorySecondary;
    
    @Bean
    ItemReader<UserVO> userItemReader() {
//        JdbcCursorItemReader<UserVO> databaseReader = new JdbcCursorItemReader<>();
//        databaseReader.setDataSource(primaryDataSource);
//        databaseReader.setSql(BatchQuery.QUERY_SEL_USER);
//        log.info("userItemReader 수행:",BatchQuery.QUERY_SEL_USER);
//        databaseReader.setRowMapper(new BeanPropertyRowMapper<>(UserVO.class));
 
        MyBatisCursorItemReader<UserVO> reader = new MyBatisCursorItemReader<>();
        reader.setSqlSessionFactory(sqlSessionFactoryPrimary);
        reader.setQueryId("selectUser");

        
        return reader;
        
    }

    @Bean
    public UserItemProcessor userSyncProcessor() {
        return new UserItemProcessor();
    }

    @Bean
    public ItemWriter<UserVO> userItemWriter() {
    	
//        JdbcBatchItemWriter<UserVO> writer = new JdbcBatchItemWriter<UserVO>();
//        writer.setItemSqlParameterSourceProvider(new BeanPropertyItemSqlParameterSourceProvider<UserVO>());
//        writer.setSql(BatchQuery.QUERY_PUT_USER);
//        log.info("userItemWriter 수행:"+BatchQuery.QUERY_PUT_USER);
//        writer.setDataSource(secondaryDataSource);
    	
    	MyBatisBatchItemWriter<UserVO> writer = new MyBatisBatchItemWriter<UserVO>();
        writer.setSqlSessionFactory(sqlSessionFactorySecondary);
        writer.setStatementId("insertUser");

        return writer;
    }
    

    @Bean
    public Job importUserJob(JobCompletionNotificationListener listener) {
        return jobBuilderFactory.get("importUserJob")
                .incrementer(new RunIdIncrementer())
                .listener(listener)
                .flow(SynchronizeUserStep())
                .end()
                .build();
    }
    
    @Bean
    public Step SynchronizeUserStep() {
        return stepBuilderFactory.get("userSyncStep")
                .<UserVO, UserVO> chunk(10)
                .reader(userItemReader())
                .processor(userSyncProcessor())
                .writer(userItemWriter())
                .build();
    }
}