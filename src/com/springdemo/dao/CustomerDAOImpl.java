package com.springdemo.dao;

import java.util.List;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.springdemo.entity.Customer;

@Repository
public class CustomerDAOImpl implements CustomerDAO{

	@Autowired
	private SessionFactory sessionFactory;

	
	@Override
	public List<Customer> getCustomers() {
		Session session = sessionFactory.getCurrentSession();
		
		Query<Customer> theQuery = session.createQuery("from Customer order by lastName", Customer.class);
		
		List<Customer> customers = theQuery.getResultList();
	
		return customers;
	}


	@Override
	public void saveCustomer(Customer customer) {
		Session session = sessionFactory.getCurrentSession();
		session.saveOrUpdate(customer);
	}


	@Override
	public Customer getCustomer(int theId) {
		Session session = sessionFactory.getCurrentSession();
		Customer customer = session.get(Customer.class, theId);
		return customer;
	}


	@Override
	public void deleteCustomer(int theId) {
		Session session = sessionFactory.getCurrentSession();
		Customer customer = session.get(Customer.class, theId);
		session.delete(customer);
	}


	@Override
	public List<Customer> searchCustomer(String theSearchName) {
        Session currentSession = sessionFactory.getCurrentSession();
        Query theQuery = null;
        if (theSearchName != null && theSearchName.trim().length() > 0) {
            theQuery =currentSession.createQuery("from Customer where lower(firstName) like :theName or lower(lastName) like :theName", Customer.class);
            theQuery.setParameter("theName", "%" + theSearchName.toLowerCase() + "%");
        }else {
            theQuery =currentSession.createQuery("from Customer", Customer.class);            
        }
        List<Customer> customers = theQuery.getResultList();    
        return customers;
	}

}
