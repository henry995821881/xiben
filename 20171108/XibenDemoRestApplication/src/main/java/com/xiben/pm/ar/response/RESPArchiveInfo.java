package com.xiben.pm.ar.response;

import com.github.pagehelper.PageHelper;

import java.util.Date;

public class RESPArchiveInfo {

    private Integer  archiveid;
    private Integer  compid;
    private String arcname;
    private String  arctype;
    private String  arcno;
    private Date arcdate;
    private Integer  deptid;
    private Integer  secretgrade;

    public Integer getArchiveid() {

        return archiveid;
    }

    public void setArchiveid(Integer archiveid) {
        this.archiveid = archiveid;
    }

    public Integer getCompid() {
        return compid;
    }

    public void setCompid(Integer compid) {
        this.compid = compid;
    }

    public String getArcname() {
        return arcname;
    }

    public void setArcname(String arcname) {
        this.arcname = arcname;
    }

    public String getArctype() {
        return arctype;
    }

    public void setArctype(String arctype) {
        this.arctype = arctype;
    }

    public String getArcno() {
        return arcno;
    }

    public void setArcno(String arcno) {
        this.arcno = arcno;
    }

    public Date getArcdate() {
        return arcdate;
    }

    public void setArcdate(Date arcdate) {
        this.arcdate = arcdate;
    }

    public Integer getDeptid() {
        return deptid;
    }

    public void setDeptid(Integer deptid) {
        this.deptid = deptid;
    }

    public Integer getSecretgrade() {
        return secretgrade;
    }

    public void setSecretgrade(Integer secretgrade) {
        this.secretgrade = secretgrade;
    }
}
