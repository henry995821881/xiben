package com.xiben.pm.wf.service;

import com.xiben.pm.wf.pojo.WfTemplate;
import com.xiben.pm.wf.pojo.WfTemplateNode;
import com.xiben.pm.wf.pojo.WfTemplateNodeApprovepart;
import com.xiben.pm.wf.request.AddtemplateRequestBody;
import com.xiben.pm.wf.request.RPAddApproveUser;
import com.xiben.pm.wf.request.RPTemplateBaseParams;
import com.xiben.pm.wf.response.RESPTempInfo;
import com.xiben.pm.wf.response.RESPTemplateDetailList;
import com.xiben.pm.wf.response.RESPTemplateList;

import java.util.List;

public interface WFTemplateServie {



    boolean createTemplate(AddtemplateRequestBody wfTemplate, int userid);

    int saveTemplate(WfTemplate wfTemplate,int userid);

    int deleteAllNodeWithTemplateId(int templateId,int userid);

    int deleteTemplate(int templateId,int userid);

    List<RESPTemplateList> getTemplateList(RPTemplateBaseParams params, int userid);



    RESPTempInfo getTemplateInfo(Integer templateid, int userid);

    int addApproveUser(RPAddApproveUser params,int userid);

    int removeApproveUser(RPAddApproveUser params,int userid);

    int updateApprovetype(RPAddApproveUser params,int userid);


    int createFlow(WfTemplate wfTemplate, WfTemplateNode templateNode, WfTemplateNodeApprovepart wfTemplateNodeApprovepart, int userid);

    /**
     * 更新部门授权人
     * @return
     */
    int updateApproveUser(int updateUserId,int deptid,int sourceUser);

    /**
     * return value >0 is true
     * @param deptid
     * @param userid
     * @return
     */
    int hasTemplateNodeWithDepId(int deptid,int userid);
    
    public List<RESPTemplateDetailList> getTemplateInfo2ListByDeptidAndUserid(int deptid,int userid);
}
