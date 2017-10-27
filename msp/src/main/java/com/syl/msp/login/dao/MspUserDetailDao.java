package com.syl.msp.login.dao;

import java.util.List;

import com.syl.msp.login.entity.MspResource;
import com.syl.msp.login.entity.MspUserDetail;

public interface MspUserDetailDao {
	public MspUserDetail findByUsername(String username) throws Exception;

	public void addUser(MspUserDetail mud) throws Exception;

	public List<MspResource> getResoucesByRole(String role) throws Exception;

	public void updatePassword(MspUserDetail mud) throws Exception;
}
