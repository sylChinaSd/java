package com.syl.msp.poduct.dao;

import java.util.List;
import java.util.Map;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.syl.msp.poduct.entity.Product;
import com.syl.msp.utils.common.CommonUtil;

@Repository("ProductDao")
public class ProductDaoImpl implements ProductDao {

	@Autowired
	private SessionFactory sessionFactory;

	public SessionFactory getSessionFactory() {
		return sessionFactory;
	}

	public void setSessionFactory(SessionFactory sessionFactory) {
		this.sessionFactory = sessionFactory;
	}

	@Override
	public Product findById(int i) {
		Product pro = sessionFactory.getCurrentSession().get(Product.class, i);
		return pro;
	}

	@Override
	public void saveById(Product pro) {
		sessionFactory.getCurrentSession().save(pro);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Product> queryPage(Product p) {
		Map<String, Object> map = CommonUtil.getSqlMap();
		int start = (p.getPage() - 1) * p.getPageSize();
		String sql = "select p from Product p ";
		if (p.getPname() != null) {
			sql += " where p.pname like :pname ";
			map.put("pname", p.getPname());
		}
		sql += " order by cTime desc";
		Query query = sessionFactory.getCurrentSession().createQuery(sql);
		query.setFirstResult(start);
		query.setMaxResults(p.getPageSize());
		// 设置参数
		String[] params = query.getNamedParameters();
		for (String param : params) {
			query.setParameter(param, map.get(param));
		}
		return query.list();
	}

	@Override
	public Long getTotalCount(Product p) {
		Session session = sessionFactory.getCurrentSession();
		Long count = 0L;
		if (p.getPname() != null) {
			count = (Long) session
					.createQuery(
							"select count(1) from Product p where p.pname like :pname")
					.setParameter("pname", p.getPname()).uniqueResult();
		} else {
			count = (Long) session.createQuery("select count(1) from Product")
					.uniqueResult();
		}

		return count;
	}

	@Override
	public void updateById(Product pro) {
		sessionFactory.getCurrentSession().update(pro);
	}

}
