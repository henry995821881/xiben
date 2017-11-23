package com.xiben.pm.ar.request;

import java.util.List;

import com.xiben.pm.md.pojo.MdAttchment;

public class CreateArchiveParam {
	private int compid;
	private int deptid;
	private int source;
	private int insid;
	private int secretgrade;
	private List<MdAttchment> mdAttachments;
	private List<Integer> approveUserList;
	
	public List<Integer> getApproveUserList() {
		return approveUserList;
	}
	public void setApproveUserList(List<Integer> approveUserList) {
		this.approveUserList = approveUserList;
	}
	public int getCompid() {
		return compid;
	}
	public void setCompid(int compid) {
		this.compid = compid;
	}
	public int getDeptid() {
		return deptid;
	}
	public void setDeptid(int deptid) {
		this.deptid = deptid;
	}
	public int getSource() {
		return source;
	}
	public void setSource(int source) {
		this.source = source;
	}
	public int getInsid() {
		return insid;
	}
	public void setInsid(int insid) {
		this.insid = insid;
	}
	public int getSecretgrade() {
		return secretgrade;
	}
	public void setSecretgrade(int secretgrade) {
		this.secretgrade = secretgrade;
	}
	public List<MdAttchment> getMdAttachments() {
		return mdAttachments;
	}
	public void setMdAttachments(List<MdAttchment> mdAttachments) {
		this.mdAttachments = mdAttachments;
	}
	
	
}
