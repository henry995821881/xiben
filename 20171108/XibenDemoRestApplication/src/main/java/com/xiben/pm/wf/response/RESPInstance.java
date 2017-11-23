package com.xiben.pm.wf.response;

import java.util.Date;

public class RESPInstance {



    private Integer insid;
    private String insname;
    private String remark;
    private String deptnames;
    private Integer insnodeid;
    private Integer insnodestatus;
    private Integer notetype;
    private String startdeptname;
    private Date createdate;
    private String insno;


    public Integer getInsid() {
        return insid;
    }

    public void setInsid(Integer insid) {
        this.insid = insid;
    }

    public Date getCreatedate() {
        return createdate;
    }

    public void setCreatedate(Date createdate) {
        this.createdate = createdate;
    }

    public String getStartdeptname() {
        return startdeptname;
    }

    public void setStartdeptname(String startdeptname) {
        this.startdeptname = startdeptname;
    }

    public Integer getNotetype() {
        return notetype;
    }

    public void setNotetype(Integer notetype) {
        this.notetype = notetype;
    }

    public Integer getInsnodestatus() {
        return insnodestatus;
    }

    public void setInsnodestatus(Integer insnodestatus) {
        this.insnodestatus = insnodestatus;
    }

    public Integer getInsnodeid() {
        return insnodeid;
    }

    public void setInsnodeid(Integer insnodeid) {
        this.insnodeid = insnodeid;
    }

    public String getDeptnames() {
        return deptnames;
    }

    public void setDeptnames(String deptnames) {
        this.deptnames = deptnames;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public String getInsname() {
        return insname;
    }

    public void setInsname(String insname) {
        this.insname = insname;
    }

    public String getInsno() {
        return insno;
    }

    public void setInsno(String insno) {
        this.insno = insno;
    }
}
