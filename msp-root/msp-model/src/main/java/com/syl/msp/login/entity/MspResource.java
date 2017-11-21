package com.syl.msp.login.entity;


/**
 * 资源实体类
 * 
 * @author SYL
 *
 */
public class MspResource {
	private Integer id;

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
