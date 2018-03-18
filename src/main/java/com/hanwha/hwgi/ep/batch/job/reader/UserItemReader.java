package com.hanwha.hwgi.ep.batch.job.reader;

import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.batch.MyBatisCursorItemReader;
import org.springframework.batch.item.ItemReader;

import com.hanwha.hwgi.ep.batch.vo.UserVO;

public class UserItemReader implements BatchItemReader{

    private SqlSessionFactory sqlSessionFactory;
    
    public UserItemReader(SqlSessionFactory sqlSessionFactory) {
		this.sqlSessionFactory = sqlSessionFactory;
	}

	@Override
	public ItemReader<UserVO> getBatchItemReader() {
		MyBatisCursorItemReader<UserVO> reader = new MyBatisCursorItemReader<UserVO>();
    	reader.setSqlSessionFactory(sqlSessionFactory);
    	reader.setQueryId("selectUser");
      
    	return reader;
	}
}
