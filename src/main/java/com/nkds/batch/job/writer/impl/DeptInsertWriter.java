package com.nkds.batch.job.writer.impl;

import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.batch.MyBatisBatchItemWriter;
import org.springframework.batch.item.ItemWriter;

import com.nkds.batch.job.writer.InfBatchItemWriter;
import com.nkds.batch.model.Dept;

public class DeptInsertWriter implements InfBatchItemWriter{
	
	@Override
	public ItemWriter<Dept> buildWriter(SqlSessionFactory sqlSessionFactory) {
		MyBatisBatchItemWriter<Dept> writer = new MyBatisBatchItemWriter<Dept>();
    	writer.setSqlSessionFactory(sqlSessionFactory);
    	writer.setStatementId("deptInsertWriter_insertDept");

    	return writer;
	}
}
