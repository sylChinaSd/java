package com.syl.msp.product.dao;

import com.syl.msp.product.entity.Product;

public interface ProductDao extends BaseDao {

	public Product findById(int id);

	public void insert(Product product);

	/*public void saveById(Product pro);

	public List<Product> queryPage(Product p);

	public Long getTotalCount(Product p);

	public void updateById(Product tmp);*/
}
