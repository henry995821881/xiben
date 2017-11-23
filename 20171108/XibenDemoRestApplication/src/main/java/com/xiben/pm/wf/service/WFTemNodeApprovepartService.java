package com.xiben.pm.wf.service;

import com.xiben.pm.wf.pojo.WfTemplateNodeApprovepart;

public interface WFTemNodeApprovepartService {

    int saveData(WfTemplateNodeApprovepart data,int userid);
    int removeAllWithTemplateId(int tid);

}
