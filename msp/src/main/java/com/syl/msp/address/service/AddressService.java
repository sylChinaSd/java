package com.syl.msp.address.service;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.syl.msp.address.dao.AddressDao;
import com.syl.msp.address.entity.Address;
import com.syl.msp.utils.common.CommonUtil;

@Service
public class AddressService {

	@Autowired
	AddressDao addressDao;

	@Transactional(propagation = Propagation.NOT_SUPPORTED, readOnly = true)
	public Map<String, Object> addressQueryAll() throws Exception {
		Map<String, Object> map = CommonUtil.getResponseMap();
		String username = CommonUtil.getCurrentUsername();
		List<Address> lists = addressDao.addressQueryAll(username);
		map.put("success", true);
		map.put("items", lists);
		return map;
	}

	@Transactional(rollbackFor = Exception.class)
	public Map<String, Object> addressCreate(Address addr) throws Exception {
		Map<String, Object> map = CommonUtil.getResponseMap();
		String username = CommonUtil.getCurrentUsername();
		addr.setUsername(username);
		addressDao.addressCreate(addr);
		map.put("success", true);
		return map;
	}

	@Transactional(rollbackFor = Exception.class)
	public Map<String, Object> addressUpdate(Address addr) throws Exception {
		Map<String, Object> map = CommonUtil.getResponseMap();
		String username = CommonUtil.getCurrentUsername();
		addr.setUsername(username);
		Address tmp = addressDao.findAddressByIdAndUsername(addr);
		if(tmp!=null){
			tmp.setAddr(addr.getAddr());
			tmp.setAddrDesc(addr.getAddrDesc());
			tmp.setContacts(addr.getContacts());
			tmp.setPhoneNo(addr.getPhoneNo());
			tmp.setBz(addr.getBz());
			addressDao.addressUpdate(tmp);
		}
		map.put("success", true);
		return map;
	}

	@Transactional(rollbackFor = Exception.class)
	public Map<String, Object> addressDelete(Address addr) throws Exception {
		Map<String, Object> map = CommonUtil.getResponseMap();
		String username = CommonUtil.getCurrentUsername();
		addr.setUsername(username);
		Address tmp = addressDao.findAddressByIdAndUsername(addr);
		if(tmp!=null){
			addressDao.addressDelete(tmp);
		}
		
		map.put("success", true);
		return map;
	}

}
