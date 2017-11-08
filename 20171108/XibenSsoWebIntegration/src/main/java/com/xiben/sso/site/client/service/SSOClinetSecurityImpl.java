package com.xiben.sso.site.client.service;

import org.springframework.stereotype.Service;

@Service
public class SSOClinetSecurityImpl implements SSOClinetSecurity {

	public boolean isInterface(String url) {
		return false;
	}

	public String getNOLoginJson(String url) {
		
		return "{'errorcode':9999,'url'"+url+"}";
	}

}
