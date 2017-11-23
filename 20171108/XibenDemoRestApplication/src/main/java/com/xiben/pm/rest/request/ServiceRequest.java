package com.xiben.pm.rest.request;


public class ServiceRequest<T> {
	private String privatefield;
	private String reqtime;
	private String version;
	
	private T reqdata;

	public String getPrivatefield() {
		return privatefield;
	}

	public void setPrivatefield(String privatefield) {
		this.privatefield = privatefield;
	}

	public String getReqtime() {
		return reqtime;
	}

	public void setReqtime(String reqtime) {
		this.reqtime = reqtime;
	}

	public String getVersion() {
		return version;
	}

	public void setVersion(String version) {
		this.version = version;
	}

	public T getReqdata() {
		return reqdata;
	}

	public void setReqdata(T reqdata) {
		this.reqdata = reqdata;
	}
}
