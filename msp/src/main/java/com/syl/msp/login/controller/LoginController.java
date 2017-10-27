package com.syl.msp.login.controller;

import java.awt.image.BufferedImage;
import java.util.Random;

import javax.imageio.ImageIO;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.syl.msp.login.service.MspUserDetailService2;
import com.syl.msp.utils.common.CommonUtil;

@Controller
public class LoginController {

	@Autowired
	MspUserDetailService2 mspUserDetailService;

	/**
	 * 跳转到首页页面
	 * 
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = { "/", "/index" }, method = RequestMethod.GET)
	public String login(HttpServletRequest req) throws Exception {
		return "index";
	}

	@RequestMapping(value = "/login/code")
	public void generateCode(HttpServletRequest req, HttpServletResponse res)
			throws Exception {
		String code = "";
		Random r = new Random();
		for (int i = 0; i < 4; ++i) {
			code += r.nextInt(10);
		}
		req.getSession().setAttribute("code", code);
		// 返回验证码图片
		BufferedImage img = CommonUtil.generateCodeImage(code);
		res.setHeader("Content-Type", "image/jpeg");
		ServletOutputStream output = res.getOutputStream();
		ImageIO.write(img, "jpeg", output);
		output.close();
	}

}
