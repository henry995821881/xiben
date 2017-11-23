package com.xiben.pm.rest.controler;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.xiben.common.qiniu.UploadTools;
import com.xiben.pm.exception.PMRuntimeException;
import com.xiben.pm.md.bean.MdCompanyEx;
import com.xiben.pm.md.bean.MdNoticeEx;
import com.xiben.pm.md.pojo.MdAttchment;
import com.xiben.pm.md.pojo.MdCompany;
import com.xiben.pm.md.request.AddCompanyManagerParam;
import com.xiben.pm.md.request.AddDepartmentParam;
import com.xiben.pm.md.request.AddDepartmentmemberParam;
import com.xiben.pm.md.request.GetCompanyDetailParam;
import com.xiben.pm.md.request.GetCompanyMgrListParam;
import com.xiben.pm.md.request.GetDepartmemberListParam;
import com.xiben.pm.md.request.GetDepartmentDetailParam;
import com.xiben.pm.md.request.GetDepartmentListParam;
import com.xiben.pm.md.request.GetMessageDetailParam;
import com.xiben.pm.md.request.ModifyDepartmentParam;
import com.xiben.pm.md.request.RemoveCompanyManagerParam;
import com.xiben.pm.md.request.RemoveDepartmentParam;
import com.xiben.pm.md.request.RemoveDepartmentmemberParam;
import com.xiben.pm.md.request.UploadCompanyLogoParam;
import com.xiben.pm.md.request.getDownloadUrlParam;
import com.xiben.pm.md.response.GetCompanyDetailBody;
import com.xiben.pm.md.response.GetCompanyMgrListBody;
import com.xiben.pm.md.response.GetDepartmemberListbody;
import com.xiben.pm.md.response.GetDepartmentDetailBody;
import com.xiben.pm.md.response.GetDepartmentListResult;
import com.xiben.pm.md.response.GetDownloadUrlResult;
import com.xiben.pm.md.response.GetMessageDetailBody;
import com.xiben.pm.md.response.GetMessageList;
import com.xiben.pm.md.service.AttachmentService;
import com.xiben.pm.md.service.EmployeeService;
import com.xiben.pm.md.service.MdCompanyService;
import com.xiben.pm.md.service.MdDeptService;
import com.xiben.pm.md.service.MdNoticeService;
import com.xiben.pm.rest.model.RestUser;
import com.xiben.pm.rest.model.UploadAttach;
import com.xiben.pm.rest.request.ServiceRequest;
import com.xiben.pm.rest.request.md.AddCompanyRequestBody;
import com.xiben.pm.rest.request.md.ChangeSuperiorPmRequestBody;
import com.xiben.pm.rest.request.md.FinishUserinfoRequestBody;
import com.xiben.pm.rest.request.md.GetCompanyListRequestBody;
import com.xiben.pm.rest.request.md.GetUploadTokenRequestBody;
import com.xiben.pm.rest.request.md.GetUserinfoRequestBody;
import com.xiben.pm.rest.request.md.UploadUserlogoRequestBody;
import com.xiben.pm.rest.response.ServiceResponse;
import com.xiben.pm.rest.response.md.AddCompanyResponseBody;
import com.xiben.pm.rest.response.md.GetCompanyListResponseBody;
import com.xiben.pm.rest.response.md.GetUploadTokenResponseBody;
import com.xiben.pm.utils.ThreadLocalHelper;
import com.xiben.sso.client.delegate.UaasRestServiceDelegator;
import com.xiben.sso.client.delegate.UaasRestServiceDelegatorImpl;
import com.xiben.sso.client.dto.ChangeUserInfoDto;
import com.xiben.sso.client.model.User;

@RestController
@RequestMapping(value="/security/masterdata")  
public class MdController  extends BaseController{
	private static final Logger logger = Logger.getLogger(MdController.class);
	
	@Autowired
	AttachmentService attachmentService;
	
