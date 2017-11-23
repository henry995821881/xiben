package com.xiben.sso.client.delegate;

import org.apache.log4j.Logger;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;
import com.xiben.common.utils.JsonUtils;
import com.xiben.sso.client.delegate.request.ServiceRequest;
import com.xiben.sso.client.delegate.request.body.ChangePasswordRequestBody;
import com.xiben.sso.client.delegate.request.body.ChangemobileRequestBody;
import com.xiben.sso.client.delegate.request.body.CheckTokenRequestBody;
import com.xiben.sso.client.delegate.request.body.GetAccessTokenOrgCodeRequestBody;
import com.xiben.sso.client.delegate.request.body.GetAccesstokenRequestBody;
import com.xiben.sso.client.delegate.request.body.GetSecureUnidRequestBody;
import com.xiben.sso.client.delegate.request.body.GetSmsCodeRequestBody;
import com.xiben.sso.client.delegate.request.body.GetUserInfoRequestBody;
import com.xiben.sso.client.delegate.request.body.GetUserRightRequestBody;
import com.xiben.sso.client.delegate.request.body.RefreshTokenRequestBody;
import com.xiben.sso.client.delegate.request.body.SetupPasswordRequest;
import com.xiben.sso.client.delegate.request.body.SetupPayPasswordRequestBody;
import com.xiben.sso.client.delegate.request.body.VerifySecurityUnidRequestBody;
import com.xiben.sso.client.delegate.request.body.VerifySmscodeRequestBody;
import com.xiben.sso.client.delegate.response.ServiceResponse;
import com.xiben.sso.client.delegate.response.body.GetSecureUnidResponseBody;
import com.xiben.sso.client.dto.ChangeUserInfoDto;
import com.xiben.sso.client.exception.SessionTimeoutException;
import com.xiben.sso.client.exception.UaasBusinessException;
import com.xiben.sso.client.model.AccessToken;
import com.xiben.sso.client.model.User;
import com.xiben.sso.client.model.UserRightModel;
import com.xiben.sso.client.utils.Constants;
import com.xiben.sso.client.utils.HttpPostUtils;
import com.xiben.sso.client.utils.ResponseTools;
import com.xiben.sso.client.utils.UaasRestConfigUtils;

public class UaasRestServiceDelegatorImpl implements UaasRestServiceDelegator{
	private static final Logger logger = Logger.getLogger(UaasRestServiceDelegatorImpl.class);
	
	private static UaasRestServiceDelegator _instance;

	
	public static UaasRestServiceDelegator instance() {
		if (null==_instance)
			_instance = new UaasRestServiceDelegatorImpl();
		return _instance;
	}
	
	public User getAccessTokenByAuthorizationCode(String orgcode,String sign) throws UaasBusinessException{
		
		ServiceRequest<GetAccessTokenOrgCodeRequestBody> request = new ServiceRequest<GetAccessTokenOrgCodeRequestBody>();
		GetAccessTokenOrgCodeRequestBody requestBody = new GetAccessTokenOrgCodeRequestBody();
		requestBody.setOrganization_code(orgcode);
		requestBody.setSign(sign);
		request.setReqdata(requestBody);

		
    	String responseStr =  HttpPostUtils.doPost(UaasRestConfigUtils.getOrgcodeLogonUrl(),JsonUtils.toJson(request));
    	logger.info(responseStr);
    	
    	ServiceResponse<User> response = (ServiceResponse<User>) JSON.parseObject(responseStr, new TypeReference<ServiceResponse<User>>(){});
    	
    	if (ResponseTools.isError(response))
    	{
    		throw new UaasBusinessException(response.getMsg());
    	}
    	
		return response.getResdata();
	}

