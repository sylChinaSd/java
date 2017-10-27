package com.syl.msp.address.dao;

import java.util.List;

import org.hibernate.Query;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.syl.msp.address.entity.Address;

@Repository("AddressDao")
public class AddressDaoImpl implements AddressDao {
	@Autowired
	private SessionFactory sessionFactory;

	public SessionFactory getSessionFactory() {
		return sessionFactory;
	}

	public void setSessionFactory(SessionFactory sessionFactory) {
		this.sessionFactory = sessionFactory;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Address> addressQueryAll(String username) throws Exception {
		Query query = sessionFactory.getCurrentSession().getNamedQuery(
				"addressQueryAll");
		query.setParameter("username", username);
		List<Address> lists = query.list();
		return lists;
	}

	@Override
	public void addressCreate(Address addr) throws Exception {
		sessionFactory.getCurrentSession().save(addr);
	}

	@Override
	public void addressUpdate(Address addr) throws Exception {
		sessionFactory.getCurrentSession().update(addr);
	}

	@Override
	public void addressDelete(Address addr) throws Exception {
		sessionFactory.getCurrentSession().delete(addr);
		/*
		 * Query query = sessionFactory.getCurrentSession().getNamedQuery(
		 * "addressDelete"); query.setParameter("username", addr.getUsername());
		 * query.setParameter("id", addr.getId()); query.executeUpdate();
		 */
	}

	@Override
	public Address findAddressByIdAndUsername(Address addr) throws Exception {
		Query query = sessionFactory.getCurrentSession().getNamedQuery(
				"findAddressByIdAndUsername");
		query.setParameter("username", addr.getUsername());
		query.setParameter("id", addr.getId());
		return (Address) query.uniqueResult();
	}

}
