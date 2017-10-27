package com.syl.msp.order.entity;

import java.math.BigDecimal;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.NamedQueries;
import org.hibernate.annotations.NamedQuery;

@Entity
@Table(name = "msp_order_detail")
@NamedQueries({
	@NamedQuery(name="queryAnalysisDetail",query="select od.productId,MIN(pname) as pname,SUM(od.pcount) as pcount,SUM(od.pcount*od.price) as price "
			+ "from OrderDetail od "
			+ "where od.orderNo in (select o.orderNo from Order o where o.cTime between :begDate and :endDate ) "
			+ "group by od.productId order by od.pcount desc")
})
public class OrderDetail {
	// /hibernate 需要@Id，这里并没有实际意义
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	@Column(name = "order_no")
	private String orderNo;
	@Column(name = "product_id")
	private Integer productId;
	private String pname;
	private Integer pcount;
	private BigDecimal price;
	private String username;

	public String getOrderNo() {
		return orderNo;
	}

	public void setOrderNo(String orderNo) {
		this.orderNo = orderNo;
	}

	public Integer getProductId() {
		return productId;
	}

	public void setProductId(Integer productId) {
		this.productId = productId;
	}

	public String getPname() {
		return pname;
	}

	public void setPname(String pname) {
		this.pname = pname;
	}

	public Integer getPcount() {
		return pcount;
	}

	public void setPcount(Integer pcount) {
		this.pcount = pcount;
	}

	public BigDecimal getPrice() {
		return price;
	}

	public void setPrice(BigDecimal price) {
		this.price = price;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}
}
