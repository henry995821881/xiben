package com.xiben.sso.site.client.web;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.xiben.sso.client.model.User;
import com.xiben.sso.site.client.util.UserUtil;

@Controller
public class Action1  {

	
	

	@RequestMapping("/page1") 
	public String toLogin(HttpServletRequest request) {
		
		System.out.println("");
		User user = UserUtil.getUser();
		return "page1";
	}

}
