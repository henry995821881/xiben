package com.xiben.pm.wf.response;

import java.util.Date;
import java.util.List;

import com.xiben.pm.wf.pojo.WfTemplate;

public class RESPInstanceInfo {
    private Integer insid;
    private String insname;
    private Date createdate;
    private Integer status;
    private RESPTempInfo templateinfo;
    private String insno;

    private List<InstanceNode> insnodelist;

    public Integer getInsid() {
        return insid;
    }

    public void setInsid(Integer insid) {
        this.insid = insid;
    }

    public String getInsname() {
        return insname;
    }

    public void setInsname(String insname) {
        this.insname = insname;
    }

    public Date getCreatedate() {
        return createdate;
    }

    public void setCreatedate(Date createdate) {
        this.createdate = createdate;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }



    public List<InstanceNode> getInsnodelist() {
        return insnodelist;
    }

    public void setInsnodelist(List<InstanceNode> insnodelist) {
        this.insnodelist = insnodelist;
    }

    public String getInsno() {
        return insno;
    }

    public void setInsno(String insno) {
        this.insno = insno;
    }

    public RESPTempInfo getTemplateinfo() {
        return templateinfo;
    }

    public void setTemplateinfo(RESPTempInfo templateinfo) {
        this.templateinfo = templateinfo;
    }

    public static class InstanceNode{
        private Integer insnodeid;
        private  Integer templatenodeid;
        private String nodename;
        private Integer approvetype;
        private Integer notetype;
        private Date createdate;
        private List<InstanceNodePartList> insnodepartlist;

        public Integer getInsnodeid() {
            return insnodeid;
        }

        public void setInsnodeid(Integer insnodeid) {
            this.insnodeid = insnodeid;
        }

        public Integer getTemplatenodeid() {
            return templatenodeid;
        }

        public void setTemplatenodeid(Integer templatenodeid) {
            this.templatenodeid = templatenodeid;
        }

        public String getNodename() {
            return nodename;
        }

        public void setNodename(String nodename) {
            this.nodename = nodename;
        }

        public Integer getApprovetype() {
            return approvetype;
        }

        public void setApprovetype(Integer approvetype) {
            this.approvetype = approvetype;
        }

        public Integer getNotetype() {
            return notetype;
        }

        public void setNotetype(Integer notetype) {
            this.notetype = notetype;
        }

        public Date getCreatedate() {
            return createdate;
        }

        public void setCreatedate(Date createdate) {
            this.createdate = createdate;
        }

        public List<InstanceNodePartList> getInsnodepartlist() {
            return insnodepartlist;
        }

        public void setInsnodepartlist(List<InstanceNodePartList> insnodepartlist) {
            this.insnodepartlist = insnodepartlist;
        }
    }

    public static class InstanceNodePartList{
        private Integer inspartid;
        private Integer templatepartid;
        private Integer deptid;
        private Integer approvetype;
        private Integer status;
        private Date createdate;
        private List<ApproveUserList> approveuserlist;

        public Integer getInspartid() {
            return inspartid;
        }

        public void setInspartid(Integer inspartid) {
            this.inspartid = inspartid;
        }

        public Integer getTemplatepartid() {
            return templatepartid;
        }

        public void setTemplatepartid(Integer templatepartid) {
            this.templatepartid = templatepartid;
        }

        public Integer getDeptid() {
            return deptid;
        }

        public void setDeptid(Integer deptid) {
            this.deptid = deptid;
        }

        public Integer getApprovetype() {
            return approvetype;
        }

        public void setApprovetype(Integer approvetype) {
            this.approvetype = approvetype;
        }

        public Integer getStatus() {
            return status;
        }

        public void setStatus(Integer status) {
            this.status = status;
        }

        public Date getCreatedate() {
            return createdate;
        }

        public void setCreatedate(Date createdate) {
            this.createdate = createdate;
        }

        public List<ApproveUserList> getApproveuserlist() {
            return approveuserlist;
        }

        public void setApproveuserlist(List<ApproveUserList> approveuserlist) {
            this.approveuserlist = approveuserlist;
        }
    }
    public static class ApproveUserList{
        private Integer userid;
        private String remark;
        private Integer status;
        private Date createdate;
        private List<InstanceAttachid> attachs;

        public Integer getUserid() {
            return userid;
        }

        public void setUserid(Integer userid) {
            this.userid = userid;
        }

        public String getRemark() {
            return remark;
        }

        public void setRemark(String remark) {
            this.remark = remark;
        }

        public Integer getStatus() {
            return status;
        }

        public void setStatus(Integer status) {
            this.status = status;
        }

        public Date getCreatedate() {
            return createdate;
        }

        public void setCreatedate(Date createdate) {
            this.createdate = createdate;
        }

        public List<InstanceAttachid> getAttachs() {
            return attachs;
        }

        public void setAttachs(List<InstanceAttachid> attachs) {
            this.attachs = attachs;
        }
    }
    public static class InstanceAttachid{
        private Integer attachid;
        private String filename;
        private Integer filetype;
        private String url;

        public Integer getAttachid() {
            return attachid;
        }

        public void setAttachid(Integer attachid) {
            this.attachid = attachid;
        }

        public String getFilename() {
            return filename;
        }

        public void setFilename(String filename) {
            this.filename = filename;
        }

        public Integer getFiletype() {
            return filetype;
        }

        public void setFiletype(Integer filetype) {
            this.filetype = filetype;
        }

        public String getUrl() {
            return url;
        }

        public void setUrl(String url) {
            this.url = url;
        }
    }
}
