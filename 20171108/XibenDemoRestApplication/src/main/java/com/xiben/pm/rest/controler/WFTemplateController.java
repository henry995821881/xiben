package com.xiben.pm.rest.controler;

import com.xiben.pm.rest.request.ServiceRequest;
import com.xiben.pm.rest.response.ServiceResponse;
import com.xiben.pm.utils.ThreadLocalHelper;
import com.xiben.pm.wf.request.AddtemplateRequestBody;
import com.xiben.pm.wf.request.RPAddApproveUser;
import com.xiben.pm.wf.request.RPInstance;
import com.xiben.pm.wf.request.RPTemplateBaseParams;
import com.xiben.pm.wf.response.*;
import com.xiben.pm.wf.service.WFInstanceService;
import com.xiben.pm.wf.service.WFTemplateServie;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping(value="security/workflow")
public class WFTemplateController extends BaseController{

    private static final Logger logger = Logger.getLogger(WFTemplateController.class);
    @Autowired
    WFTemplateServie wfService;

    @Autowired
    WFInstanceService instanceService;
    
    
    @RequestMapping(value="addtemplate",method = RequestMethod.POST)
    public ServiceResponse<Void> addtemplate(@RequestBody ServiceRequest<AddtemplateRequestBody> request) {
        ServiceResponse<Void> response = new ServiceResponse<>();
        response.setPrivatefield(request.getPrivatefield());
        try {
              wfService.createTemplate(request.getReqdata(), ThreadLocalHelper.getUser().getUserid());
        }catch (Exception e){
            logger.error(e.getMessage(), e);
            response.setCode(2000);
            response.setMsg(e.getMessage());

        }
        return response;

    }
    @RequestMapping(value="removetemplate",method = RequestMethod.POST)
    public ServiceResponse<Void> deletetemplate(@RequestBody ServiceRequest<RPTemplateBaseParams> request) {
        ServiceResponse<Void> response = new ServiceResponse<>();
        response.setPrivatefield(request.getPrivatefield());
        try {
              wfService.deleteTemplate(request.getReqdata().getTemplateid(),ThreadLocalHelper.getUser().getUserid());
        }catch (Exception e){
            logger.error(e.getMessage(), e);
            response.setCode(2000);
            response.setMsg(e.getMessage());

        }
        return response;

    }
    @RequestMapping(value="gettemplatelist",method = RequestMethod.POST)
    public ServiceResponse<List<RESPTemplateList>> queryData(@RequestBody ServiceRequest<RPTemplateBaseParams> request) {
        ServiceResponse<List<RESPTemplateList>> response = new ServiceResponse<>();
        response.setPrivatefield(request.getPrivatefield());
        try {
            List<RESPTemplateList> templateList = wfService.getTemplateList(request.getReqdata(), ThreadLocalHelper.getUser().getUserid());
            response.setResdata(templateList);
        }catch (Exception e){
            logger.error(e.getMessage(), e);
            response.setCode(2000);
            response.setMsg(e.getMessage());

        }
        return response;

    }
//    @RequestMapping(value="gettemplatedetail",method = RequestMethod.POST)
//    public ServiceResponse<RESPTempInfo> queryInfoWithTid(@RequestBody ServiceRequest<RPTemplateBaseParams> request) {
//        ServiceResponse<RESPTempInfo> response = new ServiceResponse<>();
//        response.setPrivatefield(request.getPrivatefield());
//        try {
//        	
//        	RESPTempInfo templateInfo = wfService.getTemplateInfo(request.getReqdata().getDeptid(), ThreadLocalHelper.getUser().getUserid());
//        	
//            response.setResdata(templateInfo);
//            
//        }catch (Exception e){
//            logger.error(e.getMessage(), e);
//            response.setCode(2000);
//            response.setMsg(e.getMessage());
//
//        }
//        return response;
//    }
    
