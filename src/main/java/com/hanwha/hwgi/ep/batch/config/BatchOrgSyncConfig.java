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
import com.hanwha.hwgi.ep.batch.processor.OrgItemProcessor;
import com.hanwha.hwgi.ep.batch.vo.OrgVO;

/**
 * 
 *============================================================================
 * @PROEJCT  : ?��?�� ?��로젝?��
 * @NAME     : 배치?��?�� BatchConfiguration
 * @DESC     : 배치?��?��?��?��
 * @DESC     : ?��?��?��?��: userItemReader -> processor -> userItemWriter
 * @DESC     : �??��?��?���?: BATCH_JOB_INSTANCE, BATCH_JOB_EXECUTION, BATCH_JOB_EXECUTION_PARAMS, BATCH_STEP_EXECUTION, BATCH_STEP_EXECUTION_CONTEXT, BATCH_JOB_EXECUTION_CONTEXT
 * @author   : NKDS
 *============================================================================
 *    DATE     		AUTHOR              DESCRIPTION
 *============================================================================
 * 2018. 3. 8.   	NKDS          	최초?��?��
 *
 *============================================================================
 */

@Configuration
@EnableBatchProcessing
public class BatchOrgSyncConfig {

    @Autowired
    private JobBuilderFactory jobBuilderFactory;

    @Autowired
    private StepBuilderFactory stepBuilderFactory;

    @Autowired
    private DataSource dataSource;
    
	/**
	 * 
	 * @Method Name  : SAM_STF?��?�� �?경데?��?�� �??��?���?
	 * @Method ?���? : SAM_STF?��?�� �?경데?��?���? �?�?�? ?��?��. 
	 * @Method override : 
	 * @?��?��?��   : nkds
	 * @?��?��?��   : 2018. 3. 8. 
	 * @�?경이?��  :
	 * @param 
	 * @return ItemReader
	 * @throws Exception
	 */
    @Bean
    ItemReader<OrgVO> orgItemReader() {
        JdbcCursorItemReader<OrgVO> databaseReader = new JdbcCursorItemReader<>();

        databaseReader.setDataSource(dataSource);
        databaseReader.setSql(BatchQuery.QUERY_SEL_ORG);
        databaseReader.setRowMapper(new BeanPropertyRowMapper<>(OrgVO.class));
        return databaseReader;
    }

    @Bean
    public OrgItemProcessor orgSyncProcessor() {
        return new OrgItemProcessor();
    }

    @Bean
    public JdbcBatchItemWriter<OrgVO> orgItemWriter() {
    	
        JdbcBatchItemWriter<OrgVO> writer = new JdbcBatchItemWriter<OrgVO>();
        writer.setItemSqlParameterSourceProvider(new BeanPropertyItemSqlParameterSourceProvider<OrgVO>());
        writer.setSql(BatchQuery.QUERY_PUT_ORG);
        writer.setDataSource(dataSource);
        
        return writer;
    }
    
    @Bean
    public Job importOrgJob(JobCompletionNotificationListener listener) {
        return jobBuilderFactory.get("importOrgJob")
                .incrementer(new RunIdIncrementer())
                .listener(listener)
                .flow(SynchronizeOrgStep())
                .end()
                .build();
    }

    @Bean
    public Step SynchronizeOrgStep() {
        return stepBuilderFactory.get("orgSyncStep")
                .<OrgVO, OrgVO> chunk(10)
                .reader(orgItemReader())
                .processor(orgSyncProcessor())
                .writer(orgItemWriter())
                .build();
    }
}