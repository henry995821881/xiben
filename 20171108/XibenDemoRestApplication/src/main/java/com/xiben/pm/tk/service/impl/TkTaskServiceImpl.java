package com.xiben.pm.tk.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.xiben.pm.ar.service.ArService;
import com.xiben.pm.exception.PMRuntimeException;
import com.xiben.pm.md.pojo.MdAttchment;
import com.xiben.pm.md.pojo.MdDepartment;
import com.xiben.pm.md.pojo.MdDepartmentMember;
import com.xiben.pm.md.pojo.MdDeptduty;
import com.xiben.pm.md.request.UploadAttachParam;
import com.xiben.pm.md.service.AttachmentService;
import com.xiben.pm.md.service.MdCompanyService;
import com.xiben.pm.md.service.MdDeptService;
import com.xiben.pm.md.util.MdUtil;
import com.xiben.pm.tk.bean.DeptTaskScore;
import com.xiben.pm.tk.bean.IndexTaskBean;
import com.xiben.pm.tk.bean.TkTaskEx;
import com.xiben.pm.tk.bean.TkTaskMonthScoreEx;
import com.xiben.pm.tk.mapper.TkTaskMapper;
import com.xiben.pm.tk.mapper.TkTaskMonthScoreMapper;
import com.xiben.pm.tk.mapper.TkTasknodeMapper;
import com.xiben.pm.tk.pojo.TkTask;
import com.xiben.pm.tk.pojo.TkTasknode;
import com.xiben.pm.tk.request.CheckTaskParam;
import com.xiben.pm.tk.request.CommitTaskParam;
import com.xiben.pm.tk.request.CreateTaskParam;
import com.xiben.pm.tk.request.GetIndexTaskListParam;
import com.xiben.pm.tk.response.GetIndexTaskListBody;
import com.xiben.pm.tk.response.GetMonthScoreListBody;
import com.xiben.pm.tk.response.GetTaskDetailBody;
import com.xiben.pm.tk.response.GetTaskListBody;
import com.xiben.pm.tk.response.sub.*;
import com.xiben.pm.tk.service.TkTaskService;

import com.xiben.pm.wf.service.WFTemplateServie;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
@Service
public class TkTaskServiceImpl implements TkTaskService {

	
	
	@Autowired
	TkTaskMapper tkTaskMapper;
	@Autowired
	MdCompanyService mdCompanyService;
	@Autowired
	MdDeptService mdDeptService;
	@Autowired
	ArService arService;
	@Autowired
	AttachmentService attachmentService;
	
	@Autowired
	TkTasknodeMapper tkTasknodeMapper;
	@Autowired
	TkTaskMonthScoreMapper tkTaskMonthScoreMapper;
	
	
	@Override
	public DeptTaskScore getDeptTaskScore(Integer deptid, Date publishdt) {
		// TODO Auto-generated method stub
		return tkTaskMapper.getDeptTaskScore(deptid, publishdt) ;
	}

	@Override
	public Integer getTaskNum(Integer deptid, Integer dutyid) {
		// TODO Auto-generated method stub
		return tkTaskMapper.getTaskNum(deptid,dutyid);
	}

	@Override
	public List<TkTask> getNofinished(Integer deptid) {
		
		return tkTaskMapper.getNofinished(deptid);
	}
	
	
	
	/**
	 * 获取任务列表
	 * @param dutyid
	 * @param curpageno
	 * @param pagesize
	 */
	public GetTaskListBody getTaskList(int userid,Integer dutyid,int curpageno,int pagesize){
		GetTaskListBody body = new GetTaskListBody();
		
		MdDeptduty duty = mdDeptService.getbyDutyid(dutyid);
		if(duty ==null){
			throw new PMRuntimeException("职责不存在");
		}
		
		Integer root = mdCompanyService.isRoot(userid, duty.getCompid());

		MdDepartmentMember mem = mdDeptService.getMemberByUseridAndDeptid(userid, duty.getDeptid());
		if(root ==null && mem==null){
			throw new PMRuntimeException("你不是公司管理员 或该职责的部门成员");
		}
		
		
		PageHelper.startPage(curpageno, pagesize);
		PageHelper.orderBy("status");
		PageInfo<TkTaskEx> pageInfo = new PageInfo<TkTaskEx>(tkTaskMapper.getByDutyid(userid,dutyid,duty.getCompid(),duty.getDeptid()));
		List<TkTaskEx> list = pageInfo.getList();
		Page page = new Page(); 	
		page.setCurpageno(curpageno);
		page.setPagesize(pageInfo.getPageSize());
		page.setPagenum(pageInfo.getPageNum());
		page.setTotalsize((int) pageInfo.getTotal());
		body.setPage(page);
		
		List<GetTaskListData> data = new ArrayList<>();
		for(TkTaskEx ex:list){
			GetTaskListData d = new GetTaskListData();
			BeanUtils.copyProperties(ex, d);
			d.setCreatedt(MdUtil.getDateString(ex.getCreatedate()));
			d.setCreateusername(ex.getDispname());
			d.setEnddt(MdUtil.getDateString(ex.getEnddt()));
			d.setTaskstatus(ex.getStatus());
			data.add(d);
		}
		
		body.setData(data);
		
		
		
		return body;
	}
	
