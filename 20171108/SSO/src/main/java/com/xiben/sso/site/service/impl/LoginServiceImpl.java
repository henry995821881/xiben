package com.xiben.sso.site.service.impl;




import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.xiben.common.codec.XibenBase64;
import com.xiben.common.security.DsaCoder;
import com.xiben.sso.site.bean.OauthClientDetailsWithBLOBs;
import com.xiben.sso.site.bean.OauthClientUser;
import com.xiben.sso.site.bean.OauthCode;
import com.xiben.sso.site.bean.User_;
import com.xiben.sso.site.dao.OauthClientDetailsMapper;
import com.xiben.sso.site.dao.OauthClientUserMapper;
import com.xiben.sso.site.dao.OauthCodeMapper;
import com.xiben.sso.site.dao.User_Mapper;
import com.xiben.sso.site.service.LoginService;
import com.xiben.sso.site.util.SSOUtil;


@Service("LoginService")
public class LoginServiceImpl implements LoginService{
	
	public static Logger logger = LoggerFactory.getLogger(LoginServiceImpl.class);		
	
	
	@Value("${sso.organization_code.privateKey}")
	private String sso_organization_code_privateKey;
	

	@Autowired
	private User_Mapper user_Mapper;
	@Autowired	
	private OauthClientDetailsMapper oauthClientDetailsMapper;
	@Autowired
	private OauthCodeMapper oauthCodeMapper;
	@Autowired
    private OauthClientUserMapper oauthClientUserMapper;
	



	public User_  queryByUsername(String username){
		
		return  user_Mapper.queryByUsername(username);
		
		
	}
	


	/**
	 * 账号密码认证登录
	 * 如果用户没有登录，那么调用登录方法，
	 * 如果用户已经登录了，且再去登录界面登录，那么返回false，
	 * 其他的都返回true
	 * @param username
	 * @param password
	 * @return
	 */
	public boolean shiroLogin(String username, String password) {
		
		boolean flag = true;
		
    	// UsernamePasswordToken token = new UsernamePasswordToken("usernmae", DecriptUtil.MD5("password"));  
    	UsernamePasswordToken token =new UsernamePasswordToken(username, password);
    	Subject currentUser = SecurityUtils.getSubject();  
    	if (!currentUser.isAuthenticated()){
    		// token.setRememberMe(true);  
    		currentUser.login(token);//
    	}else if(currentUser.isAuthenticated() &&username!=null && !username.equals(currentUser.getPrincipal())){
    		//如果已经登录的用户不是正在登录的用户
    		flag= false;
    	}
    	
    	return flag;
	}







