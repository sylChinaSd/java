package com.syl.msp.concern.dao;

import java.util.List;

import org.hibernate.Query;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.syl.msp.concern.entity.Concern;

@Repository("ConcernDao")
public class ConcernDaoImpl implements ConcernDao {
	@Autowired
	private SessionFactory sessionFactory;

	public SessionFactory getSessionFactory() {
		return sessionFactory;
	}

	public void setSessionFactory(SessionFactory sessionFactory) {
		this.sessionFactory = sessionFactory;
	}

	@Override
	public Concern findConcern(Concern concern) throws Exception {
		Query query = sessionFactory.getCurrentSession().getNamedQuery(
				"findConcern");
		query.setParameter("username", concern.getUsername());
		query.setParameter("productId", concern.getProductId());
		Concern result = (Concern) query.uniqueResult();
		return result;
	}

	@Override
	public void delete(Concern result) throws Exception {
		sessionFactory.getCurrentSession().delete(result);
	}

	@Override
	public void save(Concern concern) throws Exception {
		sessionFactory.getCurrentSession().save(concern);
	}

	@Override
	public void deleteByIdAndUsername(Concern c) throws Exception {
		Query query = sessionFactory.getCurrentSession().getNamedQuery(
				"deleteConcernByIdAndUsername");
		query.setParameter("username", c.getUsername());
		query.setParameter("id", c.getId());
		query.executeUpdate();
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Concern> queryPage(Concern concern) throws Exception {
		Query query = sessionFactory.getCurrentSession().getNamedQuery(
				"queryConcernPage");
		query.setParameter("username", concern.getUsername());
		query.setMaxResults(concern.getPageSize());
		query.setFirstResult((concern.getPage() - 1) * concern.getPageSize());
		List<Concern> lists = query.list();
		return lists;
	}

	@Override
	public Integer getTotalCount(Concern concern) throws Exception {
		Query query = sessionFactory.getCurrentSession().getNamedQuery(
				"getConcernTotalCount");
		query.setParameter("username", concern.getUsername());
		Long result = (Long) query.uniqueResult();
		return result.intValue();
	}

}
