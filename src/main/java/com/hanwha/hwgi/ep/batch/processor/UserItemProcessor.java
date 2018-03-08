package com.hanwha.hwgi.ep.batch.processor;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.batch.item.ItemProcessor;

import com.hanwha.hwgi.ep.batch.vo.User;

public class UserItemProcessor implements ItemProcessor<User, User> {

    private static final Logger log = LoggerFactory.getLogger(UserItemProcessor.class);

    @Override
    public User process(final User user) throws Exception {
        final String stfno = user.getStfno().toUpperCase();
        final String nm = user.getNm().toUpperCase();
        final User transformedPerson = new User(stfno, nm);

        log.info("Converting (" + user + ") into (" + transformedPerson + ")");

        return transformedPerson;
    }

	

}
