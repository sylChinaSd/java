package com.syl.msp.order.dao;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.hibernate.Query;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.syl.msp.order.entity.Order;
import com.syl.msp.order.entity.OrderDetail;
import com.syl.msp.utils.common.CommonUtil;

@Repository("OrderDao")
public class OrderDaoImpl implements OrderDao {

	@Autowired
	private SessionFactory sessionFactory;

	public SessionFactory getSessionFactory() {
		return sessionFactory;
	}

	public void setSessionFactory(SessionFactory sessionFactory) {
		this.sessionFactory = sessionFactory;
	}

	@Override
	public Integer getOrderNoSuffix() throws Exception {
		Query query = sessionFactory.getCurrentSession().createSQLQuery(
				"select nextval(:seqName) from dual");
		query.setParameter("seqName", "seq_msp_order");
		Integer l = (Integer) query.uniqueResult();
		return l;
	}

	@Override
	public void addOrder(Order order) throws Exception {
		sessionFactory.getCurrentSession().save(order);
	}

	@SuppressWarnings("unchecked")
	@Override
	public Order findByOrderNoAndUsername(Order order) throws Exception {
		Query query = sessionFactory.getCurrentSession().getNamedQuery(
				"findByOrderNoAndUsername");
		query.setParameter("username", order.getUsername());
		query.setParameter("orderNo", order.getOrderNo());
		List<Order> orders = query.list();
		return orders == null ? null : (orders.size() == 0 ? null : orders
				.get(0));
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Order> queryPageData(Order order) throws Exception {
		Map<String, Object> map = CommonUtil.getSqlMap();
		String hql = "select o from Order o where o.username=:username and o.orderNo like :orderNo ";
		map.put("username", order.getUsername());
		map.put("orderNo", order.getOrderNo());
		if (order.getOrderState() != null) {
			hql += " and o.orderState=:orderState ";
			map.put("orderState", order.getOrderState());
		}
		hql += "order by o.cTime desc";
		Query query = sessionFactory.getCurrentSession().createQuery(hql);
		Set<String> keys = map.keySet();
		for (String key : keys) {
			query.setParameter(key, map.get(key));
		}
		// 分页
		query.setFirstResult((order.getPage() - 1) * order.getPageSize());
		query.setMaxResults(order.getPageSize());
		List<Order> orders = query.list();
		return orders;
	}

	@Override
	public Long queryPageCount(Order order) throws Exception {
		Map<String, Object> map = CommonUtil.getSqlMap();
		String hql = "select COUNT(1) from Order o where o.username=:username and o.orderNo like :orderNo ";
		map.put("username", order.getUsername());
		map.put("orderNo", order.getOrderNo());
		if (order.getOrderState() != null) {
			hql += " and o.orderState=:orderState ";
			map.put("orderState", order.getOrderState());
		}
		Query query = sessionFactory.getCurrentSession().createQuery(hql);
		Set<String> keys = map.keySet();
		for (String key : keys) {
			query.setParameter(key, map.get(key));
		}
		return (Long) query.uniqueResult();
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Order> queryManagePageData(Order order) throws Exception {
		Map<String, Object> map = CommonUtil.getSqlMap();
		String hql = "select o from Order o where o.orderNo like :orderNo ";
		map.put("orderNo", order.getOrderNo());
		if (order.getOrderState() != null) {
			hql += " and o.orderState=:orderState ";
			map.put("orderState", order.getOrderState());
		}
		hql += "order by o.cTime desc";
		Query query = sessionFactory.getCurrentSession().createQuery(hql);
		Set<String> keys = map.keySet();
		for (String key : keys) {
			query.setParameter(key, map.get(key));
		}
		// 分页
		query.setFirstResult((order.getPage() - 1) * order.getPageSize());
		query.setMaxResults(order.getPageSize());
		List<Order> orders = query.list();
		return orders;
	}

	@Override
	public Long queryManagePageCount(Order order) throws Exception {
		Map<String, Object> map = CommonUtil.getSqlMap();
		String hql = "select COUNT(1) from Order o where o.orderNo like :orderNo ";
		map.put("orderNo", order.getOrderNo());
		if (order.getOrderState() != null) {
			hql += " and o.orderState=:orderState ";
			map.put("orderState", order.getOrderState());
		}
		Query query = sessionFactory.getCurrentSession().createQuery(hql);
		Set<String> keys = map.keySet();
		for (String key : keys) {
			query.setParameter(key, map.get(key));
		}
		return (Long) query.uniqueResult();
	}

	@SuppressWarnings("unchecked")
	@Override
	public Order findByOrderNo(Order order) throws Exception {
		Query query = sessionFactory.getCurrentSession().getNamedQuery(
				"findByOrderNo");
		query.setParameter("orderNo", order.getOrderNo());
		List<Order> orders = query.list();
		return orders == null ? null : (orders.size() == 0 ? null : orders
				.get(0));
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<OrderDetail> queryAnalysisDetail(Order order) throws Exception {
		Query query = sessionFactory.getCurrentSession().getNamedQuery(
				"queryAnalysisDetail");
		/*query.setParameter("orderState", order.getOrderState());*/
		Date begDate = order.getcTime();
		Date endDate = CommonUtil.getNextDate(begDate);
		query.setParameter("begDate", begDate);
		query.setParameter("endDate", endDate);
		List<Object> lists = query.list();;
		List<OrderDetail> orders = new ArrayList<OrderDetail>();
		for(Object obj : lists){
			OrderDetail od = new OrderDetail();
			Object[] arr = (Object[])obj;
			od.setProductId((Integer)arr[0]);
			od.setPname((String)arr[1]);
			od.setPcount(((Long)arr[2]).intValue());
			od.setPrice((BigDecimal)arr[3]);
			orders.add(od);
		}
		return orders;
	}

	@Override
	public void updateStatusByNo(Order order) throws Exception {
		 Order origin = this.findByOrderNo(order);
		 origin.setOrderState(order.getOrderState());
		 sessionFactory.getCurrentSession().update(origin);
	}
}
