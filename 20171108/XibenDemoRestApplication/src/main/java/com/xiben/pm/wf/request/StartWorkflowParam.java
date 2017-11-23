package com.xiben.pm.wf.request;

import java.util.List;

import com.xiben.pm.md.request.UploadAttachParam;

public class StartWorkflowParam {
	private int templateid;
	private int deptid;
	private int insgrade;
	private String remark;
	private List<UploadAttachParam> attachs;
	
	public int getInsgrade() {
		return insgrade;
	}
	public void setInsgrade(int insgrade) {
		this.insgrade = insgrade;
	}
	public int getTemplateid() {
		return templateid;
	}
	public void setTemplateid(int templateid) {
		this.templateid = templateid;
	}
	public int getDeptid() {
		return deptid;
	}
	public void setDeptid(int deptid) {
		this.deptid = deptid;
	}
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	public List<UploadAttachParam> getAttachs() {
		return attachs;
	}
	public void setAttachs(List<UploadAttachParam> attachs) {
		this.attachs = attachs;
	}
}
