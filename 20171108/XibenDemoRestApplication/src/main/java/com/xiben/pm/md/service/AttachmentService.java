package com.xiben.pm.md.service;

import java.util.List;

import com.xiben.pm.md.pojo.MdAttchment;
import com.xiben.pm.md.pojo.MdEmployeeEntity;
import com.xiben.pm.md.request.UploadAttachParam;
import com.xiben.pm.md.request.getDownloadUrlParam;
import com.xiben.pm.md.response.GetDownloadUrlResult;

public interface AttachmentService {
	public void saveAttachment(MdAttchment attachment);
	
	public void saveAttachmentList(List<UploadAttachParam> list,int bizid,int biztype,int userid);
	public void saveAttachmentList(List<UploadAttachParam> list,int bizid,int biztype,int secretgrade,int userid);
	
	public List<MdAttchment> query(Integer bizid,Integer biztype);
	public void deleteByDuty(Integer dutyid);
	
	public void update(MdAttchment attach);
	
	public GetDownloadUrlResult getDownloadUrl(getDownloadUrlParam param,int userid);

	/**
	 * 通过档案ID 获取附件
	 * @param arid
	 * @param userid
	 * @return
	 */
	List<MdAttchment> getAttchmentWithAid(Integer arid, int userid);
}
	