	public String getRedirectUrl(String salt, String client_id, String sign, String redirect_uri) throws Exception {
		
		
		 String redirect_uri_ = new String( XibenBase64.decode(redirect_uri), "UTF-8");
		 //计算code给外部应用
		 String organization_code =SSOUtil.getUUID();
		 
		 String organization_code_sign = getCodeSign(organization_code,sso_organization_code_privateKey);
		 
	    boolean flag = verifySign(salt,client_id,sign,redirect_uri_);	
	    
	    //删除该用户授权码
		String username = SecurityUtils.getSubject().getPrincipal() != null ? SecurityUtils.getSubject().getPrincipal().toString():"";
	    User_ user = user_Mapper.queryByUsername(username);
    	oauthCodeMapper.deleteByPrimaryKey(user.getId());
	    
	    
	    if(flag && verifyAppliction(client_id,SecurityUtils.getSubject().getPrincipal())){
	    	
	    	
	    	StringBuffer url = new StringBuffer("redirect:");
	    	if(redirect_uri_.indexOf("?")>0){
	    		url.append(redirect_uri_).append("&").append("organization_code=").append(organization_code)
	    		.append("&sign=").append(organization_code_sign).append("&")
	    		.append("username=").append(username);
	    	}else{
	    		
	    		url.append(redirect_uri_).append("?").append("organization_code=").append(organization_code)
	    		.append("&sign=").append(organization_code_sign).append("&")
	    		.append("username=").append(username);
	    		
	    	}
	    	
	    	//保存该用户授权码
	    	OauthCode record = new OauthCode();
	    	record.setCode(organization_code);
	    	record.setClientid(client_id);
	    	record.setUserid(user.getId());
	    	oauthCodeMapper.insertSelective(record);
	    	
	    	return url.toString();
	    	
	    }else{
	    	
	    	return "error";
	    }

	}






	
/***
 * 如果oauth_client_detail is_public =1  true
 * 或
 * 如果oauth_client_detail is_public =0 并且oauth_code 条件clientid  userid 查到数据    true
 * 
 * @param client_id
 * @param principal
 * @return
 */
	private boolean verifyAppliction(String client_id, Object principal) {
		
		boolean flag  = false;
		
		OauthClientDetailsWithBLOBs oauthClientDetails = oauthClientDetailsMapper.selectByPrimaryKey(client_id);
		if(oauthClientDetails.getIspublic().intValue() ==1){
			flag = true;
		}else{
			
			String username = principal != null ? principal.toString():"";
			User_ user = user_Mapper.queryByUsername(username);
			
			
			if(StringUtils.isNotBlank(username)&& user != null && user.getId()!=null){
				
				OauthClientUser param = new OauthClientUser();
				param.setUserId(user.getId());
				param.setClientId(client_id);
				
				List<OauthClientUser>  oauthClientUserList = oauthClientUserMapper.queryByClientIdAndUserId(param);
				  if(oauthClientUserList != null && oauthClientUserList.size()>0){
					  return true;
				  }
				  
			}
		
			
		}
		
		
        
		
		
		return flag;
	}



	private String getCodeSign(String organization_code, String sso_organization_code_privateKey2) throws Exception {
		DsaCoder coder = new DsaCoder();
		return coder.sign(organization_code, sso_organization_code_privateKey2);
		
	}



	private boolean verifySign(String salt, String client_id, String sign, String redirect_uri) throws Exception {
		DsaCoder coder = new DsaCoder();
		OauthClientDetailsWithBLOBs oauthClientDetails = oauthClientDetailsMapper.selectByPrimaryKey(client_id);	
		String publicKey = oauthClientDetails.getPublicKey();
		StringBuffer forSign = new StringBuffer();
		//签名串   salt={0}&client_id={1}&redirect_uri={2}
		forSign.append("salt=").append(salt).append("&")
		.append("client_id=").append(client_id).append("&")
		.append("redirect_uri=").append(redirect_uri);
		
	     return coder.verify(forSign.toString(), sign, publicKey);
	}












	public User_Mapper getUser_Mapper() {
		return user_Mapper;
	}

	public void setUser_Mapper(User_Mapper user_Mapper) {
		this.user_Mapper = user_Mapper;
	}



	public String getSso_organization_code_privateKey() {
		return sso_organization_code_privateKey;
	}



	public void setSso_organization_code_privateKey(String sso_organization_code_privateKey) {
		this.sso_organization_code_privateKey = sso_organization_code_privateKey;
	}

	
	
	public OauthClientDetailsMapper getOauthClientDetailsMapper() {
		return oauthClientDetailsMapper;
	}



	public void setOauthClientDetailsMapper(OauthClientDetailsMapper oauthClientDetailsMapper) {
		this.oauthClientDetailsMapper = oauthClientDetailsMapper;
	}



	public OauthCodeMapper getOauthCodeMapper() {
		return oauthCodeMapper;
	}



	public void setOauthCodeMapper(OauthCodeMapper oauthCodeMapper) {
		this.oauthCodeMapper = oauthCodeMapper;
	}



	public OauthClientUserMapper getOauthClientUserMapper() {
		return oauthClientUserMapper;
	}



	public void setOauthClientUserMapper(OauthClientUserMapper oauthClientUserMapper) {
		this.oauthClientUserMapper = oauthClientUserMapper;
	}
	
	
	
}
