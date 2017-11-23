package com.xiben.pm.rest.controler;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.xiben.pm.md.service.EmployeeService;
import com.xiben.pm.rest.request.ServiceRequest;
import com.xiben.pm.rest.request.secure.GetSmscodeRequestBody;
import com.xiben.pm.rest.request.secure.LoginRequestBody;
import com.xiben.pm.rest.request.secure.RefreshTokenRequestBody;
import com.xiben.pm.rest.response.ServiceResponse;
import com.xiben.pm.rest.response.secure.LoginResponseBody;
import com.xiben.pm.utils.ThreadLocalHelper;
import com.xiben.sso.client.delegate.UaasRestServiceDelegator;
import com.xiben.sso.client.delegate.UaasRestServiceDelegatorImpl;
import com.xiben.sso.client.model.AccessToken;
import com.xiben.sso.client.model.User;
import com.xiben.sso.client.utils.Constants;

@RestController
@RequestMapping(value="/public/login")  
public class LoginController extends BaseController {
    
	private static final Logger logger = Logger.getLogger(LoginController.class);

    private UaasRestServiceDelegator uaasDelegator = UaasRestServiceDelegatorImpl.instance();  
    
	@Autowired
	EmployeeService employeeService;
    
    @RequestMapping(value="login",method = RequestMethod.POST)
    public ServiceResponse<LoginResponseBody> login(@RequestBody ServiceRequest<LoginRequestBody> request) {
    	ServiceResponse<LoginResponseBody> response = new ServiceResponse<LoginResponseBody>();
    	try
    	{
    		response.setPrivatefield(request.getPrivatefield());
	    	AccessToken accesstoken = null;
	    		    	
	    	if (Constants.GRANT_TYPE_PASSWORD.equals(request.getReqdata().getGrant_type()))
	    	{
	    		accesstoken = uaasDelegator.getAccessTokenByPassword(request.getReqdata().getClient_id(), request.getReqdata().getClient_secret(), request.getReqdata().getUsername(), request.getReqdata().getPassword());
	    	}
	    	
	    	if (Constants.GRANT_TYPE_SMSCODE.equals(request.getReqdata().getGrant_type()))
	    	{
	    		accesstoken = uaasDelegator.getAccessTokenBySmsCode(request.getReqdata().getClient_id(), request.getReqdata().getClient_secret(), request.getReqdata().getUsername(), request.getReqdata().getPassword());
	    	}
	
	    	User uaasUser = uaasDelegator.getUserInfo(accesstoken.getAccess_token(),"");
	    	
	    	employeeService.synchronizeUser(uaasUser);
	    	
	    	LoginResponseBody body  = new LoginResponseBody();
	    	body.setToken(accesstoken);
	    	body.setUser(generateRestUser(uaasUser));
	    	
	    	response.setResdata(body);
	    	
    	}catch(Exception e)
    	{
    		logger.error(e.getMessage(), e);
    		
    		response.setCode(Constants._BIZ_ERROR);
    		response.setMsg(e.getMessage());
    	}
    	return response;
    }
    
    @RequestMapping(value="refreshtoken",method = RequestMethod.POST)
    public ServiceResponse<AccessToken> refreshtoken(@RequestBody ServiceRequest<RefreshTokenRequestBody> request) {
    	ServiceResponse<AccessToken> response = new ServiceResponse<AccessToken>();
    	try {
    
	    	response.setPrivatefield(request.getPrivatefield());
	    	AccessToken body = uaasDelegator.refreshToken(request.getReqdata().getClient_id(), request.getReqdata().getClient_secret(), request.getReqdata().getRefresh_token());
	    	
	    	response.setResdata(body);
    	
    	}catch(Exception e)
    	{
    		logger.error(e.getMessage(), e);
    		
    		response.setCode(Constants._BIZ_ERROR);
    		response.setMsg(e.getMessage());
    	}
        return response;
    }
    

    @RequestMapping(value="getsmscode",method = RequestMethod.POST)
    public ServiceResponse<Void> getsmscode(@RequestBody ServiceRequest<GetSmscodeRequestBody> request) {
    	ServiceResponse<Void> response = new ServiceResponse<Void>();
    	response.setPrivatefield(request.getPrivatefield());
    	try
    	{
    		if(request.getReqdata().getBiztype()!=1)
    		{
    			String accesstoken = ThreadLocalHelper.getAccessToken();
    			
    			User user = UaasRestServiceDelegatorImpl.instance().checkToken(accesstoken);
    			
    			employeeService.synchronizeUser(user);
    		}
    		
    		uaasDelegator.getSmsCode(ThreadLocalHelper.getAccessToken(), request.getReqdata().getMobile(), request.getReqdata().getBiztype());
    	
    	}catch(Exception e)
    	{
    		logger.error(e.getMessage(), e);
    		
    		response.setCode(2000);
    		response.setMsg(e.getMessage());
    	}
        return response;
    }
}