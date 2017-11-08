package com.xiben.sso.site.client.filter;


import java.util.Date;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.apache.shiro.web.filter.authc.UserFilter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;

import com.xiben.common.codec.XibenBase64;
import com.xiben.common.security.DsaCoder;
import com.xiben.sso.site.client.service.SSOClinetSecurity;

public class SSOShiroFilter extends UserFilter {

	public static Logger logger = LoggerFactory.getLogger( SSOShiroFilter.class);		
	
	
	@Value("${sso.organization_code_publicKey}")
	private String organization_code_publicKey;

    @Value("${web.client_id}")
	private String client_id;
	
    @Value("${web.sso_login_privateKey}")
	private String Login_privateKey;
    
    @Value("${web.sso_login_url}")
    private String SSOLogin_url;
    
    
    
   
    
    @Autowired
    private SSOClinetSecurity sSOClinetSecurity;

	public String getSSOLoginUrl(String current_url) throws Exception {
		
		 String salt =String.valueOf(new Date().getTime());
		
		DsaCoder coder = new DsaCoder();	
		
		
		//签名串   salt={0}&client_id={1}&redirect_uri={2}
		String forSign = "salt="+salt+"&client_id="+client_id+"&redirect_uri="+current_url;
		String sign2 = coder.sign(forSign, Login_privateKey);
		
		
		StringBuffer page_ = new StringBuffer();
		page_.append(SSOLogin_url).append("?");
		page_.append("salt=").append(salt).append("&");
		page_.append("client_id=").append(client_id).append("&");
		page_.append("redirect_uri=").append(XibenBase64.encode(current_url.getBytes("UTF-8"))).append("&");
		page_.append("sign=").append(sign2);
		
		return page_.toString();
	}

	public boolean verify(String organization_code, String sign) throws Exception {
		DsaCoder coder = new DsaCoder();
		return coder.verify(organization_code,sign,organization_code_publicKey);
	}

	
	@Override
	protected boolean preHandle(ServletRequest request, ServletResponse response) throws Exception {
		HttpServletRequest req = (HttpServletRequest)request;

		HttpServletResponse httpResponse = (HttpServletResponse)response;
		
		if((!SecurityUtils.getSubject().isAuthenticated())&& StringUtils.isBlank(request.getParameter("organization_code"))){//如果没有sso没有返回code，重定向到sso
			
			
			String url = this.getSSOLoginUrl(req.getRequestURL().toString());
			if(sSOClinetSecurity.isInterface(req.getRequestURL().toString())){
				
				
				httpResponse.getWriter().print(sSOClinetSecurity.getNOLoginJson(url));
				
				return false;
			}
			httpResponse.sendRedirect(url);
			
			return false;
			
		}
		
		//已经登录
		if(SecurityUtils.getSubject().isAuthenticated()){
			
			return true;
		}
		
		
		
		//验证organization_code
		 if(!this.verify(request.getParameter("organization_code"), request.getParameter("sign"))){
			 httpResponse.sendRedirect("error");
				return false; 
		 }
		
		 //登录成功
		 Subject currentUser = SecurityUtils.getSubject();
		 
		 //password 写死的
		 UsernamePasswordToken token = new UsernamePasswordToken(request.getParameter("organization_code"), ""); 
		 //web1登录
		 currentUser.login(token);
		 
		 logger.debug("");
		// Object o = SecurityUtils.getSubject().getSession().getAttribute("currentUser");
		
		// logger.debug(  SecurityUtils.getSubject().getSession().getAttribute("currentUser"));
		// SecurityUtils.getSecurityManager()
		 //SecurityUtils.
		
		
		return super.preHandle(request, response);
	}
 
	
	
}