	public AccessToken getAccessTokenBySmsCode(String clientId,String clientSecret, String username, String smscode)  throws UaasBusinessException{

		ServiceRequest<GetAccesstokenRequestBody> request = new ServiceRequest<GetAccesstokenRequestBody>();
		GetAccesstokenRequestBody body = new GetAccesstokenRequestBody();
		body.setClient_id(clientId);
		body.setClient_secret(clientSecret);
		body.setGrant_type(Constants.GRANT_TYPE_SMSCODE);
		body.setUsername(username);
		body.setPassword(smscode);
		request.setReqdata(body);
	  	
    	String responseStr =  HttpPostUtils.doPost(UaasRestConfigUtils.getRestTokenUrl(),JsonUtils.toJson(request));
    	logger.info(responseStr);
    	
    	ServiceResponse<AccessToken> response = (ServiceResponse<AccessToken>) JSON.parseObject(responseStr, new TypeReference<ServiceResponse<AccessToken>>(){});
    	logger.info(response);
    	
    	if (ResponseTools.isError(response))
    	{
    		throw new UaasBusinessException(response.getMsg());
    	}
		
		return response.getResdata();
	}

	public AccessToken getAccessTokenByPassword(String clientId,String clientSecret,String username, String password)  throws UaasBusinessException{

		ServiceRequest<GetAccesstokenRequestBody> request = new ServiceRequest<GetAccesstokenRequestBody>();
		GetAccesstokenRequestBody body = new GetAccesstokenRequestBody();
		body.setClient_id(clientId);
		body.setClient_secret(clientSecret);
		body.setGrant_type(Constants.GRANT_TYPE_PASSWORD);
		body.setUsername(username);
		body.setPassword(password);
		request.setReqdata(body);

    	String responseStr =  HttpPostUtils.doPost(UaasRestConfigUtils.getRestTokenUrl(),JsonUtils.toJson(request));
    	
    	ServiceResponse<AccessToken> response = (ServiceResponse<AccessToken>) JSON.parseObject(responseStr, new TypeReference<ServiceResponse<AccessToken>>(){});

    	if (ResponseTools.isError(response))
    	{
    		throw new UaasBusinessException(response.getMsg());
    	}
    	
		return response.getResdata();
	}

	public AccessToken refreshToken(String clientId,String clientSecret,String refreshToken) throws UaasBusinessException
	{
		
		ServiceRequest<RefreshTokenRequestBody> request = new ServiceRequest<RefreshTokenRequestBody>();
		RefreshTokenRequestBody body = new RefreshTokenRequestBody();
		body.setClient_id(clientId);
		body.setClient_secret(clientSecret);
		body.setGrant_type(Constants.GRANT_TYPE_REFRESH_TOKEN);
		body.setRefresh_token(refreshToken);
		request.setReqdata(body);


    	String responseStr =  HttpPostUtils.doPost(UaasRestConfigUtils.getRestTokenUrl(),JsonUtils.toJson(request));
    	
    	ServiceResponse<AccessToken> response = (ServiceResponse<AccessToken>) JSON.parseObject(responseStr, new TypeReference<ServiceResponse<AccessToken>>(){});

    	if (ResponseTools.isError(response))
    	{
    		throw new UaasBusinessException(response.getMsg());
    	}
    	
		return response.getResdata();
	}
	
	public User checkToken(String accesstoken)  throws UaasBusinessException,SessionTimeoutException{

		ServiceRequest<CheckTokenRequestBody> request = new ServiceRequest<CheckTokenRequestBody>();
		CheckTokenRequestBody body = new CheckTokenRequestBody();
		body.setAccess_token(accesstoken);
		request.setReqdata(body);
		
    	String responseStr =  HttpPostUtils.doPost(UaasRestConfigUtils.getCheckTokenUrl(),JsonUtils.toJson(request));

    	ServiceResponse<User> response = (ServiceResponse<User>) JSON.parseObject(responseStr, new TypeReference<ServiceResponse<User>>(){});
    	
    	if (ResponseTools.isError(response))
    	{
    		throw new UaasBusinessException(response.getMsg());
    	}
    	
    	if (ResponseTools.isSessionTimeout(response))
    	{
    		throw new SessionTimeoutException(response.getMsg());
    	}
    	
		return response.getResdata();
	}

