package com.syl.msp.pay.service;

import java.io.IOException;
import java.math.BigDecimal;
import java.text.DecimalFormat;

import com.alipay.api.AlipayApiException;
import com.alipay.api.AlipayClient;
import com.alipay.api.DefaultAlipayClient;
import com.alipay.api.request.AlipayTradeQueryRequest;
import com.alipay.api.request.AlipayTradeWapPayRequest;
import com.alipay.api.response.AlipayTradeQueryResponse;
import com.syl.msp.order.entity.Order;
import com.syl.msp.pay.entity.AlipayOrderQuery;

public class AlipayService {
	public static final String SUCCESS = "success";
	public static final String FAILURE = "failure";

	private DecimalFormat df = new DecimalFormat("#.##");
	// 支付宝客户端需要的参数
	private String serverUrl = "https://openapi.alipaydev.com/gateway.do";
	private String appId = "2016080300157751";
	private String privateKey = "MIIEvQIBADANBgkqhkiG9w0BAQEFAASCBKcwggSjAgEAAoIBAQCQY9HiHZ6IewulTmCJsbH7QJMomSecxziTUiX1fehJH8WCi83ZoCjRtQc2c4yRGriw+Udj1c0Ar11nY9dtI2Ze2Ow3iH3ZtOeAqpqHf/iag/Izs+vOYxG6qmlv2D00rtH7Qvsv3EbAVZk0iWtNUeGNVUZjcmnVorj/kPeMDM294mpIhLMAXNl9qsJR+A3jSZynBqftrxe81jl80IyQs/VW9w+n5B3uFYOA10aYueUMjuu+wv7NbB4U2ITIb/h24bu0EqrfcMarswqY3Hby3b5FH5vxFi/0G3K7Hf/wF3GWyGpcFox6dDaneNuQ/gtVwKJKwc8PnffTMA5byRf0gPM9AgMBAAECggEAIkpSceyu1aLDJULz0j+NESpIyH921NjLKtF7ik2YeKU+9r3U6TYL+6GOUPQoEd3SttS8i40e3mbHO+KOKrEZgrTphFa57JX11KyvfjZ6B9gGERlNivokH7yQxasI/hX2cEF7/eXGN68cb0mjXf1wH2x9BukJEV9PqAH5iB0ST2GzSw5aEk9kMBL4cqTiKGeDtWmwHScqQbTUKjGdaqmgQyVHb6M0sUF4BmDY1tT1+w/+d32so98PmOzZ2sZBP/HT2DlWqG1IhnUZBC7IJn65ZxAf4Kv8qg/QqNRqH7FCCBvskTTdcVULdyohRNr8yWs0Ir5eEhAtnguNpafCpGGSgQKBgQD7xsHI71zRZlX0ZvmPQXZVO2Z9C9WYuotU5fxfEhP9sQopkIOCqGe191pqwWANdM2Iuo4xKV/Kb4K8OfQhN9PWtQ1b7AZj0uq/LLjJuNDSOZRwMfsaVEvpfmHN0fTiqHBd79Pa2Ms206IYq42RtaV0Yb0mDKqnJT5efBKb6uCFZQKBgQCSz+Vv4NDRKbOJmTETgAvLDoF7oQ40tuiy/1gqInb/ARbEMWNMcdsty2XYGKdVMOOCFEHk1xu7I2MHIbny6K71effW2Y9Yll7nMuckSnOPgs0MBXDKY6sKxdRsvMWQjal9LFojar98w8Jbv+7m5pIKnPx9DghO7qo9tBGrJJkk+QKBgQDXVWdZpjaZNVAgZo3cUt2VCeee3SdCp5EQ/F47JWgJmogZgmqnrcZ9n98p3Nd8ysAax4tjSoKNzY9f0qUUX/4C7PhO/O9+It74wJQOms2mbu6ggm7DVwNR4PBfKsmprOvlu39S9gXfj2NcfCTpURUAE7PFPiIaMEVGZBE6XsjF7QKBgG+fKW6am6mr7xlbI/kTjePhL4lztn1V5C7T/ZBri9XuWgTpF/pzRpXW46QdenxHI3MvO7pl4muySASQG/UX/I4fIdR0rYxJJ/bYuOzavmoKpSPSryLyDIWwTAYwsHVrf8XwBsiOzPBrVkaz80wTWk4/dakPFjoNM9qeIiDhPp7hAoGARBSIB6xAmJu709vbTSCOMPhLxwz9yfVrf/Kg7cYDzyCJ07zTaAPJZ527zac9q5V78f2H77NTbDWLC2CJO4C4u1S5DtuLdV/j8vveD+lIju6/AI58QLgMwCSRBhFZlIPTtsABqoKjIYfkZLUi4GAucXHOOxSnafmTYhx2EfPXEjQ=";
	private String format = "json";
	private String charset = "utf-8";
	private String alipayPulicKey = "MIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEA5IVlRIqJWx8ruFfu/fDuLkBce5b2LL+cI3JKRpNfqtrCrDdkQp2SNoKevT8vDzSpEXC86XwbO9O1F7T1YOXnCGLMbxpnwi3uLjFRB4RfgWER+RvJXn6S5geu+8+WWi7rYmZ+BqzRe9FSa3SpGLjazrTE55mJ87AZ5v3Z3QfLXRHrf6sVxKRKj4PqVolQ4FrEbzx1P6nbAiOfPT5QQJgJJcrJu0AEqtoM19sNTjKa28U1iAOXkrMbaLRh17WNPNvRFG37kqMXaAPMZ5PW0VDtYyylDLmPPt8fDBEJgEhA7ybRepmzgVrocwZoaqHaHY9eTTY5GJJS87hsVs2yi6m1YQIDAQAB";
	private String signType = "RSA2";
	private String sellerId = "2088102169769676";
	// 回调网址
	private String returnUrl = "";
	private String notifyUrl = "";

