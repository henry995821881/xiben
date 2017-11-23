
package com.xiben.pm.test;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.log4j.Logger;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.xiben.pm.md.bean.MdCompanyEx;
import com.xiben.pm.md.mapper.MdCompanyMapper;
import com.xiben.pm.md.mapper.MdDepartmentMapper;
import com.xiben.pm.md.pojo.MdAttchment;
import com.xiben.pm.md.pojo.MdCompany;
import com.xiben.pm.md.pojo.MdDeptduty;
import com.xiben.pm.md.request.ModifyDepartmentParam;
import com.xiben.pm.md.request.UploadAttachParam;
import com.xiben.pm.md.request.sub.Duty;
import com.xiben.pm.md.request.sub.DutyModify;
import com.xiben.pm.md.service.MdCompanyService;
import com.xiben.pm.md.service.MdDeptService;
import com.xiben.pm.md.util.MdUtil;
import com.xiben.pm.rest.controler.MdController;
import com.xiben.pm.tk.request.CheckTaskParam;
import com.xiben.pm.tk.request.CommitTaskParam;
import com.xiben.pm.tk.request.CreateTaskParam;
import com.xiben.pm.tk.request.GetIndexTaskListParam;
import com.xiben.pm.tk.response.GetIndexTaskListBody;
import com.xiben.pm.tk.response.GetMonthScoreListBody;
import com.xiben.pm.tk.response.GetTaskDetailBody;
import com.xiben.pm.tk.response.GetTaskListBody;
import com.xiben.pm.tk.service.TkTaskService;
import com.xiben.sso.client.model.User;

@RunWith(SpringJUnit4ClassRunner.class)  
@ContextConfiguration({"classpath:spring-context.xml","classpath:spring-mybatis.xml","classpath:spring-transaction.xml"})
public class TestBaseData {
	private static final Logger logger = Logger.getLogger(TestBaseData.class);
	@Autowired
	MdCompanyMapper mdCompanyMapper;
	
	@Autowired
	MdCompanyService mdCompanyService;
	@Autowired
	MdDepartmentMapper mdDepartmentMapper;
	
	@Autowired
	MdDeptService mdDeptService;
	
	@Autowired
	TkTaskService mdTaskService;
	
	
	

	
	@Test
	public void testTask(){
		GetIndexTaskListParam	 param = new GetIndexTaskListParam();
		param.setCurpageno(1);
		param.setPagesize(15);
		GetIndexTaskListBody indexTaskList = mdTaskService.getIndexTaskList(21, param);
		System.out.println(indexTaskList);
		//DeptTaskScore deptTaskScore = mdTaskService.getDeptTaskScore(8, new Date());
		//MdDeptduty duty = mdDeptService.getDutyBydeptidwithtype1(9);
		//GetTaskListBody taskList = mdTaskService.getTaskList(55, 1, 1, 15);
	/*	CreateTaskParam	 param = new CreateTaskParam();
		param.setCreatetype(1);
		param.setDutyid(7);
		param.setEnddt(MdUtil.getDateString(new Date()));
		param.setRemark("sdfjkl");
		param.setScore(90);
		mdTaskService.createTask(21, param);*/
		 /*CommitTaskParam param = new CommitTaskParam();
		 param.setRemark("nodeddddd");
		 param.setTaskid(5);
		mdTaskService.commitTask(22, param);*/
		/* CheckTaskParam param = new CheckTaskParam();
		 param.setRemark("df");
		 param.setScore(6);
		 param.setTaskid(5);
		mdTaskService.checkTask(21, param);*/
		//GetTaskDetailBody taskDetail = mdTaskService.getTaskDetail(21, 5);
         // List<GetMonthScoreListBody> monthScoreList = mdTaskService.getMonthScoreList(21, 9, 2017);
		//mdTaskService.TaskJobForMonthScore();
          logger.debug("");
	}
	
	
	@Test
	public void testDept(){
		
		//GetDepartmentDetailBody depar = mdDeptService.getDepartmentDetail(21, 9, null, null);
		//System.out.println(depar);
		
		//List<GetDepartmemberListbody> li = mdDeptService.getDepartmemberListbody(21, 9);
		//mdDeptService.addDepartmentMember(21, 9, "13505171944");
		mdDeptService.removeDepartmentMember(21, 9, 60);
		System.out.println();
		/*AddDepartmentParam  param = new AddDepartmentParam();
		param.setCompanyid(5);
		param.setDeptname("hello部门");
		param.setMgruserid(60);
		
		List<Duty> dutylist = new ArrayList<>();
		Duty duty = new Duty();
		duty.setDutyname("sdfjk");
		duty.setRatio(45);
		duty.setRemark("remark");
		duty.setDutytype(1);
		List<UploadAttach> attachs = new ArrayList<>();
		UploadAttach at = new UploadAttach();
		at.setFk("jlkjl");
		at.setFn("90");
		at.setFs("24");
		at.setFt("65");
		attachs.add(at);
		duty.setAttachs(attachs);
		
		dutylist.add(duty);
		
		param.setDutylist(dutylist);
		*/
	/*	mdDeptService.addDepartment(21, param);*/	
		
		
		
	}
	
