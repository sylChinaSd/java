package com.syl.msp.product.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.syl.msp.product.dao.ProductDao;
import com.syl.msp.product.entity.Product;
import com.syl.msp.product.service.ProductService;

@Service
public class ProductServiceImpl implements ProductService {

	@Autowired
	ProductDao productDao;
	
	@Override
	public Product findProductById(int id) {
		return productDao.findById(id);
	}

	@Override
	public void addProduct(Product product){
		productDao.insert(product);
	}
}
