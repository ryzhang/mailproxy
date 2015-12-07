package com.sap.sme.occ.app.marketing.dao;

import com.sap.sme.occ.app.marketing.bo.EmailAccount;

public interface EmailDAO {

	public void createEmailAccount(EmailAccount account);
	
	public boolean isEmailAccountExisted(String email);
	
	public EmailAccount getEmailAccount(String email);
	
}
