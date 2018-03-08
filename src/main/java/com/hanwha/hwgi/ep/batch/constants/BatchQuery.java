package com.hanwha.hwgi.ep.batch.constants;

public class BatchQuery {
	
	public static String QUERY_SEL_USER =
            "SELECT " +
                "stfno, " +
                "nm, " +
                "orgcd " +
            "FROM SAM_STF ";
	
	public static String QUERY_PUT_USER =
            "INSERT INTO sam_stf_result (stfno, nm, orgcd) VALUES (:stfno, :nm, '2222222')";
	
}
