package com.hanwha.hwgi.ep.batch.constants;

public class BatchQuery {
	
	public static String QUERY_SEL_USER = "SELECT STFNO, NM, ORGCD FROM SAM_STF ";
	
	public static String QUERY_PUT_USER =
            "INSERT INTO sam_stf_result (stfno, nm, orgcd) VALUES (:stfno, :nm, '2222222')";
	
	public static String QUERY_SEL_ORG =
            "SELECT " +
                "orgcd, " +
                "orgnm " +
            " FROM SAM_ORG ";
	
	public static String QUERY_PUT_ORG =
            "INSERT INTO sam_org_result (orgcd, orgnm) VALUES (:orgcd, :orgnm)";
	
}
