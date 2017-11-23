package com.xiben.pm.wf.service.impl;

import com.xiben.pm.wf.mapper.WfTemplateNodeApprovepartMapper;
import com.xiben.pm.wf.pojo.WfTemplateNodeApprovepart;
import com.xiben.pm.wf.service.WFTemNodeApprovepartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
public class WFTemNodeApprovepartServiceImpl implements WFTemNodeApprovepartService {


    @Autowired
    WfTemplateNodeApprovepartMapper dao;


    @Override
    public int saveData(WfTemplateNodeApprovepart data,int userid) {
        data.setCreateby(userid);
        data.setUpdateby(userid);
        data.setCreatedate(new Date());
        data.setUpdatedate(new Date());
        dao.insert(data);
        return data.getTemplatepartid();
    }

    @Override
    public int removeAllWithTemplateId(int tid) {
         dao.deleteAllWithTempId(tid);
         return 0;
    }
}
