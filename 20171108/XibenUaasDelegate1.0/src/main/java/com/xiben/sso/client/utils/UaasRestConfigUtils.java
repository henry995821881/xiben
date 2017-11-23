package com.xiben.sso.client.utils;

import java.io.IOException;
import java.util.Properties;

public class UaasRestConfigUtils {
	public static Properties prop = null;
	static
	{
		prop = new Properties();
		try {
			
			prop.load(UaasRestConfigUtils.class.getClassLoader().getResourceAsStream(Constants.uaas_config_filename));
		} catch (IOException e) {
			throw new RuntimeException("config read error!",e);
		}
	}
	
	private static String getValue(String key)
	{
		String value = prop.getProperty(key);
		return value;
	}
	
	public static String getUaasOauthRestUrl()
	{
		return getValue(Constants.uaas_rest_url_key);
	}
	
	public static String getUserInfoUrl()
	{
		return getUaasOauthRestUrl()+Constants.USER_INFO_SUFIX;
	}
	
	public static String getRestTokenUrl()
	{
		return getUaasOauthRestUrl()+Constants.REST_TOKEN_SUFIX;
	}
	
	public static String getCheckTokenUrl() {
		return getUaasOauthRestUrl()+Constants.CHECK_TOKEN_SUFIX;	
	}
	
	public static String getSmsCodeUrl()
	{
		return getUaasOauthRestUrl()+"/getsmscode";
	}
	
	public static String getVerifySmsCodeUrl()
	{
		return getUaasOauthRestUrl()+"/verifysmscode";
	}
	
	public static String getSecurityUnidUrl()
	{
		return getUaasOauthRestUrl()+"/getsecureunid";
	}
	
	public static String verifySecurityUnidUrl()
	{
		return getUaasOauthRestUrl()+"/verifysecureunid";
	}
	
	public static String changeMobileUrl()
	{
		return getUaasOauthRestUrl()+"/changemobile";
	}
	
	public static String changePasswordUrl()
	{
		return getUaasOauthRestUrl()+"/changepassword";
	}
	
	public static String changeuserInfoURL()
	{
		return getUaasOauthRestUrl()+"/changeuserinfo";
	}
	
	public static String getUserRightUrl()
	{
		return getUaasOauthRestUrl()+"/getuserright";
	}
	
	public static String getSetupPassUrl()
	{
		return getUaasOauthRestUrl()+"/setuppassword";
	}
	
	public static String getSetupPayPassUrl()
	{
		return getUaasOauthRestUrl()+"/setuppaypassword";
	}
	
	public static String getOrgcodeLogonUrl()
	{
		return getUaasOauthRestUrl()+"/logon_byorgcode";
	}
}
