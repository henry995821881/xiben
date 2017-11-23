package com.xiben.pm.md.service;

import com.xiben.pm.md.pojo.MdEmployeeEntity;
import com.xiben.sso.client.model.User;

public interface EmployeeService {
	public MdEmployeeEntity getEmployeeByUserId(int userid);
	
	public void updateUserLogo(String logourl,int userid);
	
	public void synchronizeUser(User user);

	public MdEmployeeEntity queryByPhone(String mgrphone);
}
