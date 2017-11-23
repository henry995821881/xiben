package com.xiben.pm.ar.service.impl;
import java.io.File;
import java.util.*;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.xiben.pm.ar.mapper.*;
import com.xiben.pm.ar.pojo.*;
import com.xiben.pm.ar.request.ArchivePage;
import com.xiben.pm.ar.request.REPBaseArchive;
import com.xiben.pm.ar.response.RESPArchiveInfo;
import com.xiben.pm.ar.service.PushService;
import com.xiben.pm.md.mapper.MdNoticeMapper;
import com.xiben.pm.md.pojo.*;
import com.xiben.pm.md.request.getDownloadUrlParam;
import com.xiben.pm.md.response.GetDownloadUrlResult;
import com.xiben.pm.md.service.EmployeeService;
import com.xiben.pm.md.service.MdDeptService;
import com.xiben.pm.wf.utils.ArrayUtils;
import com.xiben.pm.wf.utils.FiledClone;
import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.xiben.common.utils.DateUtils;
import com.xiben.pm.ar.request.CreateArchiveParam;
import com.xiben.pm.ar.service.ArService;
import com.xiben.pm.ar.utils.ArchiveSource;
import com.xiben.pm.exception.PMRuntimeException;
import com.xiben.pm.md.service.AttachmentService;
import com.xiben.pm.md.service.MdCompanyService;

@Service
public class ArServiceImpl implements ArService{



	@Autowired
	EmployeeService employeeService;
	@Autowired
	ArArchiveReadapplyMapper readapplyMapper;

	@Autowired
	MdNoticeMapper noticeMapper;

	@Autowired
	ArArchiveSecretapproveuserMapper arArchiveSecretapproveuserMapper;

	@Autowired
	ArArchiveMapper archiveMapper;
	
	@Autowired
	ArArchiveSequenceMapper archiveSequenceMapper;
	
	@Autowired
	AttachmentService attachmentService;
	
	@Autowired
	MdCompanyService mdCompanyService;

	@Autowired
	MdDeptService deptService;





	@Autowired
	ArArchiveReadrecordMapper arArchiveReadrecordMapper;



	private String getCompanyShortNameSpell(int compid)
	{
		MdCompany comp = mdCompanyService.getbyid(compid);
		if (null==comp)
			throw new PMRuntimeException("公司信息有误");
		String shortNameSpell = comp.getShortnamespell();
		String pre = "";
		if (shortNameSpell!=null)
		{
			if (shortNameSpell.length()>2)
				pre=shortNameSpell.substring(0, 2);
			else
				pre = shortNameSpell;
		}
		return pre.toLowerCase();
	}
	
	public String generateWorkFlowSequenceNumber(int compid,int userid)
	{
		String pre = getCompanyShortNameSpell(compid);
		String groupno = String.format("workflow%d", compid);
		int number = generateSequenceNumber(compid,groupno,userid);
		
		String strNumber = "";
		if (number<10)
			strNumber=String.format("00%d", number);
		else if (number<100)
			strNumber=String.format("0%d", number);
		else 
			strNumber=String.format("%d", number);
		
		String flowno = String.format("%s%s-%s", pre,"l",strNumber);
		return flowno;
		
	}



	private int generateSequenceNumber(int compid,String groupno,int userid)
	{
		ArArchiveSequence sequence = archiveSequenceMapper.getArArchiveSequenceByCompidAndGroupno(compid, groupno);
		if (null==sequence)
		{
			sequence = new ArArchiveSequence();
			sequence.setCompid(compid);
			sequence.setCreateby(userid);
			sequence.setCreatedate(new Date());
			sequence.setCurrentnumber(1);
			sequence.setGroupno(groupno);
			sequence.setUpdateby(userid);
			sequence.setUpdatedate(new Date());
			this.archiveSequenceMapper.insert(sequence);
		}else
		{
			sequence.setCurrentnumber(sequence.getCurrentnumber()+1);
			this.archiveSequenceMapper.updateByPrimaryKey(sequence);
		}
		return sequence.getCurrentnumber();
	}
	
