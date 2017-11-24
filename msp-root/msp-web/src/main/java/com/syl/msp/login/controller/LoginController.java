package com.syl.msp.login.controller;

import java.awt.image.BufferedImage;
import java.util.Random;

import javax.imageio.ImageIO;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.syl.msp.utils.common.CommonUtil;

@Controller
public class LoginController {
	
	@RequestMapping(method = RequestMethod.GET, value = "/")
	public String redirectLogin() {
		return "redirect:login";
	}
	
	@RequestMapping(method = RequestMethod.GET, value = "/login")
	public String index() {
		return "login/index";
	}
	
	@RequestMapping(method = RequestMethod.GET, value = "/login/accessDenied")
	public String accessDenined() {
		return "login/accessDenied";
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
		System.out.println("验证码："+code);
		// 返回验证码图片
		BufferedImage img = CommonUtil.generateCodeImage(code);
		res.setHeader("Content-Type", "image/jpeg");
		ServletOutputStream output = res.getOutputStream();
		ImageIO.write(img, "jpeg", output);
		output.close();
	}
}
