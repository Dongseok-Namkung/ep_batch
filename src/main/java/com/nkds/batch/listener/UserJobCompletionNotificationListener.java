package com.nkds.batch.listener;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.core.BatchStatus;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.listener.JobExecutionListenerSupport;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

@Component
public class UserJobCompletionNotificationListener extends JobExecutionListenerSupport {

	private static final Logger log = LoggerFactory.getLogger(UserJobCompletionNotificationListener.class);

	private final JdbcTemplate jdbcTemplate;
	

	@Autowired
	public UserJobCompletionNotificationListener(JdbcTemplate jdbcTemplate) {
		this.jdbcTemplate = jdbcTemplate;
	}

	/**
	 * 
	 * @Method Name  : 동기화 후 후속처리 정의
	 * @Method 설명 : 
	 * @Method override : 
	 * @작성자   : nkds
	 * @작성일   : 2018. 3. 8. 
	 * @변경이력  :
	 * @param 
	 * @param 
	 * @param 
	 * @return 
	 * @throws Exception
	 */
	@Override
	public void afterJob(JobExecution jobExecution) {
		if(jobExecution.getStatus() == BatchStatus.COMPLETED) {

		}
	}
	@Override
	public void beforeJob(JobExecution jobExecution) {
		super.beforeJob(jobExecution);
	}
}