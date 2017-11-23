package com.xiben.pm.wf.service;

import com.xiben.pm.wf.pojo.WfTemplateNode;

public interface WFTemNodeService {

    int saveTempNode(WfTemplateNode node,int userid);
    int removeAllWithTemplateId(int tid);

}