	public User getUserInfo(String accesstoken,String phone)  throws UaasBusinessException,SessionTimeoutException{
		
		ServiceRequest<GetUserInfoRequestBody> request = new ServiceRequest<GetUserInfoRequestBody>();
		GetUserInfoRequestBody body = new GetUserInfoRequestBody();
		body.setAccess_token(accesstoken);
		body.setPhone(phone);
		
		request.setReqdata(body);
		
    	String responseStr =  HttpPostUtils.doPost(UaasRestConfigUtils.getUserInfoUrl(),JsonUtils.toJson(request));
		
    	logger.info(responseStr);
    	
    	ServiceResponse<User> response = (ServiceResponse<User>) JSON.parseObject(responseStr, new TypeReference<ServiceResponse<User>>(){});
    	if (ResponseTools.isError(response))
    	{
    		throw new UaasBusinessException(response.getMsg());
    	}
    	
    	if (ResponseTools.isSessionTimeout(response))
    	{
    		throw new SessionTimeoutException(response.getMsg());
    	}
    	
		return response.getResdata();
	}
	
	public void getSmsCode(String access_token,String mobile,int biztype) throws UaasBusinessException,SessionTimeoutException{
		ServiceRequest<GetSmsCodeRequestBody> request = new ServiceRequest<GetSmsCodeRequestBody>();
		GetSmsCodeRequestBody body = new GetSmsCodeRequestBody();
		body.setAccess_token(access_token);
		body.setMobile(mobile);
		body.setBiztype(biztype);
		request.setReqdata(body);
    	String responseStr =  HttpPostUtils.doPost(UaasRestConfigUtils.getSmsCodeUrl(),JsonUtils.toJson(request));
    	
    	logger.info(responseStr);
    	
    	ServiceResponse<Void> response = (ServiceResponse<Void>) JSON.parseObject(responseStr, new TypeReference<ServiceResponse<Void>>(){});
    	if (ResponseTools.isError(response))
    	{
    		throw new UaasBusinessException(response.getMsg());
    	}
    	
    	if (ResponseTools.isSessionTimeout(response))
    	{
    		throw new SessionTimeoutException(response.getMsg());
    	}
	}
	
	public void verifySmscode(String access_token,String mobile,int biztype,String smscode) throws UaasBusinessException,SessionTimeoutException{
		ServiceRequest<VerifySmscodeRequestBody> request = new ServiceRequest<VerifySmscodeRequestBody>();
		VerifySmscodeRequestBody body = new VerifySmscodeRequestBody();
		body.setAccess_token(access_token);
		body.setMobile(mobile);
		body.setBiztype(biztype);
		body.setSmscode(smscode);
		request.setReqdata(body);
    	String responseStr =  HttpPostUtils.doPost(UaasRestConfigUtils.getVerifySmsCodeUrl(),JsonUtils.toJson(request));
    	
    	logger.info(responseStr);
    	
    	ServiceResponse<Void> response = (ServiceResponse<Void>) JSON.parseObject(responseStr, new TypeReference<ServiceResponse<Void>>(){});
    	if (ResponseTools.isError(response))
    	{
    		throw new UaasBusinessException(response.getMsg());
    	}
    	
    	if (ResponseTools.isSessionTimeout(response))
    	{
    		throw new SessionTimeoutException(response.getMsg());
    	}	
	}
	
	public String getSecureUnid(String access_token,String mobile,int biztype,String smscode) throws UaasBusinessException,SessionTimeoutException{
		ServiceRequest<GetSecureUnidRequestBody> request = new ServiceRequest<GetSecureUnidRequestBody>();
		GetSecureUnidRequestBody body = new GetSecureUnidRequestBody();
		body.setAccess_token(access_token);
		body.setMobile(mobile);
		body.setBiztype(biztype);
		body.setCode(smscode);
		request.setReqdata(body);
    	String responseStr =  HttpPostUtils.doPost(UaasRestConfigUtils.getSecurityUnidUrl(),JsonUtils.toJson(request));
    	
    	logger.info(responseStr);
    	
    	ServiceResponse<GetSecureUnidResponseBody> response = (ServiceResponse<GetSecureUnidResponseBody>) JSON.parseObject(responseStr, new TypeReference<ServiceResponse<GetSecureUnidResponseBody>>(){});
    	
    	logger.info(response);
    	
    	logger.info(JsonUtils.toJson(response));
    	
    	if (ResponseTools.isError(response))
    	{
    		throw new UaasBusinessException(response.getMsg());
    	}
    	
    	if (ResponseTools.isSessionTimeout(response))
    	{
    		throw new SessionTimeoutException(response.getMsg());
    	}	
    	return response.getResdata().getSecureunid();
	}
	
