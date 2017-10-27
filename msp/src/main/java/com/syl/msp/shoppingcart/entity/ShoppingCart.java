package com.syl.msp.shoppingcart.entity;

import java.util.Date;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.hibernate.annotations.NamedNativeQueries;
import org.hibernate.annotations.NamedNativeQuery;
import org.hibernate.annotations.NamedQueries;
import org.hibernate.annotations.NamedQuery;

import com.syl.msp.poduct.entity.Product;
import com.syl.msp.utils.common.BaseEntity;

@Entity
@Table(name = "msp_shopping_cart")
@NamedNativeQueries({ 
	@NamedNativeQuery(
			name = "findByUsernameAndPid", 
			query = "select * from msp_shopping_cart where username = :username and product_id = :productId",
			resultClass = ShoppingCart.class),	
	@NamedNativeQuery(
	        name = "queryPage",
	        query = "select * from msp_shopping_cart sc where sc.username = :username order by sc.c_time desc",
	        resultClass = ShoppingCart.class)
})

@NamedQueries({
	@NamedQuery(
        name = "queryTotalCount",
        query = "select count(1) from ShoppingCart where username = :username"
    ),
    @NamedQuery(
        name = "deleteByIdAndUsername",
        query = "delete from ShoppingCart where username = :username and id = :id"
    ),
    @NamedQuery(
        name = "queryPage2",
        query = "select sc,p from ShoppingCart sc left outer join Product p on sc.username = :username and p.id = sc.productId order by sc.cTime desc"
    )    
})
public class ShoppingCart extends BaseEntity {
	/**
	 * 
	 */
	@Transient
	private static final long serialVersionUID = 1L;
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	@Column(name = "username")
	private String username;
	@Column(name = "product_count")
	private Integer productCount;
	@Column(name = "c_time",insertable=false)
	private Date cTime;
	
	@Column(name = "product_id")
	private Integer productId;
	@OneToOne(cascade=CascadeType.REFRESH,orphanRemoval = false,optional = false ,fetch = FetchType.EAGER)
	@JoinColumn(name="product_id",referencedColumnName = "id",insertable = false,updatable = false)
	private Product product;
	
	private ShoppingCart() {
	}

	public Product getProduct() {
		return product;
	}

	public void setProduct(Product product) {
		this.product = product;
	}

	public Integer getProductId() {
		return productId;
	}

	public void setProductId(Integer productId) {
		this.productId = productId;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public Integer getProductCount() {
		return productCount;
	}

	public void setProductCount(Integer productCount) {
		this.productCount = productCount;
	}

	public Date getcTime() {
		return cTime;
	}

	public void setcTime(Date cTime) {
		this.cTime = cTime;
	}

}
