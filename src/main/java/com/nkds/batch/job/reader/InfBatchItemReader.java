package com.nkds.batch.job.reader;

import org.apache.ibatis.session.SqlSessionFactory;
import org.springframework.batch.item.ItemReader;

public interface InfBatchItemReader {
	public ItemReader<?> buildReader(SqlSessionFactory sqlSessionFactorySAM, SqlSessionFactory sqlSessionFactoryEP);
	public int getLastExecutionTime(SqlSessionFactory sqlSessionFactoryEP);
}
