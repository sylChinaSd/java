package com.syl.msp.concern.controller;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.syl.msp.concern.entity.Concern;
import com.syl.msp.concern.service.ConcernService;
import com.syl.msp.shoppingcart.entity.ShoppingCart;

@Controller
public class ConcernController {
	@Autowired
	ConcernService concernService; 
	
	@RequestMapping(value = "/concern/state")
	@ResponseBody
	public Map<String, Object> concernState(Concern concern) throws Exception {
		Map<String, Object> map = concernService.getConcernState(concern);
		return map;
	}
	
	@RequestMapping(value = "/concern/toggle")
	@ResponseBody
	public Map<String, Object> concernToggleState(Concern concern) throws Exception {
		Map<String, Object> map = concernService.toggleConcernState(concern);
		return map;
	}
	
	@RequestMapping(value = "/concern/query/page")
	@ResponseBody
	public Map<String, Object> concernQueryPage(Concern concern) throws Exception {
		Map<String, Object> map = concernService.concernQueryPage(concern);
		return map;
	}
	
	@RequestMapping(value = "/concern/delete")
	@ResponseBody
	public Map<String, Object> concernDelete(@RequestBody List<Integer> ids) throws Exception {
		Map<String, Object> map = concernService.concernDelete(ids);
		return map;
	}
	
	@RequestMapping(value = "/concern/addToshoppingCart")
	@ResponseBody
	public Map<String, Object> concernAddToshoppingCart(@RequestBody List<ShoppingCart> lists) throws Exception {
		Map<String, Object> map = concernService.concernAddToshoppingCart(lists);
		return map;
	}
	
	
}