	private String generateArchiveSequenceNumber(int compid,int source,int userid) {
		
		String pre = getCompanyShortNameSpell(compid);
		
		
		String currentDate = DateUtils.formatDate(new Date(), "yyyyMMdd");
		String groupno = String.format("%s%d", currentDate,source);
		
		
		String sourceLetter = "f";
		if (source==ArchiveSource.ReceiveDoc)
			sourceLetter = "s";
		
		int number =  generateSequenceNumber(compid,groupno,userid);
		String strNumber = "";
		if (number<10)
			strNumber=String.format("00%d", number);
		else if (number<100)
			strNumber=String.format("0%d", number);
		else 
			strNumber=String.format("%d", number);
		
		String archiveNo = String.format("%s%s-%s%s", pre,sourceLetter,currentDate,strNumber);
		return archiveNo;
	}
	
	public void createArchive(CreateArchiveParam param,int userid) {
		
		if (param.getMdAttachments()==null)
			throw new PMRuntimeException("归档文件不存在");
		
		for(MdAttchment attachment: param.getMdAttachments())
		{
			ArArchive archive = new ArArchive();
			archive.setArcdate(new Date());
			archive.setArcname(attachment.getFilename());
			archive.setArcno(generateArchiveSequenceNumber(param.getCompid(),param.getSource(),userid));
			archive.setArctype(attachment.getFiletype());
			archive.setCompid(param.getCompid());
			archive.setCreateby(userid);
			archive.setCreatedate(new Date());
			archive.setDeptid(param.getDeptid());
			archive.setInsid(param.getInsid());
			archive.setSecretgrade(param.getSecretgrade());
			archive.setSource(param.getSource());
			archive.setUpdateby(userid);
			archive.setUpdatedate(new Date());
			
			if (param.getApproveUserList()!=null&&!param.getApproveUserList().isEmpty())
			{
				for (Integer approveUserId:param.getApproveUserList())
				{
					ArArchiveSecretapproveuser  approveUser= new ArArchiveSecretapproveuser();
					approveUser.setApproveuserid(approveUserId);
					approveUser.setArchiveid(archive.getArchiveid());
					approveUser.setCreateby(userid);
					approveUser.setCreatedate(new Date());
					approveUser.setUpdateby(userid);
					approveUser.setUpdatedate(new Date());
					this.arArchiveSecretapproveuserMapper.insert(approveUser);
				}
			}
			
			if (archiveMapper.insert(archive)!=1)
				throw new PMRuntimeException("创建档案发生错误");
			
			attachment.setArchiveid(archive.getArchiveid());
			attachment.setSecretgrade(param.getSecretgrade());
			attachment.setIsarchive(1);
			
			this.attachmentService.update(attachment);
		}
	}























	@Override
	public List<RESPArchiveInfo> archiveList(REPBaseArchive repBaseArchive, int userid) {
		PageHelper.startPage(repBaseArchive.getCurpageno(),repBaseArchive.getPagesize());
		List<ArArchive> arArchives = archiveMapper.queryAllWithKeywords(repBaseArchive.getKeyword());
		PageInfo<ArArchive> pageInfo  = new PageInfo<>(arArchives);
		//转list
		List<RESPArchiveInfo> respArchiveInfos = new ArrayList<>();
		for(ArArchive arArchive:pageInfo.getList()){
			RESPArchiveInfo data = FiledClone.cloneFiled(arArchive,RESPArchiveInfo.class);
			ArchivePage archivePage = new ArchivePage();
			archivePage.setPagenum(pageInfo.getPageNum());
			archivePage.setCurpageno(repBaseArchive.getCurpageno());
			archivePage.setPagesize(pageInfo.getPageSize());
			archivePage.setTotalsize(pageInfo.getTotal());
			respArchiveInfos.add(data);
		}
		return respArchiveInfos;
	}

