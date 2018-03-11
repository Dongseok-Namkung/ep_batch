package com.hanwha.hwgi.ep.batch.config;

import javax.sql.DataSource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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
import com.hanwha.hwgi.ep.batch.processor.OrgItemProcessor;
import com.hanwha.hwgi.ep.batch.processor.UserItemProcessor;
import com.hanwha.hwgi.ep.batch.vo.OrgVO;
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
	private static final Logger log = LoggerFactory.getLogger(BatchUserSyncConfig.class);
    @Autowired
    private JobBuilderFactory jobBuilderFactory;

    @Autowired
    private StepBuilderFactory stepBuilderFactory;

    @Autowired
    private DataSource dataSource;
    
    @Bean
    ItemReader<User> userItemReader() {
        JdbcCursorItemReader<User> databaseReader = new JdbcCursorItemReader<>();
        String QUERY_FIND_STUDENTS = 
        		                 "SELECT " + 
                             "stfno, " + 
        		                     "nm, " + 
        		                     "orgcd " + 
        		                 "FROM SAM_STF "; 

        databaseReader.setDataSource(dataSource);
        databaseReader.setSql(BatchQuery.QUERY_SEL_USER);
        log.info("userItemReader 수행:",BatchQuery.QUERY_SEL_USER);
        databaseReader.setRowMapper(new BeanPropertyRowMapper<>(User.class));
 
        return databaseReader;
        
    }

    @Bean
    public UserItemProcessor userSyncProcessor() {
        return new UserItemProcessor();
    }

    @Bean
    public JdbcBatchItemWriter<User> userItemWriter() {
    	
        JdbcBatchItemWriter<User> writer = new JdbcBatchItemWriter<User>();
        writer.setItemSqlParameterSourceProvider(new BeanPropertyItemSqlParameterSourceProvider<User>());
        writer.setSql(BatchQuery.QUERY_PUT_USER);
        log.info("userItemWriter 수행:"+BatchQuery.QUERY_PUT_USER);
        writer.setDataSource(dataSource);
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
                .<User, User> chunk(10)
                .reader(userItemReader())
                .processor(userSyncProcessor())
                .writer(userItemWriter())
                .build();
    }
    
//    @Bean
//    ItemReader<OrgVO> orgItemReader() {
//        JdbcCursorItemReader<OrgVO> databaseReader = new JdbcCursorItemReader<>();
//
//        databaseReader.setDataSource(dataSource);
//        databaseReader.setSql(BatchQuery.QUERY_SEL_ORG);
//        databaseReader.setRowMapper(new BeanPropertyRowMapper<>(OrgVO.class));
//        log.info("orgItemReader 수행:",BatchQuery.QUERY_SEL_ORG);
//        return databaseReader;
//    }
//
//    @Bean
//    public OrgItemProcessor orgSyncProcessor() {
//        return new OrgItemProcessor();
//    }
//
//    @Bean
//    public JdbcBatchItemWriter<OrgVO> orgItemWriter() {
//    	
//        JdbcBatchItemWriter<OrgVO> writer = new JdbcBatchItemWriter<OrgVO>();
//        writer.setItemSqlParameterSourceProvider(new BeanPropertyItemSqlParameterSourceProvider<OrgVO>());
//        writer.setSql(BatchQuery.QUERY_PUT_ORG);
//        writer.setDataSource(dataSource);
//        
//        log.info("orgItemWriter 수행:",BatchQuery.QUERY_SEL_ORG);
//        return writer;
//    }
//    
//    @Bean
//    public Job importOrgJob(JobCompletionNotificationListener listener) {
//        return jobBuilderFactory.get("importOrgJob")
//                .incrementer(new RunIdIncrementer())
//                .listener(listener)
//                .flow(SynchronizeOrgStep())
//                .end()
//                .build();
//    }
//
//    @Bean
//    public Step SynchronizeOrgStep() {
//        return stepBuilderFactory.get("orgSyncStep")
//                .<OrgVO, OrgVO> chunk(10)
//                .reader(orgItemReader())
//                .processor(orgSyncProcessor())
//                .writer(orgItemWriter())
//                .build();
//    }
}