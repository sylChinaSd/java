package com.syl.msp.address.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.hibernate.annotations.NamedQueries;
import org.hibernate.annotations.NamedQuery;

import com.syl.msp.utils.common.BaseEntity;

@Entity
@Table(name = "msp_address")
@NamedQueries({
	@NamedQuery(name="addressQueryAll",query="select a from Address a where a.username = :username order by cTime desc"),
	@NamedQuery(name="addressDelete",query="delete from Address a where a.username = :username and a.id = :id"),
	@NamedQuery(name="findAddressByIdAndUsername",query="select a from Address a where a.username = :username and a.id = :id"),
	
})
public class Address extends BaseEntity {
	@Transient
	private static final long serialVersionUID = 1L;
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	private String username;
	@Column(name = "phone_no")
	private String phoneNo;
	private String contacts;
	private String bz;
	private String addr;
	@Column(name = "addr_desc")
	private String addrDesc;
	@Column(insertable = false, name = "c_time")
	private Date cTime;
	@Column(name = "defaul_addr")
	private Integer defaulAddr;

	public String getAddrDesc() {
		return addrDesc;
	}

	public void setAddrDesc(String addrDesc) {
		this.addrDesc = addrDesc;
	}

	public Date getcTime() {
		return cTime;
	}

	public void setcTime(Date cTime) {
		this.cTime = cTime;
	}

	public Integer getDefaulAddr() {
		return defaulAddr;
	}

	public void setDefaulAddr(Integer defaulAddr) {
		this.defaulAddr = defaulAddr;
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

	public String getPhoneNo() {
		return phoneNo;
	}

	public void setPhoneNo(String phoneNo) {
		this.phoneNo = phoneNo;
	}

	public String getContacts() {
		return contacts;
	}

	public void setContacts(String contacts) {
		this.contacts = contacts;
	}

	public String getBz() {
		return bz;
	}

	public void setBz(String bz) {
		this.bz = bz;
	}

	public String getAddr() {
		return addr;
	}

	public void setAddr(String addr) {
		this.addr = addr;
	}
}
