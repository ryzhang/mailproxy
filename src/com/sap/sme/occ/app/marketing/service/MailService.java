package com.sap.sme.occ.app.marketing.service;

import com.sap.sme.occ.app.marketing.bo.EmailAccount;


public interface MailService {
	
	public String getNylasAppID();
	
	public boolean registerEmailAccount(String code, String state);
	
	public boolean isEmailAccountExsits(String emailAddress);
	
	public EmailAccount getEmailAccount(String emailAddress);
}