	/**
	 * 创建任务
	 */
	public void createTask(int userid ,CreateTaskParam param){
	
		//数据验证。。。。。。
		if(param.getScore()==null||param.getDutyid()==null||param.getCreatetype()==null||StringUtils.isBlank(param.getEnddt())){
			throw new PMRuntimeException("参数有误");
		}
		
		
		
		//1、非公司管理员不能发布直评任务。
		Integer dutyid = param.getDutyid();
		MdDeptduty duty = mdDeptService.getbyDutyid(dutyid);
		if(duty ==null){
			throw new PMRuntimeException("职责不存在");
		}
		
		Integer root = mdCompanyService.isRoot(userid, duty.getCompid());
		if(root==null && param.getCreatetype()==2){
			throw new PMRuntimeException("非公司管理员不能发布直评任务");
		}
		if(root==null && param.getCreatetype()==1){
			throw new PMRuntimeException("非公司管理员不能发布非直评任务");
		}
		MdDepartmentMember mem = mdDeptService.getMemberByUseridAndDeptid(userid, duty.getDeptid());
		if(root==null &&mem ==null){
			throw new PMRuntimeException("不是公司管理员也不是部门成员不能发布任务");
		}

		
		//主表
		TkTask tk = new TkTask();
		tk.setCreateby(userid);
		tk.setCreatedate(new Date());
		tk.setUpdateby(userid);
		tk.setUpdatedate(new Date());
		tk.setCompid(duty.getCompid());
		tk.setDeptdutyid(param.getDutyid());
		tk.setDeptid(duty.getDeptid());
		tk.setEnddt(MdUtil.getStringDate(param.getEnddt()));
		tk.setPublishdt(new Date());
		tk.setRemark(param.getRemark()==null?"":param.getRemark());
		tk.setScore(param.getScore());
		tk.setSource(param.getCreatetype());
		if(param.getCreatetype() ==1){
			tk.setStatus(1);
		}else if(param.getCreatetype() ==2){
			tk.setStatus(3);
		}else{
			tk.setStatus(2);
		}
		tk.setTaskno(arService.generateTaskSequenceNumber(duty.getCompid(), userid));
		
		tkTaskMapper.insert(tk);
		
		//节点
		TkTasknode node = new TkTasknode();
		node.setCreateby(userid);
		node.setCreatedate(new Date());
		node.setUpdateby(userid);
		node.setUpdatedate(new Date());
		node.setCompid(duty.getCompid());
		node.setDeptid(duty.getDeptid());
		node.setTaskid(tk.getTaskid());
		node.setRemark(param.getRemark()==null?"":param.getRemark());//*************
		node.setScore(param.getScore());
		
		if(param.getCreatetype() ==1){
			node.setType(1);
		}else if(param.getCreatetype() ==2){
			node.setType(3);
		}else{
			node.setType(2);
		}
		
		tkTasknodeMapper.insert(node);
		
		
		//附件
		List<UploadAttachParam> attachs = param.getAttachs();
		if(attachs ==null || attachs.size()==0){
			
		}else{
			
			attachmentService.saveAttachmentList(attachs, node.getNodeid(), 3, userid);
		}
	}
	
	
	
	
	/**
	 * 完成任务
	 */
	public void commitTask(int userid,CommitTaskParam param){
		
		//验证
		if(param.getTaskid()==null){
			throw new PMRuntimeException("任务id为空");
		}
		
		TkTask tkTask = tkTaskMapper.selectByPrimaryKey(param.getTaskid());
		if(tkTask ==null){
			throw new PMRuntimeException("任务不存在");
		}
		
		 if(tkTask.getStatus().intValue()!=1){
			 throw new PMRuntimeException("任务非发起状态");
		 }
		 
		 MdDepartmentMember mdMember = mdDeptService.getMemberByUseridAndDeptid(userid, tkTask.getDeptid());
		 if(mdMember ==null){
			 throw new PMRuntimeException("当前用户不是任务所属部门的授权人或部门成员");
		 }
		 
		//乐观更新任务
		int i= tkTaskMapper.updateByStatus(userid,2,param.getTaskid());
		
		if(i !=1){
			throw new PMRuntimeException("更新任务失败");
		}
		
		
		//添加节点
		
		TkTasknode node = new TkTasknode();
		node.setCreateby(userid);
		node.setCreatedate(new Date());
		node.setUpdateby(userid);
		node.setUpdatedate(new Date());
		node.setCompid(tkTask.getCompid());
		node.setDeptid(tkTask.getDeptid());
		node.setTaskid(tkTask.getTaskid());
		node.setRemark(param.getRemark()==null?"":param.getRemark());//*************
		node.setScore(0);
	    node.setType(2);
		tkTasknodeMapper.insert(node);
		
		
		//附件
		List<UploadAttachParam> attachs = param.getAttachs();
		if(attachs ==null || attachs.size()==0){
			
		}else{
			
			attachmentService.saveAttachmentList(attachs, node.getNodeid(), 3, userid);
		}
		
	}
	
	
	
