package com.sap.sme.occ.app.marketing.service.impl;

import java.io.IOException;
import java.io.InputStream;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.Map;

import org.apache.commons.codec.Charsets;
import org.apache.commons.codec.binary.Base64;
import org.apache.commons.io.IOUtils;
import org.apache.http.HttpHost;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.impl.conn.DefaultProxyRoutePlanner;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.google.gson.Gson;
import com.sap.sme.occ.app.marketing.bo.EmailAccount;
import com.sap.sme.occ.app.marketing.dao.EmailDAO;
import com.sap.sme.occ.app.marketing.service.MailService;

@Service("prototype")
public class MailServiceImpl implements MailService {
	
	private final static String TEST_APP_ID = "4mowy3rd0vbifrnmuz0h41mrx";
	private final static String TEST_APP_SECRET = "duiudj39664b6omhllait6ogb";
	

	private HttpClient client;
	
	@Autowired
	private EmailDAO emailDAO;
	
	@Override
	public String getNylasAppID() {
		return TEST_APP_ID;
	}

	public MailServiceImpl(){
		boolean useProxy = Boolean.parseBoolean(System.getenv("USE_PROXY"));
		if(useProxy){
			String host = System.getenv("HTTP_PROXY_HOST");
			int port = Integer.parseInt(System.getenv("HTTP_PROXY_PORT"));
			HttpHost proxy = new HttpHost(host, port);
			DefaultProxyRoutePlanner routePlanner = new DefaultProxyRoutePlanner(proxy);
			client = HttpClients.custom()
	                .setRoutePlanner(routePlanner)
	                .build();
		} else {
			client = HttpClients.createDefault();
		}
	}
	
	/**
	 *  Nylas will redirect the url to this service after user have inputed the user/password account into the mail account
	 *  
	 *  
	 */
	@Override
	public boolean registerEmailAccount(String code, String state) {
		// send the request with appid, appsecret, grant_type, code, 
		// nylas will return the response with information including email, provider, nylasID, token;
	
		try{
			URI tokenAccessURI = new URIBuilder().setPath(NylasServiceInfo.TOKEN_RETRIEVAL_URL)
								.setParameter("client_id", TEST_APP_ID)
								.setParameter("client_secret", TEST_APP_SECRET)
								.setParameter("grant_type", "authorization_code")
								.setParameter("code", code)
								.build();
		
			HttpGet request = new HttpGet(tokenAccessURI);
			HttpResponse response = client.execute(request);
			InputStream respStream = response.getEntity().getContent();
			String respbody = IOUtils.toString(respStream, Charsets.UTF_8);			
			Map<String, String> emailAccountInfo = new Gson().fromJson(respbody, Map.class);
			
			String token = emailAccountInfo.get("access_token");
			String email = emailAccountInfo.get("email_address");
			
			EmailAccount emailAccount = getEmailAccountFromNylas(token, email);
			System.out.println(emailAccount);
			
			emailDAO.createEmailAccount(emailAccount);
			return true;
			
			
		} catch (ClientProtocolException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (URISyntaxException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			
		}		
		return false;
	}

	@Override
	public boolean isEmailAccountExsits(String emailAddress) {
		return emailDAO.isEmailAccountExisted(emailAddress);
	}

	@Override
	public EmailAccount getEmailAccount(String emailAddress)
	{
		EmailAccount account = emailDAO.getEmailAccount(emailAddress);
		return account;
	}
	
	public EmailAccount getEmailAccountFromNylas(String token, String emailAddress) {

		try {
			String credential = token + ":";
			HttpGet getRequest = new HttpGet(new URI(NylasServiceInfo.ACCOUNT_RETRIVAL_URL));
			byte[] bas64EncodedToken = Base64.encodeBase64(credential.getBytes("UTF-8"));
			getRequest.addHeader("Authorization", "Basic " + new String(bas64EncodedToken));
			HttpResponse response = client.execute(getRequest);
			InputStream respStream = response.getEntity().getContent();
			String respbody = IOUtils.toString(respStream, Charsets.UTF_8);	
			Map<String, String> emailAccountInfo = new Gson().fromJson(respbody, Map.class);
			String nylasId = emailAccountInfo.get("id");
			String name = emailAccountInfo.get("name");
		    String email = emailAccountInfo.get("email_address");
		    String provider = emailAccountInfo.get("provider");
		    String organizationUnit = emailAccountInfo.get("organization_unit");
		    String sync_state = emailAccountInfo.get("sync_state");
		    
		    System.out.println(String.format("nylas id = %s, name=%s, email=%s, provider=%s, ou=%s",  
		    		nylasId, name, email, provider, organizationUnit));
		    
		    EmailAccount account = new EmailAccount();
		    account.setEmailAddress(email);
		    account.setNylasID(nylasId);
		    account.setOauthToken(token);
		    account.setProvider(provider);
		    account.setOrganizationUnit(organizationUnit);
		    account.setUserName(name);
		    account.setSync_state(sync_state);
		    return account;
			
		} catch (URISyntaxException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ClientProtocolException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return new EmailAccount();
	}

	
}