	private ArArchiveReadapply createReadapply(int userid,ArArchive arArchive,String remark){
		ArArchiveReadapply arArchiveReadapply = new ArArchiveReadapply();
		arArchiveReadapply.setCompid(arArchive.getCompid());
		arArchiveReadapply.setAppuserid(userid);
		arArchiveReadapply.setArchiveid(arArchive.getArchiveid());
		arArchiveReadapply.setRemark(remark);
		arArchiveReadapply.setStatus(1);
		arArchiveReadapply.setCreateby(userid);
		arArchiveReadapply.setUpdateby(userid);
		arArchiveReadapply.setCreatedate(new Date());
		arArchiveReadapply.setUpdatedate(new Date());
		return arArchiveReadapply;
	}
	@Autowired
	PushService pushService;
	private MdNotice createdNotice(int arArchiveReadapplyId,String content,int userid,int recvUserid,boolean push){

		MdNotice notice = new MdNotice();
		notice.setReadflag(1);
		notice.setSendflag(1);
		notice.setBizid(arArchiveReadapplyId);//申请ID
		notice.setMsgcontent(content);
		notice.setSenduserid(userid);
		notice.setBiztype(1);
		notice.setReceiveuserid(recvUserid);
		notice.setCreateby(userid);
		notice.setUpdateby(userid);
		notice.setCreatedate(new Date());
		notice.setUpdatedate(new Date());
		//发送通知
		if (push){
			pushService.pushWithTag(content,recvUserid+"");
		}
		return notice;
	}

	@Override
	public int applyarchiveread(REPBaseArchive params, int userid) {

		if (params == null){
			throw new RuntimeException("参数不能为空!");
		}
		ArArchive arArchive = archiveMapper.selectByPrimaryKey(params.getArchiveid());


		Integer root = mdCompanyService.isRoot(userid, arArchive.getCompid());
		boolean isManager = root!=null;
		if (isManager){
			throw new RuntimeException("管路员无需申请");
		}
		if (arArchive.getSecretgrade() == 1){
			throw new RuntimeException("普通档案无需申请");
		}


		List<ArArchiveReadapply> arArchiveReadapplys = readapplyMapper.getBySomeId(arArchive.getCompid(), userid, arArchive.getArchiveid());
		if (!ArrayUtils.isEmpty() && arArchiveReadapplys.get(0).getStatus() == 1){
			throw new PMRuntimeException("您的申请还未被处理,不能重复申请!");
		}
		//查阅记录是否存在
		for(ArArchiveReadapply arArchiveReadapply:arArchiveReadapplys){
			List<ArArchiveReadrecord> arArchiveReadrecords = arArchiveReadrecordMapper.queryByAppid(arArchiveReadapply.getAppid(), arArchive.getArchiveid());
			if (ArrayUtils.isEmpty(arArchiveReadrecords) && arArchiveReadapply.getStatus() == 2){
				throw new PMRuntimeException("您的申请已经通过,可直接查阅该档案!");
			}

		}
			//如果已经审批并且没有查阅
		if (arArchive.getSecretgrade() == 2){
			//是否是最后审批人
			List<ArArchiveSecretapproveuser> arArchiveSecretapproveusers = arArchiveSecretapproveuserMapper.queryBy("archiveid", arArchive.getArchiveid()+"");
			boolean createarArchiveReadapply = false;
			Integer arArchiveReadapplyId = null;
			for(ArArchiveSecretapproveuser arArchiveSecretapproveuser:arArchiveSecretapproveusers){
				if (arArchiveSecretapproveuser.getApproveuserid() == userid){
					throw new PMRuntimeException("审批人无需申请");
				}

				if (!createarArchiveReadapply){
					//如果是普通成员,提交申请
					ArArchiveReadapply arArchiveReadapply = createReadapply(userid,arArchive,params.getRemark());
					readapplyMapper.insert(arArchiveReadapply);
					createarArchiveReadapply = true;
					arArchiveReadapplyId = arArchiveReadapply.getAppid();

				}
				//创建消息
				MdNotice notice = createdNotice(arArchiveReadapplyId,params.getRemark(),userid,arArchiveSecretapproveuser.getApproveuserid(),true);
				noticeMapper.insert(notice);
			}

		}

		//绝密档案发送消息给管理员
		if (arArchive.getSecretgrade() == 3){

			ArArchiveReadapply arArchiveReadapply = createReadapply(userid,arArchive,params.getRemark());
			readapplyMapper.insert(arArchiveReadapply);
			//获取公司管理员
			//send message to manager
			List<MdCompanyMember> managers = mdCompanyService.getManager(arArchive.getCompid());
			for(MdCompanyMember mdCompanyMember:managers){
				MdNotice notice = createdNotice(arArchiveReadapply.getAppid(), params.getRemark(), userid, mdCompanyMember.getUserid(),true);
				noticeMapper.insert(notice);
			}

		}

		return 0;

	}

