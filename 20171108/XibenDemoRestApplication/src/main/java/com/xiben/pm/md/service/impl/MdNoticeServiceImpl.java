package com.xiben.pm.md.service.impl;

import java.util.List;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.xiben.pm.md.bean.MdNoticeEx;
import com.xiben.pm.md.mapper.MdNoticeMapper;
import com.xiben.pm.md.pojo.MdEmployeeEntity;
import com.xiben.pm.md.pojo.MdNotice;
import com.xiben.pm.md.response.GetMessageDetailBody;
import com.xiben.pm.md.service.EmployeeService;
import com.xiben.pm.md.service.MdNoticeService;

@Service
public class MdNoticeServiceImpl implements MdNoticeService {

	@Autowired
	private MdNoticeMapper mdNoticeMapper;

	@Autowired
	private EmployeeService employeeService;
	@Override
	public List<MdNoticeEx> getMessageList(Integer userid) {

		List<MdNoticeEx> noticeList = mdNoticeMapper.getNoticeList( userid);
		
		return noticeList;
	}

	
	
	@Override
	public GetMessageDetailBody getmessagedetail(Integer userid, Integer noticeid) {
		
		
		MdNotice notice = mdNoticeMapper.selectByPrimaryKey(noticeid);
		
		MdEmployeeEntity mdEmployee= employeeService.getEmployeeByUserId(notice.getSenduserid());
		GetMessageDetailBody body = new GetMessageDetailBody();
		BeanUtils.copyProperties(notice, body);
		body.setSenduserlogo(mdEmployee.getLogourl());
		body.setSendusername(mdEmployee.getDispname());
		//标记已读
		if(notice.getReadflag().intValue()==1){
			
			notice.setReadflag(2);
			mdNoticeMapper.updateByPrimaryKey(notice);
		}
		
		return body;
	}

	
	
	
}
