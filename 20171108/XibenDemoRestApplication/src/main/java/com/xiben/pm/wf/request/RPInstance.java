package com.xiben.pm.wf.request;

public class RPInstance {

    private Integer compid;
    private Integer type;
    private Integer insid;
    private Integer curpageno;
    private Integer pagesize;

    public Integer getCompid() {
        return compid;
    }

    public void setCompid(Integer compid) {
        this.compid = compid;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public Integer getInsid() {
        return insid;
    }

    public void setInsid(Integer insid) {
        this.insid = insid;
    }

    public Integer getCurpageno() {
        return curpageno;
    }

    public void setCurpageno(Integer curpageno) {
        this.curpageno = curpageno;
    }

    public Integer getPagesize() {
        return pagesize;
    }

    public void setPagesize(Integer pagesize) {
        this.pagesize = pagesize;
    }
}