	@Autowired
	EmployeeService employeeService;
	@Autowired
	MdDeptService mdDeptService;
	
	@Autowired
	MdNoticeService mdNoticeService;
	
	@Autowired
    private MdCompanyService mdCompanyService;
	
    private UaasRestServiceDelegator uaasDelegator = UaasRestServiceDelegatorImpl.instance();  
	
    
    @RequestMapping(value="finishuserinfo",method = RequestMethod.POST)
    public ServiceResponse<RestUser> finishUserInfo(@RequestBody ServiceRequest<FinishUserinfoRequestBody> request) {
    	ServiceResponse<RestUser> response = new ServiceResponse<RestUser>();
    	response.setPrivatefield(request.getPrivatefield());
    	try
    	{
    		User uaasUser = ThreadLocalHelper.getUser();
    		
    		if(request.getReqdata().getLogo()!=null)
    		{
	    		String downloadurl = this.saveUserLogoAttachment(uaasUser, request.getReqdata().getLogo(),attachmentService);
	    		uaasUser.setLogourl(downloadurl);
    		}
    		ChangeUserInfoDto dto = getChangeUserInfoDto(uaasUser,ThreadLocalHelper.getAccessToken());
    		
	    	dto.setNickname(request.getReqdata().getName());
	    	dto.setDispname(request.getReqdata().getName());	 
	    	dto.setSex(request.getReqdata().getSex());

	  
	    	uaasUser = uaasDelegator.changeUserInfo(dto);
	    	
	    	employeeService.updateUserLogo(uaasUser.getLogourl(), uaasUser.getUserid());
	    	
	    	response.setResdata(generateRestUser(uaasUser));
    	}catch(Exception e)
    	{
    		logger.error(e.getMessage(), e);
    		
    		response.setCode(2000);
    		response.setMsg(e.getMessage());
    	}
        return response;
    }
    
    @RequestMapping(value="getuserinfo",method = RequestMethod.POST)
    public ServiceResponse<RestUser> getUserInfo(@RequestBody ServiceRequest<GetUserinfoRequestBody> request) {
    	
    	ServiceResponse<RestUser> response = new ServiceResponse<RestUser>();
    	response.setPrivatefield(request.getPrivatefield());

    	try
    	{
	    	User uaasUser = ThreadLocalHelper.getUser();
	    	
	    	response.setResdata(generateRestUser(uaasUser));
    	}catch(Exception e)
    	{
    		logger.error(e.getMessage(), e);
    		
    		response.setCode(2000);
    		response.setMsg(e.getMessage());
    	}
    	
        return response;
    }
    
    @RequestMapping(value="uploaduserlogo",method = RequestMethod.POST)
    public ServiceResponse<RestUser> uploadUserLogo(@RequestBody ServiceRequest<UploadUserlogoRequestBody> request) {
    	
    	ServiceResponse<RestUser> response = new ServiceResponse<RestUser>();
    	response.setPrivatefield(request.getPrivatefield());

    	try
    	{
    		User uaasUser = ThreadLocalHelper.getUser();
    		
    		String downloadurl = this.saveUserLogoAttachment(uaasUser, request.getReqdata().getLogo(),attachmentService);
    		uaasUser.setLogourl(downloadurl);
    		

    		
	    	ChangeUserInfoDto dto = this.getChangeUserInfoDto(uaasUser, ThreadLocalHelper.getAccessToken());
	    	dto.setLogourl(downloadurl);
	
    		
	    	uaasUser = uaasDelegator.changeUserInfo(dto);
	    	
    		employeeService.updateUserLogo(downloadurl, uaasUser.getUserid());
	    	
	    	
	    	response.setResdata(generateRestUser(uaasUser));
    	}catch(Exception e)
    	{
    		logger.error(e.getMessage(), e);
    		
    		response.setCode(2000);
    		response.setMsg(e.getMessage());
    	}
    	
        return response;
    }
    
