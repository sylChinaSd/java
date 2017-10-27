package com.syl.msp.order.dao;

import java.util.List;

import com.syl.msp.order.entity.Order;
import com.syl.msp.order.entity.OrderDetail;

public interface OrderDao {

	public Integer getOrderNoSuffix() throws Exception;

	public void addOrder(Order order) throws Exception;

	public Order findByOrderNo(Order order) throws Exception;
	
	public Order findByOrderNoAndUsername(Order order) throws Exception;

	public List<Order> queryPageData(Order order) throws Exception;

	public Long queryPageCount(Order order) throws Exception;

	public List<Order> queryManagePageData(Order order) throws Exception;

	public Long queryManagePageCount(Order order) throws Exception;

	public List<OrderDetail> queryAnalysisDetail(Order order) throws Exception;
	
	public void updateStatusByNo(Order order) throws Exception;

}