	/**
	 * 生成查阅记录
	 */
	private void createReadRecord(ArArchive arArchive,int userid,boolean strong){
//		arArchiveReadrecordMapper

		//检查这个档案是否已经审批过

		//获取申请id
		Integer appid = 0;
		Date approvedate = new Date();
		Integer approveuserid = 0;
		if (arArchive.getSecretgrade() == 1 || strong){
			//直接生成查阅记录
		}else{
			List<ArArchiveReadapply> byArids = readapplyMapper.getByArid(arArchive.getArchiveid());//理论上只有一条记录
			//需要检查审批
			if (!ArrayUtils.isEmpty(byArids) && byArids.size() ==1){
				ArArchiveReadapply arArchiveReadapply = byArids.get(0);
				List<ArArchiveReadrecord> arArchiveReadrecords = arArchiveReadrecordMapper.queryByAppid(arArchiveReadapply.getAppid(), arArchive.getArchiveid());
				if (!ArrayUtils.isEmpty(arArchiveReadrecords)){
					ArArchiveReadrecord arArchiveReadrecord = arArchiveReadrecords.get(0);

					//如果审批通过，并且已经查阅过了，需要重新审批申请
					if (arArchiveReadapply.getStatus() == 2 && arArchiveReadrecord.getStatus() ==2){
						throw  new RuntimeException("您的档案已经查阅过了!再次查阅需要重新发起申请!");
					}
				}

				if (arArchiveReadapply.getStatus() != 2){
					String[] msg = new String[]{"未知状态,请联系管理员!","您申请的查阅还没有被审批","查阅成功","您的申请的档案没有审批通过"};
					throw  new RuntimeException(msg[arArchiveReadapply.getStatus()]);
				}else{
					//审批通过
					appid = arArchiveReadapply.getAppid();
					approvedate = arArchiveReadapply.getApprovedt();
					approveuserid = arArchiveReadapply.getApproveuserid();
				}
			}else{
				throw new PMRuntimeException("审批通过后才能查阅此档案!");
			}
		}
		ArArchiveReadrecord arArchiveReadrecord = new ArArchiveReadrecord();
		arArchiveReadrecord.setCompid(arArchive.getCompid());
		arArchiveReadrecord.setArchiveid(arArchive.getArchiveid());
		arArchiveReadrecord.setAppid(appid);
		arArchiveReadrecord.setApprovedate(approvedate);
		arArchiveReadrecord.setApproveuserid(approveuserid);
		arArchiveReadrecord.setReaddate(new Date());
		arArchiveReadrecord.setReaduserid(userid);
		arArchiveReadrecord.setStatus(2);
		arArchiveReadrecord.setUpdateby(userid);
		arArchiveReadrecord.setCreateby(userid);
		arArchiveReadrecord.setCreatedate(new Date());
		arArchiveReadrecord.setUpdatedate(new Date());
		arArchiveReadrecordMapper.insert(arArchiveReadrecord);

	}

