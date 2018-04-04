package com.nkds.batch.job.writer;

import org.apache.ibatis.session.SqlSessionFactory;
import org.springframework.batch.item.ItemWriter;

public interface InfBatchItemWriter {
	public ItemWriter<?> buildWriter(SqlSessionFactory sqlSessionFactory);
}