	public void verifySecurityUnid(String access_token,String securityunid,int biztype) throws UaasBusinessException,SessionTimeoutException{
		ServiceRequest<VerifySecurityUnidRequestBody> request = new ServiceRequest<VerifySecurityUnidRequestBody>();
		VerifySecurityUnidRequestBody body = new VerifySecurityUnidRequestBody();
		body.setAccess_token(access_token);
		body.setBiztype(biztype);
		body.setSecureunid(securityunid);
		request.setReqdata(body);
    	String responseStr =  HttpPostUtils.doPost(UaasRestConfigUtils.verifySecurityUnidUrl(),JsonUtils.toJson(request));
    	
    	logger.info(responseStr);
    	
    	ServiceResponse<Void> response = (ServiceResponse<Void>) JSON.parseObject(responseStr, new TypeReference<ServiceResponse<Void>>(){});
    	
    	logger.info(response);
    	
    	logger.info(JsonUtils.toJson(response));
    	
    	if (ResponseTools.isError(response))
    	{
    		throw new UaasBusinessException(response.getMsg());
    	}
    	
    	if (ResponseTools.isSessionTimeout(response))
    	{
    		throw new SessionTimeoutException(response.getMsg());
    	}	
	}
	
	public User changeMobile(String access_token,String securityunid,String newmobile,String newsmscode) throws UaasBusinessException,SessionTimeoutException{
		ServiceRequest<ChangemobileRequestBody> request = new ServiceRequest<ChangemobileRequestBody>();
		ChangemobileRequestBody body = new ChangemobileRequestBody();
		body.setAccess_token(access_token);
		body.setNewmobile(newmobile);
		body.setNewsmscode(newsmscode);
		body.setSecureunid(securityunid);
		request.setReqdata(body);
    	String responseStr =  HttpPostUtils.doPost(UaasRestConfigUtils.changeMobileUrl(),JsonUtils.toJson(request));
    	
    	logger.info(responseStr);
    	
    	ServiceResponse<User> response = (ServiceResponse<User>) JSON.parseObject(responseStr, new TypeReference<ServiceResponse<User>>(){});
    	
    	logger.info(response);
    	
    	logger.info(JsonUtils.toJson(response));
    	
    	if (ResponseTools.isError(response))
    	{
    		throw new UaasBusinessException(response.getMsg());
    	}
    	
    	if (ResponseTools.isSessionTimeout(response))
    	{
    		throw new SessionTimeoutException(response.getMsg());
    	}	
    	return response.getResdata();
	}
	
	public void changePassword(String access_token,String oldpassword,String newpassword) throws UaasBusinessException,SessionTimeoutException{
		ServiceRequest<ChangePasswordRequestBody> request = new ServiceRequest<ChangePasswordRequestBody>();
		ChangePasswordRequestBody body = new ChangePasswordRequestBody();
		body.setAccess_token(access_token);
		body.setOldpassword(oldpassword);
		body.setNewpassword(newpassword);
		request.setReqdata(body);
		
    	String responseStr =  HttpPostUtils.doPost(UaasRestConfigUtils.changePasswordUrl(),JsonUtils.toJson(request));
    	
    	logger.info(responseStr);
    	
    	ServiceResponse<Void> response = (ServiceResponse<Void>) JSON.parseObject(responseStr, new TypeReference<ServiceResponse<Void>>(){});
    	
    	logger.info(response);
    	
    	logger.info(JsonUtils.toJson(response));
    	
    	if (ResponseTools.isError(response))
    	{
    		throw new UaasBusinessException(response.getMsg());
    	}
    	
    	if (ResponseTools.isSessionTimeout(response))
    	{
    		throw new SessionTimeoutException(response.getMsg());
    	}	
	}
	
