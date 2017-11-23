package com.xiben.pm.rest.controler;

import org.apache.log4j.Logger;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.xiben.pm.rest.model.RestUser;
import com.xiben.pm.rest.request.ServiceRequest;
import com.xiben.pm.rest.request.secure.ChangePasswordRequestBody;
import com.xiben.pm.rest.request.secure.ChangePaypassRequestBody;
import com.xiben.pm.rest.request.secure.ChangePhoneRequestBody;
import com.xiben.pm.rest.request.secure.ChangeSecureLevelRequestBody;
import com.xiben.pm.rest.request.secure.GetSecureUnidRequestBody;
import com.xiben.pm.rest.response.ServiceResponse;
import com.xiben.pm.rest.response.secure.GetSecureUnidResonseBody;
import com.xiben.pm.utils.ThreadLocalHelper;
import com.xiben.sso.client.delegate.UaasRestServiceDelegator;
import com.xiben.sso.client.delegate.UaasRestServiceDelegatorImpl;
import com.xiben.sso.client.model.User;

@RestController
@RequestMapping(value="/security/security")  
public class SecurityController  extends BaseController {
	private static final Logger logger = Logger.getLogger(SecurityController.class);
	
	private UaasRestServiceDelegator uaasDelegator = UaasRestServiceDelegatorImpl.instance();  
	
    @RequestMapping(value="changepassword",method = RequestMethod.POST)
    public ServiceResponse<Void> changePassword(@RequestBody ServiceRequest<ChangePasswordRequestBody> request) {
    	ServiceResponse<Void> response = new ServiceResponse<Void>();
    	response.setPrivatefield(request.getPrivatefield());
    	
    	try
    	{
    		uaasDelegator.setupPassword(ThreadLocalHelper.getAccessToken(), request.getReqdata().getPhone(), request.getReqdata().getCode(), request.getReqdata().getNewpass());
    		
    		
    	}catch(Exception e)
    	{
    		logger.error(e.getMessage(), e);
    		response.setCode(2000);
    		response.setMsg(e.getMessage());
    	}
    	
        return response;
    }
    
    @RequestMapping(value="changephone",method = RequestMethod.POST)
    public ServiceResponse<RestUser> changePhone(@RequestBody ServiceRequest<ChangePhoneRequestBody> request) {
    	
    	ServiceResponse<RestUser> response = new ServiceResponse<RestUser>();
    	response.setPrivatefield(request.getPrivatefield());
    	try
    	{
    	
    		User uaasUser = uaasDelegator.changeMobile(ThreadLocalHelper.getAccessToken(), request.getReqdata().getSecureunid(), request.getReqdata().getNewphone(), request.getReqdata().getNewcode());

    		response.setResdata(this.generateRestUser(uaasUser));
    	}catch(Exception e)
    	{
    		logger.error(e.getMessage(), e);
    		
    		response.setCode(2000);
    		response.setMsg(e.getMessage());
    	}
    	
        return response;
    }
    
    @RequestMapping(value="getsecureunid",method = RequestMethod.POST)
    public ServiceResponse<GetSecureUnidResonseBody> getSecureUnid(@RequestBody ServiceRequest<GetSecureUnidRequestBody> request) {
    	
    	ServiceResponse<GetSecureUnidResonseBody> response = new ServiceResponse<GetSecureUnidResonseBody>();
    	response.setPrivatefield(request.getPrivatefield());
    	
    	
    	try
    	{
    		String securityunid = uaasDelegator.getSecureUnid(ThreadLocalHelper.getAccessToken(), request.getReqdata().getMobile(), request.getReqdata().getBiztype(), request.getReqdata().getCode());
    		
        	GetSecureUnidResonseBody body = new GetSecureUnidResonseBody();
        	body.setSecureunid(securityunid);
        	
        	response.setResdata(body);
    	}catch(Exception e)
    	{
    		logger.error(e.getMessage(), e);
    		
    		response.setCode(2000);
    		response.setMsg(e.getMessage());
    	}
    
        return response;
    }
    
    @RequestMapping(value="changesecurelevel",method = RequestMethod.POST)
    public ServiceResponse<RestUser> changeSecureLevel(@RequestBody ServiceRequest<ChangeSecureLevelRequestBody> request) {
    	
    	ServiceResponse<RestUser> response = new ServiceResponse<RestUser>();
    	response.setPrivatefield(request.getPrivatefield());

    	try
    	{
    		User uaasUser = uaasDelegator.setupPayPassword(ThreadLocalHelper.getAccessToken(), request.getReqdata().getSecureunid(), 0, 2, "", "", request.getReqdata().getSecuritylevel());
    				
        	response.setResdata(this.generateRestUser(uaasUser));
    	}catch(Exception e)
    	{
    		logger.error(e.getMessage(), e);
    		
    		response.setCode(2000);
    		response.setMsg(e.getMessage());
    	}
    	
        return response;
    }
   
    @RequestMapping(value="changepaypass",method = RequestMethod.POST)
    public ServiceResponse<RestUser> changePaypass(@RequestBody ServiceRequest<ChangePaypassRequestBody> request) {
    	
    	ServiceResponse<RestUser> response = new ServiceResponse<RestUser>();
    	response.setPrivatefield(request.getPrivatefield());
    	try
    	{
    		User uaasUser = uaasDelegator.setupPayPassword(ThreadLocalHelper.getAccessToken(), request.getReqdata().getSecureunid(), request.getReqdata().getOptype(), 3, request.getReqdata().getOldpaypass(), request.getReqdata().getPaypassword(), request.getReqdata().getSecuritylevel());
	    	
        	response.setResdata(this.generateRestUser(uaasUser));
    	}catch(Exception e)
    	{
    		logger.error(e.getMessage(), e);
    		
    		response.setCode(2000);
    		response.setMsg(e.getMessage());
    	}
    	
        return response;
    } 
}
