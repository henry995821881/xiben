package com.xiben.pm.wf.service;

import com.xiben.pm.wf.request.ApproveWorkflowParam;
import com.xiben.pm.wf.request.RPInstance;
import com.xiben.pm.wf.request.StartWorkflowParam;
import com.xiben.pm.wf.request.StopWorkFlowParam;
import com.xiben.pm.wf.response.*;

import java.util.List;

public interface WFInstanceService {
	void startWorkFlow(StartWorkflowParam param,int userid);
	
	void gotoNext(ApproveWorkflowParam param,int userid);
	
	void stopWorkFlow(StopWorkFlowParam param,int userid);

	RESPInstancePager instanceList(RPInstance instance, int userid);

	RESPInstanceInfo instanceInfo(int id, int userid);
	
	void checkInsAttachmentRight(int insUserTableId,int userid);



	/**
	 * 查询所有未完成的流程
	 * @param userid
	 * @return
	 */
	List<IndexInfo> getFlowListWithUserid(int userid);
	
}
