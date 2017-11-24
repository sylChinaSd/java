package com.syl.msp.login.entity;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

public class MspUserDetail implements UserDetails {
	private static final long serialVersionUID = 1L;
	private String username;
	private String password;
	private Integer enable;

	private MspUserDetailAuthority muda;

	private List<SimpleGrantedAuthority> authorities = new ArrayList<SimpleGrantedAuthority>();
	private List<MspResource> resources = new ArrayList<MspResource>();

	public MspUserDetailAuthority getMuda() {
		return muda;
	}

	public void setMuda(MspUserDetailAuthority muda) {
		this.muda = muda;
	}

	public List<MspResource> getResources() {
		return resources;
	}

	public void setResources(List<MspResource> resources) {
		this.resources = resources;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		return authorities;
	}

	@Override
	public String getPassword() {
		return password;
	}

	@Override
	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public void setAuthorities(List<SimpleGrantedAuthority> authorities) {
		this.authorities = authorities;
	}

	@Override
	public boolean isAccountNonExpired() {
		return true;
	}

	@Override
	public boolean isAccountNonLocked() {
		return true;
	}

	@Override
	public boolean isCredentialsNonExpired() {
		return true;
	}

	@Override
	public boolean isEnabled() {
		return true;
	}

	@Override
	public boolean equals(Object rhs) {
		if (rhs instanceof MspUserDetail) {
			return username.equals(((MspUserDetail) rhs).username);
		}
		return false;
	}

	@Override
	public int hashCode() {
		return username.hashCode();
	}

	public Integer getEnable() {
		return enable;
	}

	public void setEnable(Integer enable) {
		this.enable = enable;
	}
	
	@Override
	public String toString() {
		return "MspUserDetail [username=" + username + ", password=" + password
				+ ", enable=" + enable + "]";
	}

}
