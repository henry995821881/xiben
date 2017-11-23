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

import com.xiben.pm.exception.PMRuntimeException;
import com.xiben.pm.md.bean.MdDeptEx;
import com.xiben.pm.md.bean.MdEmployeeEx;
import com.xiben.pm.md.mapper.MdDepartmentMapper;
import com.xiben.pm.md.mapper.MdDepartmentMemberMapper;
import com.xiben.pm.md.mapper.MdDeptdutyMapper;
import com.xiben.pm.md.pojo.MdAttchment;
import com.xiben.pm.md.pojo.MdDepartment;
import com.xiben.pm.md.pojo.MdDepartmentMember;
import com.xiben.pm.md.pojo.MdDeptduty;
import com.xiben.pm.md.pojo.MdEmployeeEntity;
import com.xiben.pm.md.request.AddDepartmentParam;
import com.xiben.pm.md.request.GetDepartmentListParam;
import com.xiben.pm.md.request.ModifyDepartmentParam;
import com.xiben.pm.md.request.UploadAttachParam;
import com.xiben.pm.md.request.sub.Duty;
import com.xiben.pm.md.request.sub.DutyModify;
import com.xiben.pm.md.response.GetDepartmemberListbody;
import com.xiben.pm.md.response.GetDepartmentDetailBody;
import com.xiben.pm.md.response.GetDepartmentListResult;
import com.xiben.pm.md.response.sub.DutyWithTask;
import com.xiben.pm.md.service.AttachmentService;
import com.xiben.pm.md.service.EmployeeService;
import com.xiben.pm.md.service.MdCompanyService;
import com.xiben.pm.md.service.MdConfigService;
import com.xiben.pm.md.service.MdDeptService;
import com.xiben.pm.md.util.MdUtil;
import com.xiben.pm.tk.bean.DeptTaskScore;
import com.xiben.pm.tk.pojo.TkTask;
import com.xiben.pm.tk.service.TkTaskService;
import com.xiben.pm.wf.service.WFTemplateServie;

@Service
public class MdDeptServiceImpl implements MdDeptService {

	
	@Autowired
	private MdDepartmentMapper mdDepartmentMapper;
	@Autowired
	private MdDepartmentMemberMapper mdDepartmentMemberMapper;
	@Autowired
	private MdCompanyService mdCompanyService;
	@Autowired
	private MdDeptdutyMapper mdDeptdutyMapper;
	@Autowired
	private AttachmentService attachmentService;
	@Autowired
	private TkTaskService mdTaskService;
	@Autowired
	private MdConfigService mdConfigService;
	@Autowired
	private EmployeeService employeeService;
	
	@Autowired
	private WFTemplateServie wFTemplateServie;
	
	
	public List<MdDeptEx> getMdDeptExForCompany(Integer companyid){
		
		return mdDepartmentMapper.getMdDeptExForCompany(companyid);
		
	}
	
	
	public List<MdDepartment> getDetpByUser(Integer userid){
		
		
       return  mdDepartmentMapper.getDetpByUser(userid);
		
	}


	@Override
	public MdDepartmentMember getMemberByUseridAndDeptid(Integer userid, Integer deptid) {
		
		return mdDepartmentMemberMapper.getMemberByUseridAndDeptid(userid, deptid);
	}


