package com.syl.msp.order.service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.alipay.api.internal.util.AlipaySignature;
import com.syl.msp.order.dao.OrderDao;
import com.syl.msp.order.entity.Order;
import com.syl.msp.order.entity.OrderDetail;
import com.syl.msp.pay.entity.AlipayOrderQuery;
import com.syl.msp.pay.service.AlipayService;
import com.syl.msp.poduct.dao.ProductDao;
import com.syl.msp.poduct.entity.Product;
import com.syl.msp.shoppingcart.dao.ShoppingCartDao;
import com.syl.msp.shoppingcart.entity.ShoppingCart;
import com.syl.msp.utils.common.CommonUtil;

@Service
public class OrderService {

	@Autowired
	OrderDao orderDao;
	@Autowired
	ProductDao productDao;
	@Autowired
	ShoppingCartDao scDao;

	@Autowired
	AlipayService alipayService;

	@Transactional(rollbackFor = Exception.class)
	public Map<String, Object> addOrder(Order order) throws Exception {
		Map<String, Object> map = CommonUtil.getResponseMap();
		generateOrderNoSuffix(order);
		String orderNo = CommonUtil.formatOrderNo(order.getOrderNoSuffix());
		order.setOrderNo(orderNo);
		String username = CommonUtil.getCurrentUsername();
		order.setUsername(username);

		// 删除购物车信息
		List<ShoppingCart> items = order.getItems();
		int delRecords = 0;
		if (items != null) {
			for (ShoppingCart sc : items) {
				delRecords += scDao.deleteByIdAndUsername(sc.getId(), username);
			}
		}
		if (delRecords > 0) {
			// 配置订单
			configOrderDetail(order);
			configOrderBase(order);
			// 添加订单
			orderDao.addOrder(order);
			// 更改商品库存量
			List<OrderDetail> details = order.getDetails();
			if (details != null) {
				for (OrderDetail detail : details) {
					Product p = productDao.findById(detail.getProductId());
					if (p != null && p.getStock() >= detail.getPcount()) {
						p.setStock(p.getStock() - detail.getPcount());
						productDao.updateById(p);
					} else {
						String msg = String.format("商品[%s]库存不足！",
								detail.getPname());
						map.put("success", true);
						map.put("msg", msg);
						throw new Exception(msg);
					}
				}
			}

			map.put("success", true);
			map.put("orderNo", order.getOrderNo());
		} else {
			map.put("success", false);
			map.put("msg", "购物车中没有该商品!");
			throw new Exception("购物车中没有该商品!");
		}
		return map;
	}

	/**
	 * 生成订单尾号
	 * 
	 * @param order
	 * @throws Exception
	 */
	@Transactional(propagation = Propagation.REQUIRES_NEW)
	public void generateOrderNoSuffix(Order order) throws Exception {
		Integer orderNoSuffix = orderDao.getOrderNoSuffix();
		order.setOrderNoSuffix(orderNoSuffix);
	}

	/**
	 * 设置order明细
	 * 
	 * @param order
	 * @throws Exception
	 */
	private void configOrderDetail(Order order) throws Exception {
		List<ShoppingCart> items = order.getItems();
		if (items != null) {
			List<OrderDetail> details = new ArrayList<OrderDetail>();
			for (ShoppingCart sc : items) {
				Product p = productDao.findById(sc.getProductId());
				OrderDetail detail = new OrderDetail();
				detail.setOrderNo(order.getOrderNo());
				detail.setPcount(sc.getProductCount());
				detail.setPname(p.getPname());
				detail.setPrice(p.getDiscount().multiply(p.getPrice()));
				detail.setProductId(p.getId());
				detail.setUsername(order.getUsername());
				details.add(detail);
			}
			order.setDetails(details);
		}
	}

	/**
	 * 设置订单基本信息
	 * 
	 * @param order
	 * @throws Exception
	 */
	private void configOrderBase(Order order) throws Exception {
		order.setOrderState(0);
		List<OrderDetail> details = order.getDetails();
		if (details != null) {
			BigDecimal amount = new BigDecimal(0.0);
			for (OrderDetail detail : details) {
				BigDecimal pcount = new BigDecimal(detail.getPcount());
				amount = amount.add(detail.getPrice().multiply(pcount));
			}
			order.setAmount(amount);
		}
	}

