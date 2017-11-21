package com.syl.msp.login.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.syl.msp.login.dao.MspUserDetailDao;
import com.syl.msp.login.entity.MspUserDetail;

/**
 * @author SYL
 *
 */
@Service
public class MspUserDetailService2 {

	@Autowired
	MspUserDetailDao mspUserDao;

	@SuppressWarnings("unchecked")
	public UserDetails loadUserByUsername(String username)
			throws UsernameNotFoundException {
		// 根据名字查询并设置userdetail的值
		MspUserDetail mud = null;
		try {
			mud = mspUserDao.findByUsername(username);
			// 权限角色的设置
			String authority = mud.getMuda().getAuthority();
			if (authority != null) {
				String[] authorities = authority.split(",");
				List<SimpleGrantedAuthority> list = (List<SimpleGrantedAuthority>) mud
						.getAuthorities();

				// 设置权限
				for (String str : authorities) {
					list.add(new SimpleGrantedAuthority(str));
					// 获取角色对应的资源
					//mud.getResources().addAll(mspUserDao.getResoucesByRole(str));
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		if (mud == null) {
			throw new UsernameNotFoundException(username
					+ " -- user not found!");
		}
		return mud;
	}

	public void addUser(MspUserDetail mud) {
		//mspUserDao.addUser(mud);
	}

	public Boolean existUser(String username){
		return mspUserDao.findByUsername(username) != null;
	}

	public void updatePassword(MspUserDetail mud) {
		//mspUserDao.updatePassword(mud);
	}
}
