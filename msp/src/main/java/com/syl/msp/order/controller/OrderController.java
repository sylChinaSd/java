package com.syl.msp.order.controller;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.syl.msp.order.entity.Order;
import com.syl.msp.order.service.OrderService;

@Controller
public class OrderController {

	@Autowired
	OrderService orderService;

	@RequestMapping("/order/create")
	@ResponseBody
	public Map<String, Object> addOrder(@RequestBody Order order)
			throws Exception {
		Map<String, Object> map = orderService.addOrder(order);
		return map;
	}

	@RequestMapping(value = "/order/easycreate", produces = "text/html;charset=utf-8")
	@ResponseBody
	public String addEasyOrder(@RequestBody Order order) throws Exception {
		String form = orderService.addEasyOrder(order);
		return form;
	}

	@RequestMapping("/order/query")
	@ResponseBody
	public Map<String, Object> orderDetailQuery(Order order) throws Exception {
		Map<String, Object> map = orderService.orderQuey(order);
		return map;
	}

	@RequestMapping("/order/query/page")
	@ResponseBody
	public Map<String, Object> orderQueryPage(Order order) throws Exception {
		Map<String, Object> map = orderService.orderQueryPage(order);
		return map;
	}

	@RequestMapping("/order/manage/query")
	@ResponseBody
	public Map<String, Object> orderManageDetailQuery(Order order)
			throws Exception {
		Map<String, Object> map = orderService.orderManageDetailQuery(order);
		return map;
	}

	@RequestMapping("/order/manage/query/page")
	@ResponseBody
	public Map<String, Object> orderManageQueryPage(Order order)
			throws Exception {
		Map<String, Object> map = orderService.orderManageQueryPage(order);
		return map;
	}

	@RequestMapping("/order/manage/analysis")
	@ResponseBody
	public Map<String, Object> orderManageAnalysis(Order order)
			throws Exception {
		Map<String, Object> map = orderService.orderManageAnalysis(order);
		return map;
	}

	@RequestMapping(value = "/order/pay", produces = "text/html;charset=utf-8")
	@ResponseBody
	public String orderPay(@RequestBody Order order) throws Exception {
		String form = orderService.orderPay(order);
		return form;
	}

	/**
	 * 支付宝异步通知
	 * 
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/order/alipay", method = RequestMethod.POST)
	@ResponseBody
	public String orderAlipaySync(HttpServletRequest request) throws Exception {
		String status = orderService.orderAlipaySync(request);
		return status;
	}

}
