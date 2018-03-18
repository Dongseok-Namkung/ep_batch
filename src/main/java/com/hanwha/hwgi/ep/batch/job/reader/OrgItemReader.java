package com.hanwha.hwgi.ep.batch.job.reader;

import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.batch.MyBatisCursorItemReader;
import org.springframework.batch.item.ItemReader;

import com.hanwha.hwgi.ep.batch.vo.OrgVO;

public class OrgItemReader implements BatchItemReader{

    private SqlSessionFactory sqlSessionFactory;
    
    public OrgItemReader(SqlSessionFactory sqlSessionFactory) {
		this.sqlSessionFactory = sqlSessionFactory;
	}

	@Override
	public ItemReader<OrgVO> getBatchItemReader() {
		MyBatisCursorItemReader<OrgVO> reader = new MyBatisCursorItemReader<OrgVO>();
    	reader.setSqlSessionFactory(sqlSessionFactory);
    	reader.setQueryId("selectOrg");
      
    	return reader;
	}
}
