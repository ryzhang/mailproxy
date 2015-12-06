package com.sap.sme.occ.app.marketing.bo;

import org.apache.commons.lang3.tuple.Pair;

public class EmailMessage {
	private String id;
	
	private String account_id;
	
	private String thread_id;
	
	private String subject;
	
	private Pair<String, String> from;
	
	private Pair<String, String> to;
	
	private Pair<String, String> cc;
	
	private Pair<String, String> bcc;

}