    @RequestMapping(value="changesuperiorpm",method = RequestMethod.POST)
    public ServiceResponse<RestUser> changeSuperiorPm(@RequestBody ServiceRequest<ChangeSuperiorPmRequestBody> request) {
    	ServiceResponse<RestUser> response = new ServiceResponse<RestUser>();
    	response.setPrivatefield(request.getPrivatefield());

    	try
    	{
	    	User uaasUser = ThreadLocalHelper.getUser();
	    	
	    	response.setResdata(generateRestUser(uaasUser));
    	}catch(Exception e)
    	{
    		logger.error(e.getMessage(), e);
    		
    		response.setCode(2000);
    		response.setMsg(e.getMessage());
    	}
    	
        return response;
    }
    
    @RequestMapping(value="getuploadtoken",method = RequestMethod.POST)
    public ServiceResponse<GetUploadTokenResponseBody> getuploadtoken(@RequestBody ServiceRequest<GetUploadTokenRequestBody> request) {
    	ServiceResponse<GetUploadTokenResponseBody> response = new ServiceResponse<GetUploadTokenResponseBody>();
    	response.setPrivatefield(request.getPrivatefield());

    	try
    	{
    		if (request.getReqdata().getType()!=1&&request.getReqdata().getType()!=2)
    			throw new Exception("参数传入有误");
    		
    		GetUploadTokenResponseBody body = new GetUploadTokenResponseBody();
    		
    		if (request.getReqdata().getType()==1)
    		{
    			body.setUptoken(UploadTools.getPrivateUploadToken());
    		}
    		
    		if (request.getReqdata().getType()==2)
    		{
    			body.setUptoken(UploadTools.getPublicUploadToken());
    		}
    		
    		response.setResdata(body);
    		
    	}catch(Exception e)
    	{
    		logger.error(e.getMessage(), e);
    		
    		response.setCode(2000);
    		response.setMsg(e.getMessage());
    	}
    	
        return response;
    }
    
    /*
     * 
     * henry
     * 获取公司列表
     */
    @RequestMapping(value="getcompanylist",method = RequestMethod.POST)
    public ServiceResponse<List<GetCompanyListResponseBody>> getcompanylist(@RequestBody ServiceRequest<GetCompanyListRequestBody> request) {
    	ServiceResponse<List<GetCompanyListResponseBody>> response = new ServiceResponse<List<GetCompanyListResponseBody>>();
    	response.setPrivatefield(request.getPrivatefield());

    	try
    	{
    		
    		List<MdCompanyEx> list = mdCompanyService.queryCompany(ThreadLocalHelper.getUser().getUserid());
    		List<GetCompanyListResponseBody> data = new ArrayList<GetCompanyListResponseBody>();
    		for(MdCompanyEx c :list){
    			GetCompanyListResponseBody b = new GetCompanyListResponseBody();
    			b.setCompanyid(c.getCompanyid());
    			b.setFullname(c.getFullname());
    			b.setShortname(c.getShortname());
    			b.setLogo(c.getLogo());
    			b.setUserright(c.getRole());
    			data.add(b);
    		}
    		
         response.setResdata(data);

    	}catch(PMRuntimeException e1){
    		
            logger.error(e1.getMessage(), e1);
    		response.setCode(2000);
    		response.setMsg(e1.getMessage());
    	}catch(Exception e)
    	{
    		logger.error(e.getMessage(), e);
    		response.setCode(2000);
    		response.setMsg("系统异常");
    	}
    	
        return response;
    }
    
