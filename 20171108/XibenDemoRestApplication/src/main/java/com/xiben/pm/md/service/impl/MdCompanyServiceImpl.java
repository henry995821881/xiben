package com.xiben.pm.md.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.xiben.common.qiniu.DownLoadTools;
import com.xiben.pm.exception.PMRuntimeException;
import com.xiben.pm.md.bean.MdCompanyEx;
import com.xiben.pm.md.bean.MdDeptEx;
import com.xiben.pm.md.bean.MdEmployeeEx;
import com.xiben.pm.md.mapper.MdCompanyMapper;
import com.xiben.pm.md.mapper.MdCompanyMemberMapper;
import com.xiben.pm.md.pojo.MdAttchment;
import com.xiben.pm.md.pojo.MdCompany;
import com.xiben.pm.md.pojo.MdCompanyMember;
import com.xiben.pm.md.pojo.MdDepartment;
import com.xiben.pm.md.pojo.MdEmployeeEntity;
import com.xiben.pm.md.request.UploadAttachParam;
import com.xiben.pm.md.response.GetCompanyDetailBody;
import com.xiben.pm.md.response.GetCompanyMgrListBody;
import com.xiben.pm.md.response.sub.Dept;
import com.xiben.pm.md.response.sub.Mgr;
import com.xiben.pm.md.response.sub.Mgrdept;
import com.xiben.pm.md.service.AttachmentService;
import com.xiben.pm.md.service.EmployeeService;
import com.xiben.pm.md.service.MdCompanyService;
import com.xiben.pm.md.service.MdConfigService;
import com.xiben.pm.md.service.MdDeptService;
import com.xiben.pm.md.util.MdUtil;
import com.xiben.pm.tk.bean.DeptTaskScore;
import com.xiben.pm.tk.service.TkTaskService;
import com.xiben.pm.wf.pojo.WfTemplate;
import com.xiben.pm.wf.pojo.WfTemplateNode;
import com.xiben.pm.wf.pojo.WfTemplateNodeApprovepart;
import com.xiben.pm.wf.service.WFTemplateServie;
import com.xiben.sso.client.model.User;

@Service("mdCompanyS")
public class MdCompanyServiceImpl implements MdCompanyService {
	
	@Autowired
	private WFTemplateServie wFTemplateServie;
	
	@Autowired
	private MdCompanyMapper mdCompanyMapper;
	@Autowired
	private MdCompanyMemberMapper mdCompanyMemberMapper;
	@Autowired
	private AttachmentService attachService;
	@Autowired
	private MdConfigService mdConfigService;
	@Autowired
	private EmployeeService employeeService;
	
	@Autowired
	private MdDeptService mdDeptService;
	
	
	@Autowired
	TkTaskService mdTaskService;
	

	public List<MdCompanyEx> queryCompany(Integer userid){
		
		return  mdCompanyMapper.queryComanpy(userid);
	}

	
	/**
	 * 插入公司成员
	 * @param m
	 */

