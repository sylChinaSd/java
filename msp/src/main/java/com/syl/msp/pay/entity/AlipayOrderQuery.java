package com.syl.msp.pay.entity;

import java.math.BigDecimal;

/**
 * 支付宝订单查询实体类
 * 
 * @author SYL
 *
 */
public class AlipayOrderQuery {
	public static final String QUERY_FAILED = "QUERY_FAILED";
	public static final String WAIT_BUYER_PAY = "WAIT_BUYER_PAY";// 交易创建，等待买家付款
	public static final String TRADE_CLOSED = "TRADE_CLOSED";// 未付款交易超时关闭，或支付完成后全额退款
	public static final String TRADE_SUCCESS = "TRADE_SUCCESS";// 交易支付成功
	public static final String TRADE_FINISHED = "TRADE_FINISHED";// 交易结束，不可退款

	private String tradeNo;// 支付宝28位交易号
	private String outTradeNo;// 支付时传入的商户订单号
	private String tradeStatus;// 交易当前状态
	private BigDecimal totalAmount;// 订单总额
	private String code;// 请求结果代码
	private String subCode;// 请求结果子代码
	private String msg;// 请求结果描述
	private String subMsg;// 请求结果子描述

	public String getOutTradeNo() {
		return outTradeNo;
	}

	public void setOutTradeNo(String outTradeNo) {
		this.outTradeNo = outTradeNo;
	}

	public String getTradeStatus() {
		return tradeStatus;
	}

	public void setTradeStatus(String tradeStatus) {
		this.tradeStatus = tradeStatus;
	}

	public String getTradeNo() {
		return tradeNo;
	}

	public void setTradeNo(String tradeNo) {
		this.tradeNo = tradeNo;
	}

	public BigDecimal getTotalAmount() {
		return totalAmount;
	}

	public void setTotalAmount(BigDecimal totalAmount) {
		this.totalAmount = totalAmount;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getSubCode() {
		return subCode;
	}

	public void setSubCode(String subCode) {
		this.subCode = subCode;
	}

	public String getMsg() {
		return msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}

	public String getSubMsg() {
		return subMsg;
	}

	public void setSubMsg(String subMsg) {
		this.subMsg = subMsg;
	}

	@Override
	public String toString() {
		return "AlipayOrderQuery [tradeNo=" + tradeNo + ", outTradeNo="
				+ outTradeNo + ", tradeStatus=" + tradeStatus
				+ ", totalAmount=" + totalAmount + ", code=" + code
				+ ", subCode=" + subCode + ", msg=" + msg + ", subMsg="
				+ subMsg + "]";
	}

}
