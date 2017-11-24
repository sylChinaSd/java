package com.syl.msp.login.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

/**
 * 该实例手动使用bean注册，因此不使用@service
 * 
 * @author SYL
 *
 */
public class MspUserDetailSecurityService implements UserDetailsService {

	@Autowired
	MspUserDetailService mspUserDetailService;

	@Override
	public UserDetails loadUserByUsername(String username)
			throws UsernameNotFoundException {
		// 根据名字查询并设置userdetail的值
		UserDetails mud = null;
		try {
			mud = mspUserDetailService.findUserByUsername(username);
		} catch (Exception e) {
			e.printStackTrace();
		}
		if (mud == null) {
			throw new UsernameNotFoundException(username
					+ " -- user not found!");
		}
		return mud;
	}
}
