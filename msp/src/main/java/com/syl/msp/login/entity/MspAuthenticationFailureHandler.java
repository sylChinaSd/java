package com.syl.msp.login.entity;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;

import com.syl.msp.utils.common.CommonUtil;

public class MspAuthenticationFailureHandler implements
		AuthenticationFailureHandler {

	@Override
	public void onAuthenticationFailure(HttpServletRequest arg0,
			HttpServletResponse response, AuthenticationException arg2)
			throws IOException, ServletException {
		CommonUtil.setExceptionResponse(response, arg2);
	}
}
