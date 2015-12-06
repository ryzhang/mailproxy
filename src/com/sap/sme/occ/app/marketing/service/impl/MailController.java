package com.sap.sme.occ.app.marketing.service.impl;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.google.gson.Gson;
import com.sap.sme.occ.app.marketing.bo.EmailAccount;
import com.sap.sme.occ.app.marketing.service.MailService;

@Controller
public class MailController {

	@Autowired
	private MailService mailService;
	
	@RequestMapping(value="/registerEmailAccount", method=RequestMethod.GET)
	public  ResponseEntity<Boolean> registerEmailAccount(HttpServletRequest request, HttpServletResponse response)
	{
		String code = request.getParameter("code");
		String state = request.getParameter("state");
		boolean result = mailService.registerEmailAccount(code, state);
		HttpStatus status = result? HttpStatus.OK : HttpStatus.BAD_REQUEST;
		ResponseEntity<Boolean> respEntity = new ResponseEntity<Boolean>(status);
		return respEntity;		
	}
	
	
	@RequestMapping(value="/sayHello", method=RequestMethod.GET)
	public ResponseEntity<String> sayHello(HttpServletRequest request, HttpServletResponse response)
	{
		ResponseEntity<String> respEntity = new ResponseEntity<String>("HelloWorld", HttpStatus.OK);
		return respEntity;
	}
	
	@RequestMapping(value="/account", method=RequestMethod.GET)
	public ResponseEntity<String> retriveAccountInfo(HttpServletRequest request, HttpServletResponse response)
	{
		String emailAddress = request.getParameter("email");
		EmailAccount account = mailService.getEmailAccount(emailAddress);
		String respBody = new Gson().toJson(account);
		HttpHeaders responseHeaders = new HttpHeaders();
		responseHeaders.setContentType(MediaType.APPLICATION_JSON_UTF8);
		ResponseEntity<String> respEntity = new ResponseEntity<String>(respBody, HttpStatus.OK);
		return respEntity;
	}
	
	@RequestMapping(value="/messages", method=RequestMethod.GET)
	public ResponseEntity<String> retriveMessages(HttpServletRequest request, HttpServletResponse response)
	{
		
		
		
	}

}
