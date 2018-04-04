package com.nkds.batch.job.reader.impl;

import java.util.HashMap;
import java.util.Map;

import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.batch.MyBatisCursorItemReader;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.nkds.batch.job.reader.InfBatchItemReader;
import com.nkds.batch.model.User;
import com.nkds.batch.util.Constants;

public class UserInsertReader implements InfBatchItemReader{
	private static final Logger log = LoggerFactory.getLogger(UserInsertReader.class);
	
	@Override
	public MyBatisCursorItemReader<User> buildReader(SqlSessionFactory sqlSessionFactory, SqlSessionFactory primarySqlSessionFactory) {
		MyBatisCursorItemReader<User> reader = new MyBatisCursorItemReader<User>();
    	reader.setSqlSessionFactory(sqlSessionFactory);
    	
    	Map<String, Object> parameterValues = new HashMap<String, Object>();
    	
    	parameterValues.put("endTime", getLastExecutionTime(primarySqlSessionFactory));
    	
    	reader.setParameterValues(parameterValues);
    	reader.setQueryId("userInsertItemReader_selectUser");
    	reader.setSaveState(true);
    	
    	return reader;
	}


	@Override
	public int getLastExecutionTime(SqlSessionFactory primarySqlSessionFactory) {
		SqlSession sqlSession = primarySqlSessionFactory.openSession();
		
		return sqlSession.selectOne("selectLastDate", Constants.JOB_USER_INSERT.name());
	}
	
	
}
