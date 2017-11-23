package com.xiben.pm.wf.service.impl;

import com.xiben.pm.exception.PMRuntimeException;
import com.xiben.pm.wf.mapper.WfTemplateNodeMapper;
import com.xiben.pm.wf.pojo.WfTemplateNode;
import com.xiben.pm.wf.service.WFTemNodeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
public class WFTemNodeServiceImpl implements WFTemNodeService{


    @Autowired
    WfTemplateNodeMapper dao;

    @Override
    public int saveTempNode(WfTemplateNode data,int userid) {
        if (data == null)throw new PMRuntimeException("WFTemNodeServiceImpl saveTempNode node not null");
        data.setCreateby(userid);
        data.setUpdateby(userid);
        data.setCreatedate(new Date());
        data.setUpdatedate(new Date());
        dao.insert(data);
        return data.getTemplatenodeid();
    }

    @Override
    public int removeAllWithTemplateId(int tid) {
         dao.deleteAllWithTempId(tid);
         return 0;
    }

}
