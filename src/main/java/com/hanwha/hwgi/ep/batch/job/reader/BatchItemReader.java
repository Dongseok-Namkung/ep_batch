package com.hanwha.hwgi.ep.batch.job.reader;

import org.springframework.batch.item.ItemReader;

public interface BatchItemReader {
	public ItemReader<?> getBatchItemReader();
}