    /**
     * 添加公司
     * @param request
     * @return
     */
    @RequestMapping(value="addcompany",method = RequestMethod.POST)
    public ServiceResponse<AddCompanyResponseBody> addcompany(@RequestBody ServiceRequest<AddCompanyRequestBody> request) {
    	ServiceResponse<AddCompanyResponseBody> response = new ServiceResponse<AddCompanyResponseBody>();
    	response.setPrivatefield(request.getPrivatefield());

    	try
    	{
    		UploadAttach attach = request.getReqdata().getLogo();
    		User user = ThreadLocalHelper.getUser();
    		
    		MdAttchment mdAttachment = null;
    		if(attach ==null || StringUtils.isBlank(attach.getFk())){
    			
    		}else{
    			mdAttachment = new  MdAttchment();
    			mdAttachment.setFilekey(attach.getFk());
    			mdAttachment.setFilename(attach.getFn());
    			mdAttachment.setFilesize(attach.getFs());
    			mdAttachment.setFiletype(attach.getFt());
    		}
    		MdCompany md_ = new MdCompany();
    		md_.setFullname(request.getReqdata().getFullname());
    		md_.setShortname(request.getReqdata().getShortname());
    		mdCompanyService.addCompany(md_, user, mdAttachment);

    	}catch(PMRuntimeException e1){
    		
            logger.error(e1.getMessage(), e1);
    		response.setCode(2000);
    		response.setMsg(e1.getMessage());
    	}catch(Exception e)
    	{
    		logger.error(e.getMessage(), e);
    		response.setCode(2000);
    		response.setMsg("系统异常");
    	}
    	
        return response;
    }
    
    
    
    /*
     * 
     * henry
     * 公司详情
     */
    @RequestMapping(value="getcompanydetail",method = RequestMethod.POST)
    public ServiceResponse<GetCompanyDetailBody> getCompanyDetail(@RequestBody ServiceRequest<GetCompanyDetailParam> request) {
    	ServiceResponse<GetCompanyDetailBody> response = new ServiceResponse<GetCompanyDetailBody>();
    	response.setPrivatefield(request.getPrivatefield());

    	try
    	{
    		Integer companyid = request.getReqdata().getCompanyid();
    		User user = ThreadLocalHelper.getUser();
    		GetCompanyDetailBody companyDetail = mdCompanyService.getCompanyDetail(user.getUserid(), companyid);
    		response.setResdata(companyDetail);
       
    	}catch(PMRuntimeException e1){
    		
            logger.error(e1.getMessage(), e1);
    		response.setCode(2000);
    		response.setMsg(e1.getMessage());
    	}catch(Exception e)
    	{
    		logger.error(e.getMessage(), e);
    		response.setCode(2000);
    		response.setMsg("系统异常");
    	}
    	
        return response;
    }
   
    
    /**
     * 获取公司管理员
     * @param request
     * @return
     */
    @RequestMapping(value="getcompanymgrlist",method = RequestMethod.POST)
    public ServiceResponse<GetCompanyMgrListBody> getcompanymgrlist(@RequestBody ServiceRequest<GetCompanyMgrListParam> request) {
    	ServiceResponse<GetCompanyMgrListBody> response = new ServiceResponse<GetCompanyMgrListBody>();
    	response.setPrivatefield(request.getPrivatefield());

    	try
    	{
    		Integer companyid = request.getReqdata().getCompanyid();
    		User user = ThreadLocalHelper.getUser();
    		 GetCompanyMgrListBody body = mdCompanyService.getCompanymgrList(user.getUserid(), companyid); 
    		 response.setResdata(body);
       
    	}catch(PMRuntimeException e1){
    		
            logger.error(e1.getMessage(), e1);
    		response.setCode(2000);
    		response.setMsg(e1.getMessage());
    	}catch(Exception e)
    	{
    		logger.error(e.getMessage(), e);
    		response.setCode(2000);
    		response.setMsg("系统异常");
    	}
    	
        return response;
    }
     
 
    
