package com.nkds.batch;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
/**
 * 
 *============================================================================
 * @PROEJCT  : 스프링배치
 * @NAME     : 배치 Executor
 * @DESC     : 관련테이블: BATCH_JOB_INSTANCE, BATCH_JOB_EXECUTION, BATCH_JOB_EXECUTION_PARAMS, BATCH_STEP_EXECUTION, BATCH_STEP_EXECUTION_CONTEXT, BATCH_JOB_EXECUTION_CONTEXT
 * @author   : NKDS
 *============================================================================
 *    DATE     		AUTHOR              DESCRIPTION
 *============================================================================
 * 2018. 1. 8.   	NKDS          	최초작성
 *
 *============================================================================
 */

@ComponentScan
@SpringBootApplication

public class NamBatchApplication {
	private static final Logger log = LoggerFactory.getLogger(NamBatchApplication.class);
	
	public static void main(String[] args) {
		
		SpringApplication app = new SpringApplication(NamBatchApplication.class);
		
		app.run(args);
	}
}
