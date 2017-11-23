package com.xiben.pm.wf.response;

import java.util.List;

public class RESPTemplateList {



    private Integer templateid;
    private String templatename;
    private String templateremark;
    private String startdepts;
    private int type;
    private List<Integer> startdeptidlist;
    private List<SubNode> midnodelist;
    private List<Startrightdeptlist> startrightdeptlist;
    
    
    
    
    public String getTemplateremark() {
		return templateremark;
	}

	public void setTemplateremark(String templateremark) {
		this.templateremark = templateremark;
	}

	public List<Startrightdeptlist> getStartrightdeptlist() {
		return startrightdeptlist;
	}

	public void setStartrightdeptlist(List<Startrightdeptlist> startrightdeptlist) {
		this.startrightdeptlist = startrightdeptlist;
	}

	public int getType() {
		return type;
	}

	public void setType(int type) {
		this.type = type;
	}

	public List<SubNode> getMidnodelist() {
        return midnodelist;
    }

    public void setMidnodelist(List<SubNode> midnodelist) {
        this.midnodelist = midnodelist;
    }

    public String getStartdepts() {
        return startdepts;
    }

    public void setStartdepts(String startdepts) {
        this.startdepts = startdepts;
    }

    public String getTemplatename() {
        return templatename;
    }

    public void setTemplatename(String templatename) {
        this.templatename = templatename;
    }

    public Integer getTemplateid() {
        return templateid;
    }

    public void setTemplateid(Integer templateid) {
        this.templateid = templateid;
    }






	public List<Integer> getStartdeptidlist() {
		return startdeptidlist;
	}

	public void setStartdeptidlist(List<Integer> startdeptidlist) {
		this.startdeptidlist = startdeptidlist;
	}



	public static class Startrightdeptlist{
		private Integer deptid;
		private String deptname;
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

	}


	public static class SubNode{
        private List<Integer> deptidlist;
        private Integer templatenodeid;
        private String nodename;
        private Integer approvetype;
        private String approvedepts;

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

        public String getApprovedepts() {
            return approvedepts;
        }

        public void setApprovedepts(String approvedepts) {
            this.approvedepts = approvedepts;
        }

        public Integer getApprovetype() {
            return approvetype;
        }

        public void setApprovetype(Integer approvetype) {
            this.approvetype = approvetype;
        }

		public List<Integer> getDeptidlist() {
			return deptidlist;
		}

		public void setDeptidlist(List<Integer> deptidlist) {
			this.deptidlist = deptidlist;
		}


    }
}
