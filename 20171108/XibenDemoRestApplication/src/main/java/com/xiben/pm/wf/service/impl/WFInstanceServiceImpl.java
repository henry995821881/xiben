package com.xiben.pm.wf.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.xiben.pm.md.pojo.MdCompany;
import com.xiben.pm.wf.response.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.xiben.pm.ar.request.CreateArchiveParam;
import com.xiben.pm.ar.service.ArService;
import com.xiben.pm.exception.PMRuntimeException;
import com.xiben.pm.md.pojo.MdAttchment;
import com.xiben.pm.md.pojo.MdDepartment;
import com.xiben.pm.md.pojo.MdDeptduty;
import com.xiben.pm.md.service.AttachmentService;
import com.xiben.pm.md.service.MdCompanyService;
import com.xiben.pm.md.service.MdDeptService;
import com.xiben.pm.md.util.MdAttachmentBiztypeContants;
import com.xiben.pm.wf.common.WfConstants;
import com.xiben.pm.wf.mapper.WfInstanceMapper;
import com.xiben.pm.wf.mapper.WfInstanceNodeApproveUserMapper;
import com.xiben.pm.wf.mapper.WfInstanceNodeApprovepartMapper;
import com.xiben.pm.wf.mapper.WfInstanceNodeMapper;
import com.xiben.pm.wf.mapper.WfTemplateMapper;
import com.xiben.pm.wf.mapper.WfTemplateNodeApproveUserMapper;
import com.xiben.pm.wf.mapper.WfTemplateNodeApprovepartMapper;
import com.xiben.pm.wf.mapper.WfTemplateNodeMapper;
import com.xiben.pm.wf.pojo.WfInstance;
import com.xiben.pm.wf.pojo.WfInstanceNode;
import com.xiben.pm.wf.pojo.WfInstanceNodeApproveUser;
import com.xiben.pm.wf.pojo.WfInstanceNodeApprovepart;
import com.xiben.pm.wf.pojo.WfTemplate;
import com.xiben.pm.wf.pojo.WfTemplateNode;
import com.xiben.pm.wf.pojo.WfTemplateNodeApproveUser;
import com.xiben.pm.wf.pojo.WfTemplateNodeApprovepart;
import com.xiben.pm.wf.request.ApproveWorkflowParam;
import com.xiben.pm.wf.request.RPInstance;
import com.xiben.pm.wf.request.StartWorkflowParam;
import com.xiben.pm.wf.request.StopWorkFlowParam;
import com.xiben.pm.wf.service.WFInstanceService;
import com.xiben.pm.wf.service.WFTemplateServie;
import com.xiben.pm.wf.utils.FiledClone;


@Service
public class WFInstanceServiceImpl implements WFInstanceService{
	



	@Autowired
	private WfTemplateMapper wfTemplateMapper;
	
	
	@Autowired
	private WfTemplateNodeMapper wfTempNodeMapper;
	
	@Autowired
	private WfTemplateNodeApprovepartMapper wfTemplateNodeDeptMapper;
	
	@Autowired
	private WfTemplateNodeApproveUserMapper wfTemplateNodeUserMapper;

	
	@Autowired
	private WfInstanceMapper wfInsMapper;
	
	@Autowired
	private WfInstanceNodeMapper wfInsNodeMapper;
	
	@Autowired
	private WfInstanceNodeApprovepartMapper wfInsNodeDeptMapper;
	
	@Autowired
	private WfInstanceNodeApproveUserMapper wfInsNodeUserMapper;
	
	@Autowired
	AttachmentService attachmentService;
	
	@Autowired
	MdDeptService mdDeptService;
	
	@Autowired
	MdCompanyService companyService;
	
	@Autowired
	ArService arService;
	
	@Autowired
	WFTemplateServie templateService;
	
	private void checkWorkflowTemplateStatus(WfTemplate template)
	{
		if(template==null)
			throw new PMRuntimeException("流程模板数据有误");
		if (template.getStatus()!=2)
			throw new PMRuntimeException("流程模板未启用");
	}
	
	private void checkRightForStartWorkflow(int templateid,int deptid,int userid)
	{
		Map<String,Integer> map = new HashMap<String,Integer>();
		map.put("templateid",templateid);
		map.put("deptid", deptid);
		map.put("userid", userid);
		
		int count = this.wfTemplateNodeUserMapper.selectTemplateNodeUserCountForUserStartRight(map);
		if (count==0)
			throw new PMRuntimeException("该用户无启动流程权限");
		
	}
	
	private int getWorkflowDeptDutyid(WfInstance instance,int deptid)
	{
		if (instance.getType()!=WfConstants.WfType.Rule)
			return 0;
		
		MdDeptduty duty = mdDeptService.getDutyBydeptidwithtype1(deptid);
		if (duty!=null)
			return duty.getDutyid();
		
		//返回部门职责ID
		return 0;
	}
	
	private void checkWFInstanceStatus(WfInstance instance)
	{
		if (instance==null)
			throw new PMRuntimeException("流程审批数据有误");
		
		if (instance.getStatus()!=1)
			throw new PMRuntimeException("流程审批状态有误");
	}
	
	private void checkWFInstanceNodeStatus(WfInstanceNode node) {
		if (node==null)
			throw new PMRuntimeException("节点审批数据有误");
		if (node.getStatus()!=WfConstants.WfInsNodeStatus.NoApproved&&node.getStatus()!=WfConstants.WfInsNodeStatus.Approving)
			throw new PMRuntimeException("节点审批状态有误");
	}
	
	private void checkWFInstancePartStatus(WfInstanceNodeApprovepart part) {
		if (part==null)
			throw new PMRuntimeException("部门审批数据有误");
		if (part.getStatus()!=WfConstants.WfInsNodePartStatus.NoApproved&&part.getStatus()!=WfConstants.WfInsNodePartStatus.Approving)
			throw new PMRuntimeException("部门审批状态有误");
	}
	
