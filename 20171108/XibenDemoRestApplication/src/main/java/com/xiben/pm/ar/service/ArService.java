package com.xiben.pm.ar.service;

import com.xiben.pm.ar.response.RESPArchiveInfo;
import com.xiben.pm.ar.request.CreateArchiveParam;
import com.xiben.pm.ar.request.REPBaseArchive;

import java.util.List;
import java.util.Map;

public interface ArService {

	void createArchive(CreateArchiveParam param,int userid);
	
	String generateWorkFlowSequenceNumber(int compid,int userid);

	/**
	 * 档案列表
	 */

	List<RESPArchiveInfo>  archiveList(REPBaseArchive repBaseArchive , int userid);


	/**
	 * 档案查询申请
	 */

	int applyarchiveread(REPBaseArchive params,int userid);
	/**
	 * 档案查阅
	 *
	 */

	Map<String,String> archiveread(Integer aid, int userid);
	/**
	 * 档案查阅审批
	 *
	 */
	int approvearchiveread(REPBaseArchive params,int userid);
	
	
	public String generateTaskSequenceNumber(int compid,int userid);

}