	public User changeUserInfo(ChangeUserInfoDto model) throws UaasBusinessException,SessionTimeoutException{
		ServiceRequest<ChangeUserInfoDto> request = new ServiceRequest<ChangeUserInfoDto>();

		request.setReqdata(model);
		
    	String responseStr =  HttpPostUtils.doPost(UaasRestConfigUtils.changeuserInfoURL(),JsonUtils.toJson(request));
    	
    	logger.info(responseStr);
    	
    	ServiceResponse<User> response = (ServiceResponse<User>) JSON.parseObject(responseStr, new TypeReference<ServiceResponse<User>>(){});
    	
    	logger.info(response);
    	
    	logger.info(JsonUtils.toJson(response));
    	
    	if (ResponseTools.isError(response))
    	{
    		throw new UaasBusinessException(response.getMsg());
    	}
    	
    	if (ResponseTools.isSessionTimeout(response))
    	{
    		throw new SessionTimeoutException(response.getMsg());
    	}	
    	return response.getResdata();
	}
	
	public UserRightModel getUserRight(String accesstoken) throws UaasBusinessException,SessionTimeoutException
	{
		ServiceRequest<GetUserRightRequestBody> request = new ServiceRequest<GetUserRightRequestBody>();
		GetUserRightRequestBody body = new GetUserRightRequestBody();
		body.setAccess_token(accesstoken);
		request.setReqdata(body);
		
    	String responseStr =  HttpPostUtils.doPost(UaasRestConfigUtils.getUserRightUrl(),JsonUtils.toJson(request));
    	
    	logger.info(responseStr);
    	
    	ServiceResponse<UserRightModel> response = (ServiceResponse<UserRightModel>) JSON.parseObject(responseStr, new TypeReference<ServiceResponse<UserRightModel>>(){});
    	
    	logger.info(response);
    	
    	logger.info(JsonUtils.toJson(response));
    	
    	if (ResponseTools.isError(response))
    	{
    		throw new UaasBusinessException(response.getMsg());
    	}
    	
    	if (ResponseTools.isSessionTimeout(response))
    	{
    		throw new SessionTimeoutException(response.getMsg());
    	}	
    	return response.getResdata();
	}
	
	public void setupPassword(String access_token,String mobile,String smscode,String newpassword) throws UaasBusinessException,SessionTimeoutException{
		ServiceRequest<SetupPasswordRequest> request = new ServiceRequest<SetupPasswordRequest>();
		SetupPasswordRequest body = new SetupPasswordRequest();
		body.setAccess_token(access_token);
		body.setMobile(mobile);
		body.setSmscode(smscode);
		body.setNewpass(newpassword);
		request.setReqdata(body);
		
    	String responseStr =  HttpPostUtils.doPost(UaasRestConfigUtils.getSetupPassUrl(),JsonUtils.toJson(request));
    	
    	logger.info(responseStr);
    	
    	ServiceResponse<Void> response = (ServiceResponse<Void>) JSON.parseObject(responseStr, new TypeReference<ServiceResponse<Void>>(){});
    	
    	logger.info(response);
    	
    	logger.info(JsonUtils.toJson(response));
    	
    	if (ResponseTools.isError(response))
    	{
    		throw new UaasBusinessException(response.getMsg());
    	}
    	
    	if (ResponseTools.isSessionTimeout(response))
    	{
    		throw new SessionTimeoutException(response.getMsg());
    	}	
	}
	
	public User setupPayPassword(String access_token,String secureunid,int optype,int opscope,String oldpaypass,String paypassword,int securitylevel) throws UaasBusinessException,SessionTimeoutException
	{
		ServiceRequest<SetupPayPasswordRequestBody> request = new ServiceRequest<SetupPayPasswordRequestBody>();
		SetupPayPasswordRequestBody body = new SetupPayPasswordRequestBody();
		body.setAccess_token(access_token);
		body.setSecureunid(secureunid);
		body.setOptype(optype);
		body.setOpscope(opscope);
		body.setOldpaypass(oldpaypass);
		body.setPaypassword(paypassword);
		body.setSecuritylevel(securitylevel);
		request.setReqdata(body);

		
    	String responseStr =  HttpPostUtils.doPost(UaasRestConfigUtils.getSetupPayPassUrl(),JsonUtils.toJson(request));
    	
    	logger.info(responseStr);
    	
    	ServiceResponse<User> response = (ServiceResponse<User>) JSON.parseObject(responseStr, new TypeReference<ServiceResponse<User>>(){});
    	
    	logger.info(JsonUtils.toJson(response));
    	
    	if (ResponseTools.isError(response))
    	{
    		throw new UaasBusinessException(response.getMsg());
    	}
    	
    	if (ResponseTools.isSessionTimeout(response))
    	{
    		throw new SessionTimeoutException(response.getMsg());
    	}	
    	return response.getResdata();
	}
	
