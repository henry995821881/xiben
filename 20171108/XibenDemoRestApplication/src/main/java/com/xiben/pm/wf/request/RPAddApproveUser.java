package com.xiben.pm.wf.request;

public class RPAddApproveUser {

    private Integer templatepartid;
    private String phone;
    private Integer userid;
    private Integer approvetype;

    public Integer getTemplatepartid() {
        return templatepartid;
    }

    public void setTemplatepartid(Integer templatepartid) {
        this.templatepartid = templatepartid;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public Integer getApprovetype() {
        return approvetype;
    }

    public void setApprovetype(Integer approvetype) {
        this.approvetype = approvetype;
    }

    public Integer getUserid() {
        return userid;
    }

    public void setUserid(Integer userid) {
        this.userid = userid;
    }
}
