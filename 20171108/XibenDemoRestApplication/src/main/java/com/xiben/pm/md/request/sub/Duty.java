package com.xiben.pm.md.request.sub;

import java.util.List;

import org.apache.commons.lang3.builder.ReflectionToStringBuilder;

import com.xiben.pm.md.request.UploadAttachParam;
import com.xiben.pm.rest.model.UploadAttach;

public class Duty {
/**
 * "dutylist": [
            {
                "dutyname": "",
                "remark": "",
                "ratio": 45,
                "dutytype": 1,
                "attachs": [
                    {}
                ]
            }
        ]
 */
	
	private String dutyname;
	private String remark;
	private Integer ratio;
	private Integer dutytype ;
	private List<UploadAttachParam> attachs;
	public String getDutyname() {
		return dutyname;
	}
	public void setDutyname(String dutyname) {
		this.dutyname = dutyname;
	}
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	public Integer getRatio() {
		return ratio;
	}
	public void setRatio(Integer ratio) {
		this.ratio = ratio;
	}
	public Integer getDutytype() {
		return dutytype;
	}
	public void setDutytype(Integer dutytype) {
		this.dutytype = dutytype;
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
