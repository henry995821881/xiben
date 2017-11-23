package com.xiben.sso.client.utils;

public class Constants {
	public static final String uaas_config_filename="uaas_rest_config.properties";
	public static final String uaas_rest_url_key="uaas_rest_url";
	
	public static final String REST_TOKEN_SUFIX="/rest_token";
	public static final String USER_INFO_SUFIX="/user_info";
	public static final String CHECK_TOKEN_SUFIX="/check_token";
	
	public static final String GRANT_TYPE_AUTHORIZATION_CODE="authorization_code";
	public static final String GRANT_TYPE_PASSWORD="password";
	public static final String GRANT_TYPE_SMSCODE="smscode";
	public static final String GRANT_TYPE_REFRESH_TOKEN="refresh_token";
	
	public static final int _SUCCESS =1000;
	public static final int _SESSION_TIMEOUT = 3000;
	public static final int _SYS_ERROR = 9999;
	public static final int _BIZ_ERROR = 2000;
}
