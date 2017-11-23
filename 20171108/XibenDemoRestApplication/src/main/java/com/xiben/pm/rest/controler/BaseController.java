package com.xiben.pm.rest.controler;

import java.util.Date;

import com.xiben.common.qiniu.Config;
import com.xiben.common.qiniu.DownLoadTools;
import com.xiben.pm.md.pojo.MdAttchment;
import com.xiben.pm.md.service.AttachmentService;
import com.xiben.pm.rest.model.RestUser;
import com.xiben.pm.rest.model.UploadAttach;
import com.xiben.sso.client.dto.ChangeUserInfoDto;
import com.xiben.sso.client.model.User;



public class BaseController {

	protected String saveUserLogoAttachment(User uaasUser,UploadAttach attach, AttachmentService attachService)
    {
    	
    	
		MdAttchment mdAttachment = new MdAttchment();
		mdAttachment.setBizid(uaasUser.getUserid());
		mdAttachment.setBiztype(1);
		mdAttachment.setCreateby(uaasUser.getUserid());
		mdAttachment.setCreatedate(new Date());
		mdAttachment.setDescription("");
		mdAttachment.setFilekey(attach.getFk());
		mdAttachment.setFilename(attach.getFn());
		mdAttachment.setFilesize(attach.getFs());
		mdAttachment.setFiletype(attach.getFt());
		mdAttachment.setStatus(1);
		mdAttachment.setTablespace(Config.getPublicTablename());
		mdAttachment.setUpdateby(uaasUser.getUserid());
		mdAttachment.setUpdatedate(new Date());
		attachService.saveAttachment(mdAttachment);
		
		String downloadurl = DownLoadTools.getPublicFileDownloadUrl(attach.getFk());
    	
    	return downloadurl;
    }
    
    protected RestUser generateRestUser(User uaasUser)
    {
    	RestUser rUser = new RestUser();
    	rUser.setUsername(uaasUser.getUsername());
    	rUser.setAuthstatus(uaasUser.getAuthstatus());
    	rUser.setDescription("");
    	rUser.setDispname(uaasUser.getDispname());
    	rUser.setEmail(uaasUser.getEmail());
    	rUser.setHadpaypass(uaasUser.getHadsecuritypass());
    	rUser.setLogourl(uaasUser.getLogourl());
    	rUser.setNickname(uaasUser.getNickname());
    	rUser.setPhone(uaasUser.getPhone());
    	rUser.setSecuritylevel(uaasUser.getSecuritylevel());
    	rUser.setSex(uaasUser.getSex());

    	rUser.setSuperioruserid(uaasUser.getSuperioruserid());
    	rUser.setTruename(uaasUser.getTruename());
    	rUser.setUnreadnotice(0);
    	rUser.setUserid(uaasUser.getUserid());
    	rUser.setUsername(uaasUser.getUsername());
    	
    	rUser.setSuperiorname("上级测试");
    	rUser.setSuperiorphone("22222222222");
    	rUser.setSuperiortime("2017-10-27");
    	return rUser;
    }
    
    protected ChangeUserInfoDto getChangeUserInfoDto(User uaasUser,String accesstoken)
    {
    	ChangeUserInfoDto dto = new ChangeUserInfoDto();
    	dto.setAccess_token(accesstoken);
    	dto.setNickname(uaasUser.getNickname());
    	dto.setDispname(uaasUser.getDispname());
    	dto.setTruename(uaasUser.getTruename());
    	dto.setIsshownickname(uaasUser.getIsshownickname());
    	dto.setSex(uaasUser.getSex());
    	dto.setLogourl(uaasUser.getLogourl());//private UploadAttach logo;
    	dto.setEmail(uaasUser.getEmail());
    	dto.setSuperioruserid(uaasUser.getSuperioruserid());//private String superiorphone;
    	dto.setAuthstatus(uaasUser.getAuthstatus());
    	return dto;
    }
}