	@Override
	public Map<String,String> archiveread(Integer aid, int userid) {


		if (aid == null){
			throw new PMRuntimeException("id不能为空");
		}
//		判断当前用户是否为公司管理员、部门授权人和部门成员。
		ArArchive arArchive = archiveMapper.selectByPrimaryKey(aid);
		List<MdCompanyMember> managers = mdCompanyService.getManager(arArchive.getCompid());
		boolean ismanager = false;
		for(MdCompanyMember mdCompanyMember:managers){
			if (mdCompanyMember.getUserid() == userid){
				ismanager =  true;

			}
		}


		List<MdEmployeeEntity> deptManagerByDeptids = deptService.getDeptManagerByDeptid(arArchive.getDeptid());
		boolean ishouquan = false;
		for(MdEmployeeEntity mdEmployeeEntity:deptManagerByDeptids){
			if (mdEmployeeEntity.getUserid() == userid){
				ishouquan = true;
			}
		}

		boolean ischengyuan = deptService.getMemberByUseridAndDeptid(userid, arArchive.getDeptid()) != null;


		if (!(ischengyuan || ishouquan || ismanager)){
			throw new RuntimeException("没有权限");
		}


		Map<String,String> resp = new HashMap<>();

//		1=普通，2=加密，3=绝密
		List<MdAttchment> attchmentWithAids = attachmentService.getAttchmentWithAid(arArchive.getArchiveid(), userid);
		if (ArrayUtils.isEmpty(attchmentWithAids)){
			throw new RuntimeException("档案不存在!");
		}
		getDownloadUrlParam params = new getDownloadUrlParam();
		params.setAttachid(attchmentWithAids.get(0).getAttachid());
		GetDownloadUrlResult downloadUrlResult = attachmentService.getDownloadUrl(params, userid);
		if (downloadUrlResult == null || downloadUrlResult.getDownloadurl() == null){
			throw new RuntimeException("档案不存在！");
		}
		resp.put("url",downloadUrlResult.getDownloadurl());
		if (arArchive.getSecretgrade() == 1){
			if (!ArrayUtils.isEmpty(attchmentWithAids)){
				//创建查阅记录
				createReadRecord(arArchive,userid,false);
			}

		}else if (arArchive.getSecretgrade() == 2){


			//查看当前档案最后节点的审批人
			List<ArArchiveSecretapproveuser> approveuserid = arArchiveSecretapproveuserMapper.queryBy("approveuserid", userid + "");

			if (ismanager || !ArrayUtils.isEmpty(approveuserid)){
				//创建查阅记录
				createReadRecord(arArchive,userid,false);
			}else{
				throw new PMRuntimeException("您没有权限查看此档案!");
			}

		}else if (arArchive.getSecretgrade() == 3){

			if (ismanager){
				//生成查阅记录
				createReadRecord(arArchive,userid,true);
			}else{
				throw new PMRuntimeException("您没有权限查看此档案!");
			}

		}else{
			throw new PMRuntimeException("您没有权限查看此档案!");
		}


		return resp;

	}

	@Override
	public int approvearchiveread(REPBaseArchive params, int userid) {


		if (params == null){
			throw new PMRuntimeException("参数不能为空");
		}

		Integer status = params.getOptype();
		if (status>0 && status<=3){

			ArArchiveReadapply arArchiveReadapply = readapplyMapper.selectByPrimaryKey(params.getAppid());

			if (arArchiveReadapply.getStatus() != 1){
				throw new PMRuntimeException("此申请已经审批过了!");
			}

			ArArchive arArchive = archiveMapper.selectByPrimaryKey(arArchiveReadapply.getArchiveid());
			if (arArchive.getSecretgrade() == 2){
				//如果为加密档案
				List<ArArchiveSecretapproveuser> approveuserid = arArchiveSecretapproveuserMapper.queryBy("approveuserid", userid + "");
				if (ArrayUtils.isEmpty(approveuserid)){
					throw new PMRuntimeException("加密档案只有最后审批人才能操作!");
				}

				if (approveuserid.get(0).getApproveuserid() == userid){
					//如果是审批人
					arArchiveReadapply.setUpdatedate(new Date());
					arArchiveReadapply.setUpdateby(userid);
					arArchiveReadapply.setStatus(status);
					readapplyMapper.updateByPrimaryKey(arArchiveReadapply);
				}
			}else if (arArchive.getSecretgrade() == 3){
				//如果为绝密档案
				List<MdCompanyMember> managers = mdCompanyService.getManager(arArchiveReadapply.getCompid());
				for(MdCompanyMember mdCompanyMember:managers){
					if (mdCompanyMember.getUserid() == userid){
						//如果是公司管理员
						arArchiveReadapply.setUpdatedate(new Date());
						arArchiveReadapply.setUpdateby(userid);
						arArchiveReadapply.setStatus(status);
						readapplyMapper.updateByPrimaryKey(arArchiveReadapply);
						return 1;
					}
				}
				throw new PMRuntimeException("绝密档案只有管理与才能操作!");
			}



		}else{
			throw new PMRuntimeException("参数非法!");
		}
		return 0;

	}



	public String generateTaskSequenceNumber(int compid,int userid)
	{
		String pre = getCompanyShortNameSpell(compid);
		String groupno = String.format("task%d", compid);
		int number = generateSequenceNumber(compid,groupno,userid);
		
		String strNumber = "";
		if (number<10)
			strNumber=String.format("00%d", number);
		else if (number<100)
			strNumber=String.format("0%d", number);
		else 
			strNumber=String.format("%d", number);
		
		String flowno = String.format("%s%s-%s", pre,"t",strNumber);
		return flowno;
		
	}

}







