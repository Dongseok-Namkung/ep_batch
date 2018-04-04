package com.nkds.batch.job.writer.impl;

import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.batch.MyBatisBatchItemWriter;
import org.springframework.batch.item.ItemWriter;

import com.nkds.batch.job.writer.InfBatchItemWriter;
import com.nkds.batch.model.User;

public class UserInsertWriter implements InfBatchItemWriter{
	
	@Override
	public ItemWriter<User> buildWriter(SqlSessionFactory sqlSessionFactory) {
		MyBatisBatchItemWriter<User> writer = new MyBatisBatchItemWriter<User>();
    	writer.setSqlSessionFactory(sqlSessionFactory);
    	writer.setStatementId("userInsertItemWriter_insertUser");

    	return writer;
	}
}