	private void checkWFInstanceUserStatus(WfInstanceNodeApproveUser approveUser){
		if (approveUser==null)
			throw new PMRuntimeException("用户审批数据有误");
		
		if (approveUser.getStatus()!=WfConstants.WfInsNodeUserStatus.NoApproved)
			throw new PMRuntimeException("用户审批状态有误");
	}
	
	private WfInstance saveWorkflowInstanceByTemplate(StartWorkflowParam param,WfTemplate template,int userid)
	{
		WfInstance instance = new WfInstance();
		instance.setTemplateid(param.getTemplateid());
		instance.setType(template.getType());
		instance.setInsname(template.getTemplatename());
		instance.setCompid(template.getCompid());
		instance.setStartdeptid(param.getDeptid());
		instance.setStartuserid(userid);
		
		instance.setDeptdutyid(getWorkflowDeptDutyid(instance,param.getDeptid()));
		instance.setInsgrade(param.getInsgrade());
		
		instance.setRemark(param.getRemark());
		instance.setCreateby(userid);
		instance.setUpdateby(userid);
		instance.setStatus(WfConstants.WfInsStatus.NoFinish);
		
		//生成流程号
		instance.setInsno(arService.generateWorkFlowSequenceNumber(template.getCompid(), userid));
		
		wfInsMapper.insertSelective(instance);
		return instance;
	}
	
	private WfTemplateNode getStartTemplateNode(int templateid)
	{
		return wfTempNodeMapper.getStartNodeByTemplateid(templateid);
	}
	
	
	private WfInstanceNode saveInstanceNode(WfInstance instance,WfTemplateNode templateNode,int userid,int insNodeStatus)
	{
		WfInstanceNode instanceNode = new WfInstanceNode();
		
		instanceNode.setApprovetype(templateNode.getApprovetype());
		instanceNode.setCompid(templateNode.getCompid());
		instanceNode.setCreateby(userid);
		instanceNode.setInsid(instance.getInsid());
		instanceNode.setNodename(templateNode.getNodename());
		instanceNode.setNotetype(templateNode.getNotetype());
		instanceNode.setStatus(insNodeStatus);
		instanceNode.setTemplateid(templateNode.getTemplateid());
		instanceNode.setTemplatenodeid(templateNode.getTemplatenodeid());
		instanceNode.setUpdateby(userid);
		
		wfInsNodeMapper.insertSelective(instanceNode);
			
		return instanceNode;
	}
	
	private WfInstanceNodeApprovepart saveInstanceNodePart(WfInstanceNode instanceNode,WfTemplateNodeApprovepart templatePart,int userid,int status)
	{
		WfInstanceNodeApprovepart part = new WfInstanceNodeApprovepart();
		part.setApprovetype(templatePart.getApprovetype());
		part.setCompid(instanceNode.getCompid());
		part.setDeptid(templatePart.getDeptid());
		part.setInsid(instanceNode.getInsid());
		part.setInsnodeid(instanceNode.getInsnodeid());
		part.setStatus(status);
		part.setTemplateid(instanceNode.getTemplateid());
		part.setTemplatenodeid(instanceNode.getTemplatenodeid());
		part.setTemplatepartid(templatePart.getTemplatepartid());
		
		part.setCreateby(userid);
		part.setUpdateby(userid);
		
		wfInsNodeDeptMapper.insertSelective(part);
		return part;
	}
	
	private WfInstanceNodeApproveUser saveInstanceNodeUser(WfInstanceNodeApprovepart insNodePart,int approveUserid,int userid,int status,String remark) {
		WfInstanceNodeApproveUser approveUser = new WfInstanceNodeApproveUser();
		approveUser.setCompid(insNodePart.getCompid());
		approveUser.setCreateby(userid);
		approveUser.setDeptid(insNodePart.getDeptid());
		approveUser.setInsid(insNodePart.getInsid());
		approveUser.setInsnodeid(insNodePart.getInsnodeid());
		approveUser.setInspartid(insNodePart.getInspartid());
		approveUser.setStatus(status);
		approveUser.setTemplateid(insNodePart.getTemplateid());
		approveUser.setTemplatenodeid(insNodePart.getTemplatenodeid());
		approveUser.setUpdateby(userid);
		approveUser.setUserid(approveUserid);
		approveUser.setRemark(remark);
		
		wfInsNodeUserMapper.insertSelective(approveUser);
		return approveUser;
	}
	
	
	private void savePreNode(WfTemplateNode currentNode,WfInstance instance,int userid)
	{
		//保存上一节点
		WfTemplateNode preNode = wfTempNodeMapper.getPreNode(currentNode);
		if (preNode==null)
			throw new PMRuntimeException("上一节点不存在");
		
		WfInstanceNode preInsNode = saveInstanceNode(instance,preNode,userid,WfConstants.WfInsNodeStatus.NoApproved);
		
		//保存上一节点审批部门，上一节点不是启动节点
		if(preInsNode.getNotetype()!=WfConstants.WfNodeType.StartNode)
		{
			List<WfTemplateNodeApprovepart> templateNodePartList = wfTemplateNodeDeptMapper.selectTemplateNodePartListByNodeId(preInsNode.getTemplatenodeid());
			for (WfTemplateNodeApprovepart preTemplateNodePart:templateNodePartList) {
				WfInstanceNodeApprovepart preInsPart = saveInstanceNodePart(preInsNode,preTemplateNodePart,userid,WfConstants.WfInsNodePartStatus.NoApproved);
				
				//SavePreUser
				List<WfTemplateNodeApproveUser> templateUserList = wfTemplateNodeUserMapper.selectTemplatePartUserListByTempPartId(preInsPart.getTemplatepartid());
				for(WfTemplateNodeApproveUser tempNodeUser:templateUserList) {
					saveInstanceNodeUser(preInsPart,tempNodeUser.getUserid(),userid,WfConstants.WfInsNodeUserStatus.NoApproved,"");
				}
			}
			
			return;
		}
		
		//上一节点为发起节点
		WfTemplateNodeApprovepart startPart = this.getTemplateNodeApprovePartByTempNodeIdAndDeptid(preInsNode.getTemplatenodeid(), instance.getStartdeptid());
		WfInstanceNodeApprovepart preInsPart = saveInstanceNodePart(preInsNode,startPart,userid,1);
		this.saveInstanceNodeUser(preInsPart, instance.getStartuserid(), userid, WfConstants.WfInsNodeUserStatus.NoApproved, instance.getRemark());
	}
	
