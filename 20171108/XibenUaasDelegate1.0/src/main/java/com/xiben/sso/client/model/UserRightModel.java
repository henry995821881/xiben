package com.xiben.sso.client.model;

import java.util.List;

public class UserRightModel {
	private List<String> privileges;
	private List<String> privilegeuris;
	public List<String> getPrivileges() {
		return privileges;
	}
	public void setPrivileges(List<String> privileges) {
		this.privileges = privileges;
	}
	public List<String> getPrivilegeuris() {
		return privilegeuris;
	}
	public void setPrivilegeuris(List<String> privilegeuris) {
		this.privilegeuris = privilegeuris;
	}
}
