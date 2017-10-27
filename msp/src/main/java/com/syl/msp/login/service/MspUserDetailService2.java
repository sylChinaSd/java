package com.syl.msp.login.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

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
	@Transactional(propagation = Propagation.NOT_SUPPORTED)
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
					mud.getResources()
							.addAll(mspUserDao.getResoucesByRole(str));
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

	@Transactional(rollbackFor = Exception.class)
	public void addUser(MspUserDetail mud) throws Exception {
		mspUserDao.addUser(mud);
	}

	@Transactional(propagation = Propagation.NOT_SUPPORTED, readOnly = true)
	public Boolean existUser(String username) throws Exception {
		return mspUserDao.findByUsername(username) != null;
	}

	@Transactional(rollbackFor = Exception.class)
	public void updatePassword(MspUserDetail mud) throws Exception {
		mspUserDao.updatePassword(mud);
	}
}