	//保存流程下一节点
	private void saveNextNode(WfTemplateNode currentNode,WfInstance instance,int userid)
	{
		//Save NextNode
		WfTemplateNode nextNode = wfTempNodeMapper.getNextNode(currentNode);
		
		if (nextNode==null)
			throw new PMRuntimeException("下一节点不存在");
		
		WfInstanceNode nextInsNode = saveInstanceNode(instance,nextNode,userid,WfConstants.WfInsNodeStatus.NoApproved);
		
		//Save NextPart
		List<WfTemplateNodeApprovepart> templateNodePartList = wfTemplateNodeDeptMapper.selectTemplateNodePartListByNodeId(nextInsNode.getTemplatenodeid());
		for (WfTemplateNodeApprovepart nextTemplateNodePart:templateNodePartList) {
			WfInstanceNodeApprovepart nextInsPart = saveInstanceNodePart(nextInsNode,nextTemplateNodePart,userid,WfConstants.WfInsNodePartStatus.NoApproved);
			
			//SaveNextUser
			List<WfTemplateNodeApproveUser> templateUserList = wfTemplateNodeUserMapper.selectTemplatePartUserListByTempPartId(nextInsPart.getTemplatepartid());
			for(WfTemplateNodeApproveUser tempNodeUser:templateUserList) {
				saveInstanceNodeUser(nextInsPart,tempNodeUser.getUserid(),userid,WfConstants.WfInsNodeUserStatus.NoApproved,"");
			}
		}

	}
	//启动流程
	public void startWorkFlow(StartWorkflowParam param,int userid) {
			
		
		WfTemplate template = wfTemplateMapper.selectByPrimaryKey(param.getTemplateid());
		
		//检查模板状态
		checkWorkflowTemplateStatus(template);
		
		//检查是否有启动权限
		checkRightForStartWorkflow(param.getTemplateid(),param.getDeptid(), userid);
		
		//检查收发文流程的档案等级
		if(template.getType()==WfConstants.WfType.ReceiveDoc||template.getType()==WfConstants.WfType.SendDoc)
		{
			if (param.getInsgrade()!=WfConstants.WfInsGrade.Normal&&param.getInsgrade()!=WfConstants.WfInsGrade.Middle&&param.getInsgrade()!=WfConstants.WfInsGrade.Important)
				throw new PMRuntimeException("收发文流程必须传入档案等级");
		}
		
		//保存流程实例
		WfInstance instance = saveWorkflowInstanceByTemplate(param,template,userid);

		//保存启动节点
		WfTemplateNode startNode = getStartTemplateNode(param.getTemplateid());
		WfInstanceNode instanceNode = saveInstanceNode(instance,startNode,userid,WfConstants.WfInsNodeStatus.Agree);
		
		//保存启动节点的审批部门
		WfTemplateNodeApprovepart templateNodePart = this.getTemplateNodeApprovePartByTempNodeIdAndDeptid(startNode.getTemplatenodeid(),param.getDeptid());
		WfInstanceNodeApprovepart insNodePart = saveInstanceNodePart(instanceNode,templateNodePart,userid,WfConstants.WfInsNodePartStatus.Agree);
		
		//保存启动节点的审批人
		WfInstanceNodeApproveUser instanceNodeApproveUser = saveInstanceNodeUser(insNodePart,userid,userid,WfConstants.WfInsNodeUserStatus.Agree,param.getRemark());

		//保存启动附件
		attachmentService.saveAttachmentList(param.getAttachs(), instanceNodeApproveUser.getId(), MdAttachmentBiztypeContants.WfNodeUser,instance.getInsgrade(),userid);
		
		//保存流程下一节点
		saveNextNode(startNode,instance,userid);	
	}
	
	private WfTemplateNodeApprovepart getTemplateNodeApprovePartByTempNodeIdAndDeptid(int tempNodeId,int deptid)
	{
		Map<String,Integer> map = new HashMap<String,Integer>();
		map.put("templatenodeid", tempNodeId);
		map.put("deptid", deptid);
		WfTemplateNodeApprovepart templateNodePart = wfTemplateNodeDeptMapper.selectTemplateNodePartByNodeIdAndDeptid(map);
		return templateNodePart;
	}
	
	
	//1.确认个人审批状态	
	private void approveWorkFlowForUser(ApproveWorkflowParam param,WfInstance ins,WfInstanceNode insnode, WfInstanceNodeApprovepart inspart,WfInstanceNodeApproveUser insuser, int userid)
	{

		insuser.setStatus(WfConstants.WfInsNodeUserStatus.Agree);
		insuser.setRemark(param.getRemark());
		insuser.setUpdateby(userid);
		insuser.setUpdatedate(new Date());
		if(wfInsNodeUserMapper.updateByPrimaryKey(insuser)!=1)
			throw new PMRuntimeException("流程审批人状态有误");
	}
	
