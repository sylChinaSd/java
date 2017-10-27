package com.syl.msp.poduct.entity;

import java.math.BigDecimal;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.persistence.Version;

import com.syl.msp.utils.common.BaseEntity;

@Entity
@Table(name = "msp_product")
public class Product extends BaseEntity {
	/**
	 * 
	 */
	@Transient
	private static final long serialVersionUID = 1L;
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	@Column(name = "shop_id")
	private Integer shopId;
	@Column(name = "user_id")
	private Integer userId;
	@Column(name = "product_type_id")
	private Integer productTypeId;
	private String pname;
	private String remark;
	private String pdesc;
	@Column(name = "rule_param")
	private String ruleParam;
	@Column(name = "pack_list")
	private String packList;
	private BigDecimal price;
	private BigDecimal discount;
	private Integer stock;
	@Column(name = "index_seq")
	private Integer indexSeq;
	@Column(name = "c_time")
	private Date cTime;
	@Column(name = "m_pic")
	private String mPic;
	private String pic1;
	private String pic2;
	private String pic3;
	private String pic4;
	private Integer pcondi;
	@Version
	private Long version;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getShopId() {
		return shopId;
	}

	public void setShopId(Integer shopId) {
		this.shopId = shopId;
	}

	public Integer getUserId() {
		return userId;
	}

	public void setUserId(Integer userId) {
		this.userId = userId;
	}

	public Integer getProductTypeId() {
		return productTypeId;
	}

	public void setProductTypeId(Integer productTypeId) {
		this.productTypeId = productTypeId;
	}

	public String getPname() {
		return pname;
	}

	public void setPname(String pname) {
		this.pname = pname;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public String getPdesc() {
		return pdesc;
	}

	public void setPdesc(String pdesc) {
		this.pdesc = pdesc;
	}

	public String getRuleParam() {
		return ruleParam;
	}

	public void setRuleParam(String ruleParam) {
		this.ruleParam = ruleParam;
	}

	public String getPackList() {
		return packList;
	}

	public void setPackList(String packList) {
		this.packList = packList;
	}

	public BigDecimal getPrice() {
		return price;
	}

	public void setPrice(BigDecimal price) {
		this.price = price;
	}

	public BigDecimal getDiscount() {
		return discount;
	}

	public void setDiscount(BigDecimal discount) {
		this.discount = discount;
	}

	public Integer getStock() {
		return stock;
	}

	public void setStock(Integer stock) {
		this.stock = stock;
	}

	public Integer getIndexSeq() {
		return indexSeq;
	}

	public void setIndexSeq(Integer indexSeq) {
		this.indexSeq = indexSeq;
	}

	public Date getcTime() {
		return cTime;
	}

	public void setcTime(Date cTime) {
		this.cTime = cTime;
	}

	public String getmPic() {
		return mPic;
	}

	public void setmPic(String mPic) {
		this.mPic = mPic;
	}

	public String getPic1() {
		return pic1;
	}

	public void setPic1(String pic1) {
		this.pic1 = pic1;
	}

	public String getPic2() {
		return pic2;
	}

	public void setPic2(String pic2) {
		this.pic2 = pic2;
	}

	public String getPic3() {
		return pic3;
	}

	public void setPic3(String pic3) {
		this.pic3 = pic3;
	}

	public String getPic4() {
		return pic4;
	}

	public void setPic4(String pic4) {
		this.pic4 = pic4;
	}

	public Integer getPcondi() {
		return pcondi;
	}

	public void setPcondi(Integer pcondi) {
		this.pcondi = pcondi;
	}

	public Long getVersion() {
		return version;
	}

	public void setVersion(Long version) {
		this.version = version;
	}

	@Override
	public String toString() {
		return "Product [id=" + id + ", shopId=" + shopId + ", userId="
				+ userId + ", productTypeId=" + productTypeId + ", pname="
				+ pname + ", remark=" + remark + ", pdesc=" + pdesc
				+ ", ruleParam=" + ruleParam + ", packList=" + packList
				+ ", price=" + price + ", discount=" + discount + ", stock="
				+ stock + ", indexSeq=" + indexSeq + ", cTime=" + cTime
				+ ", mPic=" + mPic + ", pic1=" + pic1 + ", pic2=" + pic2
				+ ", pic3=" + pic3 + ", pic4=" + pic4 + ", pcondi=" + pcondi
				+ ", version=" + version + "]";
	}

}
