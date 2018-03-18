package com.hanwha.hwgi.ep.batch.job.writer;

import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.batch.MyBatisBatchItemWriter;
import org.springframework.batch.item.ItemWriter;

import com.hanwha.hwgi.ep.batch.vo.OrgVO;

public class OrgItemWriter implements BatchItemWriter{
	
	private SqlSessionFactory sqlSessionFactory;

    public OrgItemWriter( SqlSessionFactory sqlSessionFactory) {
    	this.sqlSessionFactory = sqlSessionFactory;
	}
	@Override
	public ItemWriter<OrgVO> getBatchItemWriter() {
		MyBatisBatchItemWriter<OrgVO> writer = new MyBatisBatchItemWriter<OrgVO>();
    	writer.setSqlSessionFactory(sqlSessionFactory);
    	writer.setStatementId("insertOrg");

    	return writer;
	}
}