	//2.确认部门审批状态
	private void approveWorkFlowForPart(ApproveWorkflowParam param,WfInstance ins,WfInstanceNode insnode, WfInstanceNodeApprovepart inspart,WfInstanceNodeApproveUser insuser, int userid)
	{
	
		if (inspart.getApprovetype()==WfConstants.WfApproveType.OR)	
		{
			//更新部门审批状态为已审批
			inspart.setStatus(WfConstants.WfInsNodePartStatus.Agree);
			inspart.setUpdateby(userid);
			inspart.setUpdatedate(new Date());
			if(wfInsNodeDeptMapper.updateByPrimaryKey(inspart)!=1)
				throw new PMRuntimeException("流程审批部门状态有误");
			
			//更新本部门其他人的审批状态
			List<WfInstanceNodeApproveUser> insUserList = wfInsNodeUserMapper.selectInstanceNodeApproveUserByInsPartId(inspart.getInspartid());
			for(WfInstanceNodeApproveUser otherInsUser:insUserList)
			{
				if (otherInsUser.getId()!=insuser.getId())
				{
					otherInsUser.setStatus(WfConstants.WfInsNodeUserStatus.Deleted);
					otherInsUser.setUpdateby(userid);
					otherInsUser.setUpdatedate(new Date());
					if (wfInsNodeUserMapper.updateByPrimaryKey(otherInsUser)!=1)
						throw new PMRuntimeException("更新本部门其他流程审批人状态发生错误");
				}
			}
		}else //会签审批
		{
			boolean isPartApprove = true;
			List<WfInstanceNodeApproveUser> insUserList = wfInsNodeUserMapper.selectInstanceNodeApproveUserByInsPartId(inspart.getInspartid());
			for(WfInstanceNodeApproveUser otherInsUser:insUserList)
			{
				if (otherInsUser.getStatus()!=WfConstants.WfInsNodeUserStatus.Agree)
				{
					isPartApprove = false;
					break;
				}
			}
			
			if (isPartApprove)
			{
				//更新部门审批状态为已审批
				inspart.setStatus(WfConstants.WfInsNodePartStatus.Agree);
				inspart.setUpdateby(userid);
				inspart.setUpdatedate(new Date());
				if(wfInsNodeDeptMapper.updateByPrimaryKey(inspart)!=1)
					throw new PMRuntimeException("流程审批部门状态有误");
			}else
			{
				//更新部门审批状态为审批中
				inspart.setStatus(WfConstants.WfInsNodePartStatus.Approving);
				inspart.setUpdateby(userid);
				inspart.setUpdatedate(new Date());
				if(wfInsNodeDeptMapper.updateByPrimaryKey(inspart)!=1)
					throw new PMRuntimeException("流程审批部门状态有误");
			}
		}
	}
	
	//3.确认节点审批状态
	private void approveWorkFlowForNode(ApproveWorkflowParam param,WfInstance ins,WfInstanceNode insnode, WfInstanceNodeApprovepart inspart,WfInstanceNodeApproveUser insuser, int userid) {
		
		
		if (inspart.getStatus()==WfConstants.WfInsNodePartStatus.Agree)
		{
			List<WfInstanceNodeApprovepart> insPartList =  wfInsNodeDeptMapper.selectInsNodePartListByInsNodeId(insnode.getInsnodeid());
			//节点或审批
			if (insnode.getApprovetype()==WfConstants.WfApproveType.OR)
			{
				for(WfInstanceNodeApprovepart otherPart: insPartList )
				{
					if (otherPart.getInspartid()!=inspart.getInspartid())
					{
						//更新其他部门审批状态
						otherPart.setStatus(WfConstants.WfInsNodePartStatus.Deleted);
						otherPart.setUpdateby(userid);
						otherPart.setUpdatedate(new Date());
						if (wfInsNodeDeptMapper.updateByPrimaryKey(otherPart)!=1)
							throw new PMRuntimeException("流程其他审批部门状态有误");
						
						//更新其他人审批状态
						List<WfInstanceNodeApproveUser> insOtherUserList = wfInsNodeUserMapper.selectInstanceNodeApproveUserByInsPartId(otherPart.getInspartid());
						{
							for(WfInstanceNodeApproveUser otherPartUser:insOtherUserList)
							{
								if (otherPartUser.getStatus()==WfConstants.WfInsNodeUserStatus.NoApproved)
								{
									otherPartUser.setStatus(WfConstants.WfInsNodeUserStatus.Deleted);
									otherPartUser.setUpdateby(userid);
									otherPartUser.setUpdatedate(new Date());
									if (wfInsNodeUserMapper.updateByPrimaryKey(otherPartUser)!=1)
										throw new PMRuntimeException("其他部门审批人状态有误");
								}
							}
						}
					}
				}
				
				//更新节点状态为已审批
				insnode.setStatus(WfConstants.WfInsNodeStatus.Agree);
				insnode.setUpdateby(userid);
				insnode.setUpdatedate(new Date());
				if (wfInsNodeMapper.updateByPrimaryKey(insnode)!=1)
					throw new PMRuntimeException("流程节点状态有误");
			}else //节点会签
			{
				//判断节点是否审批通过
				boolean isApproveInsNode = true;
				
				for(WfInstanceNodeApprovepart otherPart: insPartList )
				{
					if (otherPart.getStatus()!=WfConstants.WfInsNodePartStatus.Agree)
					{
						isApproveInsNode=false;
						break;
					}
				}
				
				if (isApproveInsNode)
				{
					//更新节点状态为已审批
					insnode.setStatus(WfConstants.WfInsNodeStatus.Agree);
					insnode.setUpdateby(userid);
					insnode.setUpdatedate(new Date());
					if (wfInsNodeMapper.updateByPrimaryKey(insnode)!=1)
						throw new PMRuntimeException("流程节点状态有误");
				}else
				{
					//更新节点状态为审批中
					insnode.setStatus(WfConstants.WfInsNodeStatus.Approving);
					insnode.setUpdateby(userid);
					insnode.setUpdatedate(new Date());
					if (wfInsNodeMapper.updateByPrimaryKey(insnode)!=1)
						throw new PMRuntimeException("流程节点状态有误");
				}
				
			}
			
		}else {
			insnode.setStatus(WfConstants.WfInsNodeStatus.Approving);
			insnode.setUpdateby(userid);
			insnode.setUpdatedate(new Date());
			if (wfInsNodeMapper.updateByPrimaryKey(insnode)!=1)
				throw new PMRuntimeException("流程节点状态有误");
		}
	}
	
