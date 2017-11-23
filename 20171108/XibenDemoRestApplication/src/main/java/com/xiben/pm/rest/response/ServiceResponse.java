package com.xiben.pm.rest.response;

import com.xiben.common.utils.StringUtils;

public class ServiceResponse <T>{
	private String privatefield;
	private String restime;
	private int code;
	private String msg;
	private T resdata;
	
	public ServiceResponse()
	{
		code = 1000;
		msg = "SUCCESS";
		restime = StringUtils.formatDate();
	}
	
	public String getPrivatefield() {
		return privatefield;
	}
	public void setPrivatefield(String privatefield) {
		this.privatefield = privatefield;
	}
	public String getRestime() {
		return restime;
	}
	public void setRestime(String restime) {
		this.restime = restime;
	}
	public int getCode() {
		return code;
	}
	public void setCode(int code) {
		this.code = code;
	}
	public String getMsg() {
		return msg;
	}
	public void setMsg(String msg) {
		this.msg = msg;
	}
	public T getResdata() {
		return resdata;
	}
	public void setResdata(T resdata) {
		this.resdata = resdata;
	}
	
	
	
}
