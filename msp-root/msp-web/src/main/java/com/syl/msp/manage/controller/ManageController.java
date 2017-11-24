package com.syl.msp.manage.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
public class ManageController {
	@RequestMapping(method = RequestMethod.GET, value = "/manage/index")
	public String index() {
		return "manage/index";
	}
}
