package com.syl.msp.login.dao;

import java.util.List;

import org.hibernate.Query;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.syl.msp.login.entity.MspResource;
import com.syl.msp.login.entity.MspUserDetail;

@Repository("MspUserDetailDao")
public class MspUserDetailDaoImpl implements MspUserDetailDao {

	@Autowired
	private SessionFactory sessionFactory;

	public SessionFactory getSessionFactory() {
		return sessionFactory;
	}

	public void setSessionFactory(SessionFactory sessionFactory) {
		this.sessionFactory = sessionFactory;
	}

	@Override
	public MspUserDetail findByUsername(String username) throws Exception {
		MspUserDetail mud = sessionFactory.getCurrentSession().get(
				MspUserDetail.class, username);
		return mud;
	}

	@Override
	public void addUser(MspUserDetail mud) throws Exception {
		if (this.findByUsername(mud.getUsername()) != null) {
		} else {
			sessionFactory.getCurrentSession().save(mud);
		}
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<MspResource> getResoucesByRole(String role) throws Exception {
		Query query = sessionFactory.getCurrentSession().getNamedQuery(
				"getResoucesByRole");
		query.setParameter("role", role);
		return query.list();
	}

	@Override
	public void updatePassword(MspUserDetail mud) throws Exception {
		Query query = sessionFactory
				.getCurrentSession()
				.createQuery(
						"update MspUserDetail set password = :password where username = :username");
		query.setParameter("password", mud.getPassword());
		query.setParameter("username", mud.getUsername());
		query.executeUpdate();
	}
}
