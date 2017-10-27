package com.syl.msp.shoppingcart.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.syl.msp.shoppingcart.dao.ShoppingCartDao;
import com.syl.msp.shoppingcart.entity.ShoppingCart;

@Service
@Transactional(propagation = Propagation.NOT_SUPPORTED)
public class ShoppingCartService {

	@Autowired
	ShoppingCartDao scDao;

	public void save(ShoppingCart sc) throws Exception {
		scDao.save(sc);
	}

	public Long queryTotalCount(String username) throws Exception {
		return scDao.queryTotalCount(username);
	}

	public ShoppingCart findByUsernameAndPid(ShoppingCart sc) throws Exception {
		return scDao.findByUsernameAndPid(sc);
	}

	public List<ShoppingCart> queryPage(ShoppingCart sc) throws Exception {
		return scDao.queryPage(sc);
	}

	@Transactional(rollbackFor = Exception.class)
	public void deleteShoppingCart(List<Integer> ids, String username) throws Exception {
		for (Integer id : ids) {
			scDao.deleteByIdAndUsername(id, username);
		}
	}

}