	@Override
	public List<MdEmployeeEntity> getDeptManagerByDeptid(Integer deptid) {
		
		return mdDepartmentMemberMapper.getDeptManagerByDeptid(deptid);
	}

/**
 * 添加部门
 */
	@Override
	public void addDepartment(Integer userid, AddDepartmentParam param) {
		
		//对param 做一下验证
		//....
		
		if(param.getCompanyid() ==null){
			throw new PMRuntimeException("公司id不能空");
		}
		if(param.getDeptname() ==null){
			throw new PMRuntimeException("部门名称不能空");
		}
		if(param.getMgruserid() ==null){
			throw new PMRuntimeException("授权人id不能空");
		}
		
		
		Integer role = mdCompanyService.isRoot(userid, param.getCompanyid());
		
		if(role ==null){
		throw new PMRuntimeException("你不是公司管理员");
		}
		//插入部门
		MdDepartment record = new MdDepartment();
		record.setCompid(param.getCompanyid());
		record.setDeptname(param.getDeptname());
		record.setIsdelete(0);
		record.setCreateby(userid);
		record.setCreatedate(new Date());
		record.setIssys(0);
		record.setUpdateby(userid);
		record.setUpdatedate(new Date());
		mdDepartmentMapper.insert(record);
		Integer deptid = record.getDeptid();
		
		//插入部门授权人
		MdDepartmentMember mmm = new MdDepartmentMember();
		mmm.setCompid(param.getCompanyid());
		mmm.setDeptid(deptid);
		mmm.setDeptrole(1);
		mmm.setMemberid(param.getMgruserid());
		mmm.setCreateby(userid);
		mmm.setCreatedate(new Date());
		mmm.setUpdateby(userid);
		mmm.setUpdatedate(new Date());
		mdDepartmentMemberMapper.insertSelective(mmm);
		
		//插入职责
		if(param.getDutylist()==null || param.getDutylist().size()==0){
			throw new PMRuntimeException("新建部门必须传入至少一个部门职责");
		}
		
		
		
		int  dtype = 0;
		for(Duty duty:param.getDutylist()){
			
			MdDeptduty d = new MdDeptduty();
			BeanUtils.copyProperties(duty, d);
			if(dtype!=0 && duty.getDutytype().intValue()==1){
		      throw new PMRuntimeException("部门职责只能有一个是规章制度");
			}
			if(duty.getDutytype().intValue()==1){
				dtype=1;
			}
			d.setCompid(param.getCompanyid());
			d.setDeptid(deptid);
			d.setCreateby(userid);
			d.setCreatedate(new Date());
			d.setIsdelete(0);
			d.setUpdateby(userid);
			d.setUpdatedate(new Date());
			mdDeptdutyMapper.insert(d);
			//插入附件
			Integer dutyid = d.getDutyid();
			
			List<UploadAttachParam> attachs = duty.getAttachs();
			if(attachs ==null){
				attachs =new ArrayList<>();
			}
			attachmentService.saveAttachmentList(attachs, dutyid, 5,  userid);
			
		}
		
		if(dtype ==0){
			throw new PMRuntimeException("新建部门必须有岗位职责");
		}
		
	}





@Override
public MdDepartment getDeptById(Integer deptid) {
	// TODO Auto-generated method stub
	return mdDepartmentMapper.selectByPrimaryKey(deptid);
}
	
/**
 * 修改部门
 * @param userid
 */
     public void modifyDepartment(Integer userid,ModifyDepartmentParam param){

    	 MdDepartment mdDepartment = this.getDeptById(param.getDeptid());
    	 if(mdDepartment ==null){
    		 throw new PMRuntimeException("该部门不存在");
    	 }
    	 Integer role = mdCompanyService.isRoot(userid, mdDepartment.getCompid());
 		
 		if(role ==null){
 			
 		    throw new PMRuntimeException("你不是公司管理员");
 		}
 		
 	/*	 if(mdDepartment.getIssys()==1){
  		   throw new PMRuntimeException("系统创建的部门不能修改");
  	     }*/
 		
 		//修改部门
 		mdDepartment.setDeptname(param.getDeptname());	
 		mdDepartment.setUpdateby(userid);
 		mdDepartment.setUpdatedate(new Date());
 		mdDepartmentMapper.updateByPrimaryKeySelective(mdDepartment);
 		
 		//修改部门授权人
 		
 		List<MdEmployeeEntity> MdEmployeeList = mdDepartmentMemberMapper.getDeptManagerByDeptid(param.getDeptid());
 		if(MdEmployeeList ==null || MdEmployeeList.size()==0){
 			
 			MdDepartmentMember  re = new MdDepartmentMember();
 			re.setCompid(mdDepartment.getCompid());
 			re.setCreateby(userid);
 			re.setCreatedate(new Date());
 			re.setDeptid(param.getDeptid());
 			re.setDeptrole(1);
 			re.setMemberid(param.getMgruserid());
 			re.setUpdateby(userid);
 			re.setUpdatedate(new Date());
 			mdDepartmentMemberMapper.insert(re);
 			 
 		}else{
 			
 			
 			//老的部门授权人
 			MdDepartmentMember member_old = mdDepartmentMemberMapper.getMemberByUseridAndDeptid(MdEmployeeList.get(0).getUserid(), param.getDeptid());
 			
 			//如果新的授权人和以前不一样，
 			if(param.getMgruserid().intValue()!=member_old.getMemberid().intValue()){
 				
 				MdDepartmentMember member_new = mdDepartmentMemberMapper.getMemberByUseridAndDeptid(userid, param.getDeptid());
 				if(member_new !=null){
 					//如果新加的授权人，以前是成员则删除
 					mdDepartmentMemberMapper.deleteByPrimaryKey(member_new.getId());
 				}
 				
 				//修改成新的授权人
 				member_old.setMemberid(param.getMgruserid());
 				member_old.setUpdateby(userid);
 				member_old.setUpdatedate(new Date());
 				mdDepartmentMemberMapper.updateByPrimaryKeySelective(member_old);
 				//修改流程的授权人
 				wFTemplateServie.updateApproveUser(param.getMgruserid(), param.getDeptid(), userid);
 				
 			}
 		}
 	
 			
 			
 			
 		//修改职责
 		List<MdDeptduty> dutys = mdDeptdutyMapper.queryAllDutyByDeptid(param.getDeptid());
 		Map<Integer,MdDeptduty> dutys_map = new HashMap<>();
 	
 		if(dutys !=null){
 			
 			for(MdDeptduty du :dutys){
 				
 				dutys_map.put(du.getDutyid(), du);
 			}
 		}

 		List<DutyModify> dutylist = param.getDutylist();
 		if(dutylist != null){
 		
 			for(DutyModify mod :dutylist){
 				
 				if(mod.getDutyid().intValue()==0){//新增
 					MdDeptduty d = new MdDeptduty();
 					BeanUtils.copyProperties(mod, d);
 					if( mod.getDutytype()==1){
 						 throw new PMRuntimeException("部门职责只能有一个是规章制度");
 					}
 					
 					
 					d.setCompid(mdDepartment.getCompid());
 					d.setCreateby(userid);
 					d.setCreatedate(new Date());
 					d.setUpdateby(userid);
 					d.setUpdatedate(new Date());
 					d.setIsdelete(0);
 					d.setDeptid(param.getDeptid());
 					mdDeptdutyMapper.insertSelective(d);
 					//插入附件
 					List<UploadAttachParam> attachs = mod.getAttachs();
 					attachmentService.saveAttachmentList(attachs, d.getDutyid(), 5,  userid);
 				}
 				
 				if(dutys_map.containsKey(mod.getDutyid())){//修改
 					MdDeptduty d = dutys_map.get(mod.getDutyid());
 					if(d.getDutytype().intValue()!=1){//规章制度 只能更新占比
 						/*if( mod.getDutytype()==1){
 	 						 throw new PMRuntimeException("部门职责只能有一个是规章制度");
 	 					}*/
 						d.setDutyname(mod.getDutyname());
 						d.setRemark(mod.getRemark());
 						d.setRatio(mod.getRatio());
 						d.setDutytype(mod.getDutytype());
 						mdDeptdutyMapper.updateByPrimaryKey(d);
 						//删除以前的附件
 						mdDeptdutyMapper.deleteByPrimaryKey(d.getDutyid());
 						//插入附
 						List<UploadAttachParam> attachs = mod.getAttachs();
 						attachmentService.saveAttachmentList(attachs, d.getDutyid(), 5, userid);
 					}else{
 						d.setRatio(mod.getRatio());
 						
 						mdDeptdutyMapper.updateByPrimaryKey(d);
 					}
 					//修改完移map除掉
 					dutys_map.remove(mod.getDutyid());
 				}
 				
 			}
 			
 		}
 		
 		//dutys_map 剩余的就是删除的
 		if(dutys_map.size()>0){
 			for (Integer key : dutys_map.keySet()) {
 				MdDeptduty mdDeptduty = dutys_map.get(key);
 				if(mdDeptduty.getDutytype().intValue()!=1){//不是规章制度 才可以删除
 					mdDeptduty.setIsdelete(1);
 					mdDeptdutyMapper.updateByPrimaryKeySelective(mdDeptduty);
 					//删除以前的附件
				    mdDeptdutyMapper.deleteByPrimaryKey(mdDeptduty.getDutyid());
 				}
 				
 			}
 		}
 		
 		
 		
    	 
     }	
	
     
     /**
      * 移除部门
      */
     public void removeDepartment(Integer userid,Integer deptid){
    	 
    	 if(deptid ==null){
    		 throw new PMRuntimeException("部门id不能空"); 
    	 }
    	 
    	 MdDepartment mdDepartment = this.getDeptById(deptid);
    	 if(mdDepartment ==null){
    		 throw new PMRuntimeException("该部门不存在");
    	 }
    	 Integer role = mdCompanyService.isRoot(userid, mdDepartment.getCompid());
 		
 		if(role ==null){
 			
 		    throw new PMRuntimeException("你不是公司管理员");
 		}
    	 
    	 if(mdDepartment.getIssys()==1){
    		   throw new PMRuntimeException("系统创建的部门不能删除");
    	 }
    	 
    	 List<TkTask> tkTasks = mdTaskService.getNofinished(deptid);
    	 if(tkTasks !=null && tkTasks.size()>0){
    		 throw new PMRuntimeException("该部门还有未完成的任务！请完成后再删除");
    	 }
    	 
    	 int hasTemplateNodeWithDepId = wFTemplateServie.hasTemplateNodeWithDepId(deptid, userid);
    	 
    	 if(hasTemplateNodeWithDepId >0){
    		 throw new PMRuntimeException("该部门参与过流程模板！不能移除删除");
    	 }
 		
    	int i =  mdDepartmentMapper.updateForDelete(deptid);
    	
    	if(i!=1){
    		 throw new PMRuntimeException("删除部门失败");
    	}
    	
    	 mdDeptdutyMapper.updateForDelete(deptid);
    	
    	
    	 
     }


