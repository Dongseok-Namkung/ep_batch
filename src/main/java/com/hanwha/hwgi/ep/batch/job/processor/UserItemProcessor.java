package com.hanwha.hwgi.ep.batch.job.processor;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.batch.item.ItemProcessor;

import com.hanwha.hwgi.ep.batch.vo.UserVO;

public class UserItemProcessor implements ItemProcessor<UserVO, UserVO> {

    private static final Logger log = LoggerFactory.getLogger(UserItemProcessor.class);

    @Override
    public UserVO process(final UserVO user) throws Exception {
        final String stfno = user.getStfno().toUpperCase();
        final String nm = user.getNm().toUpperCase();
        final UserVO transformedPerson = new UserVO(stfno, nm);

        log.info("Converting (" + user + ") into (" + transformedPerson + ")");

        return transformedPerson;
    }

	

}