	/**
	 * 考核任务
	 * @param userid
	 */
	public void checkTask(int userid,CheckTaskParam param){
		/**
		 * 1、	确认任务状态为已发起或已完成
           2、	确认当前用户任务所属公司的管理员。
		 */
		
		//验证
		if(param.getTaskid()==null){
			throw new PMRuntimeException("任务id为空");
		}
		
		TkTask tkTask = tkTaskMapper.selectByPrimaryKey(param.getTaskid());
		if(tkTask ==null){
			throw new PMRuntimeException("任务不存在");
		}
		if(tkTask.getStatus() ==3 ){
			throw new PMRuntimeException("任务的状态应该为已发起或已完成");
			
		}
		Integer root = mdCompanyService.isRoot(userid, tkTask.getCompid());
		if(root==null){
             throw new PMRuntimeException("当前用户不是任务所属公司的管理员");
		}
		
		//乐观更新任务
		int i= tkTaskMapper.updateScoreByStatus(param.getScore()==null?0:param.getScore(),userid,3,param.getTaskid());
		
		if(i !=1){
			throw new PMRuntimeException("考核 更新任务失败");
		}
		
		
	
		//添加节点	
		TkTasknode node = new TkTasknode();
		node.setCreateby(userid);
		node.setCreatedate(new Date());
		node.setUpdateby(userid);
		node.setUpdatedate(new Date());
		node.setCompid(tkTask.getCompid());
		node.setDeptid(tkTask.getDeptid());
		node.setTaskid(tkTask.getTaskid());
		node.setRemark(param.getRemark()==null?"":param.getRemark());//*************
		node.setScore(param.getScore()==null?0:param.getScore());
	    node.setType(3);
		tkTasknodeMapper.insert(node);
		
		
		
	}
	
	
	/**
	 * 获取任务详情
	 */
	public GetTaskDetailBody getTaskDetail(int userid,Integer taskid){
		
		if(taskid ==null){
			throw new PMRuntimeException("任务id不能为空");
		}
		
		TkTask tkTask = tkTaskMapper.selectByPrimaryKey(taskid);
		if(tkTask ==null){
			throw new PMRuntimeException("任务不存在");
		}
		
		Integer root = mdCompanyService.isRoot(userid, tkTask.getCompid());
		MdDepartmentMember mem = mdDeptService.getMemberByUseridAndDeptid(userid, tkTask.getDeptid());
		if(root==null &&mem ==null){
			throw new PMRuntimeException("不是公司管理员也不是部门成员不能获取任务详情");
		}
		
		MdDeptduty duty = mdDeptService.getbyDutyid(tkTask.getDeptdutyid());
		
		List<TkTasknode> nodes = tkTasknodeMapper.queryByTaskId(taskid);
		
		//
		GetTaskDetailBody body = new GetTaskDetailBody();
		body.setTaskid(taskid);
		body.setTaskno(tkTask.getTaskno());
		
		
		for(TkTasknode node: nodes){
			
		   if(node.getType().intValue()==1){//发起
			   
			   Publishinfo pinfo = new Publishinfo();
			   pinfo.setPublishdt(MdUtil.getDateString(tkTask.getPublishdt()));
			   pinfo.setRemark(node.getRemark());
			   pinfo.setTitle(duty.getDutyname()+"-"+"请于"+MdUtil.getDateStringWithoutTime(tkTask.getEnddt())+"完成该重要任务");
			   
			   List<MdAttchment> attr = attachmentService.query(node.getNodeid(), 3); 
			   List<UploadAttachParam>  attachs = getAttachmenet2uploadAttach(attr);
			   pinfo.setAttachs(attachs);
			   body.setPublishinfo(pinfo);
		    }else if(node.getType().intValue()==2){//完成
				 
				 Finishinfo finfo = new Finishinfo();
				 finfo.setFinishdt(MdUtil.getDateString(node.getCreatedate()));
				 finfo.setRemark(node.getRemark());
				  
				 List<MdAttchment> attr = attachmentService.query(node.getNodeid(), 3); 
				 List<UploadAttachParam>  attachs = getAttachmenet2uploadAttach(attr);
				 finfo.setAttachs(attachs);
				 body.setFinishinfo(finfo);
				
			}else if(node.getType().intValue()==3){//考核
				Checkinfo cinfo = new Checkinfo();
				cinfo.setCheckdt(MdUtil.getDateString(node.getCreatedate()));
				cinfo.setScore(node.getScore());
				cinfo.setRemark(node.getRemark());
				body.setCheckinfo(cinfo);
				
				
			}
			
		}
		
		
		return body;
		
	}

