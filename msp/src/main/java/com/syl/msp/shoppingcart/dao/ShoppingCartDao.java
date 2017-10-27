package com.syl.msp.shoppingcart.dao;

import java.util.List;

import com.syl.msp.shoppingcart.entity.ShoppingCart;

public interface ShoppingCartDao {

	public Long queryTotalCount(String username) throws Exception;

	public void save(ShoppingCart sc) throws Exception;

	public ShoppingCart findByUsernameAndPid(ShoppingCart sc) throws Exception;

	public List<ShoppingCart> queryPage(ShoppingCart sc) throws Exception;

	public int deleteByIdAndUsername(Integer id, String username) throws Exception;

}
