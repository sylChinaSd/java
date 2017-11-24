package com.syl.msp.product.controller;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.syl.msp.product.service.ProductService;

@Controller
public class ProductController {

	@Autowired
	ProductService productService;

	@RequestMapping(method = RequestMethod.GET, value = "/product")
	public String index() {
		
		return "product/index";
	}

	@RequestMapping(method = RequestMethod.POST, value = "/product/query/page/{page}")
	@ResponseBody
	public Map<String, Object> query(@PathVariable int page) {
		System.out.println("page:" + page);
		return null;
	}

}
