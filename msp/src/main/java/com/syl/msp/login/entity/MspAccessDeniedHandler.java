package com.syl.msp.login.entity;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;

import com.syl.msp.utils.common.CommonUtil;

public class MspAccessDeniedHandler implements AccessDeniedHandler{

	@Override
	public void handle(HttpServletRequest httpservletrequest,
			HttpServletResponse httpservletresponse,
			AccessDeniedException accessdeniedexception) throws IOException,
			ServletException {
		CommonUtil.setExceptionResponse(httpservletresponse, accessdeniedexception);
	}

}
