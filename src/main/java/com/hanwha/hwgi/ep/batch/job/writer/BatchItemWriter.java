package com.hanwha.hwgi.ep.batch.job.writer;

import org.springframework.batch.item.ItemWriter;

public interface BatchItemWriter {
	public ItemWriter<?> getBatchItemWriter();
}