	private List<UploadAttachParam> getAttachmenet2uploadAttach(List<MdAttchment> attr) {
		List<UploadAttachParam> pList = new ArrayList<>();
		
		for(MdAttchment mat : attr){
			UploadAttachParam p =new UploadAttachParam();
			p.setFk(mat.getFilekey());
			p.setFn(mat.getFilename());
			p.setFs(mat.getFilesize());
			p.setFt(mat.getFiletype());
			pList.add(p);
			
		}
		
		return pList;
	}
	
	
	
	/**
	 * 2.10.7.	任务月度评分列表
	 * @param userid
	 */
	public List<GetMonthScoreListBody> getMonthScoreList(int userid,Integer deptid,Integer year){
		
		if(deptid==null){
			throw new PMRuntimeException("部门id不能空");
		}
		
		MdDepartment deptment = mdDeptService.getDeptById(deptid);
		if(deptment==null){
			throw new PMRuntimeException("不存在该部门");
		}
		
		Integer root = mdCompanyService.isRoot(userid, deptment.getCompid());
		MdDepartmentMember mem = mdDeptService.getMemberByUseridAndDeptid(userid, deptid);
		if(root==null &&mem ==null){
			throw new PMRuntimeException("不是公司管理员也不是部门成员不能获取任务月度评分列表");
		}
		
		List<GetMonthScoreListBody> bodyList = new ArrayList<>();
		List<TkTaskMonthScoreEx> monthScoreList = tkTaskMonthScoreMapper.getBydeptid(deptid,year);
		for(TkTaskMonthScoreEx ex:monthScoreList){

          GetMonthScoreListBody body = new GetMonthScoreListBody();
          BeanUtils.copyProperties(ex, body);
          body.setTaskScore(ex.getTaskscore()+"");
          
          bodyList.add(body);
			
		}
		
		
		
		return bodyList;
		
	}
	
	
	/**
	 * 每天晚上1点更新 tk_task_monthscore 的完成情况
	 */
	@Scheduled(cron="0 0 1 * * ?")
	public void TaskJobForMonthScore(){
		
		//删除所有数据
		tkTaskMonthScoreMapper.deleteAll();
	    
		tkTaskMonthScoreMapper.insertAll();
		
	}
	
	/**
	 * 2.10.1.	首页待完成任务列表
	 * @param userid
	 */
	public GetIndexTaskListBody getIndexTaskList(int userid,GetIndexTaskListParam param){
		
		GetIndexTaskListBody body = new GetIndexTaskListBody();
		PageHelper.startPage(param.getCurpageno(), param.getPagesize());
		PageHelper.orderBy("ordertime desc");
		//List<IndexTaskBean> list = tkTaskMapper.queryIndex(userid);
		PageInfo<IndexTaskBean> pageinfo = new PageInfo<IndexTaskBean>(tkTaskMapper.queryIndex(userid));
		List<IndexTaskBean> list = pageinfo.getList();
		
		//page
		Page page = new Page(); 	
		page.setCurpageno(param.getCurpageno());
		page.setPagesize(pageinfo.getPageSize());
		page.setPagenum(pageinfo.getPageNum());
		page.setTotalsize((int) pageinfo.getTotal());
		body.setPage(page);
		
		//data
		List<GetIndexTaskData> dataList = new ArrayList<>();
		for(IndexTaskBean b:list){
			GetIndexTaskData data = new GetIndexTaskData();
			BeanUtils.copyProperties(b, data);
			dataList.add(data);
			
		}
		body.setData(dataList);
		
		return body;
	}

	
	

}
