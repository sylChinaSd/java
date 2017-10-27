package com.syl.msp.order.entity;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.hibernate.annotations.NamedQueries;
import org.hibernate.annotations.NamedQuery;

import com.syl.msp.shoppingcart.entity.ShoppingCart;
import com.syl.msp.utils.common.BaseEntity;

@Entity
@Table(name = "msp_order")
@NamedQueries({
		@NamedQuery(name = "findByOrderNo", query = " select o from Order o "
				+ "where o.orderNo = :orderNo order by cTime desc"),
		@NamedQuery(name = "findByOrderNoAndUsername", query = " select o from Order o "
				+ "where o.username=:username and o.orderNo = :orderNo order by cTime desc") })
public class Order extends BaseEntity {

	/**
	 * 
	 */
	@Transient
	private static final long serialVersionUID = 1L;
	@Transient
	public static final Integer DCL = 0;
	@Transient
	public static final Integer DFH = 1;
	@Transient
	public static final Integer DSH = 2;
	@Transient
	public static final Integer YWC = 3;
	@Transient
	public static final Integer QT = 4;

	public static enum WeekDay {
		DCL(0), DFH(1), DSH(2), YWC(3), QT(4);
		Integer state;

		private WeekDay(Integer state) {
			this.state = state;
		}

		public Integer getState() {
			return this.state;
		}
	}

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	@Column(name = "order_no")
	private String orderNo;
	private String addr;
	private String contacts;
	@Column(name = "phone_no")
	private String phoneNo;
	private String username;
	@Column(name = "operator_name")
	private String operatorName;
	private String postman;
	@Column(name = "c_time", insertable = false)
	private Date cTime;
	@Column(name = "cancel_reason")
	private String cancelReason;
	@Column(name = "order_state")
	private Integer orderState;
	private String bz;
	private Integer payment;
	private BigDecimal amount;

	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER, orphanRemoval = true)
	@JoinColumn(name = "order_no", referencedColumnName = "order_no")
	private List<OrderDetail> details;
	@Transient
	private List<ShoppingCart> items;
	@Transient
	private Integer orderNoSuffix;

	public Integer getOrderNoSuffix() {
		return orderNoSuffix;
	}

	public void setOrderNoSuffix(Integer orderNoSuffix) {
		this.orderNoSuffix = orderNoSuffix;
	}

	public List<ShoppingCart> getItems() {
		return items;
	}

	public void setItems(List<ShoppingCart> items) {
		this.items = items;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getOrderNo() {
		return orderNo;
	}

	public void setOrderNo(String orderNo) {
		this.orderNo = orderNo;
	}

	public String getAddr() {
		return addr;
	}

	public void setAddr(String addr) {
		this.addr = addr;
	}

	public String getContacts() {
		return contacts;
	}

	public void setContacts(String contacts) {
		this.contacts = contacts;
	}

	public String getPhoneNo() {
		return phoneNo;
	}

	public void setPhoneNo(String phoneNo) {
		this.phoneNo = phoneNo;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getOperatorName() {
		return operatorName;
	}

	public void setOperatorName(String operatorName) {
		this.operatorName = operatorName;
	}

	public String getPostman() {
		return postman;
	}

	public void setPostman(String postman) {
		this.postman = postman;
	}

	public Date getcTime() {
		return cTime;
	}

	public void setcTime(Date cTime) {
		this.cTime = cTime;
	}

	public String getCancelReason() {
		return cancelReason;
	}

	public void setCancelReason(String cancelReason) {
		this.cancelReason = cancelReason;
	}

	public Integer getOrderState() {
		return orderState;
	}

	public void setOrderState(Integer orderState) {
		this.orderState = orderState;
	}

	public String getBz() {
		return bz;
	}

	public void setBz(String bz) {
		this.bz = bz;
	}

	public Integer getPayment() {
		return payment;
	}

	public void setPayment(Integer payment) {
		this.payment = payment;
	}

	public BigDecimal getAmount() {
		return amount;
	}

	public void setAmount(BigDecimal amount) {
		this.amount = amount;
	}

	public List<OrderDetail> getDetails() {
		return details;
	}

	public void setDetails(List<OrderDetail> details) {
		this.details = details;
	}

	/**
	 * 生成支付标题
	 * 
	 * @return
	 */
	public String generatePaySubject() {
		String subject = "商品订单";
		if (details != null && details.size() > 0) {
			subject = "";
			for (OrderDetail od : details) {
				subject += od.getPname() + " ";
			}
			subject += details.size() + "种商品";
		}

		return subject.length() > 256 ? subject.substring(0, 256) : subject;
	}

	/**
	 * 生成支付内容
	 * 
	 * @return
	 */
	public String generatePayBody() {
		String body = "订单描述";
		if (details != null && details.size() > 0) {
			body = "";
			for (OrderDetail od : details) {
				body += od.getPname() + od.getPcount() + "件 ";
			}
		}

		return body.length() > 128 ? body.substring(0, 128) : body;
	}

}
