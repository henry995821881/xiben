package com.xiben.pm.rest.controler;

import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.xiben.pm.exception.PMRuntimeException;
import com.xiben.pm.rest.request.ServiceRequest;
import com.xiben.pm.rest.response.ServiceResponse;
import com.xiben.pm.tk.request.CheckTaskParam;
import com.xiben.pm.tk.request.CommitTaskParam;
import com.xiben.pm.tk.request.CreateTaskParam;
import com.xiben.pm.tk.request.GetIndexTaskListParam;
import com.xiben.pm.tk.request.GetMonthScoreListParam;
import com.xiben.pm.tk.request.GetTaskDetailParam;
import com.xiben.pm.tk.request.GetTaskListParam;
import com.xiben.pm.tk.response.GetIndexTaskListBody;
import com.xiben.pm.tk.response.GetMonthScoreListBody;
import com.xiben.pm.tk.response.GetTaskDetailBody;
import com.xiben.pm.tk.response.GetTaskListBody;
import com.xiben.pm.tk.service.TkTaskService;
import com.xiben.pm.utils.ThreadLocalHelper;
import com.xiben.sso.client.model.User;

@RestController
@RequestMapping(value="/security/task")  
public class TkController  extends BaseController{

	
	@Autowired
	private TkTaskService tkTaskService;
	
	
	private static final Logger logger = Logger.getLogger(TkController.class);
	
	/**
	 * 首页待完成任务列表
	 * @param request
	 * @return
	 */
	  @RequestMapping(value="getindextasklist",method = RequestMethod.POST)
	    public ServiceResponse<Object> getindextasklist(@RequestBody ServiceRequest<GetIndexTaskListParam> request) {
	    	ServiceResponse<Object> response = new ServiceResponse<Object>();
	    	response.setPrivatefield(request.getPrivatefield());

	    	try
	    	{
	    		GetIndexTaskListParam param = request.getReqdata();
	    		User user = ThreadLocalHelper.getUser();
	    		GetIndexTaskListBody body = tkTaskService.getIndexTaskList(user.getUserid(), param);
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
		 * 2.10.2.	任务列表
		 * @param request
		 * @return
		 */
		  @RequestMapping(value="gettasklist",method = RequestMethod.POST)
		    public ServiceResponse<GetTaskListBody> getTaskList(@RequestBody ServiceRequest<GetTaskListParam> request) {
		    	ServiceResponse<GetTaskListBody> response = new ServiceResponse<GetTaskListBody>();
		    	response.setPrivatefield(request.getPrivatefield());

		    	try
		    	{
		    		
		    		User user = ThreadLocalHelper.getUser();
		    		Integer dutyid = request.getReqdata().getDutyid();
		    		Integer pagesize = request.getReqdata().getPagesize();
		    		Integer curpageno = request.getReqdata().getCurpageno();
		    		GetTaskListBody taskList = tkTaskService.getTaskList(user.getUserid(), dutyid, curpageno, pagesize);
		    		response.setResdata(taskList);
		    		
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
		   * 创建任务
		   * @param request
		   * @return
		   */
		  
		  @RequestMapping(value="createtask",method = RequestMethod.POST)
		    public ServiceResponse<Object> createtask(@RequestBody ServiceRequest<CreateTaskParam> request) {
		    	ServiceResponse<Object> response = new ServiceResponse<Object>();
		    	response.setPrivatefield(request.getPrivatefield());

		    	try
		    	{
		    		CreateTaskParam param = request.getReqdata();
		    		User user = ThreadLocalHelper.getUser();
		    		tkTaskService.createTask(user.getUserid(), param);
		    		
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
		   * 完成任务
		   * @param request
		   * @return
		   */
		  @RequestMapping(value="committask",method = RequestMethod.POST)
		    public ServiceResponse<Object> committask(@RequestBody ServiceRequest<CommitTaskParam> request) {
		    	ServiceResponse<Object> response = new ServiceResponse<Object>();
		    	response.setPrivatefield(request.getPrivatefield());

		    	try
		    	{
		    		CommitTaskParam param = request.getReqdata();
		    		User user = ThreadLocalHelper.getUser();
		    		tkTaskService.commitTask(user.getUserid(), param);
		    		
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
		   * 2.10.5.	考核任务
		   * @param request
		   * @return
		   */
		  @RequestMapping(value="checktask",method = RequestMethod.POST)
		    public ServiceResponse<Object> checktask(@RequestBody ServiceRequest<CheckTaskParam> request) {
		    	ServiceResponse<Object> response = new ServiceResponse<Object>();
		    	response.setPrivatefield(request.getPrivatefield());

		    	try
		    	{
		    		
		    		CheckTaskParam param = request.getReqdata();
		    		User user = ThreadLocalHelper.getUser();
		    		tkTaskService.checkTask(user.getUserid(), param);
		    		
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
		   * 任务详情
		   * @param request
		   * @return
		   */
		  @RequestMapping(value="gettaskdetail",method = RequestMethod.POST)
		    public ServiceResponse<GetTaskDetailBody> gettaskdetail(@RequestBody ServiceRequest<GetTaskDetailParam> request) {
		    	ServiceResponse<GetTaskDetailBody> response = new ServiceResponse<GetTaskDetailBody>();
		    	response.setPrivatefield(request.getPrivatefield());

		    	try
		    	{
		    		Integer taskid = request.getReqdata().getTaskid();
		    		User user = ThreadLocalHelper.getUser();
		    		GetTaskDetailBody taskDetail = tkTaskService.getTaskDetail(user.getUserid(), taskid);
		    		response.setResdata(taskDetail);
		    		
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
		   * 2.10.7.	任务月度评分列表
		   * @param request
		   * @return
		   */
		  @RequestMapping(value="getmonthscorelist",method = RequestMethod.POST)
		    public ServiceResponse<List<GetMonthScoreListBody>> getmonthscorelist(@RequestBody ServiceRequest<GetMonthScoreListParam> request) {
		    	ServiceResponse<List<GetMonthScoreListBody>> response = new ServiceResponse<List<GetMonthScoreListBody>>();
		    	response.setPrivatefield(request.getPrivatefield());

		    	try
		    	{
		    		Integer deptid = request.getReqdata().getDeptid();
		    		Integer year = request.getReqdata().getYear();
		    		User user = ThreadLocalHelper.getUser();
                    List<GetMonthScoreListBody> monthScoreList = tkTaskService.getMonthScoreList(user.getUserid(), deptid, year);
                    response.setResdata(monthScoreList);
		    		
		    		
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
	
	
