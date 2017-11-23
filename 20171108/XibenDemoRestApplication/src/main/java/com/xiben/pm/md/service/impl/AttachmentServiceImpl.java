package com.xiben.pm.md.service.impl;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.xiben.common.qiniu.Config;
import com.xiben.common.qiniu.DownLoadTools;
import com.xiben.pm.exception.PMRuntimeException;
import com.xiben.pm.md.mapper.MdAttchmentMapper;
import com.xiben.pm.md.pojo.MdAttchment;
import com.xiben.pm.md.request.UploadAttachParam;
import com.xiben.pm.md.request.getDownloadUrlParam;
import com.xiben.pm.md.response.GetDownloadUrlResult;
import com.xiben.pm.md.service.AttachmentService;
import com.xiben.pm.md.util.MdAttachmentBiztypeContants;
import com.xiben.pm.wf.service.WFInstanceService;

@Service("attachmentS")
public class AttachmentServiceImpl implements AttachmentService {
	
	@Autowired
	MdAttchmentMapper attachmentMapper;
	
	@Autowired
	WFInstanceService insService;
	
	public void saveAttachment(MdAttchment attachment) {
		attachmentMapper.insertSelective(attachment);
	}

	public void saveAttachmentList(List<UploadAttachParam> list,int bizid,int biztype,int secretgrade,int userid) {
		if(list !=null){
			
			
			for(UploadAttachParam attach:list){
				
				MdAttchment  mdAttachment = new MdAttchment();
				
				mdAttachment.setBizid(bizid);
				mdAttachment.setBiztype(biztype);
				mdAttachment.setCreateby(userid);
				mdAttachment.setCreatedate(new Date());
				mdAttachment.setDescription("");
				mdAttachment.setFilekey(attach.getFk());
				mdAttachment.setFilename(attach.getFn());
				mdAttachment.setFilesize(attach.getFs());
				mdAttachment.setFiletype(attach.getFt());
				mdAttachment.setSecretgrade(secretgrade);
				mdAttachment.setStatus(1);
				if(biztype==1 || biztype ==2){
					
					mdAttachment.setTablespace(Config.getPublicTablename());
				}else{
					mdAttachment.setTablespace(Config.getPrivateTablename());
				}
				mdAttachment.setUpdateby(userid);
				mdAttachment.setUpdatedate(new Date());
				
				attachmentMapper.insertSelective(mdAttachment);
			}
			
		}
	}

	@Override
	public void saveAttachmentList(List<UploadAttachParam> list, int bizid, int biztype,  int userid) {
		
		this.saveAttachmentList(list, bizid, biztype, 0, userid);
		
	}


	@Override
	public List<MdAttchment> query(Integer bizid, Integer biztype) {
		// TODO Auto-generated method stub
		return attachmentMapper.query(bizid, biztype);
	}


	@Override
	public void update(MdAttchment attach) {
		
		if (attachmentMapper.updateByPrimaryKeySelective(attach)!=1)
			throw new PMRuntimeException("附件更新失败");
		
	}
	
	@Override
	public GetDownloadUrlResult getDownloadUrl(getDownloadUrlParam param,int userid) {
		GetDownloadUrlResult result = new GetDownloadUrlResult();
		
		MdAttchment attachment = attachmentMapper.selectByPrimaryKey(param.getAttachid());
		if (null==attachment)
			return result;
		
		if (attachment.getBiztype()==MdAttachmentBiztypeContants.UserLogo||attachment.getBiztype()==MdAttachmentBiztypeContants.CompanyLogo) {
			result.setDownloadurl(DownLoadTools.getPublicFileDownloadUrl(attachment.getFilekey()));
			return result;
		}
			
		
		if (attachment.getBiztype()==MdAttachmentBiztypeContants.DutyFile||attachment.getBiztype()==MdAttachmentBiztypeContants.TaskNode)
		{
			result.setDownloadurl(DownLoadTools.getPrivateFileDownloadUrl(attachment.getFilekey()));
			return result;
		}
		
		if (attachment.getBiztype()==MdAttachmentBiztypeContants.WfNodeUser)
		{
			if (attachment.getArchiveid()>0)
			{
				throw new PMRuntimeException("该附件已生成档案，请从档案中搜索!");
			}
			
			//流程普通文件
			if (attachment.getSecretgrade()<=1)
			{
				
				insService.checkInsAttachmentRight(attachment.getBizid(), userid);
				result.setDownloadurl(DownLoadTools.getPrivateFileDownloadUrl(attachment.getFilekey()));
			}
			
			//当前节点审批人
			if (attachment.getSecretgrade()==2)
			{
				insService.checkInsAttachmentRight(attachment.getBizid(), userid);
				result.setDownloadurl(DownLoadTools.getPrivateFileDownloadUrl(attachment.getFilekey()));
			}
			
			//当前节点审批人
			if (attachment.getSecretgrade()==3)
			{
				insService.checkInsAttachmentRight(attachment.getBizid(), userid);
				result.setDownloadurl(DownLoadTools.getPrivateFileDownloadUrl(attachment.getFilekey()));
			}
		}
		
		return result;
	}

	@Override
	public List<MdAttchment> getAttchmentWithAid(Integer arid, int userid) {

		return attachmentMapper.queryWithAid(arid);
	}

	@Override
	public void deleteByDuty(Integer dutyid) {
		
		attachmentMapper.deleteByDuty(dutyid);
	}

}