    /**
     * 2.7.11.	添加公司管理员
     * @param request
     * @return
     */
    @RequestMapping(value="addcompanymanager",method = RequestMethod.POST)
    public ServiceResponse<Object> addcompanymanager(@RequestBody ServiceRequest<AddCompanyManagerParam> request) {
    	ServiceResponse<Object> response = new ServiceResponse<Object>();
    	response.setPrivatefield(request.getPrivatefield());

    	try
    	{
    		Integer companyid = request.getReqdata().getCompanyid();
    		String mgrphone = request.getReqdata().getMgrphone();
    		User user = ThreadLocalHelper.getUser();
    		mdCompanyService.addCompanyManager(user.getUserid(), companyid, mgrphone);
       
    	}catch(PMRuntimeException e1){
    		
            logger.error(e1.getMessage(), e1);
    		response.setCode(2000);
    		response.setMsg(e1.getMessage());
    	}catch(Exception e)
    	{
    		logger.error(e.getMessage(), e);
    		response.setCode(2000);
    		response.setMsg("系统异常");
    	}
    	
        return response;
    }
    
  
    /**
     * 	删除公司管理员
     * @param request
     * @return
     */
    @RequestMapping(value="removecompanymanager",method = RequestMethod.POST)
    public ServiceResponse<Object> removecompanymanager(@RequestBody ServiceRequest<RemoveCompanyManagerParam> request) {
    	ServiceResponse<Object> response = new ServiceResponse<Object>();
    	response.setPrivatefield(request.getPrivatefield());

    	try
    	{
    		Integer companyid = request.getReqdata().getCompanyid();
    		Integer mgruserid = request.getReqdata().getMgruserid();
    		User user = ThreadLocalHelper.getUser();
    		mdCompanyService.removeCompanyManager(user.getUserid(), companyid, mgruserid);
    	}catch(PMRuntimeException e1){
    		
            logger.error(e1.getMessage(), e1);
    		response.setCode(2000);
    		response.setMsg(e1.getMessage());
    	}catch(Exception e)
    	{
    		logger.error(e.getMessage(), e);
    		response.setCode(2000);
    		response.setMsg("系统异常");
    	}
    	
        return response;
    }
    
    
    /**
     * 	上传公司logo
     * @param request
     * @return
     */
    @RequestMapping(value="uploadcompanylogo",method = RequestMethod.POST)
    public ServiceResponse<Object> uploadcompanylogo(@RequestBody ServiceRequest<UploadCompanyLogoParam> request) {
    	ServiceResponse<Object> response = new ServiceResponse<Object>();
    	response.setPrivatefield(request.getPrivatefield());

    	try
    	{
    		Integer companyid = request.getReqdata().getCompanyid();
    		UploadAttach attach = request.getReqdata().getLogo();
    		
    		if(attach ==null){
    			throw new PMRuntimeException("附件不能空");
    		}
    		MdAttchment mdAttachment = new  MdAttchment();
    		mdAttachment.setFilekey(attach.getFk());
    		mdAttachment.setFilename(attach.getFn());
    		mdAttachment.setFilesize(attach.getFs());
    		mdAttachment.setFiletype(attach.getFt());
    		User user = ThreadLocalHelper.getUser();
    		
    		mdCompanyService.uploadCompanyLogo(user, companyid, mdAttachment);

    	}catch(PMRuntimeException e1){
    		
            logger.error(e1.getMessage(), e1);
    		response.setCode(2000);
    		response.setMsg(e1.getMessage());
    	}catch(Exception e)
    	{
    		logger.error(e.getMessage(), e);
    		response.setCode(2000);
    		response.setMsg("系统异常");
    	}
    	
        return response;
    }
     

    /**
     * 添加部门
     * @param request
     * @return
     */
    @RequestMapping(value="adddepartment",method = RequestMethod.POST)
    public ServiceResponse<Object> adddepartment(@RequestBody ServiceRequest<AddDepartmentParam> request) {
    	ServiceResponse<Object> response = new ServiceResponse<Object>();

    	response.setPrivatefield(request.getPrivatefield());

    	try
    	{

    		AddDepartmentParam reqdata = request.getReqdata();
    		User user = ThreadLocalHelper.getUser();	
    		
    		mdDeptService.addDepartment(user.getUserid(), reqdata);
    		

    	}catch(PMRuntimeException e1){
    		
            logger.error(e1.getMessage(), e1);
    		response.setCode(2000);
    		response.setMsg(e1.getMessage());
    	}catch(Exception e)
    	{
    		logger.error(e.getMessage(), e);
    		response.setCode(2000);
    		response.setMsg("系统异常");
    	}
    	
        return response;
    } 
    
    
    
