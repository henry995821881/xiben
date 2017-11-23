package com.xiben.pm.md.service.impl;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.xiben.pm.md.mapper.MdEmployeeEntityMapper;
import com.xiben.pm.md.pojo.MdEmployeeEntity;
import com.xiben.pm.md.service.AttachmentService;
import com.xiben.pm.md.service.EmployeeService;
import com.xiben.sso.client.model.User;

@Service("employeeS")
public class EmployeeServiceImpl implements EmployeeService {
	
	@Autowired
	AttachmentService attachmentService;
	
	@Autowired
	MdEmployeeEntityMapper employeeMapper;


	public MdEmployeeEntity getEmployeeByUserId(int userid) {
		return employeeMapper.selectByPrimaryKey(userid);
	}
	
	public void updateUserLogo(String logourl,int userid) {
		MdEmployeeEntity entity = this.getEmployeeByUserId(userid);
		
		if (entity!=null)
		{
			entity.setLogourl(logourl);
			
			employeeMapper.updateByPrimaryKey(entity);
		}
	}
	
	private void copyUserToEmployee(User user,MdEmployeeEntity entity)
	{
		entity.setAuthstatus(user.getAuthstatus());
		entity.setDispname(user.getDispname());
		entity.setEmail(user.getEmail());
		entity.setIsshownickname(user.getIsshownickname());
		entity.setLogourl(user.getLogourl());
		entity.setNickname(user.getNickname());
		entity.setPhone(user.getPhone());
		entity.setSex(user.getSex());
		entity.setStatus(1);
		entity.setSuperioruserid(user.getSuperioruserid());
		entity.setTruename(user.getTruename());
		entity.setUserid(user.getUserid());
		entity.setUsername(user.getUsername());
		entity.setVersion(user.getVersion());
		entity.setCreateby(0);
		entity.setCreatedate(new Date());
		entity.setUpdateby(0);
		entity.setUpdatedate(new Date());
	}
	
	public void synchronizeUser(User user) {
		MdEmployeeEntity entity = this.getEmployeeByUserId(user.getUserid());
		if (null==entity)
		{
			entity = new MdEmployeeEntity();
			copyUserToEmployee(user,entity);
			employeeMapper.insert(entity);
		}else if (entity.getVersion()!=user.getVersion())
		{
			copyUserToEmployee(user,entity);
			employeeMapper.updateByPrimaryKey(entity);
		}		
	}

	@Override
	public MdEmployeeEntity queryByPhone(String mgrphone) {
		// TODO Auto-generated method stub
		return employeeMapper.queryByPhone(mgrphone);
	}
}
