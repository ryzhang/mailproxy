package com.sap.sme.occ.app.marketing.dao.impl;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.sap.sme.occ.app.marketing.bo.EmailAccount;
import com.sap.sme.occ.app.marketing.dao.EmailDAO;

@Repository
public class EmailDAOImpl implements EmailDAO {

	@PersistenceContext
	private EntityManager em;
	
	@Override
	@Transactional
	public void createEmailAccount(EmailAccount account) {
		em.merge(account);
	}
	@Override
	@Transactional
	public boolean isEmailAccountExisted(String email)
	{
	    Query query = em.createQuery("select emailAccount from EmailAccount emailAccount where emailAccount.emailAddress=:email");	
	    query.setParameter("email", email);
	    List<EmailAccount> accounts = query.getResultList();
	    return accounts.size() > 0;
	}
	
	public EmailAccount getEmailAccount(String email)
	{
	    Query query = em.createQuery("select emailAccount from EmailAccount emailAccount where emailAccount.emailAddress=:email");	
	    query.setParameter("email", email);
	    List<EmailAccount> accounts = query.getResultList();
	    if(accounts.size()>0) return accounts.get(0);
	    else return null;
	}

}