	// 支付宝客户端
	private AlipayClient alipayClient = null;

	public AlipayService() {
		alipayClient = null;
	}

	public AlipayService(String serverUrl, String appId, String privateKey,
			String format, String charset, String alipayPulicKey,
			String signType) {
		this.serverUrl = serverUrl;
		this.appId = appId;
		this.privateKey = privateKey;
		this.format = format;
		this.charset = charset;
		this.alipayPulicKey = alipayPulicKey;
		this.signType = signType;

		alipayClient = new DefaultAlipayClient(serverUrl, appId, privateKey,
				format, charset, alipayPulicKey, signType);

	}

	// 支付
	public String pay(Order order) throws AlipayApiException, IOException {
		// 创建API对应的request
		alipayClient = getAlipayClient();
		AlipayTradeWapPayRequest alipayRequest = new AlipayTradeWapPayRequest();
		// 在公共参数中设置回跳和通知地址
		alipayRequest.setReturnUrl(returnUrl);
		alipayRequest.setNotifyUrl(notifyUrl);
		// 填充业务参数
		StringBuffer sb = new StringBuffer();
		sb.append("{");
		// 订单号
		sb.append("\"out_trade_no\":\"");
		sb.append(order.getOrderNo());
		sb.append("\",");
		// 订单标题
		sb.append("\"subject\":\"");
		sb.append(order.generatePaySubject());
		sb.append("\",");
		// 订单描述
		sb.append("\"body\":\"");
		sb.append(order.generatePayBody());
		sb.append("\",");
		// 总金额
		sb.append("\"total_amount\":\"");
		sb.append(df.format(order.getAmount()));
		sb.append("\",");
		// 卖家id
		sb.append("\"seller_id\":\"");
		sb.append(sellerId);
		sb.append("\",");
		// 销售产品码，商家和支付宝签约的产品码
		sb.append("\"product_code\":\"QUICK_WAP_PAY\"");
		sb.append("}");
		alipayRequest.setBizContent(sb.toString());

		// 调用SDK生成表单
		String form = alipayClient.pageExecute(alipayRequest).getBody();
		return form;
		/*
		 * response.setContentType("text/html;charset=" + charset); //
		 * 直接将完整的表单html输出到页面 response.getWriter().write(form);
		 * response.getWriter().flush();
		 */
	}

	/**
	 * 支付宝订单状态查询
	 * 
	 * @param order
	 * @return
	 * @throws AlipayApiException
	 */
	public AlipayOrderQuery query(Order order) throws AlipayApiException {
		// 创建API对应的request
		AlipayOrderQuery aoq = new AlipayOrderQuery();
		alipayClient = getAlipayClient();
		AlipayTradeQueryRequest request = new AlipayTradeQueryRequest();
		request.setBizContent("{" + "    \"out_trade_no\":\""
				+ order.getOrderNo() + "\"" + "  }");

		AlipayTradeQueryResponse response = alipayClient.execute(request);
		aoq.setTradeNo(response.getTradeNo());
		aoq.setOutTradeNo(response.getOutTradeNo());

		aoq.setCode(response.getCode());
		aoq.setSubCode(response.getSubCode());
		aoq.setMsg(response.getMsg());
		aoq.setSubMsg(response.getSubMsg());
		/* System.out.println(response.getBody()); */

		if (response.isSuccess()) {
			aoq.setTradeStatus(response.getTradeStatus());
			aoq.setTotalAmount(new BigDecimal(response.getTotalAmount()));
		} else {
			aoq.setTradeStatus(AlipayOrderQuery.QUERY_FAILED);
		}
		return aoq;
	}

	// getter and setter
	public String getServerUrl() {
		return serverUrl;
	}

	public void setServerUrl(String serverUrl) {
		this.serverUrl = serverUrl;
	}

	public String getAppId() {
		return appId;
	}

	public void setAppId(String appId) {
		this.appId = appId;
	}

	public String getPrivateKey() {
		return privateKey;
	}

	public void setPrivateKey(String privateKey) {
		this.privateKey = privateKey;
	}

	public String getFormat() {
		return format;
	}

	public void setFormat(String format) {
		this.format = format;
	}

	public String getCharset() {
		return charset;
	}

	public void setCharset(String charset) {
		this.charset = charset;
	}

	public String getAlipayPulicKey() {
		return alipayPulicKey;
	}

	public void setAlipayPulicKey(String alipayPulicKey) {
		this.alipayPulicKey = alipayPulicKey;
	}

	public String getSignType() {
		return signType;
	}

	public void setSignType(String signType) {
		this.signType = signType;
	}

	public AlipayClient getAlipayClient() {
		return alipayClient;
	}

	public void setAlipayClient(AlipayClient alipayClient) {
		this.alipayClient = alipayClient;
	}

	public String getReturnUrl() {
		return returnUrl;
	}

	public void setReturnUrl(String returnUrl) {
		this.returnUrl = returnUrl;
	}

	public String getNotifyUrl() {
		return notifyUrl;
	}

	public void setNotifyUrl(String notifyUrl) {
		this.notifyUrl = notifyUrl;
	}

	public String getSellerId() {
		return sellerId;
	}

	public void setSellerId(String sellerId) {
		this.sellerId = sellerId;
	}


}
