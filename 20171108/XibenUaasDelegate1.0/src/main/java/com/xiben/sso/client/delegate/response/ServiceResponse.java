package com.xiben.sso.client.delegate.response;

public class ServiceResponse<T> {
	private String privatefield;
	private String restime;
	private String msg;
	private int code;
	private T resdata;
	
	

	
	public String getMsg() {
		return msg;
	}
	public void setMsg(String msg) {
		this.msg = msg;
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

	public T getResdata() {
		return resdata;
	}

	public void setResdata(T resdata) {
		this.resdata = resdata;
	}

}