	/**
	 * 查询订单
	 * 
	 * @param order
	 * @return
	 * @throws Exception
	 */
	@Transactional(propagation = Propagation.NOT_SUPPORTED, readOnly = true)
	public Map<String, Object> orderQuey(Order order) throws Exception {
		Map<String, Object> map = CommonUtil.getResponseMap();
		order.setUsername(CommonUtil.getCurrentUsername());
		Order result = orderDao.findByOrderNo(order);
		map.put("item", result);
		map.put("success", true);
		return map;
	}

	@Transactional(propagation = Propagation.NOT_SUPPORTED, readOnly = true)
	public Map<String, Object> orderQueryPage(Order order) throws Exception {
		Map<String, Object> map = CommonUtil.getResponseMap();
		String username = CommonUtil.getCurrentUsername();
		order.setUsername(username);
		if (order.getOrderState() == null || order.getOrderState() == -1) {
			order.setOrderState(null);
		}
		if (order.getOrderNo() != null) {
			order.setOrderNo("%" + order.getOrderNo() + "%");
		}
		order.setUsername(CommonUtil.getCurrentUsername());
		List<Order> results = orderDao.queryPageData(order);
		Long totalCount = orderDao.queryPageCount(order);
		map.put("items", results);
		map.put("totalCount", totalCount);
		map.put("success", true);
		return map;
	}

	@Transactional(propagation = Propagation.NOT_SUPPORTED, readOnly = true)
	public Map<String, Object> orderManageQueryPage(Order order)
			throws Exception {
		Map<String, Object> map = CommonUtil.getResponseMap();
		if (order.getOrderState() == null || order.getOrderState() == -1) {
			order.setOrderState(null);
		}
		if (order.getOrderNo() != null) {
			order.setOrderNo("%" + order.getOrderNo() + "%");
		}
		order.setUsername(CommonUtil.getCurrentUsername());
		List<Order> results = orderDao.queryManagePageData(order);
		Long totalCount = orderDao.queryManagePageCount(order);
		map.put("items", results);
		map.put("totalCount", totalCount);
		map.put("success", true);
		return map;
	}

	@Transactional(propagation = Propagation.NOT_SUPPORTED, readOnly = true)
	public Map<String, Object> orderManageDetailQuery(Order order)
			throws Exception {
		Map<String, Object> map = CommonUtil.getResponseMap();
		order.setUsername(CommonUtil.getCurrentUsername());
		Order result = orderDao.findByOrderNo(order);
		map.put("item", result);
		map.put("success", true);
		return map;
	}

	@Transactional(propagation = Propagation.NOT_SUPPORTED, readOnly = true)
	public Map<String, Object> orderManageAnalysis(Order order)
			throws Exception {
		Map<String, Object> map = CommonUtil.getResponseMap();
		/* order.setOrderState(Order.WeekDay.YWC.getState()); */
		order.setOrderState(Order.WeekDay.DCL.getState());
		order.setcTime(CommonUtil.getDayBreak(new Date()));
		List<OrderDetail> lists = orderDao.queryAnalysisDetail(order);

		map.put("items", lists);
		map.put("success", true);
		return map;
	}

	@Transactional(rollbackFor = Exception.class)
	public String addEasyOrder(Order order) throws Exception {
		Map<String, Object> map = CommonUtil.getResponseMap();
		generateOrderNoSuffix(order);
		String orderNo = CommonUtil.formatOrderNo(order.getOrderNoSuffix());
		order.setOrderNo(orderNo);
		String username = CommonUtil.getCurrentUsername();
		order.setUsername(username);
		// 配置订单
		configOrderDetail(order);
		configOrderBase(order);
		// 添加订单
		orderDao.addOrder(order);
		// 更改商品库存量
		List<OrderDetail> details = order.getDetails();
		if (details != null) {
			for (OrderDetail detail : details) {
				Product p = productDao.findById(detail.getProductId());
				if (p != null && p.getStock() >= detail.getPcount()) {
					p.setStock(p.getStock() - detail.getPcount());
					productDao.updateById(p);
				} else {
					String msg = String
							.format("商品[%s]库存不足！", detail.getPname());
					map.put("success", true);
					map.put("msg", msg);
					throw new Exception(msg);
				}
			}
		}

		String form = alipayService.pay(order);
		return form;
	}