	private List<MdAttchment> getInsStartNodeAttachments(int insid)
	{
		List<MdAttchment> returnList = new ArrayList<MdAttchment>();
			
		List<Integer> approveUserEntityIdList = this.wfInsNodeUserMapper.selectInstanceStartNodeApprovedUser(insid);
		for(Integer id:approveUserEntityIdList)
		{
			List<MdAttchment> mdAttachments =  this.attachmentService.query(id,MdAttachmentBiztypeContants.WfNodeUser);
			returnList.addAll(mdAttachments);
		}
		return returnList;
	}
	
	//4、确认流程流转
	private void approveWorkFlowForInstance(ApproveWorkflowParam param,WfInstance ins,WfInstanceNode insnode, WfInstanceNodeApprovepart inspart,WfInstanceNodeApproveUser insuser, int userid) {
		
		//节点未审批通过直接返回
		if (insnode.getStatus()!=WfConstants.WfInsNodeStatus.Agree)
			return;
		
		if (insnode.getNotetype()!=WfConstants.WfNodeType.EndNode)
		{
			WfTemplateNode currentNode =  wfTempNodeMapper.selectByPrimaryKey(insnode.getTemplatenodeid());
			this.saveNextNode(currentNode, ins, userid);
			return;
		}
			
		//流程结束
		ins.setStatus(WfConstants.WfInsStatus.Finished);
		ins.setUpdateby(userid);
		ins.setUpdatedate(new Date());
		if (this.wfInsMapper.updateByPrimaryKey(ins)!=1)
			throw new PMRuntimeException("流程状态有误");
		
		//生成规章制度
		if (ins.getType()==WfConstants.WfType.Rule&&ins.getDeptdutyid()>0)
		{

			List<MdAttchment> mdAttachments =  getInsStartNodeAttachments(ins.getInsid());
			for(MdAttchment attach:mdAttachments)
			{
				attach.setAttachid(null);
				attach.setBiztype(MdAttachmentBiztypeContants.DutyFile);
				attach.setBizid(ins.getDeptdutyid());
				this.attachmentService.saveAttachment(attach);
			}
		}
		
		//生成档案
		if (ins.getType()==WfConstants.WfType.ReceiveDoc||ins.getType()==WfConstants.WfType.SendDoc)
		{
			List<MdAttchment> mdAttachments =  getInsStartNodeAttachments(ins.getInsid());
			
			CreateArchiveParam createArchiveParam = new CreateArchiveParam();
			createArchiveParam.setCompid(ins.getCompid());
			createArchiveParam.setDeptid(ins.getStartdeptid());
			createArchiveParam.setInsid(ins.getInsid());
			createArchiveParam.setMdAttachments(mdAttachments);
			createArchiveParam.setSecretgrade(ins.getInsgrade());
			createArchiveParam.setSource(ins.getType());
			
			//生成档案审批人
			if (ins.getInsgrade()==WfConstants.WfInsGrade.Middle)
			{
				List<WfInstanceNodeApproveUser> approveUserList = this.wfInsNodeUserMapper.selectInstanceNodeApproveUserListByInsNodeid(insnode.getInsnodeid());
				List<Integer> paramApproveUserList = new ArrayList<Integer>();
				for(WfInstanceNodeApproveUser approveUser:approveUserList)
				{
					if (approveUser.getStatus()==WfConstants.WfInsNodeUserStatus.Agree) {
						paramApproveUserList.add(approveUser.getUserid());
					}
				}
				createArchiveParam.setApproveUserList(paramApproveUserList);
			}
			
			arService.createArchive(createArchiveParam, userid);
		}
	}
	
	private void approveWorkFlow(ApproveWorkflowParam param,WfInstance ins,WfInstanceNode insnode, WfInstanceNodeApprovepart inspart,WfInstanceNodeApproveUser insuser, int userid) {
		//1.确认个人审批状态
		approveWorkFlowForUser(param,ins,insnode,inspart,insuser,userid);
		
		//2.确认部门审批状态
		approveWorkFlowForPart(param,ins,insnode,inspart,insuser,userid);
		
		//3.确认节点审批状态
		approveWorkFlowForNode(param,ins,insnode,inspart,insuser,userid);
		
		//4、确认流程流转
		approveWorkFlowForInstance(param,ins,insnode,inspart,insuser,userid);
	}
	
