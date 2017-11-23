package com.xiben.pm.wf.response;

import java.util.List;

public class RESPTemplateDetailList {
	private int templateid;
	private String templatename;
	private String templateremark;
	private int type;
	
	private List<RESPTemplateDetailNodeItem> nodelist;
	
	
	public int getTemplateid() {
		return templateid;
	}

	public void setTemplateid(int templateid) {
		this.templateid = templateid;
	}

	public String getTemplatename() {
		return templatename;
	}

	public void setTemplatename(String templatename) {
		this.templatename = templatename;
	}

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

	public List<RESPTemplateDetailNodeItem> getNodelist() {
		return nodelist;
	}

	public void setNodelist(List<RESPTemplateDetailNodeItem> nodelist) {
		this.nodelist = nodelist;
	}

	public static class RESPTemplateDetailNodeItem{
		
		private int nodeid;
		private String nodename;
		private int approvetype;
		private int createtype;
		private int nodetype;
		private String deptnames;
		private int userrole;
		private int deptapprovetype;
		
		private List<RESPTemplateDetailApproveuserItem> approveuserlist;
		
		public List<RESPTemplateDetailApproveuserItem> getApproveuserlist() {
			return approveuserlist;
		}
		public void setApproveuserlist(List<RESPTemplateDetailApproveuserItem> approveuserlist) {
			this.approveuserlist = approveuserlist;
		}
		public int getNodeid() {
			return nodeid;
		}
		public void setNodeid(int nodeid) {
			this.nodeid = nodeid;
		}
		public String getNodename() {
			return nodename;
		}
		public void setNodename(String nodename) {
			this.nodename = nodename;
		}
		public int getApprovetype() {
			return approvetype;
		}
		public void setApprovetype(int approvetype) {
			this.approvetype = approvetype;
		}
		public int getCreatetype() {
			return createtype;
		}
		public void setCreatetype(int createtype) {
			this.createtype = createtype;
		}
		public int getNodetype() {
			return nodetype;
		}
		public void setNodetype(int nodetype) {
			this.nodetype = nodetype;
		}
		public String getDeptnames() {
			return deptnames;
		}
		public void setDeptnames(String deptnames) {
			this.deptnames = deptnames;
		}
		public int getUserrole() {
			return userrole;
		}
		public void setUserrole(int userrole) {
			this.userrole = userrole;
		}
		public int getDeptapprovetype() {
			return deptapprovetype;
		}
		public void setDeptapprovetype(int deptapprovetype) {
			this.deptapprovetype = deptapprovetype;
		}
		
		
	}
	
	public static class RESPTemplateDetailApproveuserItem{
		private int templatepartid;
		private int deptid;
		private String deptname;
		private int userid;
		private String dispname;
		private String phone;
		private String logo;
		private int userrole;
		public int getTemplatepartid() {
			return templatepartid;
		}
		public void setTemplatepartid(int templatepartid) {
			this.templatepartid = templatepartid;
		}
		public int getDeptid() {
			return deptid;
		}
		public void setDeptid(int deptid) {
			this.deptid = deptid;
		}
		public String getDeptname() {
			return deptname;
		}
		public void setDeptname(String deptname) {
			this.deptname = deptname;
		}
		public int getUserid() {
			return userid;
		}
		public void setUserid(int userid) {
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
		public int getUserrole() {
			return userrole;
		}
		public void setUserrole(int userrole) {
			this.userrole = userrole;
		}
		
	}
	
}