    /**
     * 修改部门
     * @param request
     * @return
     */
    @RequestMapping(value="modifydepartment",method = RequestMethod.POST)
    public ServiceResponse<Object> modifydepartment(@RequestBody ServiceRequest<ModifyDepartmentParam> request) {
    	ServiceResponse<Object> response = new ServiceResponse<Object>();
    	response.setPrivatefield(request.getPrivatefield());

    	try
    	{
    		ModifyDepartmentParam reqdata = request.getReqdata();
    		User user = ThreadLocalHelper.getUser();	
    		
    		mdDeptService.modifyDepartment(user.getUserid(), reqdata);
    		

    	}catch(PMRuntimeException e1){
    		
            logger.error(e1.getMessage(), e1);
    		response.setCode(2000);
    		response.setMsg(e1.getMessage());
    	}catch(Exception e)
    	{
    		logger.error(e.getMessage(), e);
    		response.setCode(2000);
    		response.setMsg("系统异常");
    	}
    	
        return response;
    }
    
    
    
    /**
     * 移除部门
     * @param request
     * @return
     */
    @RequestMapping(value="removedepartment",method = RequestMethod.POST)
    public ServiceResponse<Object> removedepartment(@RequestBody ServiceRequest<RemoveDepartmentParam> request) {
    	ServiceResponse<Object> response = new ServiceResponse<Object>();
    	response.setPrivatefield(request.getPrivatefield());

    	try
    	{
    		 Integer deptid = request.getReqdata().getDeptid();
    		User user = ThreadLocalHelper.getUser();	
    		
    		mdDeptService.removeDepartment(user.getUserid(), deptid);
    		


    	}catch(PMRuntimeException e1){
    		
            logger.error(e1.getMessage(), e1);
    		response.setCode(2000);
    		response.setMsg(e1.getMessage());
    	}catch(Exception e)
    	{
    		logger.error(e.getMessage(), e);
    		response.setCode(2000);
    		response.setMsg("系统异常");
    	}
    	
        return response;

    }

    
    
