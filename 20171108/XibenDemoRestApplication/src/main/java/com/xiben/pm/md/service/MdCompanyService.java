package com.xiben.pm.md.service;

import java.util.List;

import com.xiben.pm.md.bean.MdCompanyEx;
import com.xiben.pm.md.pojo.MdAttchment;
import com.xiben.pm.md.pojo.MdCompany;
import com.xiben.pm.md.pojo.MdCompanyMember;
import com.xiben.pm.md.response.GetCompanyDetailBody;
import com.xiben.pm.md.response.GetCompanyMgrListBody;
import com.xiben.sso.client.model.User;

public interface MdCompanyService {

	public List<MdCompanyEx> queryCompany(Integer userid);
	
	public void insert(MdCompanyMember m);
	
	
	public void  insertCompanyLogo(User uaasUser,MdAttchment mdAttachment,Integer companyid);
	
	public void addCompany(MdCompany md_,User uaasUser,MdAttchment mdAttachment);
	public boolean isManagerForCompany(Integer userId,Integer companyId);
	public GetCompanyDetailBody getCompanyDetail(Integer userid,Integer companyid);

	public GetCompanyMgrListBody getCompanymgrList(int userid, Integer companyid);
	public void addCompanyManager(int userid, Integer companyid,String mgrphone);
	public void removeCompanyManager(int userid, Integer companyid,Integer mgrUseid);
	public void uploadCompanyLogo(User uaasUser,Integer companyid,MdAttchment mdAttachment);
	public Integer isRoot(Integer userid, Integer companyid);
	public MdCompany getbyid(Integer companyid);
	public List<MdCompanyMember> getManager(Integer companyid);


}
