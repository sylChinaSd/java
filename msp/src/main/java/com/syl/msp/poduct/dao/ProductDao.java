package com.syl.msp.poduct.dao;

import java.util.List;

import com.syl.msp.poduct.entity.Product;

public interface ProductDao {

	Product findById(int i) throws Exception;

	void saveById(Product pro) throws Exception;

	List<Product> queryPage(Product p) throws Exception;

	Long getTotalCount(Product p) throws Exception;

	void updateById(Product tmp) throws Exception;

}
