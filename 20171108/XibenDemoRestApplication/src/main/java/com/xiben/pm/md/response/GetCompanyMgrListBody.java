package com.xiben.pm.md.response;

import java.io.Serializable;
import java.util.List;

import org.apache.commons.lang3.builder.ReflectionToStringBuilder;

import com.xiben.pm.md.response.sub.Mgr;

public class GetCompanyMgrListBody implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
   private Integer userright;
   private List<Mgr> mgrList;
public Integer getUserright() {
	return userright;
}
public void setUserright(Integer userright) {
	this.userright = userright;
}
public List<Mgr> getMgrList() {
	return mgrList;
}
public void setMgrList(List<Mgr> mgrList) {
	this.mgrList = mgrList;
}

@Override
public String toString() {
	 return ReflectionToStringBuilder.toString(this); 
}  
}
