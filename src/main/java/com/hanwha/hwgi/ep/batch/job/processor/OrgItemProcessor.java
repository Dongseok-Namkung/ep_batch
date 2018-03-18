package com.hanwha.hwgi.ep.batch.job.processor;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.item.ItemProcessor;

import com.hanwha.hwgi.ep.batch.vo.OrgVO;

public class OrgItemProcessor implements ItemProcessor<OrgVO, OrgVO> {

    private static final Logger log = LoggerFactory.getLogger(OrgItemProcessor.class);

    @Override
    public OrgVO process(final OrgVO orgVO) throws Exception {
        final String orgcd = orgVO.getOrgcd().toUpperCase();
        final String orgnm = orgVO.getOrgnm().toUpperCase();
        final OrgVO transformedPerson = new OrgVO(orgcd, orgnm);

        log.info("Converting (" + orgVO + ") into (" + transformedPerson + ")");

        return transformedPerson;
    }

	

}
