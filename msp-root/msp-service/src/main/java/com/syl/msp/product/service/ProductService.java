package com.syl.msp.product.service;

import com.syl.msp.product.entity.Product;

public interface ProductService {
	public Product findProductById(int id);
	
	public void addProduct(Product product);
	
}