     /**
      * 获取部门详情
      */
	@Override
	public GetDepartmentDetailBody getDepartmentDetail(Integer userid, Integer deptid, String year, String month) {
		GetDepartmentDetailBody body = new GetDepartmentDetailBody();
				
		if(deptid ==null){
			 throw new PMRuntimeException("部门id不能空"); 
		}
		
		//1=超级管理员，2=公司管理员，3=部门授权人，4=部门成员， 这些人可以查询部门信息
		 MdDepartment mdDepartment = this.getDeptById(deptid);
    	 if(mdDepartment ==null){
    		 throw new PMRuntimeException("该部门不存在");
    	 }
        Integer role = mdCompanyService.isRoot(userid, mdDepartment.getCompid());
    	MdDepartmentMember deptMember = this.getMemberByUseridAndDeptid(userid, deptid);
 		if(role ==null && deptMember ==null){
 			 throw new PMRuntimeException("公司超级管理员，公司管理员，部门授权人，部门成员 才能查询部门信息");
 		}
 		
 		Integer userright = getUserright(role,deptMember);
 		
 		
 		Date publish =null;
 		if(StringUtils.isNotBlank(year) &&StringUtils.isNotBlank(month)){
 			
 			publish = MdUtil.getMonth(year, month);
 			
 		}else{
 			publish =new Date();
 		}
 		//部门得分
 		DeptTaskScore deptTaskScore = mdTaskService.getDeptTaskScore(deptid, publish);
 		
 		//获取部门授权人
 		List<MdEmployeeEntity> mgrEmployeeList = this.getDeptManagerByDeptid(deptid);
 		if(mgrEmployeeList ==null || mgrEmployeeList.size()==0){
 			throw new PMRuntimeException("改部门没有授权人");
 		}
 		MdEmployeeEntity mgrEmp = mgrEmployeeList.get(0);
 		body.setDeptid(deptid);
 		body.setCompid(mdDepartment.getCompid());
 		body.setDeptname(mdDepartment.getDeptname());
 		body.setUserright(userright);
 		body.setMgruserid(mgrEmp.getUserid());
 		body.setMgrdispname(mgrEmp.getDispname());
 		body.setMgrlogo(mgrEmp.getLogourl());
 		body.setMgrphone(mgrEmp.getPhone());
 		body.setDeptscore(deptTaskScore!=null?deptTaskScore.getScores():0);
 		
 		//获取职责列表
 		List<DutyWithTask> dutylist = new ArrayList<>();
 		List<MdDeptduty> dutys = mdDeptdutyMapper.queryAllDutyByDeptid(deptid);
 		if(dutys != null){
 			for(MdDeptduty duty :dutys){
 				DutyWithTask dt = new DutyWithTask();
 				BeanUtils.copyProperties(duty, dt);
 				//任务数量和附件
 				Integer taskNum = mdTaskService.getTaskNum(deptid, dt.getDutyid());
 				dt.setTaskcnt(taskNum);
                List<UploadAttachParam> attachs = getAttach(dt.getDutyid(), 5);	
 				dt.setAttachs(attachs);
 				dutylist.add(dt);
 				
 			}
 		}
 		body.setDutylist(dutylist);
 		
		
 		return body;
	}

private List<UploadAttachParam> getAttach(Integer dutyid, int i) {
		
	    List<UploadAttachParam> attachP = new ArrayList<>();
	    List<MdAttchment> query = attachmentService.query(dutyid, i);
	    
	    if(query !=null){
	    	for(MdAttchment at :query){
	    		UploadAttachParam pap = new UploadAttachParam();
	    		
	    		pap.setFk(at.getFilekey());
	    		pap.setFn(at.getFilename());
	    		pap.setFs(at.getFilesize());
	    		pap.setFt(at.getFiletype());
	    		
	    		attachP.add(pap);		
	    	}
	    }
	    
	    
		return attachP;
	}


/**
 *获取用户角色
 * @param role
 * @param deptMember
 * @return
 */
	private Integer getUserright(Integer role, MdDepartmentMember deptMember) {
		 int r=0;
		 
		 if(role !=null){
			 r =role;
		 }else{
			 r = deptMember.getDeptrole()==1?3:4;
		 }
		
		return r;
	}

	
	/**
	 * 获取部门列表
	 */

@Override
public List<GetDepartmemberListbody> getDepartmemberListbody(Integer userid, Integer deptid) {
	
	List<GetDepartmemberListbody> body = new ArrayList<>();
	
	if(deptid ==null){
		 throw new PMRuntimeException("部门id不能空"); 
	}
	
	//1=超级管理员，2=公司管理员，3=部门授权人， 
	 MdDepartment mdDepartment = this.getDeptById(deptid);
	 if(mdDepartment ==null){
		 throw new PMRuntimeException("该部门不存在");
	 }
   Integer role = mdCompanyService.isRoot(userid, mdDepartment.getCompid());
	MdDepartmentMember deptMember = this.getMemberByUseridAndDeptid(userid, deptid);
	if(role ==null ){
		if(deptMember ==null ||deptMember.getDeptrole()!=1 ){
			 throw new PMRuntimeException("公司超级管理员，公司管理员，部门授权人才 可以获取部门成员");
		}
		
	}
	
	List<MdEmployeeEx> memeberList = this.getMemeberList(deptid);
	if(memeberList !=null){
		for(MdEmployeeEx ex:memeberList ){
			
			GetDepartmemberListbody b = new GetDepartmemberListbody();
			BeanUtils.copyProperties(ex, b);
			b.setLogo(ex.getLogourl());
			body.add(b);
			
		}
	}
	
	return body;
}


