package com.syl.msp.shoppingcart.dao;

import java.util.List;

import org.hibernate.Query;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.syl.msp.shoppingcart.entity.ShoppingCart;

@Repository("ShopingCartDao")
public class ShoppingCartDaoImpl implements ShoppingCartDao {
	@Autowired
	private SessionFactory sessionFactory;

	public SessionFactory getSessionFactory() {
		return sessionFactory;
	}

	public void setSessionFactory(SessionFactory sessionFactory) {
		this.sessionFactory = sessionFactory;
	}

	@Override
	public Long queryTotalCount(String username) throws Exception {
		Query query = sessionFactory.getCurrentSession().getNamedQuery(
				"queryTotalCount");
		query.setParameter("username", username);
		long result = ((Number)query.uniqueResult()).longValue();
		return result;
	}

	@Override
	public void save(ShoppingCart sc) throws Exception {
		/*Query query = sessionFactory.getCurrentSession().getNamedQuery("insertRecord");
		query.setParameter("username", sc.getUsername());
		query.setParameter("productId", sc.getProductId());
		query.setParameter("productCount", sc.getProductCount());
		query.executeUpdate();*/		
		sessionFactory.getCurrentSession().save(sc);
	}

	@Override
	public ShoppingCart findByUsernameAndPid(ShoppingCart sc) throws Exception {
		Query query = sessionFactory.getCurrentSession().getNamedQuery(
				"findByUsernameAndPid");
		query.setParameter("username", sc.getUsername());
		query.setParameter("productId", sc.getProductId());
		@SuppressWarnings("unchecked")
		List<ShoppingCart> lists = query.list();
		ShoppingCart result = (lists == null || lists.size() == 0) ? null
				: lists.get(0);
		return result;
	}

	@Override
	public List<ShoppingCart> queryPage(ShoppingCart sc) throws Exception {
		Query query = sessionFactory.getCurrentSession().getNamedQuery(
				"queryPage");
		query.setParameter("username", sc.getUsername());
		int start = (sc.getPage() - 1) * sc.getPageSize();
		query.setFirstResult(start);
		query.setMaxResults(sc.getPageSize());
		@SuppressWarnings("unchecked")
		List<ShoppingCart> lists = query.list();
		return lists;
	}

	@Override
	public int deleteByIdAndUsername(Integer id, String username)
			throws Exception {
		Query query = sessionFactory.getCurrentSession().getNamedQuery("deleteByIdAndUsername");
		query.setParameter("id", id);
		query.setParameter("username", username);
		return query.executeUpdate();
	}

}
