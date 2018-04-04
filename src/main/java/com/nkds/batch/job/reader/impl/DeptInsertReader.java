package com.nkds.batch.job.reader.impl;

import java.util.HashMap;
import java.util.Map;

import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.batch.MyBatisCursorItemReader;
import org.springframework.batch.item.ItemReader;

import com.nkds.batch.job.reader.InfBatchItemReader;
import com.nkds.batch.model.Dept;
import com.nkds.batch.util.Constants;

public class DeptInsertReader implements InfBatchItemReader{

    
	@Override
	public ItemReader<Dept> buildReader(SqlSessionFactory sqlSessionFactory, SqlSessionFactory primarySqlSessionFactory) {
		MyBatisCursorItemReader<Dept> reader = new MyBatisCursorItemReader<Dept>();
    	reader.setSqlSessionFactory(sqlSessionFactory);
    	
    	Map<String, Object> parameterValues = new HashMap<String, Object>();
    	
    	parameterValues.put("endTime", getLastExecutionTime(primarySqlSessionFactory));
    	
    	reader.setParameterValues(parameterValues);
    	reader.setQueryId("deptInsertReader_selectDept");
    	reader.setSaveState(true);
		
    	
    	return reader;
	}
	
	@Override
	public int getLastExecutionTime(SqlSessionFactory primarySqlSessionFactory) {
		SqlSession sqlSession = primarySqlSessionFactory.openSession();
		
		return sqlSession.selectOne("selectLastDate", Constants.JOB_DEPT_INSERT.name());
	}

}
