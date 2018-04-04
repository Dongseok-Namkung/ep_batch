package com.nkds.batch.job.processor;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.item.ItemProcessor;

import com.nkds.batch.model.Dept;

public class DeptInsertProcessor implements ItemProcessor<Dept, Dept> {

    private static final Logger log = LoggerFactory.getLogger(DeptInsertProcessor.class);

    @Override
    public Dept process(final Dept dept) throws Exception {
    	log.info("@Dept_Insert: {}", dept.toString());
    	
        return dept;
    }

	

}
