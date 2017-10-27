package com.syl.msp.login.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.NamedNativeQueries;
import org.hibernate.annotations.NamedNativeQuery;

/**
 * 资源实体类
 * 
 * @author SYL
 *
 */
@Entity
@Table(name = "msp_resouces")
@NamedNativeQueries({ @NamedNativeQuery(name = "getResoucesByRole", query = "select * from msp_resources where role_name = :role", resultClass = MspResource.class) })
public class MspResource {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;

	@Column(name = "role_name")
	private String roleName;
	private String name;
	private String rpath;

	public MspResource() {
	}

	public MspResource(String name, String rpath) {
		super();
		this.name = name;
		this.rpath = rpath;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getRoleName() {
		return roleName;
	}

	public void setRoleName(String roleName) {
		this.roleName = roleName;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getRpath() {
		return rpath;
	}

	public void setRpath(String rpath) {
		this.rpath = rpath;
	}

}