	public void insert(MdCompanyMember m){

		mdCompanyMemberMapper.insertSelective(m);
	}
	
	
	public void insertCompanyLogo(User uaasUser,MdAttchment mdAttachment,Integer companyid){
		
		
	/*	
		mdAttachment.setBizid(companyid);
		mdAttachment.setBiztype(2);
		mdAttachment.setCreateby(uaasUser.getUserid());
		mdAttachment.setCreatedate(new Date());
		mdAttachment.setDescription("");
		mdAttachment.setFilekey(attach.getFk());
		mdAttachment.setFilename(attach.getFn());
		mdAttachment.setFilesize(attach.getFs());
		mdAttachment.setFiletype(attach.getFt());
		mdAttachment.setStatus(1);
		mdAttachment.setTablespace(Config.getPublicTablename());
		mdAttachment.setUpdateby(uaasUser.getUserid());
		mdAttachment.setUpdatedate(new Date());
		attachService.saveAttachment(mdAttachment);
		*/
		List<UploadAttachParam> list = new ArrayList<>();
		UploadAttachParam p = new UploadAttachParam();
		p.setFk(mdAttachment.getFilekey());
		p.setFn(mdAttachment.getFilename());
		p.setFs(mdAttachment.getFilesize());
		p.setFt(mdAttachment.getFiletype());
		list.add(p);
		attachService.saveAttachmentList(list, companyid, 2, uaasUser.getUserid());	
	
	}
	
	
	/**
	 * 
	 * 提前set /*mdAttachment.setFilekey(attach.getFk());
		mdAttachment.setFilename(attach.getFn());
		mdAttachment.setFilesize(attach.getFs());
		mdAttachment.setFiletype(attach.getFt());
	
	 * 添加公司
	 */
	public void addCompany(MdCompany md_,User uaasUser,MdAttchment mdAttachment){
		
		if(StringUtils.isBlank(md_.getFullname())){
			throw new PMRuntimeException("公司全称为空");
		}
		
        if(StringUtils.isBlank(md_.getShortname())){
			
        	throw new PMRuntimeException("公司简称为空");
		}
		
		
		//判断是否已经有该公司了
		MdCompany mdCompany =  mdCompanyMapper.queryByName(md_.getFullname());
		
		if(mdCompany != null){
			throw new PMRuntimeException("该公司已经存在");
		}
	
		
		
      
		String downloadurl =mdAttachment==null?"": DownLoadTools.getPublicFileDownloadUrl(mdAttachment.getFilekey());
		
		//添加公司
		md_.setShortnamespell(MdUtil.getPingYinString(md_.getShortname()));
		md_.setLogo(downloadurl);
		md_.setCreateby(uaasUser.getUserid());
		md_.setCreatedate(new Date());
		md_.setUpdateby(uaasUser.getUserid());
		md_.setUpdatedate(new Date());
		mdCompanyMapper.insert(md_);
		
		//添加附件表
		if(mdAttachment !=null){
			
			this.insertCompanyLogo(uaasUser, mdAttachment,md_.getCompanyid());
		}
		
		//添加公司超级管理员
		MdCompany md =  mdCompanyMapper.queryByName(md_.getFullname());
		MdCompanyMember mdc = new MdCompanyMember();
		mdc.setCompanyid(md.getCompanyid());
		mdc.setRole(1);
		mdc.setUserid(uaasUser.getUserid());
		mdc.setCreateby(uaasUser.getUserid());
		mdc.setCreatedate(new Date());
		mdc.setUpdateby(uaasUser.getUserid());
		mdc.setUpdatedate(new Date());
		this.insert(mdc);
		
		/**
		 * 添加商家要有默认设置：
          1、部门（总经办[init_dept_office]、财务部[init_dept_finance]），
                                         职责默认遵守公司制度及其他任务【init_duty_rule】，占比默认【init_duty_rule_ratio】。
         2、通过公司详情将默认职责传入手机端，默认部门职责（遵守公司制度及其他任务【init_duty_rule】）
                                       以及其默认占比【init_duty_rule_ratio】。
         3、默认流程（发文流程【init_workflow_senddoc】、收文流程【init_workflow_receivedoc】
                                       以及公司制度制定【init_workflow_rule】），插入一个节点，部门为总经办【init_dept_office】。

          4、文档归档以及任务和流程的编号前缀

		 */
		 // return_map.put("office_dept_id", dept1.getDeptid());
    	 // return_map.put("office_duty_id",duty1.getDutyid());
    	  //return_map.put("office_detp_name",duty1.getDutyid());
		  Map<String,Integer> return_map = new  HashMap<String,Integer>();
		
		  mdDeptService.initDept_office_finance(md_.getCompanyid(),uaasUser.getUserid(),return_map);
		
		//发文流程
		//收文流程 
		//规章制度流程
		   String workflow_senddoc = mdConfigService.getValue("init_workflow_senddoc");
		   String workflow_receivedoc = mdConfigService.getValue("init_workflow_receivedoc");
		   String workflow_rule = mdConfigService.getValue("init_workflow_rule");
		   for(int i =1 ;i<4;i++){
			   
			   //WfTemplate
			   WfTemplate  wfTemplate = new WfTemplate();
			   wfTemplate.setCompid(md_.getCompanyid());
			   wfTemplate.setCreateby(uaasUser.getUserid());
			   wfTemplate.setCreatedate(new Date());
			   wfTemplate.setUpdateby(uaasUser.getUserid());
			   wfTemplate.setUpdatedate(new Date());
			   wfTemplate.setCreatetype(1);
			   wfTemplate.setStatus(1);
			   
			   
			   if(i==1){
				   wfTemplate.setTemplatename(workflow_receivedoc);
			   }else if(i==2){
				   wfTemplate.setTemplatename(workflow_senddoc);
			   }else{
				   wfTemplate.setTemplatename(workflow_rule);
			   }
			   wfTemplate.setType(i); 
			   //WfTemplateNode
			   WfTemplateNode templateNode = new WfTemplateNode();
			   
			   templateNode.setCompid(md_.getCompanyid());
			   templateNode.setCreateby(uaasUser.getUserid());
			   templateNode.setCreatedate(new Date());
			   templateNode.setCreatetype(1);
			   templateNode.setUpdateby(uaasUser.getUserid());
			   templateNode.setUpdatedate(new Date());
			   templateNode.setNodename("审批");
			   templateNode.setNotetype(3);
			   templateNode.setApprovetype(1);
			   templateNode.setRank(1);
			   
			  
			   //WfTemplateNodeApprovepart 
			   WfTemplateNodeApprovepart wfTemplateNodeApprovepart = new WfTemplateNodeApprovepart();
			   wfTemplateNodeApprovepart.setCompid(md_.getCompanyid());
			   wfTemplateNodeApprovepart.setApprovetype(1);
			   wfTemplateNodeApprovepart.setDeptid(0);
			   wfTemplateNodeApprovepart.setDeptid(return_map.get("office_dept_id"));   
			   wfTemplateNodeApprovepart.setCreateby(uaasUser.getUserid());
			   wfTemplateNodeApprovepart.setCreatedate(new Date());
			   wfTemplateNodeApprovepart.setUpdateby(uaasUser.getUserid());
			   wfTemplateNodeApprovepart.setUpdatedate(new Date());
			   
			 wFTemplateServie.createFlow(wfTemplate, templateNode, wfTemplateNodeApprovepart, uaasUser.getUserid());
		   }
		
		
	}