	public static void main(String[] agrs) throws UaasBusinessException,SessionTimeoutException
	{
		UaasRestServiceDelegatorImpl delegate = new UaasRestServiceDelegatorImpl();
		
		delegate.getAccessTokenByAuthorizationCode("dd54714f271e4adfbef76bb0b389aa5a", "MCwCFGdsT1OLN5KFHK6KbXVNNGKNDmAsAhQY3DVz5zVjHHfDWO6eOQtZeSk_-w");
		
		//AccessToken accesstoken = delegate.getAccessTokenByPassword("mobile_client", "mobile_secret", "admin", "admin");
		
		
		
		//logger.info("userInfo:"+JsonUtils.toJson(accesstoken));
		
//		User user = delegate.checkToken("c3f9627b-d6a3-4bfb-b3a8-d2196f8c4375");
//		
//	    logger.info("userInfo:"+JsonUtils.toJson(user));
		
	    
	    
		//c3f9627b-d6a3-4bfb-b3a8-d2196f8c4375
		
		//delegate.getUserInfo("", "139169296151");
		
//		logger.info(JsonUtils.toJson(accesstoken));
//		
////		delegate.getSmsCode(accesstoken.getAccess_token(), "23916929615",2);
//		
//	    User user = delegate.checkToken(accesstoken.getAccess_token());
//	    logger.info("userInfo:"+JsonUtils.toJson(user));
//		
//	    user = delegate.getUserInfo(accesstoken.getAccess_token());
//	    logger.info("userInfo:"+JsonUtils.toJson(user));
//		
//	    ChangeUserInfoDto model = new ChangeUserInfoDto();
//	    model.setAccess_token(accesstoken.getAccess_token());
//	    model.setAuthstatus(1);
//	    model.setDispname("测试");
//	    model.setEmail("wby@96369.net");
//	    model.setIsshownickname(1);
//	    model.setLogourl("");
//	    model.setNickname("昵称");
//	    model.setSex(1);
//	    model.setSuperioruserid(0);
//	    model.setTruename("西本");
//	    
//	    user = delegate.changeUserInfo(model);
//	    
//	    delegate.getUserRight(accesstoken.getAccess_token());
//	    
//	    delegate.getSmsCode(accesstoken.getAccess_token(), "13916929615", 4);
//	    
//	    delegate.setupPassword(accesstoken.getAccess_token(), "13916929615", "123456", "admin");
	    
//	    delegate.getSmsCode(accesstoken.getAccess_token(), "13916929615", 6);
//	    String securityunid = delegate.getSecureUnid(accesstoken.getAccess_token(), "13916929615",6,"123456");
//	    
//	    delegate.setupPayPassword(accesstoken.getAccess_token(), securityunid, 0, 3, "","888888", 2);
	    
//		String securityunid = delegate.getSecureUnid(accesstoken.getAccess_token(), "23916929615",2,"123456");
		
		//delegate.verifySecurityUnid(accesstoken.getAccess_token(), securityunid,7);
//		delegate.getSmsCode(accesstoken.getAccess_token(), "13916929615",3);
		
//		delegate.changeMobile(accesstoken.getAccess_token(), securityunid, "13916929615", "123456");
		
		//delegate.changePassword(accesstoken.getAccess_token(), "admin1", "admin");
		//logger.info("accesstoken="+JsonUtils.toJson(accesstoken));
		
//	    accesstoken = delegate.getAccessTokenByPassword("mobile_client", "mobile_secret", "admin", "admin");
//	    logger.info(JsonUtils.toJson(accesstoken));
//	    
   
	}

}
