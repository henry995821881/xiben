package com.xiben.sso.client.delegate;

import com.xiben.sso.client.delegate.response.ServiceResponse;
import com.xiben.sso.client.dto.ChangeUserInfoDto;
import com.xiben.sso.client.exception.SessionTimeoutException;
import com.xiben.sso.client.exception.UaasBusinessException;
import com.xiben.sso.client.model.AccessToken;
import com.xiben.sso.client.model.User;
import com.xiben.sso.client.model.UserRightModel;

public interface UaasRestServiceDelegator {
	User getAccessTokenByAuthorizationCode(String orgcode,String sign) throws UaasBusinessException;
	AccessToken getAccessTokenBySmsCode(String clientId,String clientSecret, String username, String smscode) throws UaasBusinessException;
	AccessToken getAccessTokenByPassword(String clientId,String clientSecret,String username, String password) throws UaasBusinessException;
	User checkToken(String accesstoken) throws UaasBusinessException,SessionTimeoutException;
	User getUserInfo(String accesstoken,String phone) throws UaasBusinessException,SessionTimeoutException;
	AccessToken refreshToken(String clientId,String clientSecret,String refreshToken) throws UaasBusinessException;
	
	void getSmsCode(String access_token,String mobile,int biztype) throws UaasBusinessException,SessionTimeoutException;
	void verifySmscode(String access_token,String mobile,int biztype,String smscode) throws UaasBusinessException,SessionTimeoutException;
	public String getSecureUnid(String access_token,String mobile,int biztype,String smscode) throws UaasBusinessException,SessionTimeoutException;
	
	void verifySecurityUnid(String access_token,String securityunid,int biztype) throws UaasBusinessException,SessionTimeoutException;
	User changeMobile(String access_token,String securityunid,String newmobile,String newsmscode) throws UaasBusinessException,SessionTimeoutException;
	void changePassword(String access_token,String oldpassword,String newpassword) throws UaasBusinessException,SessionTimeoutException;
	
	User changeUserInfo(ChangeUserInfoDto model) throws UaasBusinessException,SessionTimeoutException;
	
	UserRightModel getUserRight(String accesstoken) throws UaasBusinessException,SessionTimeoutException;
	
	void setupPassword(String access_token,String mobile,String smscode,String newpassword) throws UaasBusinessException,SessionTimeoutException;
	
	User setupPayPassword(String access_token,String secureunid,int optype,int opscope,String oldpaypass,String paypassword,int securitylevel) throws UaasBusinessException,SessionTimeoutException;
	
}
