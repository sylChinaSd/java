package com.syl.msp.login.dao;

import java.util.List;

import com.syl.msp.login.entity.MspResource;
import com.syl.msp.login.entity.MspUserDetail;
import com.syl.msp.product.dao.BaseDao;

public interface MspUserDetailDao extends BaseDao {
	public MspUserDetail findByUsername(String username);

	//public void addUser(MspUserDetail mud);

	public List<MspResource> getResoucesByRole(String role);

	//public void updatePassword(MspUserDetail mud);
}
