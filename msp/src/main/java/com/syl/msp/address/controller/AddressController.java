package com.syl.msp.address.controller;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.syl.msp.address.entity.Address;
import com.syl.msp.address.service.AddressService;

@Controller
public class AddressController {
	@Autowired
	AddressService addressService;

	@RequestMapping(value = "/address/query/all")
	@ResponseBody
	public Map<String, Object> addressQueryAll() throws Exception {
		Map<String, Object> map = addressService.addressQueryAll();
		return map;
	}
	
	@RequestMapping(value = "/address/create")
	@ResponseBody
	public Map<String, Object> addressCreate(Address addr) throws Exception {
		Map<String, Object> map = addressService.addressCreate(addr);
		return map;
	}
	
	@RequestMapping(value = "/address/update")
	@ResponseBody
	public Map<String, Object> addressUpdate(Address addr) throws Exception {
		Map<String, Object> map = addressService.addressUpdate(addr);
		return map;
	}
	
	@RequestMapping(value = "/address/delete")
	@ResponseBody
	public Map<String, Object> addressDelete(Address addr) throws Exception {
		Map<String, Object> map = addressService.addressDelete(addr);
		return map;
	}
}