	@Override
	public MdDeptduty getDutyBydeptidwithtype1(Integer deptid) {
		// TODO Auto-generated method stub
		return mdDeptdutyMapper.getDutyBydeptidwithtype1(deptid);
	}


	@Override
	public List<MdEmployeeEx> getMemeberList(Integer deptid) {
		
		return mdDepartmentMemberMapper.getMemeberList(deptid);
	}


	/**
	 * 部门添加成员
	 */
	@Override
	public void addDepartmentMember(Integer userid,Integer deptid, String phone) {
		if(StringUtils.isBlank(phone)||deptid==null){
			throw new PMRuntimeException("部门id,手机号不能为空");
		}
		
		//1=超级管理员，2=公司管理员，3=部门授权人， 
		 MdDepartment mdDepartment = this.getDeptById(deptid);
		 if(mdDepartment ==null){
			 throw new PMRuntimeException("该部门不存在");
		 }
	   Integer role = mdCompanyService.isRoot(userid, mdDepartment.getCompid());
		MdDepartmentMember deptMember = this.getMemberByUseridAndDeptid(userid, deptid);
		if(role ==null ){
			if(deptMember ==null ||deptMember.getDeptrole()!=1 ){
				 throw new PMRuntimeException("公司超级管理员，公司管理员，部门授权人才 可以添加部门成员");
			}
			
		}
		MdEmployeeEntity mdEmployeeEntity = employeeService.queryByPhone(phone);
		if(mdEmployeeEntity ==null){
			throw new PMRuntimeException("该用户不存在");
		}
		MdDepartmentMember deptMember2 = this.getMemberByUseridAndDeptid(mdEmployeeEntity.getUserid(), deptid);
		if(deptMember2 !=null){
			throw new PMRuntimeException("已为部门授权人或部门成员不支持添加");
		}
		MdDepartmentMember r = new MdDepartmentMember();
		r.setCompid(mdDepartment.getCompid());
		r.setCreateby(userid);
		r.setCreatedate(new Date());
		r.setDeptid(deptid);
		r.setDeptrole(2);
		r.setMemberid(mdEmployeeEntity.getUserid());
		r.setUpdateby(userid);
		r.setUpdatedate(new Date());
		mdDepartmentMemberMapper.insertSelective(r);
		
		
	}

/**
 * 移除部门成员
 */
	@Override
	public void removeDepartmentMember(Integer userid, Integer deptid, Integer removeuserid) {
		if(removeuserid ==null||deptid==null){
			throw new PMRuntimeException("部门id,用户id不能为空");
		}
		
		//1=超级管理员，2=公司管理员，3=部门授权人， 
		 MdDepartment mdDepartment = this.getDeptById(deptid);
		 if(mdDepartment ==null){
			 throw new PMRuntimeException("该部门不存在");
		 }
	   Integer role = mdCompanyService.isRoot(userid, mdDepartment.getCompid());
		MdDepartmentMember deptMember = this.getMemberByUseridAndDeptid(userid, deptid);
		if(role ==null ){
			if(deptMember ==null ||deptMember.getDeptrole()!=1 ){
				 throw new PMRuntimeException("公司超级管理员，公司管理员，部门授权人才 可以删除部门成员");
			}
			
		}
		
		List<MdEmployeeEntity> MdEmployeeEntityList = this.getDeptManagerByDeptid(deptid);
		
		if(MdEmployeeEntityList==null || MdEmployeeEntityList.size()==0){
			throw new PMRuntimeException("该部门没有授权人");
		}
		
		if(MdEmployeeEntityList.get(0).getUserid().intValue()==removeuserid){
			throw new PMRuntimeException("该部门授权人不能移除");
		}
		
		
		mdDepartmentMemberMapper.deleteByMemeberid(removeuserid,deptid);
		
	}


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
	@Override
	public void initDept_office_finance(Integer companyid,Integer userid, Map<String,Integer> return_map) {
		//总经办
         String detpname_office = mdConfigService.getValue("init_dept_office");
		//财务部
          String detpname_finance = mdConfigService.getValue("init_dept_finance");
         //职责默认
         String dutyname = mdConfigService.getValue("init_duty_rule");
         //占比默认
         String dutyratio = mdConfigService.getValue("init_duty_rule_ratio");
        
         
         for(int i =0 ;i<2;i++){
        	 
        	  MdDepartment dept1 = new MdDepartment();
              dept1.setCompid(companyid);
              dept1.setCreateby(userid);
              dept1.setCreatedate(new Date());
              
              dept1.setDeptname(detpname_office);
              if(i==1){
            	  dept1.setDeptname(detpname_finance);
              }
              
              dept1.setIsdelete(0);
              dept1.setIssys(1);
              dept1.setUpdateby(userid);
              dept1.setUpdatedate(new Date());
              mdDepartmentMapper.insert(dept1);
              
              
              MdDeptduty duty1 = new MdDeptduty(); 
              duty1.setCompid(companyid);
              duty1.setCreateby(userid);
              duty1.setCreatedate(new Date());
              duty1.setDeptid(dept1.getDeptid());
              duty1.setDutyname(dutyname);
              duty1.setDutytype(1);
              duty1.setIsdelete(0);
              duty1.setRatio(Integer.valueOf(dutyratio));
              duty1.setRemark("");
              duty1.setUpdateby(userid);
              duty1.setUpdatedate(new Date());
              
              mdDeptdutyMapper.insert(duty1);
              
              if(i==0){//设置返回值
            	  
            	  return_map.put("office_dept_id", dept1.getDeptid());
            	  return_map.put("office_duty_id",duty1.getDutyid());
            	 
              }
        	 
         }
       
       
         
         
         

	}


	@Override
	public MdDeptduty getbyDutyid(Integer dutyid) {
		// TODO Auto-generated method stub
		return mdDeptdutyMapper.selectByPrimaryKey(dutyid);
	}  
	
	public List<GetDepartmentListResult> getDepartmentList(GetDepartmentListParam param,int userid){
		List<MdDeptEx> list = this.getMdDeptExForCompany(param.getCompid());
		List<GetDepartmentListResult> result = new ArrayList<GetDepartmentListResult>();
		for (MdDeptEx item:list)
		{
			GetDepartmentListResult o = new GetDepartmentListResult();
			o.setDeptid(item.getDeptid());
			o.setDeptname(item.getDeptname());
			o.setDeptmgrid(item.getUserid());
			o.setDeptmgrname(item.getDispname());
			o.setDeptmgrphone(item.getPhone());
			o.setDeptmgrlogo(item.getLogourl());
			result.add(o);
		}
		return result;
	}
	
}
