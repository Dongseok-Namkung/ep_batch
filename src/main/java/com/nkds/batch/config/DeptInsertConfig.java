package com.nkds.batch.config;

import javax.annotation.Resource;

import org.apache.ibatis.session.SqlSessionFactory;
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

import com.nkds.batch.job.processor.DeptInsertProcessor;
import com.nkds.batch.job.reader.impl.DeptInsertReader;
import com.nkds.batch.job.writer.impl.DeptInsertWriter;
import com.nkds.batch.listener.DeptJobCompletionNotificationListener;
import com.nkds.batch.model.Dept;
import com.nkds.batch.util.Constants;

/**
 * 
 *============================================================================
 * @PROEJCT  : 스프링 배치
 * @NAME     : 
 * @DESC     : 배치설정 DeptInsertConfig
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
public class DeptInsertConfig {

    @Autowired
    private JobBuilderFactory jobBuilderFactory;

    @Autowired
    private StepBuilderFactory stepBuilderFactory;
    
    @Resource(name = "primarySqlSessionFactory")
    private SqlSessionFactory primarySqlSessionFactory;
    
    @Resource(name = "secondSqlSessionFactory")
    private SqlSessionFactory secondSqlSessionFactory;
    
    @Bean
    public Job deptInsertJob(DeptJobCompletionNotificationListener listener) {
        return jobBuilderFactory.get(Constants.JOB_DEPT_INSERT.name())
                .incrementer(new RunIdIncrementer())
                .listener(listener)
                .preventRestart()
                .flow(deptInsertStep())
                .end()
                .build();
    }

    @Bean
    public Step deptInsertStep() {
        return stepBuilderFactory.get(Constants.STEP_DEPT_INSERT.name())
                .<Dept, Dept> chunk(100)
                .reader(deptInsertReader())
                .processor(deptInsertProcessor())
                .writer(deptInsertWriter())
                .build();
    }
    
    @Bean
    ItemReader<Dept> deptInsertReader() {
    	 
    	return new DeptInsertReader().buildReader(secondSqlSessionFactory, primarySqlSessionFactory);
    }

    @Bean
    public DeptInsertProcessor deptInsertProcessor() {
        return new DeptInsertProcessor();
    }

    @Bean
    public ItemWriter<Dept> deptInsertWriter() {
        return new DeptInsertWriter().buildWriter(primarySqlSessionFactory);
    }
}