	private void rejectWorkFlow(ApproveWorkflowParam param,WfInstance ins,WfInstanceNode insnode, WfInstanceNodeApprovepart inspart,WfInstanceNodeApproveUser insuser,int userid) {
		
		if (insnode.getNotetype()==1)
			throw new PMRuntimeException("当前节点为发起节点");
		
		//1.更新用户审批状态为已拒绝
		//1.1 更新用户审批状态为已拒绝
		insuser.setStatus(WfConstants.WfInsNodeUserStatus.Rejected);
		insuser.setRemark(param.getRemark());
		insuser.setUpdateby(userid);
		insuser.setUpdatedate(new Date());
		if (this.wfInsNodeUserMapper.updateByPrimaryKey(insuser)!=1)
			throw new PMRuntimeException("用户审批状态有误");

		//1.2 更新本部门其他用户审批状态为拒绝
		List<WfInstanceNodeApproveUser> insOtherUserList = wfInsNodeUserMapper.selectInstanceNodeApproveUserByInsPartId(inspart.getInspartid());
		for(WfInstanceNodeApproveUser otherUser:insOtherUserList)
		{
			if (otherUser.getId()!=insuser.getId())
			{
				if (otherUser.getStatus()==1)
				{
					otherUser.setStatus(WfConstants.WfInsNodeUserStatus.Deleted);
					otherUser.setUpdateby(userid);
					otherUser.setUpdatedate(new Date());
					if (this.wfInsNodeUserMapper.updateByPrimaryKey(otherUser)!=1)
						throw new PMRuntimeException("本部门其他用户审批状态有误");		
				}
			}
		}
		
		//2.更新部门审批状态为已拒绝
		//2.1 更新部门审批状态为已拒绝
		inspart.setStatus(WfConstants.WfInsNodePartStatus.Rejected);
		inspart.setUpdateby(userid);
		inspart.setUpdatedate(new Date());
		if (this.wfInsNodeDeptMapper.updateByPrimaryKey(inspart)!=1)
			throw new PMRuntimeException("部门审批状态有误");
		
		//2.2 更新其他部门和其他人的审批状态
	    List<WfInstanceNodeApprovepart> otherPartList =	this.wfInsNodeDeptMapper.selectInsNodePartListByInsNodeId(insnode.getInsnodeid());
	    for(WfInstanceNodeApprovepart otherPart:otherPartList)
	    {
	    	if (otherPart.getInspartid()!=inspart.getInspartid())
	    	{
	    		if (otherPart.getStatus()==WfConstants.WfInsNodePartStatus.NoApproved)
	    		{
	    			List<WfInstanceNodeApproveUser> otherUserList = this.wfInsNodeUserMapper.selectInstanceNodeApproveUserByInsPartId(otherPart.getInspartid());
	    			for(WfInstanceNodeApproveUser otherUser:otherUserList)
	    			{
	    				if (otherUser.getStatus()==WfConstants.WfInsNodeUserStatus.NoApproved)
	    				{
	    					otherUser.setStatus(WfConstants.WfInsNodeUserStatus.Deleted);
	    					otherUser.setUpdateby(userid);
	    					otherUser.setUpdatedate(new Date());
	    					if (this.wfInsNodeUserMapper.updateByPrimaryKey(otherUser)!=1)
	    						throw new PMRuntimeException("其他人审批状态有误");
	    				}
	    			}
	    			
    				otherPart.setStatus(WfConstants.WfInsNodePartStatus.Deleted);
    				otherPart.setUpdateby(userid);
    				otherPart.setUpdatedate(new Date());
    				if (this.wfInsNodeDeptMapper.updateByPrimaryKey(otherPart)!=1)
    					throw new PMRuntimeException("其他部门审批状态有误");
	    		}
	    	}
	    }
	    
		//3.更新节点审批状态为已拒绝
		insnode.setStatus(WfConstants.WfInsNodeStatus.Rejected);
		insnode.setUpdateby(userid);
		insnode.setUpdatedate(new Date());
		if (this.wfInsNodeMapper.updateByPrimaryKey(insnode)!=1)
			throw new PMRuntimeException("流程节点状态有误");
		
		//4.确认是否需要启动下一个流程。
		WfTemplateNode currentTemplateNode = this.wfTempNodeMapper.selectByPrimaryKey(insnode.getTemplatenodeid());
		this.savePreNode(currentTemplateNode, ins, userid);
	}
	
	public void gotoNext(ApproveWorkflowParam param,int userid) {
		
		//检查数据有效性
		if(param.getOptype()!=1 && param.getOptype()!=2)
			throw new PMRuntimeException("参数传入有误");
		
		WfInstanceNodeApprovepart currentPart = wfInsNodeDeptMapper.selectByPrimaryKey(param.getInspartid());
		this.checkWFInstancePartStatus(currentPart);
		
		WfInstanceNode currentNode = wfInsNodeMapper.selectByPrimaryKey(currentPart.getInsnodeid());
		this.checkWFInstanceNodeStatus(currentNode);
		
		WfInstance instance = wfInsMapper.selectByPrimaryKey(currentNode.getInsid());
		this.checkWFInstanceStatus(instance);
		
		
		WfInstanceNodeApproveUser currentUser =  getInstanceApproveUserByInsPartId(currentPart.getInspartid(),userid);
		this.checkWFInstanceUserStatus(currentUser);
		
		if (param.getOptype()==1)
		{
			approveWorkFlow(param,instance,currentNode,currentPart,currentUser,userid);
		}
		
		if (param.getOptype()==2)
		{
			rejectWorkFlow(param,instance,currentNode,currentPart,currentUser,userid);
		}	
	}
	
	public WfInstanceNodeApproveUser getInstanceApproveUserByInsPartId(int inspartid,int userid)
	{
		Map<String,Integer> map = new HashMap<String,Integer>();
		map.put("inspartid",inspartid);
		map.put("userid", userid);
		
		WfInstanceNodeApproveUser approveUser =  wfInsNodeUserMapper.selectInstanceNodeApproveUserByUserId(map);
		return approveUser;
	}
	
