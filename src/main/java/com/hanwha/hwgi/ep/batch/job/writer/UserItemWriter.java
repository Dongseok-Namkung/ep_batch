package com.hanwha.hwgi.ep.batch.job.writer;

import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.batch.MyBatisBatchItemWriter;
import org.springframework.batch.item.ItemWriter;

import com.hanwha.hwgi.ep.batch.vo.UserVO;

public class UserItemWriter implements BatchItemWriter{
	
	private SqlSessionFactory sqlSessionFactory;

    public UserItemWriter( SqlSessionFactory sqlSessionFactory) {
    	this.sqlSessionFactory = sqlSessionFactory;
	}
	@Override
	public ItemWriter<UserVO> getBatchItemWriter() {
		MyBatisBatchItemWriter<UserVO> writer = new MyBatisBatchItemWriter<UserVO>();
    	writer.setSqlSessionFactory(sqlSessionFactory);
    	writer.setStatementId("insertUser");

    	return writer;
	}
}
