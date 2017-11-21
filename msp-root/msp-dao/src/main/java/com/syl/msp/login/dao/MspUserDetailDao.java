package com.syl.msp.login.dao;

import com.syl.msp.login.entity.MspUserDetail;
import com.syl.msp.product.dao.BaseDao;

public interface MspUserDetailDao extends BaseDao {
	public MspUserDetail findByUsername(String username);

	//public void addUser(MspUserDetail mud) throws Exception;

	//public List<MspResource> getResoucesByRole(String role) throws Exception;

	//public void updatePassword(MspUserDetail mud) throws Exception;
}
