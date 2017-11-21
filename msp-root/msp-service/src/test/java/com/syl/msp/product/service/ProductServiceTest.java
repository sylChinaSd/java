package com.syl.msp.product.service;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.syl.msp.product.entity.Product;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:applicationContext-mybatis.xml","classpath:applicationContext-service.xml" })
public class ProductServiceTest {
	@Autowired
	ProductService pService;
	
	@Test
	public void findTest(){
		System.out.println(pService.findProductById(4));
	}
	
	@Test
	public void addTest(){
		Product product = new Product();
		product.setPname("测试");
		pService.addProduct(product);
		
	}
}
