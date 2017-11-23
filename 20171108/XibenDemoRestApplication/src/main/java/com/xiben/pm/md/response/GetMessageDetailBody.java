package com.xiben.pm.md.response;

import org.apache.commons.lang3.builder.ReflectionToStringBuilder;

public class GetMessageDetailBody {


	private Integer noticeid;
	private String msgcontent;
	private Integer biztype;
	private Integer bizid;
	private Integer senduserid;
	private String sendusername;
	private String senduserlogo;
	private Integer readflag;
	public Integer getNoticeid() {
		return noticeid;
	}
	public void setNoticeid(Integer noticeid) {
		this.noticeid = noticeid;
	}
	public String getMsgcontent() {
		return msgcontent;
	}
	public void setMsgcontent(String msgcontent) {
		this.msgcontent = msgcontent;
	}
	public Integer getBiztype() {
		return biztype;
	}
	public void setBiztype(Integer biztype) {
		this.biztype = biztype;
	}
	public Integer getBizid() {
		return bizid;
	}
	public void setBizid(Integer bizid) {
		this.bizid = bizid;
	}
	public Integer getSenduserid() {
		return senduserid;
	}
	public void setSenduserid(Integer senduserid) {
		this.senduserid = senduserid;
	}
	public String getSendusername() {
		return sendusername;
	}
	public void setSendusername(String sendusername) {
		this.sendusername = sendusername;
	}
	public String getSenduserlogo() {
		return senduserlogo;
	}
	public void setSenduserlogo(String senduserlogo) {
		this.senduserlogo = senduserlogo;
	}
	public Integer getReadflag() {
		return readflag;
	}
	public void setReadflag(Integer readflag) {
		this.readflag = readflag;
	}
	@Override
	public String toString() {
		 return ReflectionToStringBuilder.toString(this); 
	} 	
}