	@Override
	public boolean isManagerForCompany(Integer userId, Integer companyId) {
		List<MdCompanyMember> list = mdCompanyMemberMapper.queryManagerByComIdAndUserId(userId,companyId);
		
		if(list != null && list.size()>0){
			
			return true;
		}
		
		return false;
		}
	
	
	public Integer isRoot(Integer userid, Integer companyid){
		
		return mdCompanyMemberMapper.isRoot(userid,companyid);
		
	}
	
	/**
	 * 获取公司信息
	 * @param userid
	 * @param companyid
	 */
	public GetCompanyDetailBody getCompanyDetail(Integer userid,Integer companyid){
		
		
		 Integer role = mdCompanyMemberMapper.isRoot(userid,companyid);
		 if(role ==null){
			 throw new PMRuntimeException("你不是公司管理员");
			 
		 }
		
		//获取公司
		MdCompanyEx ex = mdCompanyMapper.getCompanyDetail(userid,companyid);
		if(ex ==null){
			throw new PMRuntimeException("没有查询到公司");
		}
		

		GetCompanyDetailBody body = new GetCompanyDetailBody();
		BeanUtils.copyProperties(ex, body);
		body.setUserright(ex.getRole());
		body.setDefaultduty(mdConfigService.getValue("init_duty_rule"));
		body.setDefaultdutyratio(mdConfigService.getValue("init_duty_rule_ratio"));
		
		//获取部门
		List<MdDeptEx> MdDeptExList = mdDeptService.getMdDeptExForCompany(companyid);
		List<Dept> dList = new ArrayList<Dept>();
		for(MdDeptEx x:MdDeptExList){
			Dept d = new Dept();
		BeanUtils.copyProperties(x, d);
		d.setDeptmgrid(x.getUserid());
		d.setDeptmgrlogo(x.getLogourl());
		d.setDeptmgrname(x.getDispname());
		d.setDeptmgrphone(x.getPhone());
		DeptTaskScore deptTaskScore = mdTaskService.getDeptTaskScore(x.getDeptid(), new Date());
		if(deptTaskScore !=null){
			
			d.setDeptscore(deptTaskScore.getScores());
			d.setTaskcnt(deptTaskScore.getTasknum());
		}
		dList.add(d);	
		}
		body.setDeptlist(dList);
		
		//获取当前用户所在部门
		List<MdDepartment> detpList = mdDeptService.getDetpByUser(userid);
		List<Mgrdept> mList = new ArrayList<Mgrdept>();
		for(MdDepartment md :detpList){
			Mgrdept mg = new Mgrdept();
			BeanUtils.copyProperties(md, mg);
			
			mList.add(mg);
		}
		body.setMgrdeptlist(mList);
		
	
		
		
		return body;
		
	}

/**
 * 获取公司管理员
 */
	@Override
	public GetCompanyMgrListBody getCompanymgrList(int userid, Integer companyid) {
		
		//管理员还是root
		
		 Integer role = mdCompanyMemberMapper.isRoot(userid,companyid);
		 if(role ==null){
			 throw new PMRuntimeException("你不是公司管理员");
			 
		 }
		
		//获取其他管理员信息
		List<MdEmployeeEx> list =  mdCompanyMemberMapper.queryManager(companyid);
         GetCompanyMgrListBody body = new GetCompanyMgrListBody();
		body.setUserright(role);
		List<Mgr> mgrList = new ArrayList<>();
		for(MdEmployeeEx ex:list){
			Mgr m = new Mgr();
			BeanUtils.copyProperties(ex, m);
			m.setLogo(ex.getLogourl());
			mgrList.add(m);
		}
		
		
		
		
		body.setMgrList(mgrList);
		
		return body;
		
	}
	
	
	//超级管理员给用户添加到公司的管理员
	public void addCompanyManager(int userid, Integer companyid,String mgrphone){
		
		//管理员还是root
		
		 Integer role = mdCompanyMemberMapper.isRoot(userid,companyid);
		 if(role ==null){
			 throw new PMRuntimeException("你不是公司管理员"); 
		 }
		 if(role !=1){ 
			 throw new PMRuntimeException("你不是公司超级管理员");
		 }
		 
		 if(StringUtils.isBlank(mgrphone)){
			 throw new PMRuntimeException("手机号不能为空"); 
		 }
		 
		 MdEmployeeEntity employeeByUserId =employeeService.queryByPhone(mgrphone);
		 
		 if(employeeByUserId==null){
			 throw new PMRuntimeException("该手机号对应的用户不存在"); 
		 }
		 Integer role2 = mdCompanyMemberMapper.isRoot(employeeByUserId.getUserid(),companyid);
		 if(role2 !=null &&(role2==1 ||role2==2)){
			 throw new PMRuntimeException("该手机号对应的用户已经是该公司管理员"); 
		 }
		 
		 if(role2 !=null){
			 //更新 role=2
			 MdCompanyMember mcm =mdCompanyMemberMapper.queryByUseridAndCompanyid(employeeByUserId.getUserid(),companyid);
			 mcm.setRole(2);
			 mcm.setUpdateby(userid);
			 mcm.setUpdatedate(new Date());
			 mdCompanyMemberMapper.updateByPrimaryKeySelective(mcm);
			 
		 }else{
			 //新增数据 role=2
			 MdCompanyMember m = new MdCompanyMember();
			 m.setRole(2);
			 m.setCompanyid(companyid);
			 m.setCreateby(userid);
			 m.setCreatedate(new Date());
			 m.setUpdateby(userid);
			 m.setUpdatedate(new Date());
			 m.setUserid(employeeByUserId.getUserid());
			 mdCompanyMemberMapper.insertSelective(m);
		 }
		
	}

/**
 * 超级管理员删除公司管理员
 */
	@Override
	public void removeCompanyManager(int userid, Integer companyid, Integer mgrUserid) {
		
		
		if(mgrUserid ==null ||companyid==null){
			 throw new PMRuntimeException("管理员id,公司id都不能为空"); 
		}
		
		//管理员还是root
		 Integer role = mdCompanyMemberMapper.isRoot(userid,companyid);
		 if(role ==null){
			 throw new PMRuntimeException("你不是公司管理员"); 
		 }
		 if(role !=1){ 
			 throw new PMRuntimeException("你不是公司超级管理员");
		 }
		 
		 Integer roleMgr = mdCompanyMemberMapper.isRoot(mgrUserid,companyid);
		 if(roleMgr ==null){
			 throw new PMRuntimeException("该用户已经不是管理员,无需删除"); 
		 }
		 if(roleMgr ==1){ 
			 throw new PMRuntimeException("该用户是公司超级管理员,不能删除");
		 }
		 
		 int i = mdCompanyMemberMapper.deleteByUserid(mgrUserid,companyid);
		 
		 if(i!=1){
			 throw new PMRuntimeException("该用户已经不是管理员,请刷新");
		 }
		
	}