    /**
     * 获取部门详情
     * @param request
     * @return
     */
    @RequestMapping(value="getdepartmentdetail",method = RequestMethod.POST)
    public ServiceResponse<GetDepartmentDetailBody> getdepartmentdetail(@RequestBody ServiceRequest<GetDepartmentDetailParam> request) {
    	ServiceResponse<GetDepartmentDetailBody> response = new ServiceResponse<GetDepartmentDetailBody>();
    	response.setPrivatefield(request.getPrivatefield());

    	try
    	{
    		Integer deptid = request.getReqdata().getDeptid();
    		String year = request.getReqdata().getYear();
    		String month = request.getReqdata().getMonth();
    		User user = ThreadLocalHelper.getUser();	
    		GetDepartmentDetailBody departmentDetail = mdDeptService.getDepartmentDetail(user.getUserid(), deptid, year, month);
    		response.setResdata(departmentDetail);
    		
    	}catch(PMRuntimeException e1){
    		
            logger.error(e1.getMessage(), e1);
    		response.setCode(2000);
    		response.setMsg(e1.getMessage());
    	}catch(Exception e)
    	{
    		logger.error(e.getMessage(), e);
    		response.setCode(2000);
    		response.setMsg("系统异常");
    	}
    	
        return response;
    } 
    
    
    
    
    /**
     * 获取部门成员
     * @param request
     * @return
     */
    @RequestMapping(value="getdepartmemberlist",method = RequestMethod.POST)
    public ServiceResponse<List<GetDepartmemberListbody>> getdepartmemberlist(@RequestBody ServiceRequest<GetDepartmemberListParam> request) {
    	ServiceResponse<List<GetDepartmemberListbody>> response = new ServiceResponse<List<GetDepartmemberListbody>>();
    	response.setPrivatefield(request.getPrivatefield());

    	try
    	{
    		 Integer deptid = request.getReqdata().getDeptid();
    		  User user = ThreadLocalHelper.getUser();	
    		
    		List<GetDepartmemberListbody> departmemberListbody = mdDeptService.getDepartmemberListbody(user.getUserid(), deptid);
    		response.setResdata(departmemberListbody);

    	}catch(PMRuntimeException e1){
    		
            logger.error(e1.getMessage(), e1);
    		response.setCode(2000);
    		response.setMsg(e1.getMessage());
    	}catch(Exception e)
    	{
    		logger.error(e.getMessage(), e);
    		response.setCode(2000);
    		response.setMsg("系统异常");
    	}
    	
        return response;
    }
    
    
    /**
     * 添加部门成员
     * @param request
     * @return
     */
    @RequestMapping(value="adddepartmentmember",method = RequestMethod.POST)
    public ServiceResponse<Object> adddepartmentmember(@RequestBody ServiceRequest<AddDepartmentmemberParam> request) {
    	ServiceResponse<Object> response = new ServiceResponse<Object>();
    	response.setPrivatefield(request.getPrivatefield());

    	try
    	{
    		 Integer deptid = request.getReqdata().getDeptid();
    		 String phone = request.getReqdata().getPhone();
    		User user = ThreadLocalHelper.getUser();	
    		mdDeptService.addDepartmentMember(user.getUserid(), deptid, phone);

    	}catch(PMRuntimeException e1){
    		
            logger.error(e1.getMessage(), e1);
    		response.setCode(2000);
    		response.setMsg(e1.getMessage());
    	}catch(Exception e)
    	{
    		logger.error(e.getMessage(), e);
    		response.setCode(2000);
    		response.setMsg("系统异常");
    	}
    	
        return response;
    }
    
    
    
