package com.xiben.pm.wf.request;

import java.util.List;

public class AddtemplateRequestBody {

    private int compid;
    private int templateid;
    private String templatename;
    private String templateremark;
    private List<Integer> startdeptidlist;
    private int status;
    private List<Midnodelist> midnodelist;
    
    
    
    
    
    public String getTemplateremark() {
		return templateremark;
	}
	public void setTemplateremark(String templateremark) {
		this.templateremark = templateremark;
	}
	public void setCompid(int compid) {
        this.compid = compid;
    }
    public int getCompid() {
        return compid;
    }

    public void setTemplateid(int templateid) {
        this.templateid = templateid;
    }
    public int getTemplateid() {
        return templateid;
    }

    public void setTemplatename(String templatename) {
        this.templatename = templatename;
    }
    public String getTemplatename() {
        return templatename;
    }

    public void setStartdeptidlist(List<Integer> startdeptidlist) {
        this.startdeptidlist = startdeptidlist;
    }
    public List<Integer> getStartdeptidlist() {
        return startdeptidlist;
    }

    public void setStatus(int status) {
        this.status = status;
    }
    public int getStatus() {
        return status;
    }

    public void setMidnodelist(List<Midnodelist> midnodelist) {
        this.midnodelist = midnodelist;
    }
    public List<Midnodelist> getMidnodelist() {
        return midnodelist;
    }
    /**
     * Auto-generated: 2017-11-13 14:53:8
     *
     * @author bejson.com (i@bejson.com)
     * @website http://www.bejson.com/java2pojo/
     */
    public static class Midnodelist {

        private int templatenodeid;
        private String nodename;
        private int approvetype;
        private List<Integer> deptidlist;
        private int notetype;
        public void setTemplatenodeid(int templatenodeid) {
            this.templatenodeid = templatenodeid;
        }
        public int getTemplatenodeid() {
            return templatenodeid;
        }

        public void setNodename(String nodename) {
            this.nodename = nodename;
        }
        public String getNodename() {
            return nodename;
        }

        public void setApprovetype(int approvetype) {
            this.approvetype = approvetype;
        }
        public int getApprovetype() {
            return approvetype;
        }

        public void setDeptidlist(List<Integer> deptidlist) {
            this.deptidlist = deptidlist;
        }
        public List<Integer> getDeptidlist() {
            return deptidlist;
        }

        public int getNotetype() {
            return notetype;
        }

        public void setNotetype(int notetype) {
            this.notetype = notetype;
        }
    }

}