	@Transactional(propagation = Propagation.NOT_SUPPORTED, readOnly = true)
	public String orderPay(Order order) throws Exception {
		String username = CommonUtil.getCurrentUsername();
		order.setUsername(username);
		order = orderDao.findByOrderNoAndUsername(order);
		String form = null;
		if (order != null) {
			// 从支付宝查询订单状态进行比对
			AlipayOrderQuery aoq = alipayService.query(order);
			System.out.println(aoq);
			String tradeStatus = aoq.getTradeStatus();
			if (AlipayOrderQuery.QUERY_FAILED.equals(tradeStatus)) {// 交易查询失败
				// 如果是订单不存在
				if ("ACQ.TRADE_NOT_EXIST".equals(aoq.getSubCode())) {
					form = alipayService.pay(order);
				} else {

				}
			} else if (AlipayOrderQuery.WAIT_BUYER_PAY.equals(tradeStatus)) {// 等支付
				form = alipayService.pay(order);
			} else if (AlipayOrderQuery.TRADE_CLOSED.equals(tradeStatus)) {// 交易关闭

			} else if (AlipayOrderQuery.TRADE_FINISHED.equals(tradeStatus)) {// 交易结束

			} else if (AlipayOrderQuery.TRADE_SUCCESS.equals(tradeStatus)) {// 支付成功

			}
		}
		return form;
	}

	@Transactional(rollbackFor = Exception.class)
	public String orderAlipaySync(HttpServletRequest request) throws Exception {
		String status = AlipayService.FAILURE;
		// 将异步通知中收到的所有参数都存放到map中
		Map<String, String> paramsMap = new HashMap<String, String>();
		Enumeration<String> paramNames = request.getParameterNames();
		while (paramNames.hasMoreElements()) {
			String key = paramNames.nextElement();// 调用nextElement方法获得元素
			paramsMap.put(key, request.getParameter(key));
		}
		boolean signVerified = false;
		try {
			signVerified = AlipaySignature.rsaCheckV1(paramsMap,
					alipayService.getAlipayPulicKey(), alipayService.getCharset(),
					alipayService.getSignType()); // 调用SDK验证签名
			/*signVerified = AlipaySignature.rsaCheckV1(paramsMap,
					alipayService.getAlipayPulicKey(), alipayService.getCharset(),
					"RSA2");*/
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		if (signVerified) {
			// 验签成功后，按照支付结果异步通知中的描述，对支付结果中的业务内容进行二次校验，校验成功后在response中返回success并继续商户自身业务处理，校验失败返回failure
			String outTradeNo = paramsMap.get("out_trade_no");
			String totalAmount = paramsMap.get("total_amount");
			String sellerId = paramsMap.get("seller_id");
			String appId = paramsMap.get("app_id");
			Order order = new Order();
			order.setOrderNo(outTradeNo);
			order = orderDao.findByOrderNo(order);
			
			System.out.println(paramsMap);
			
			// 1、商户需要验证该通知数据中的out_trade_no是否为商户系统中创建的订单号，
			if (outTradeNo != null && order != null
					&& outTradeNo.equals(order.getOrderNo())) {
				// 2、判断total_amount是否确实为该订单的实际金额（即商户订单创建时的金额），
				if (new BigDecimal(totalAmount).compareTo(order.getAmount()) == 0) {
					// 3、校验通知中的seller_id（或者seller_email)
					if (sellerId != null
							&& sellerId.equals(alipayService.getSellerId())) {
						// 是否为out_trade_no这笔单据的对应的操作方（有的时候，一个商户可能有多个seller_id/seller_email
						// 4、验证app_id是否为该商户本身。
						if (appId != null
								&& appId.equals(alipayService.getAppId())) {

							String tradeStatus = paramsMap.get("trade_status");
							//交易成功
							if (AlipayOrderQuery.TRADE_FINISHED
									.equals(tradeStatus)
									|| AlipayOrderQuery.TRADE_SUCCESS
											.equals(tradeStatus)) {
								//更新订单状态为正在出库
								order.setOrderState(Order.DFH);
								orderDao.updateStatusByNo(order);
							}else{
							}
							status = AlipayService.SUCCESS;
							// trade_status
						} else {
							status = AlipayService.FAILURE;
						}
					} else {
						status = AlipayService.FAILURE;
					}
				} else {
					status = AlipayService.FAILURE;
				}
				// 上述1、2、3、4有任何一个验证不通过，则表明本次通知是异常通知，务必忽略。
				// 在上述验证通过后商户必须根据支付宝不同类型的业务通知，正确的进行不同的业务处理，并且过滤重复的通知结果数据。
				// 在支付宝的业务通知中，只有交易通知状态为TRADE_SUCCESS或TRADE_FINISHED时，支付宝才会认定为买家付款成功。
			} else {
				status = AlipayService.FAILURE;
			}
		} else {
			// TODO 验签失败则记录异常日志，并在response中返回failure.
			status = AlipayService.FAILURE;
		}
		return status;
	}

}
