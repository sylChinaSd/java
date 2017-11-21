package com.syl.msp.exception.controller;

import java.io.IOException;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

@ControllerAdvice("com.syl.msp")
public class ExceptionControllerAdvice {
	
	@ExceptionHandler(value={SQLException.class,IOException.class,NullPointerException.class})
	@ResponseBody
	public Map<String,Object> handAll(final Exception ex){
		Map<String,Object> map = new HashMap<String,Object>();
		map.put("success", false);
		map.put("msg", ex.getMessage());
		return map;
	}
}