	@Test
	public void modifydept(){
		ModifyDepartmentParam  param = new ModifyDepartmentParam();
		param.setDeptid(9);
		param.setDeptname("hello部门2");
		param.setMgruserid(59);
		
		List<DutyModify> dutylist = new ArrayList<>();
		DutyModify duty = new DutyModify();
		duty.setDutyname("duty");
		duty.setRatio(45);
		duty.setRemark("remark");
		duty.setDutytype(1);
		duty.setDutyid(0);
		
		List<UploadAttachParam> attachs = new ArrayList<>();
		UploadAttachParam at = new UploadAttachParam();
		at.setFk("888888");
		at.setFn("90");
		at.setFs("24");
		at.setFt("65");
		attachs.add(at);
		duty.setAttachs(attachs);
		
		dutylist.add(duty);
		
		param.setDutylist(dutylist);
		
		mdDeptService.modifyDepartment(21, param);
		
	}
	
	@Test
	public void delDept(){
		mdDeptService.removeDepartment(21, 3);
	}
	/*
	@Test
	public void testCompany(){
		List<MdDeptEx> mdDeptExForCompany = mdDepartmentMapper.getMdDeptExForCompany(5);
		//boolean managerForCompany = mdCompanyService.isManagerForCompany(21, 20);
		//System.out.println(managerForCompany);
		System.out.println("sdf");
		List<MdCompanyEx> list = mdCompanyMapper.queryComanpy();
		System.out.println(list);
		
		MdCompany m = mdCompanyMapper.queryByName("全名西本3");
		System.out.println(m);
		
	}
	@Test
	public void CompanyDetail(){
		
		MdDepartmentMember memberByUseridAndDeptid = mdDeptService.getMemberByUseridAndDeptid(21, 8);
		System.out.println(memberByUseridAndDeptid);
		//mdCompanyService.getCompanyDetail(21, 5);
	}
	@Test
	public void companeyMgr(){
		
		mdCompanyService.getCompanymgrList(21, 5);
	}
	
	@Test
	public void addMgr(){
		mdCompanyService.addCompanyManager(21, 5, "18626150532");
	}
	@Test
	public void delMgr(){
		
		mdCompanyService.removeCompanyManager(21, 5, 9);
	}*/
	
	@Test
	public void insertCompany(){
		User user = new User();
		user.setUserid(52);
		MdCompany md_ = new MdCompany();
		md_.setFullname("电脑公司全称");
		md_.setShortname("电脑公司简称");
		MdAttchment m = new MdAttchment();
		m.setFilekey("0205e429b5fd4611b015c57f0bf83bb0.jpg");
		m.setFilename("filename");
		m.setFilesize("1000");
		m.setFiletype("jpg");
		
		mdCompanyService.addCompany(md_, user, m);
		
	/*	for(int i=3;i<100;i++){
			MdCompany m  = new MdCompany();
			
			m.setCompanyid(i);
			m.setCreateby(i);
			m.setCreatedate(new Date());
			m.setFullname("全名西本"+i);
			m.setLogo("log"+i);
			m.setShortname("西本简称"+i);
			m.setUpdateby(i);
			m.setUpdatedate(new Date());
			m.setShortnamespell("西本简拼"+i);
			
			mdCompanyMapper.insertSelective(m);
		}*/
	}
	
}