	public void stopWorkFlow(StopWorkFlowParam param,int userid) {


		//检查流程实例状态
		WfInstance instance = this.wfInsMapper.selectByPrimaryKey(param.getInsid());
		this.checkWFInstanceStatus(instance);
		
		if (userid!=instance.getStartuserid())
			throw new PMRuntimeException("非流程发起人不能终止");
		
		//获取启动节点实例
		WfInstanceNode startInsNode = this.wfInsNodeMapper.selectInsNoApproveStartNode(instance.getInsid());
		this.checkWFInstanceNodeStatus(startInsNode);
		
		//获取节点审批部门
		List<WfInstanceNodeApprovepart> noApprovePartList = this.wfInsNodeDeptMapper.selectInsNodeNoApprovePartList(startInsNode.getInsnodeid());
		if(null==noApprovePartList||noApprovePartList.isEmpty())
			throw new PMRuntimeException("启动节点审批部门数据有误");
		
		WfInstanceNodeApprovepart startInsNodePart = noApprovePartList.get(0);
		this.checkWFInstancePartStatus(startInsNodePart);
		
		//获取节点审批人
		WfInstanceNodeApproveUser approveUser = getInstanceApproveUserByInsPartId(startInsNodePart.getInspartid(),userid);
		this.checkWFInstanceUserStatus(approveUser);
		
		//个人审批拒绝
		approveUser.setStatus(WfConstants.WfInsNodeUserStatus.Rejected);
		approveUser.setUpdateby(userid);
		approveUser.setUpdatedate(new Date());
		if (this.wfInsNodeUserMapper.updateByPrimaryKey(approveUser)!=1)
			throw new PMRuntimeException("流程审批人状态有误");
		
		//审批部门审批拒绝
		startInsNodePart.setStatus(WfConstants.WfInsNodePartStatus.Rejected);
		startInsNodePart.setUpdateby(userid);
		startInsNodePart.setUpdatedate(new Date());
		if (this.wfInsNodeDeptMapper.updateByPrimaryKey(startInsNodePart)!=1)
			throw new PMRuntimeException("流程审批部门状态有误");
		
		//审批节点审批拒绝
		startInsNode.setStatus(WfConstants.WfInsNodeStatus.Rejected);
		startInsNode.setUpdateby(userid);
		startInsNode.setUpdatedate(new Date());
		if (this.wfInsNodeMapper.updateByPrimaryKey(startInsNode)!=1)
			throw new PMRuntimeException("流程审批节点状态有误");
		
		//终止流程
		instance.setStatus(WfConstants.WfInsStatus.Stoped);
		instance.setUpdateby(userid);
		instance.setUpdatedate(new Date());
		if (this.wfInsMapper.updateByPrimaryKey(instance)!=1)
			throw new PMRuntimeException("流程状态有误");
	}

	public static final String PARAMS_NOL_STR = "参数不能为空!";
	@Override
	public RESPInstancePager instanceList(RPInstance instance, int userid) {

		if (instance == null){
			throw new PMRuntimeException(PARAMS_NOL_STR);
		}

		List<RESPInstance> instances = new ArrayList<>();
		PageHelper.startPage(instance.getCurpageno(),instance.getPagesize());
		List<WfInstanceNodeApproveUser> wfInstanceNodeApproveUsers = wfInsNodeUserMapper.queryInsNodeUserMapperWithType(instance.getCompid(),instance.getType());
//		if (instance.getType() == 1){
////			wfInstanceNodeApproveUsers = wfInsNodeUserMapper.queryBy1("compid", instance.getCompid() + "", "status", 1 + "");
//			wfInstanceNodeApproveUsers  = wfInsNodeUserMapper.queryByTest("compid",100+"");
//		}else{
//			wfInstanceNodeApproveUsers = wfInsNodeUserMapper.queryBy2("compid", instance.getCompid() + "", "status!=", 1 + "","status!=", 99 + "");
//		}
		PageInfo<WfInstanceNodeApproveUser> pageInfo = new PageInfo<>(wfInstanceNodeApproveUsers);


		for(WfInstanceNodeApproveUser wfInstanceNodeApproveUser:pageInfo.getList() ){

			WfInstanceNodeApprovepart wfInstanceNodeApprovepart = wfInsNodeDeptMapper.selectByPrimaryKey(wfInstanceNodeApproveUser.getInspartid());
			//获取实例
			WfInstance wfInstance = wfInsMapper.selectByPrimaryKey(wfInstanceNodeApproveUser.getInsid());
			RESPInstance respInstance =  FiledClone.cloneFiled(wfInstance,RESPInstance.class);

			//获取部门
			MdDepartment department = mdDeptService.getDeptById(wfInstanceNodeApprovepart.getDeptid());
			respInstance.setDeptnames(department.getDeptname());

			WfInstanceNode wfInstanceNode = wfInsNodeMapper.selectByPrimaryKey(wfInstanceNodeApproveUser.getInsnodeid());

			//获取节点
			respInstance.setInsnodeid(wfInstanceNodeApproveUser.getInsnodeid());
			respInstance.setInsnodestatus(wfInstanceNode.getStatus());
			MdDepartment startDept = mdDeptService.getDeptById(wfInstance.getStartdeptid());
			respInstance.setStartdeptname(startDept.getDeptname());
			respInstance.setCreatedate(wfInstance.getCreatedate());
			respInstance.setInsno(wfInstance.getInsno());
			instances.add(respInstance);
		}

		RESPInstancePager pager = new RESPInstancePager();
		RESPInstancePager.PageObj pageObj = new RESPInstancePager.PageObj();
		pageObj.setCurpageno(instance.getCurpageno());
		pageObj.setPagenum(pageInfo.getPageNum());
		pageObj.setPagesize(pageInfo.getPageSize());
		pageObj.setTotalsize(pageInfo.getTotal());
		pager.setPage(pageObj);
		pager.setData(instances);
		return pager;
	}

