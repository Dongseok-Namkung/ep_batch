package com.hanwha.hwgi.ep.batch.listener;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.core.BatchStatus;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.listener.JobExecutionListenerSupport;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

@Component
public class JobCompletionNotificationListener extends JobExecutionListenerSupport {

	private static final Logger log = LoggerFactory.getLogger(JobCompletionNotificationListener.class);

	private final JdbcTemplate jdbcTemplate;

	@Autowired
	public JobCompletionNotificationListener(JdbcTemplate jdbcTemplate) {
		this.jdbcTemplate = jdbcTemplate;
	}

	/**
	 * 
	 * @Method Name  : SAM_STF User 동기화 후 후속처리 정의
	 * @Method 설명 : SAM_STF에서 변경데이터를 가지고 온다. 
	 * @Method override : 
	 * @작성자   : nkds
	 * @작성일   : 2018. 3. 8. 
	 * @변경이력  :
	 * @param 
	 * @param 
	 * @param 
	 * @return ItemReader
	 * @throws Exception
	 */
	@Override
	public void afterJob(JobExecution jobExecution) {
		if(jobExecution.getStatus() == BatchStatus.COMPLETED) {
//			log.info("!!! JOB FINISHED! Time to verify the results");
//
//			List<User> results = jdbcTemplate.query("SELECT stfno, nm FROM sam_stf", new RowMapper<User>() {
//				@Override
//				public User mapRow(ResultSet rs, int row) throws SQLException {
//					return new User(rs.getString(1), rs.getString(2));
//				}
//			});
//
//			for (User person : results) {
//				log.info("Found <" + person + "> in the database.");
//			}

		}
	}
}