package com.xiben.pm.rest.filter;

import java.io.IOException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import com.xiben.common.utils.StringUtils;
import com.xiben.pm.md.service.EmployeeService;
import com.xiben.pm.utils.Constants;
import com.xiben.pm.utils.ResponseBuilder;
import com.xiben.pm.utils.ThreadLocalHelper;
import com.xiben.sso.client.delegate.UaasRestServiceDelegatorImpl;
import com.xiben.sso.client.exception.SessionTimeoutException;
import com.xiben.sso.client.model.User;

public class LogonSecurityFilter implements Filter{

	
	private static final Logger logger = Logger.getLogger(LogonSecurityFilter.class);
	
	private WebApplicationContext wac=null;
	
	public void init(FilterConfig filterConfig) throws ServletException {

		wac =WebApplicationContextUtils.getRequiredWebApplicationContext(filterConfig.getServletContext());
	}
	
	
	public boolean isValidateUser(User user)
	{
		if(null==user)
			return false;
		
		if (StringUtils.isEmpty(user.getUsername()))
			return false;
	
		
		return true;
	}

	public boolean isSecurityResource(HttpServletRequest request)
	{
		String strPattern = "^/*"+request.getContextPath()+"/+"+"security";
		String uri = request.getRequestURI();
		Pattern pattern = Pattern.compile(strPattern);
		Matcher matcher = pattern.matcher(uri);
		return matcher.find();
	}
	
	
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {
		
		HttpServletRequest httpRquest = (HttpServletRequest)request;
		HttpServletResponse httpResponse = (HttpServletResponse)response;
		
		String accessToken = httpRquest.getHeader(Constants.HTTPRequestHeader.xibenesb_accesstoken);
		
		ThreadLocalHelper.setAccessToken(accessToken);
		
		if (isSecurityResource(httpRquest))
		{
			if (StringUtils.isEmpty(accessToken))
			{
//				User user = new User();
//				user.setUserid(21);
//
//				ThreadLocalHelper.setUser(user);
//				chain.doFilter(request, response);

				
				ResponseBuilder.buildErrorMessageToResponse(2000, "accesstoken is empty", httpResponse,Constants.ESBResponseCode.ERR_ACCESSTOKEN);
				return;
			}
			
			User user = null;
			try
			{
				user = UaasRestServiceDelegatorImpl.instance().checkToken(accessToken);
			
				//Synchronize User
				EmployeeService employeeService = (EmployeeService)wac.getBean("employeeS");
				employeeService.synchronizeUser(user);
				
				ThreadLocalHelper.setUser(user);
				
			}catch(SessionTimeoutException e)
			{
				logger.error(e.getMessage(), e);
				ResponseBuilder.buildErrorMessageToResponse(2000, "accesstoken timeout", httpResponse,Constants.ESBResponseCode.BIZ_ACCESSTOKEN_TIMEOUT);
				return;
			}catch(Exception e)
			{
				logger.error(e.getMessage(), e);
				ResponseBuilder.buildErrorMessageToResponse(2000, "accesstoken is not validate", httpResponse,Constants.ESBResponseCode.ERR_ACCESSTOKEN);	
				return;
			}
		}
		
		

		chain.doFilter(request, response);
	}

	public void destroy() {
		
	
	}

}