	@Override
	public RESPInstanceInfo instanceInfo(int id, int userid) {

		WfInstance wfInstance = wfInsMapper.selectByPrimaryKey(id);
		if (wfInstance == null){
			throw  new RuntimeException("实例不存在!");
		}
		RESPInstanceInfo respInstanceInfo = FiledClone.cloneFiled(wfInstance, RESPInstanceInfo.class);;

		//设置流程模板
		WfTemplate wfTemplate = wfTemplateMapper.selectByPrimaryKey(wfInstance.getTemplateid());
		respInstanceInfo.setTemplateinfo(templateService.getTemplateInfo(wfTemplate.getTemplateid(), userid));

		List<WfInstanceNode> wfInstanceNodes = wfInsNodeMapper.queryBy("insid", id + "");
		List<RESPInstanceInfo.InstanceNode> instanceNodes = new ArrayList<>();
		for(WfInstanceNode wfInstanceNode:wfInstanceNodes){
			RESPInstanceInfo.InstanceNode instanceNodeClone = FiledClone.cloneFiled(wfInstanceNode, RESPInstanceInfo.InstanceNode.class);
			List<WfInstanceNodeApprovepart>  instanceNodeParts = wfInsNodeDeptMapper.queryBy1("insid",id+"","insnodeid",wfInstanceNode.getInsnodeid()+"");
			List<RESPInstanceInfo.InstanceNodePartList> instanceNodePartList = new ArrayList<>();
			for(WfInstanceNodeApprovepart instanceNodeApprovepart:instanceNodeParts){
				RESPInstanceInfo.InstanceNodePartList partClone = FiledClone.cloneFiled(instanceNodeApprovepart, RESPInstanceInfo.InstanceNodePartList.class);

				List<RESPInstanceInfo.ApproveUserList> approveUserLists = new ArrayList<>();
				List<WfInstanceNodeApproveUser> instanceNodeApproveUsers = wfInsNodeUserMapper.queryBy2("insid", id + "", "insnodeid", wfInstanceNode.getInsnodeid() + "", "inspartid", instanceNodeApprovepart.getInspartid() + "");
				for(WfInstanceNodeApproveUser user:instanceNodeApproveUsers){
					RESPInstanceInfo.ApproveUserList approveUserList = FiledClone.cloneFiled(user, RESPInstanceInfo.ApproveUserList.class);


					List<MdAttchment> queryAttchment = this.attachmentService.query(user.getId(), MdAttachmentBiztypeContants.WfNodeUser);
					List<RESPInstanceInfo.InstanceAttachid> instanceAttachids = new ArrayList<>();
					for(MdAttchment mdAttchment:queryAttchment){
						RESPInstanceInfo.InstanceAttachid instanceAttachid = FiledClone.cloneFiled(mdAttchment, RESPInstanceInfo.InstanceAttachid.class);
						instanceAttachid.setUrl(null);
						instanceAttachids.add(instanceAttachid);
					}
					approveUserList.setAttachs(instanceAttachids);
					approveUserLists.add(approveUserList);

				}
				partClone.setApproveuserlist(approveUserLists);
				instanceNodePartList.add(partClone);

			}
			instanceNodeClone.setInsnodepartlist(instanceNodePartList);
			instanceNodes.add(instanceNodeClone);

		}
		respInstanceInfo.setInsnodelist(instanceNodes);



		return respInstanceInfo;
	}
	
	public void checkInsAttachmentRight(int insUserTableId,int userid) {
		
		WfInstanceNodeApproveUser insUser = this.wfInsNodeUserMapper.selectByPrimaryKey(insUserTableId);
		if (null==insUser)
			throw new PMRuntimeException("流程信息有误");
		
		WfInstance ins = this.wfInsMapper.selectByPrimaryKey(insUser.getInsid());
		
		if (companyService.isManagerForCompany(userid, ins.getCompid()))
			return;
		
		boolean isUser = false;
		
		boolean isCurrentUser = false;
		
		List<WfInstanceNodeApproveUser> insUserList =  this.wfInsNodeUserMapper.selectInstanceNodeApproveUserList(ins.getInsid());
		for(WfInstanceNodeApproveUser approveUser:insUserList)
		{
			if (approveUser.getStatus()!=WfConstants.WfInsNodeUserStatus.Deleted)
			{
				isUser = true;
			}
			if (approveUser.getStatus()==WfConstants.WfInsNodeUserStatus.NoApproved)
			{
				isCurrentUser = true;
				break;
			}
		}
		
		if (ins.getInsgrade()==0||ins.getInsgrade()==WfConstants.WfInsGrade.Normal)
		{
			if(!isUser)
				throw new PMRuntimeException("权限不足");
			return;
		}
		
		if (ins.getInsgrade()==WfConstants.WfInsGrade.Middle||ins.getInsgrade()==WfConstants.WfInsGrade.Important)
		{
			if(!isCurrentUser)
				throw new PMRuntimeException("权限不足");			
			return;
		}
	}

	@Override
	public List<IndexInfo> getFlowListWithUserid(int userid) {

		List<IndexInfo> indexInfos = new ArrayList<>();
		List<WfInstanceNodeApproveUser> wfInstanceNodeApproveUsers = wfInsNodeUserMapper.queryBy1("userid", userid + "", "status", 1 + "");
		for (WfInstanceNodeApproveUser wfInstanceNodeApproveUser:wfInstanceNodeApproveUsers){
			IndexInfo indexInfo = new IndexInfo();
			indexInfo.setCompid(wfInstanceNodeApproveUser.getCompid());
			WfInstance wfInstance = wfInsMapper.selectByPrimaryKey(wfInstanceNodeApproveUser.getInsid());
			indexInfo.setTitle(wfInstance.getInsname());
			indexInfo.setRemark(wfInstance.getRemark());
			MdCompany mdCompany = companyService.getbyid(wfInstance.getCompid());
			indexInfo.setComplogo(mdCompany.getLogo());
			MdDepartment dept = mdDeptService.getDeptById(wfInstanceNodeApproveUser.getDeptid());
			indexInfo.setDeptname(dept.getDeptname());
			indexInfos.add(indexInfo);
		}
		return indexInfos;
	}
}
