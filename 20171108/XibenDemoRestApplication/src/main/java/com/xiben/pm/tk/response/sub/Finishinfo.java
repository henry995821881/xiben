package com.xiben.pm.tk.response.sub;

import java.util.List;

import org.apache.commons.lang3.builder.ReflectionToStringBuilder;

import com.xiben.pm.md.request.UploadAttachParam;

public class Finishinfo {

   private String 	remark;
   private String   finishdt;
   private List<UploadAttachParam>  attachs;
   
   public String getRemark() {
	return remark;
}

public void setRemark(String remark) {
	this.remark = remark;
}

public String getFinishdt() {
	return finishdt;
}

public void setFinishdt(String finishdt) {
	this.finishdt = finishdt;
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