    @RequestMapping(value="gettemplatedetail",method = RequestMethod.POST)
    public ServiceResponse<List<RESPTemplateDetailList>> queryInfoWithTid(@RequestBody ServiceRequest<RPTemplateBaseParams> request) {
        ServiceResponse<List<RESPTemplateDetailList>> response = new ServiceResponse<>();
        response.setPrivatefield(request.getPrivatefield());
        try {
        	
        	List<RESPTemplateDetailList> templateDetailList = wfService.getTemplateInfo2ListByDeptidAndUserid(request.getReqdata().getDeptid(), ThreadLocalHelper.getUser().getUserid());
        	
            response.setResdata(templateDetailList);
            
        }catch (Exception e){
            logger.error(e.getMessage(), e);
            response.setCode(2000);
            response.setMsg(e.getMessage());

        }
        return response;
    }    

    @RequestMapping(value="addnodeuser",method = RequestMethod.POST)
    public ServiceResponse<Void> addApproveUser(@RequestBody ServiceRequest<RPAddApproveUser> request) {
        ServiceResponse<Void> response = new ServiceResponse<>();
        response.setPrivatefield(request.getPrivatefield());
        try {
            wfService.addApproveUser(request.getReqdata(),ThreadLocalHelper.getUser().getUserid());
        }catch (Exception e){
            logger.error(e.getMessage(), e);
            response.setCode(2000);
            response.setMsg(e.getMessage());

        }
        return response;
    }
    @RequestMapping(value="removenodeuser",method = RequestMethod.POST)
    public ServiceResponse<Void> removeApproveUser(@RequestBody ServiceRequest<RPAddApproveUser> request) {
        ServiceResponse<Void> response = new ServiceResponse<>();
        response.setPrivatefield(request.getPrivatefield());
        try {
            wfService.removeApproveUser(request.getReqdata(),ThreadLocalHelper.getUser().getUserid());
        }catch (Exception e){
            logger.error(e.getMessage(), e);
            response.setCode(2000);
            response.setMsg(e.getMessage());

        }
        return response;
    }
    @RequestMapping(value="setnodeapprovetype",method = RequestMethod.POST)
    public ServiceResponse<Void> setnodeapprovetype(@RequestBody ServiceRequest<RPAddApproveUser> request) {
        ServiceResponse<Void> response = new ServiceResponse<>();
        response.setPrivatefield(request.getPrivatefield());
        try {
            wfService.updateApprovetype(request.getReqdata(),ThreadLocalHelper.getUser().getUserid());
        }catch (Exception e){
            logger.error(e.getMessage(), e);
            response.setCode(2000);
            response.setMsg(e.getMessage());

        }
        return response;
    }
    @RequestMapping(value="getworkflowinslist",method = RequestMethod.POST)
    public ServiceResponse<RESPInstancePager> getworkflowinslist(@RequestBody ServiceRequest<RPInstance> request) {
        ServiceResponse<RESPInstancePager> response = new ServiceResponse<>();
        response.setPrivatefield(request.getPrivatefield());
        try {
            RESPInstancePager respInstances = instanceService.instanceList(request.getReqdata(), ThreadLocalHelper.getUser().getUserid());
            response.setResdata(respInstances);
        }catch (Exception e){

            logger.error(e.getMessage(), e);
            response.setCode(2000);
            response.setMsg(e.getMessage());

        }
        return response;
    }
    @RequestMapping(value="getworkflowinsdetail",method = RequestMethod.POST)
    public ServiceResponse<RESPInstanceInfo> getworkflowinsdetail(@RequestBody ServiceRequest<RPInstance> request) {
        ServiceResponse<RESPInstanceInfo> response = new ServiceResponse<>();
        response.setPrivatefield(request.getPrivatefield());
        try {
            RESPInstanceInfo respInstanceInfo = instanceService.instanceInfo(request.getReqdata().getInsid(), ThreadLocalHelper.getUser().getUserid());
            response.setResdata(respInstanceInfo);
        }catch (Exception e){
            logger.error(e.getMessage(), e);
            response.setCode(2000);
            response.setMsg(e.getMessage());

        }
        return response;
    }




}
