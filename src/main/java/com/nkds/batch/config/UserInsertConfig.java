package com.nkds.batch.config;

import javax.annotation.Resource;

import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.batch.MyBatisCursorItemReader;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.item.ItemWriter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.nkds.batch.job.processor.UserInsertProcessor;
import com.nkds.batch.job.reader.impl.UserInsertReader;
import com.nkds.batch.job.writer.impl.UserInsertWriter;
import com.nkds.batch.listener.UserJobCompletionNotificationListener;
import com.nkds.batch.model.User;
import com.nkds.batch.util.Constants;

/**
 * 
 *============================================================================
 * @PROEJCT  : 스프링 배치
 * @NAME     : 
 * @DESC     : UserInsertConfig
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
public class UserInsertConfig {
	private static final Logger log = LoggerFactory.getLogger(UserInsertConfig.class);
	
    @Autowired
    private JobBuilderFactory jobBuilderFactory;

    @Autowired
    private StepBuilderFactory stepBuilderFactory;
    
    @Resource(name = "primarySqlSessionFactory")
    private SqlSessionFactory primarySqlSessionFactory;
    
    @Resource(name = "secondSqlSessionFactory")
    private SqlSessionFactory secondSqlSessionFactory;
    
    @Bean
    public Job userInsertJob(UserJobCompletionNotificationListener listener) {
        return jobBuilderFactory.get(Constants.JOB_USER_INSERT.name())
                .incrementer(new RunIdIncrementer())
                .listener(listener)
                .preventRestart()
                .flow(userInsertStep())
                .end()
                .build();
    }
    
    @Bean
    public Step userInsertStep() {
        return stepBuilderFactory.get(Constants.STEP_USER_INSERT.name())
                .<User, User> chunk(100)
                .reader(userInsertReader())
                .processor(userInsertProcessor())
                .writer(userInsertWriter())
                .build();
    }
    
    @Bean
    MyBatisCursorItemReader<User> userInsertReader() {
        return new UserInsertReader().buildReader(secondSqlSessionFactory, primarySqlSessionFactory);
    }
    
    @Bean
    public UserInsertProcessor userInsertProcessor() {
        return new UserInsertProcessor();
    }
    
    @Bean
    public ItemWriter<User> userInsertWriter() {
        return new UserInsertWriter().buildWriter(primarySqlSessionFactory);
    }
    
}