    /**
     * 移除部门成员
     * @param request
     * @return
     */
    @RequestMapping(value="removedepartmentmember",method = RequestMethod.POST)
    public ServiceResponse<Object> removedepartmentmember(@RequestBody ServiceRequest<RemoveDepartmentmemberParam> request) {
    	ServiceResponse<Object> response = new ServiceResponse<Object>();
    	response.setPrivatefield(request.getPrivatefield());

    	try
    	{
    		 Integer deptid = request.getReqdata().getDeptid();
    		 Integer removeuserid = request.getReqdata().getUserid();
    		 User user = ThreadLocalHelper.getUser();	
    		mdDeptService.removeDepartmentMember(user.getUserid(), deptid, removeuserid);

    	}catch(PMRuntimeException e1){
    		
            logger.error(e1.getMessage(), e1);
    		response.setCode(2000);
    		response.setMsg(e1.getMessage());
    	}catch(Exception e)
    	{
    		logger.error(e.getMessage(), e);
    		response.setCode(2000);
    		response.setMsg("系统异常");
    	}
    	
        return response;
    }
    
    
    
    
    /**
     * 消息列表
     * @param request
     * @return
     */
    @RequestMapping(value="getmessagelist",method = RequestMethod.POST)
    public ServiceResponse<List<GetMessageList>> getmessagelist(@RequestBody ServiceRequest<Object> request) {
    	ServiceResponse<List<GetMessageList>> response = new ServiceResponse<List<GetMessageList>>();
    	response.setPrivatefield(request.getPrivatefield());

    	try
    	{
    		 User user = ThreadLocalHelper.getUser();
    		 List<MdNoticeEx> messageList = mdNoticeService.getMessageList(user.getUserid());
    		 
    		 List<GetMessageList> resdata = new ArrayList<>();
    		 for(MdNoticeEx ex:messageList){
    			 GetMessageList msg = new GetMessageList();
    			
    			 BeanUtils.copyProperties(ex, msg);
    			 msg.setSenduserlogo(ex.getLogourl());
    			 msg.setSendusername(ex.getDispname());
    			 
    			 resdata.add(msg); 
    		 }

    		 response.setResdata(resdata);
    	}catch(PMRuntimeException e1){
    		
            logger.error(e1.getMessage(), e1);
    		response.setCode(2000);
    		response.setMsg(e1.getMessage());
    	}catch(Exception e)
    	{
    		logger.error(e.getMessage(), e);
    		response.setCode(2000);
    		response.setMsg("系统异常");
    	}
    	
        return response;
    }
    
    
    /**
     * 消息详情
     * @param request
     * @return
     */
    @RequestMapping(value="getmessagedetail",method = RequestMethod.POST)
    public ServiceResponse<GetMessageDetailBody> getmessagedetail(@RequestBody ServiceRequest<GetMessageDetailParam> request) {
    	ServiceResponse<GetMessageDetailBody> response = new ServiceResponse<GetMessageDetailBody>();
    	response.setPrivatefield(request.getPrivatefield());

    	try
    	{
    		 User user = ThreadLocalHelper.getUser();
    		 Integer noticeid = request.getReqdata().getNoticeid();
    		 GetMessageDetailBody getmessagedetail = mdNoticeService.getmessagedetail(user.getUserid(), noticeid);
    		 response.setResdata(getmessagedetail); 
    		
    	}catch(PMRuntimeException e1){
    		
            logger.error(e1.getMessage(), e1);
    		response.setCode(2000);
    		response.setMsg(e1.getMessage());
    	}catch(Exception e)
    	{
    		logger.error(e.getMessage(), e);
    		response.setCode(2000);
    		response.setMsg("系统异常");
    	}
    	
        return response;
    }
    
    @RequestMapping(value="getdownloadurl",method = RequestMethod.POST)
    public ServiceResponse<GetDownloadUrlResult> getDownloadUrl(@RequestBody ServiceRequest<getDownloadUrlParam> request) {
    	ServiceResponse<GetDownloadUrlResult> response = new ServiceResponse<GetDownloadUrlResult>();
    	response.setPrivatefield(request.getPrivatefield());

    	try
    	{
    		response.setResdata(this.attachmentService.getDownloadUrl(request.getReqdata(),ThreadLocalHelper.getUser().getUserid()));
    	}catch(PMRuntimeException e1){
    		
            logger.error(e1.getMessage(), e1);
    		response.setCode(2000);
    		response.setMsg(e1.getMessage());
    	}catch(Exception e)
    	{
    		logger.error(e.getMessage(), e);
    		response.setCode(2000);
    		response.setMsg("系统异常");
    	}
    	
        return response;
    }
    
    @RequestMapping(value="getdepartmentlist",method = RequestMethod.POST)
    public ServiceResponse<List<GetDepartmentListResult>> getdepartmentlist(@RequestBody ServiceRequest<GetDepartmentListParam> request) {
    	ServiceResponse<List<GetDepartmentListResult>> response = new ServiceResponse<List<GetDepartmentListResult>>();
    	response.setPrivatefield(request.getPrivatefield());
    	try
    	{
    		List<GetDepartmentListResult> resdata = this.mdDeptService.getDepartmentList(request.getReqdata(), ThreadLocalHelper.getUser().getUserid());
    		response.setResdata(resdata);
    	}catch(PMRuntimeException e1){
    		
            logger.error(e1.getMessage(), e1);
    		response.setCode(2000);
    		response.setMsg(e1.getMessage());
    	}catch(Exception e)
    	{
    		logger.error(e.getMessage(), e);
    		response.setCode(2000);
    		response.setMsg("系统异常");
    	}
    	
        return response;
    }    
}
