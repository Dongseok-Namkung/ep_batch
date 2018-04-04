package com.nkds.batch.job.processor;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.batch.item.ItemProcessor;

import com.nkds.batch.model.User;

public class UserInsertProcessor implements ItemProcessor<User, User> {

    private static final Logger log = LoggerFactory.getLogger(UserInsertProcessor.class);

    @Override
    public User process(final User user) throws Exception {
    	
    	log.info("@User_Insert: {}", user.toString());

        return user;
    }

	

}
