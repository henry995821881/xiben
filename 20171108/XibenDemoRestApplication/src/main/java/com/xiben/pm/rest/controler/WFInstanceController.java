package com.xiben.pm.rest.controler;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.xiben.pm.rest.request.ServiceRequest;
import com.xiben.pm.rest.response.ServiceResponse;
import com.xiben.pm.utils.ThreadLocalHelper;
import com.xiben.pm.wf.request.ApproveWorkflowParam;
import com.xiben.pm.wf.request.StartWorkflowParam;
import com.xiben.pm.wf.request.StopWorkFlowParam;
import com.xiben.pm.wf.service.WFInstanceService;

@RestController
@RequestMapping(value="security/wfinstance")
public class WFInstanceController {
	private static final Logger logger = Logger.getLogger(WFInstanceController.class);
	
	@Autowired
	WFInstanceService instanceService;
	
    @RequestMapping(value="startworkflow",method = RequestMethod.POST)
    public ServiceResponse<Void> startworkflow(@RequestBody ServiceRequest<StartWorkflowParam> request) {
        ServiceResponse<Void> response = new ServiceResponse<>();
        response.setPrivatefield(request.getPrivatefield());
        try {
        	
        	instanceService.startWorkFlow(request.getReqdata(), ThreadLocalHelper.getUser().getUserid());
        	
        }catch (Exception e){
            logger.error(e.getMessage(), e);
            response.setCode(2000);
            response.setMsg(e.getMessage());
        }
        return response;

    }
    
    
    @RequestMapping(value="gotonext",method = RequestMethod.POST)
    public ServiceResponse<Void> gotoNext(@RequestBody ServiceRequest<ApproveWorkflowParam> request) {
        ServiceResponse<Void> response = new ServiceResponse<>();
        response.setPrivatefield(request.getPrivatefield());
        try {
        	
        	instanceService.gotoNext(request.getReqdata(), ThreadLocalHelper.getUser().getUserid());
        	
        }catch (Exception e){
            logger.error(e.getMessage(), e);
            response.setCode(2000);
            response.setMsg(e.getMessage());
        }
        return response;

    }
    
    @RequestMapping(value="stopworkflow",method = RequestMethod.POST)
    public ServiceResponse<Void> stopWorkFlow(@RequestBody ServiceRequest<StopWorkFlowParam> request) {
        ServiceResponse<Void> response = new ServiceResponse<>();
        response.setPrivatefield(request.getPrivatefield());
        try {
        	
        	instanceService.stopWorkFlow(request.getReqdata(), ThreadLocalHelper.getUser().getUserid());
        	
        }catch (Exception e){
            logger.error(e.getMessage(), e);
            response.setCode(2000);
            response.setMsg(e.getMessage());
        }
        return response;

    }
    
    
}	
