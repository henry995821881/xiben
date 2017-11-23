package com.xiben.pm.wf.response;

import java.util.List;

public class RESPTempInfo extends BaseRespTemp{

        private List<RESPTemplateNode> nodelist;

    public List<RESPTemplateNode> getNodelist() {
        return nodelist;
    }

    public void setNodelist(List<RESPTemplateNode> nodelist) {
        this.nodelist = nodelist;
    }
}
