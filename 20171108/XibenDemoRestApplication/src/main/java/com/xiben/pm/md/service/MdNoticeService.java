package com.xiben.pm.md.service;

import java.util.List;

import com.xiben.pm.md.bean.MdNoticeEx;
import com.xiben.pm.md.pojo.MdNotice;
import com.xiben.pm.md.response.GetMessageDetailBody;

public interface MdNoticeService {

	
	public List<MdNoticeEx> getMessageList(Integer userid);
	
	public GetMessageDetailBody getmessagedetail(Integer userid,Integer noticeid);
	
}
