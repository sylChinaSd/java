package com.syl.msp.concern.service;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.syl.msp.concern.dao.ConcernDao;
import com.syl.msp.concern.entity.Concern;
import com.syl.msp.shoppingcart.dao.ShoppingCartDao;
import com.syl.msp.shoppingcart.entity.ShoppingCart;
import com.syl.msp.utils.common.CommonUtil;

@Service
public class ConcernService {
	@Autowired
	ConcernDao concernDao;

	@Autowired
	ShoppingCartDao scDao;

	@Transactional(propagation = Propagation.NOT_SUPPORTED, readOnly = true)
	public Map<String, Object> getConcernState(Concern concern)
			throws Exception {
		Map<String, Object> map = CommonUtil.getResponseMap();
		String username = CommonUtil.getCurrentUsername();
		concern.setUsername(username);

		Concern result = concernDao.findConcern(concern);
		map.put("success", true);
		map.put("isConcern", result != null);
		return map;
	}

	@Transactional(rollbackFor = Exception.class)
	public Map<String, Object> toggleConcernState(Concern concern)
			throws Exception {
		Map<String, Object> map = CommonUtil.getResponseMap();
		String username = CommonUtil.getCurrentUsername();
		concern.setUsername(username);
		Concern result = concernDao.findConcern(concern);
		boolean isConcern = false;
		if (result != null) {
			concernDao.delete(result);
			isConcern = false;
		} else {
			concernDao.save(concern);
			isConcern = true;
		}
		map.put("success", true);
		map.put("isConcern", isConcern);
		return map;
	}

	@Transactional(propagation = Propagation.NOT_SUPPORTED, readOnly = true)
	public Map<String, Object> concernQueryPage(Concern concern)
			throws Exception {
		Map<String, Object> map = CommonUtil.getResponseMap();
		String username = CommonUtil.getCurrentUsername();
		concern.setUsername(username);
		List<Concern> lists = concernDao.queryPage(concern);
		Integer totalCount = concernDao.getTotalCount(concern);
		map.put("success", true);
		map.put("items", lists);
		map.put("totalCount", totalCount);
		return map;
	}

	@Transactional(rollbackFor = Exception.class)
	public Map<String, Object> concernDelete(List<Integer> ids)
			throws Exception {
		Map<String, Object> map = CommonUtil.getResponseMap();
		String username = CommonUtil.getCurrentUsername();
		if (ids != null) {
			Concern c = new Concern();
			c.setUsername(username);
			for (Integer id : ids) {
				c.setId(id);
				concernDao.deleteByIdAndUsername(c);
			}
		}
		map.put("success", true);
		return map;
	}

	@Transactional(rollbackFor = Exception.class)
	public Map<String, Object> concernAddToshoppingCart(List<ShoppingCart> lists)
			throws Exception {
		Map<String, Object> map = CommonUtil.getResponseMap();
		String username = CommonUtil.getCurrentUsername();
		if (lists != null) {
			for (ShoppingCart sc : lists) {
				sc.setUsername(username);
				scDao.save(sc);
			}
		}
		map.put("success", true);
		return map;
	}

}
