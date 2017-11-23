package com.xiben.pm.wf.response;

import java.util.List;

public class BaseRespTemp {

    private Integer templateid;
    private String templatename;
    private String templateremark;
    private int type;
    
    
    

    public String getTemplateremark() {
		return templateremark;
	}

	public void setTemplateremark(String templateremark) {
		this.templateremark = templateremark;
	}

	public int getType() {
		return type;
	}

	public void setType(int type) {
		this.type = type;
	}

	public Integer getTemplateid() {
        return templateid;
    }

    public void setTemplateid(Integer templateid) {
        this.templateid = templateid;
    }

    public String getTemplatename() {
        return templatename;
    }

    public void setTemplatename(String templatename) {
        this.templatename = templatename;
    }


    public static class RESPApproveusers{
        private Integer userid;
        private String  dispname;
        private String phone;
        private String logo;
        private Integer userrole;
        public Integer getUserid() {
            return userid;
        }

        public void setUserid(Integer userid) {
            this.userid = userid;
        }

        public String getDispname() {
            return dispname;
        }

        public void setDispname(String dispname) {
            this.dispname = dispname;
        }

        public String getPhone() {
            return phone;
        }

        public void setPhone(String phone) {
            this.phone = phone;
        }

        public String getLogo() {
            return logo;
        }

        public void setLogo(String logo) {
            this.logo = logo;
        }

        public Integer getUserrole() {
            return userrole;
        }

        public void setUserrole(Integer userrole) {
            this.userrole = userrole;
        }
    }
    public static class RESPDepment{
        private Integer templatepartid;
        private Integer deptid;
        private String deptname;

        private Integer approvetype;
        private List<RESPApproveusers> approveusers;

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

        public String getDeptname() {
            return deptname;
        }

        public void setDeptname(String deptname) {
            this.deptname = deptname;
        }


        public Integer getApprovetype() {
            return approvetype;
        }

        public void setApprovetype(Integer approvetype) {
            this.approvetype = approvetype;
        }

        public List<RESPApproveusers> getApproveusers() {
            return approveusers;
        }

        public void setApproveusers(List<RESPApproveusers> approveusers) {
            this.approveusers = approveusers;
        }
    }
    public static class RESPTemplateNode {
        private Integer templatenodeid;
        private String nodename;
        private Integer approvetype;
        private Integer createtype;
        private Integer notetype;
        private List<RESPDepment> deptlist;

        public Integer getNotetype() {
            return notetype;
        }

        public void setNotetype(Integer notetype) {
            this.notetype = notetype;
        }

        public Integer getCreatetype() {
            return createtype;
        }

        public void setCreatetype(Integer createtype) {
            this.createtype = createtype;
        }

        public Integer getApprovetype() {
            return approvetype;
        }

        public void setApprovetype(Integer approvetype) {
            this.approvetype = approvetype;
        }

        public String getNodename() {
            return nodename;
        }

        public void setNodename(String nodename) {
            this.nodename = nodename;
        }

        public Integer getTemplatenodeid() {
            return templatenodeid;
        }

        public void setTemplatenodeid(Integer templatenodeid) {
            this.templatenodeid = templatenodeid;
        }

        public List<RESPDepment> getDeptlist() {
            return deptlist;
        }

        public void setDeptlist(List<RESPDepment> deptlist) {
            this.deptlist = deptlist;
        }
    }
}
