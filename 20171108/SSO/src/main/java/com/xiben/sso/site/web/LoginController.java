package com.xiben.sso.site.web;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import com.xiben.sso.site.bean.User_;
import com.xiben.sso.site.service.LoginService;
import com.xiben.sso.site.util.ValidateCode;



@Controller
@RequestMapping("/sso")
public class LoginController {
	
	@Autowired
	private LoginService loginService;
	
	public static Logger logger = LoggerFactory.getLogger(LoginController.class);		
	

	
	
	
	
	
	
	/**
	 * 跳到登录页面
	 * @param request
	 * @param model
	 * @param salt
	 * @param client_id
	 * @param sign
	 * @param redirect_uri
	 * @return
	 */
	@RequestMapping("/login")
	public String toLoginPage(HttpServletRequest request, Model model,HttpSession session, String salt, String client_id, String sign,
			String redirect_uri) {

	
		// 之前要做一下验证
		try {
			
			
			
			
			if (SecurityUtils.getSubject().isAuthenticated()) {// 如果已经登录了返回主页，或者返回code给外部应用

				if (redirect_uri != null) {// 来自外部应用
					
					
				
					String url = loginService.getRedirectUrl(salt, client_id, sign, redirect_uri);

					return url;

				} else {

					return "redirect:/sso/main";
				}

			} else {//如果没有登录调转到登录界面

			     if(redirect_uri!= null){//外部应用
			    	
			    	 if(null ==session.getAttribute("redirect_uri")){
			    		 //先把参数保存到session中
			    		    session.setAttribute("salt", salt);
			    		    session.setAttribute("client_id", client_id);
			    		    session.setAttribute("sign", sign);
			    		    session.setAttribute("redirect_uri", redirect_uri);
			    	 }
			    	 
			     }
                   
					

				 
					return "login";
			}
		} catch (Exception e) {
			logger.error(e.getMessage(),e);
			return "error";
		}

	}
	
	 
	 
	 
	 
	 
	 
	 
	
	
	
	/**
	 * 页面提交验证登录
	 * @param request
	 * @param session
	 * @param model
	 * @param username
	 * @param password
	 * @return
	 */
    @RequestMapping(value="/checklogin")
    public String checkLogin(HttpServletRequest request,HttpSession session,String validatecode, Model model,String username,String password
    		
    		) {
    	//web应用重定向过来的url
    	//page=  "redirect:http://localhost:8080/sso/login/?salt=1&client_id=123&sign=567&redirect_uri="; 
    	String page = "redirect:/sso/main";
    	Map<String,String> result= new HashMap<String,String>();
        try{
        	
        	
        	//验证验证码
        	if(!validatecode.equalsIgnoreCase(SecurityUtils.getSubject().getSession().getAttribute("validatecode").toString())){
        		
        		
        		 logger.error("登录失败--图片验证码");
                 result.put("msg", "图形验证码错误");
                 page ="login";
                 return page;
        	}
        	
        	
        	
        	if(null != session.getAttribute("redirect_uri")){//web应用重定向过来的登录过来的登录
        		
        		

        		
        		
        		    UsernamePasswordToken token =new UsernamePasswordToken(username, password);
            	    Subject currentUser = SecurityUtils.getSubject();  
            		currentUser.login(token);
            		
            		page= loginService.getRedirectUrl(
            				session.getAttribute("salt").toString(), 
            				session.getAttribute("client_id").toString(), 
            				session.getAttribute("sign").toString(), 
            				session.getAttribute("redirect_uri").toString()
            				);
        	}else{//本地登录
        		
        		boolean shiroLogin = loginService.shiroLogin(username,password);
        		
        		if(!shiroLogin){
        			 page ="login";
        			 result.put("msg", "请先退出后，再登录");
        		}
        	}
     
        }catch(Exception ex){
           logger.error("登录失败",ex);
           result.put("msg", "登录失败,请登录");
           page ="login";
        }
  
        model.addAttribute("result",result);
    	
        return page;
    }
    
   
    
    /**
     * 主页
     * @param request
     * @param model
     * @param session
     * @return
     */
    @RequestMapping("/main")
    public String toMain(HttpServletRequest request, Model model,HttpSession session) {
    	
    	try {
			User_ loginUser = loginService.queryByUsername(SecurityUtils.getSubject().getPrincipal().toString());
			
			
			model.addAttribute("loginUser", loginUser);
		} catch (Exception e) {
			logger.error("登录主页失败！",e);
			return "error";
		}
    	
        return "main";
    }




    
    /** 
     * 响应验证码页面 
     * @return 
     */  
    @RequestMapping(value="/validateCode")  
    public String validateCode(HttpServletRequest request,HttpServletResponse response) throws Exception{  
        // 设置响应的类型格式为图片格式  
        response.setContentType("image/jpeg");  
        //禁止图像缓存。  
        response.setHeader("Pragma", "no-cache");  
        response.setHeader("Cache-Control", "no-cache");  
        response.setDateHeader("Expires", 0);  
      
      
        ValidateCode vCode = new ValidateCode(120,40,5,100); 
        SecurityUtils.getSubject().getSession().setAttribute("validatecode", vCode.getCode());
       // HttpSession session = request.getSession();  
        //session.setAttribute("validatecode", vCode.getCode());  
        vCode.write(response.getOutputStream());  
        return null;  
    } 







	public LoginService getLoginService() {
		return loginService;
	}











	public void setLoginService(LoginService loginService) {
		this.loginService = loginService;
	}



	   @RequestMapping("/login1")
	    public String toLogin1() {
	    	
	    
	    	
	        return "login1";
	    }





	   @RequestMapping("/login2")
	    public String toLogin2() {
	    	
	    
	    	
	        return "login2";
	    }






  
    
    
    
    
    



    
    
}
