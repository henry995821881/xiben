package com.xiben.pm.tk.request;

import java.util.List;

import org.apache.commons.lang3.builder.ReflectionToStringBuilder;

import com.xiben.pm.md.request.UploadAttachParam;

public class CreateTaskParam {
/**
 * 
 *  "createtype": 1,
        "dutyid": 1,
        "enddt": "",
        "remark": "",
        "score": 1,
        "attachs": []

 */
	
	private Integer createtype;
	private Integer dutyid;
	private String enddt;
	private String remark;
	private Integer score;
	private List<UploadAttachParam> attachs;
	public Integer getCreatetype() {
		return createtype;
	}
	public void setCreatetype(Integer createtype) {
		this.createtype = createtype;
	}
	public Integer getDutyid() {
		return dutyid;
	}
	public void setDutyid(Integer dutyid) {
		this.dutyid = dutyid;
	}
	public String getEnddt() {
		return enddt;
	}
	public void setEnddt(String enddt) {
		this.enddt = enddt;
	}
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	public Integer getScore() {
		return score;
	}
	public void setScore(Integer score) {
		this.score = score;
	}
	public List<UploadAttachParam> getAttachs() {
		return attachs;
	}
	public void setAttachs(List<UploadAttachParam> attachs) {
		this.attachs = attachs;
	}
	
	@Override
	public String toString() {
		 return ReflectionToStringBuilder.toString(this); 
	}	
	
}
