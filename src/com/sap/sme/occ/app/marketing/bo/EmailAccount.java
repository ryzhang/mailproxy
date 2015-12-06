package com.sap.sme.occ.app.marketing.bo;

import org.springframework.stereotype.Service;


public class EmailAccount {
	private int id;
	
	private String userName;
	
	private String emailAddress;
	
	private String oauthToken;
	
	private String provider;
	
	private String nylasID;
	
	private String organizationUnit;
	
	private String sync_state;
	
	
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getEmailAddress() {
		return emailAddress;
	}

	public void setEmailAddress(String emailAddress) {
		this.emailAddress = emailAddress;
	}

	public String getOauthToken() {
		return oauthToken;
	}

	public void setOauthToken(String oauthToken) {
		this.oauthToken = oauthToken;
	}

	public String getProvider() {
		return provider;
	}

	public void setProvider(String provider) {
		this.provider = provider;
	}

	public String getNylasID() {
		return nylasID;
	}

	public void setNylasID(String nylasID) {
		this.nylasID = nylasID;
	}
	
	
	public String getOrganizationUnit() {
		return organizationUnit;
	}

	public void setOrganizationUnit(String organizationUnit) {
		this.organizationUnit = organizationUnit;
	}

	
	public String getSync_state() {
		return sync_state;
	}

	public void setSync_state(String sync_state) {
		this.sync_state = sync_state;
	}

	@Override
	public String toString() {
		return "EmailAccount [id=" + id + ", userName=" + userName
				+ ", emailAddress=" + emailAddress + ", oauthToken="
				+ oauthToken + ", provider=" + provider + ", nylasID="
				+ nylasID + ", organizationUnit=" + organizationUnit
				+ ", sync_state=" + sync_state + "]";
	}



}