	/**
	 * 新增公司logo
	 * 
	 * 
	 * 提前set /*mdAttachment.setFilekey(attach.getFk());
		mdAttachment.setFilename(attach.getFn());
		mdAttachment.setFilesize(attach.getFs());
		mdAttachment.setFiletype(attach.getFt());
	 */
	@Override
	public void uploadCompanyLogo(User uaasUser, Integer companyid, MdAttchment mdAttachment) {
		
		if(companyid==null){
			 throw new PMRuntimeException("公司id不能为空"); 
		}
		
		//管理员还是root
		 Integer role = mdCompanyMemberMapper.isRoot(uaasUser.getUserid(),companyid);
		 if(role ==null){
			 throw new PMRuntimeException("你不是公司管理员"); 
		 }
		
		 
	     String downloadurl = DownLoadTools.getPublicFileDownloadUrl(mdAttachment.getFilekey());
		int i= mdCompanyMapper.updateLogo(uaasUser.getUserid(),companyid,downloadurl);
		if(i!=1){
			 throw new PMRuntimeException("更新logo失败"); 
		}
		this.insertCompanyLogo(uaasUser, mdAttachment,companyid);
		 
	}


	@Override
	public MdCompany getbyid(Integer companyid) {
		
		return mdCompanyMapper.selectByPrimaryKey(companyid);
	}


	@Override
	public List<MdCompanyMember> getManager(Integer companyid) {
		// TODO Auto-generated method stub
		return mdCompanyMemberMapper.getManager(companyid);
	}
	
}
