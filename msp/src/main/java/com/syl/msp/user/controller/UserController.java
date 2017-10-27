package com.syl.msp.user.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.syl.msp.login.entity.MspResource;
import com.syl.msp.login.entity.MspUserDetail;
import com.syl.msp.login.entity.MspUserDetailAuthority;
import com.syl.msp.login.service.MspUserDetailService2;
import com.syl.msp.utils.common.CommonUtil;
import com.syl.msp.utils.common.SpringContextUtils;

@Controller
public class UserController {
	private final static MspResource DEFAULT_RESOURCE = new MspResource("商品列表",
			"/product/list");
	private final static String DEFAULT_PASSWORD = "111111";
	@Autowired
	MspUserDetailService2 mudService;

	@RequestMapping(value = "/user/current")
	@ResponseBody
	public Map<String, Object> pageQuery() throws Exception {
		Map<String, Object> map = CommonUtil.getResponseMap();
		String username = CommonUtil.getCurrentUsername();
		// 设置返回数据
		map.put("username", username);
		map.put("success", true);
		return map;
	}

	@RequestMapping(value = "/menu/query")
	@ResponseBody
	public Map<String, Object> menuQuery() throws Exception {
		Map<String, Object> map = CommonUtil.getResponseMap();
		List<MspResource> list = CommonUtil.getCurrentResources();
		if (list == null) {
			list = new ArrayList<MspResource>();
			list.add(DEFAULT_RESOURCE);
		}
		// 设置返回数据
		map.put("success", true);
		map.put("items", list);
		return map;
	}

	@RequestMapping(value = "/user/add")
	@ResponseBody
	public Map<String, Object> userAdd(String username, String password,
			String role, HttpServletRequest request) throws Exception {
		Map<String, Object> map = CommonUtil.getResponseMap();
		MspUserDetail mud = new MspUserDetail();
		BCryptPasswordEncoder bcryptEncoder = SpringContextUtils.getBean(
				"bcryptEncoder", BCryptPasswordEncoder.class);
		mud.setUsername(username);
		mud.setPassword(bcryptEncoder.encode(password));
		MspUserDetailAuthority muda = new MspUserDetailAuthority();
		muda.setUsername(username);
		muda.setAuthority(role);
		mud.setMuda(muda);

		mudService.addUser(mud);
		// 设置返回数据
		map.put("success", true);
		return map;
	}
	
	@RequestMapping(value = "/user/exist")
	@ResponseBody
	public Map<String, Object> userExist(String username) throws Exception {
		Map<String, Object> map = CommonUtil.getResponseMap();
		// 设置返回数据
		map.put("success", mudService.existUser(username));
		return map;
	}
	
	@RequestMapping(value = "/user/reset")
	@ResponseBody
	public Map<String, Object> userReset(String username) throws Exception {
		Map<String, Object> map = CommonUtil.getResponseMap();
		BCryptPasswordEncoder bcryptEncoder = SpringContextUtils.getBean(
				"bcryptEncoder", BCryptPasswordEncoder.class);
		MspUserDetail mud = new MspUserDetail();
		mud.setUsername(username);
		mud.setPassword(bcryptEncoder.encode(DEFAULT_PASSWORD));
		mudService.updatePassword(mud);
		// 设置返回数据
		map.put("success", true);
		return map;
	}
}
