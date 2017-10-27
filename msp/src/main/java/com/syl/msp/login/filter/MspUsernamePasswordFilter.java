package com.syl.msp.login.filter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.authentication.AuthenticationServiceException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AbstractAuthenticationProcessingFilter;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.util.Assert;

public class MspUsernamePasswordFilter extends
		AbstractAuthenticationProcessingFilter {

	public MspUsernamePasswordFilter(String pattern,
			AuthenticationSuccessHandler authenticationSuccessHandler,
			AuthenticationFailureHandler authenticationFailureHandler) {
		super(new AntPathRequestMatcher(pattern, "POST"));
		super.setAuthenticationSuccessHandler(authenticationSuccessHandler);
		super.setAuthenticationFailureHandler(authenticationFailureHandler);
		usernameParameter = "username";
		passwordParameter = "password";
		codeParameter = "code";
		postOnly = true;
	}

	public Authentication attemptAuthentication(HttpServletRequest request,
			HttpServletResponse response) throws AuthenticationException {
		if (postOnly && !request.getMethod().equals("POST"))
			throw new AuthenticationServiceException((new StringBuilder())
					.append("Authentication method not supported: ")
					.append(request.getMethod()).toString());
		String username = obtainUsername(request);
		String password = obtainPassword(request);
		String code = obtainCode(request);
		String code2 = obtainSessionCode(request);
		if (username == null)
			username = "";
		if (password == null)
			password = "";
		if (code == null)
			code = "";
		if (code2 == null)
			code2 = "";
		username = username.trim();
		UsernamePasswordAuthenticationToken authRequest = new UsernamePasswordAuthenticationToken(
				username, password);
		System.out.println("code2:" + code2 + ",code:" + code);
		if (code2.equalsIgnoreCase(code)) {
			setDetails(request, authRequest);
		} else {
			throw new AuthenticationServiceException("验证码错误!");
		}
		return getAuthenticationManager().authenticate(authRequest);
	}

	/**
	 * 找到验证码并让其失效
	 * 
	 * @param request
	 * @return
	 */
	protected String obtainSessionCode(HttpServletRequest request) {
		String code = (String) request.getSession().getAttribute(codeParameter);
		request.getSession().removeAttribute(codeParameter);
		return code;
	}

	protected String obtainCode(HttpServletRequest request) {
		return request.getParameter(codeParameter);
	}

	protected String obtainPassword(HttpServletRequest request) {
		return request.getParameter(passwordParameter);
	}

	protected String obtainUsername(HttpServletRequest request) {
		return request.getParameter(usernameParameter);
	}

	protected void setDetails(HttpServletRequest request,
			UsernamePasswordAuthenticationToken authRequest) {
		authRequest.setDetails(authenticationDetailsSource
				.buildDetails(request));
	}

	public void setUsernameParameter(String usernameParameter) {
		Assert.hasText(usernameParameter,
				"Username parameter must not be empty or null");
		this.usernameParameter = usernameParameter;
	}

	public void setPasswordParameter(String passwordParameter) {
		Assert.hasText(passwordParameter,
				"Password parameter must not be empty or null");
		this.passwordParameter = passwordParameter;
	}

	public void setPostOnly(boolean postOnly) {
		this.postOnly = postOnly;
	}

	public final String getUsernameParameter() {
		return usernameParameter;
	}

	public final String getPasswordParameter() {
		return passwordParameter;
	}

	public String getCodeParameter() {
		return codeParameter;
	}

	public void setCodeParameter(String codeParameter) {
		this.codeParameter = codeParameter;
	}

	public static final String SPRING_SECURITY_FORM_USERNAME_KEY = "username";
	public static final String SPRING_SECURITY_FORM_PASSWORD_KEY = "password";
	public static final String SPRING_SECURITY_FORM_CODE_KEY = "code";
	private String usernameParameter;
	private String passwordParameter;
	private String codeParameter;
	private boolean postOnly;
}
