package com.syl.msp.concern.entity;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Entity;
import javax.persistence.Transient;

import org.hibernate.annotations.NamedQueries;
import org.hibernate.annotations.NamedQuery;

import com.syl.msp.poduct.entity.Product;
import com.syl.msp.utils.common.BaseEntity;

@Entity
@Table(name = "msp_concern")
@NamedQueries({
		@NamedQuery(name = "findConcern", query = "select c from Concern c where c.username = :username and c.productId = :productId"),
		@NamedQuery(name = "getConcernTotalCount", query = "select COUNT(1) from Concern c where c.username = :username"),
		@NamedQuery(name = "queryConcernPage", query = "select c from Concern c where c.username = :username order by id desc"),
		@NamedQuery(name = "deleteConcernByIdAndUsername", query = "delete from Concern c where c.username = :username and c.id = :id") })
public class Concern extends BaseEntity {
	@Transient
	private static final long serialVersionUID = 1L;
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;

	@Column(name = "product_id")
	private Integer productId;
	@OneToOne(cascade = CascadeType.REFRESH, orphanRemoval = false, optional = false, fetch = FetchType.EAGER)
	@JoinColumn(name = "product_id", referencedColumnName = "id", insertable = false, updatable = false)
	private Product product;

	@Transient
	private Integer productCount;
	private String username;

	
	public Concern() {
		super();
		productCount = 1;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getProductId() {
		return productId;
	}

	public void setProductId(Integer productId) {
		this.productId = productId;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public Product getProduct() {
		return product;
	}

	public void setProduct(Product product) {
		this.product = product;
	}

	public Integer getProductCount() {
		return productCount;
	}

	public void setProductCount(Integer productCount) {
		this.productCount = productCount;
	}

}
