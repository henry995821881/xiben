package com.xiben.pm.wf.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.xiben.pm.exception.PMRuntimeException;
import com.xiben.pm.md.pojo.MdDepartment;
import com.xiben.pm.md.pojo.MdEmployeeEntity;
import com.xiben.pm.md.service.EmployeeService;
import com.xiben.pm.md.service.MdCompanyService;
import com.xiben.pm.md.service.MdDeptService;
import com.xiben.pm.wf.mapper.WfInstanceMapper;
import com.xiben.pm.wf.mapper.WfTemplateMapper;
import com.xiben.pm.wf.mapper.WfTemplateNodeApproveUserMapper;
import com.xiben.pm.wf.mapper.WfTemplateNodeApprovepartMapper;
import com.xiben.pm.wf.mapper.WfTemplateNodeMapper;
import com.xiben.pm.wf.pojo.WfTemplate;
import com.xiben.pm.wf.pojo.WfTemplateNode;
import com.xiben.pm.wf.pojo.WfTemplateNodeApproveUser;
import com.xiben.pm.wf.pojo.WfTemplateNodeApprovepart;
import com.xiben.pm.wf.request.AddtemplateRequestBody;
import com.xiben.pm.wf.request.RPAddApproveUser;
import com.xiben.pm.wf.request.RPTemplateBaseParams;
import com.xiben.pm.wf.response.BaseRespTemp;
import com.xiben.pm.wf.response.RESPTempInfo;
import com.xiben.pm.wf.response.RESPTemplateDetailList;
import com.xiben.pm.wf.response.RESPTemplateDetailList.RESPTemplateDetailApproveuserItem;
import com.xiben.pm.wf.response.RESPTemplateDetailList.RESPTemplateDetailNodeItem;
import com.xiben.pm.wf.response.RESPTemplateList;
import com.xiben.pm.wf.response.RESPTemplateList.Startrightdeptlist;
import com.xiben.pm.wf.service.WFTemNodeApprovepartService;
import com.xiben.pm.wf.service.WFTemNodeService;
import com.xiben.pm.wf.service.WFTemplateServie;
import com.xiben.pm.wf.utils.ArrayUtils;
import com.xiben.pm.wf.utils.FiledClone;

@Service
public class WFTemplateServieImpl implements WFTemplateServie {


    @Autowired
    EmployeeService employeeService;

    @Autowired
    MdDeptService deptService;
    @Autowired
    WfInstanceMapper wfInstanceMapper;

    @Autowired
    WFTemNodeService wfTemNodeService;

    @Autowired
    MdCompanyService mdCompanyService;
    @Autowired
    WFTemNodeApprovepartService wfTemNodeApprovepartService;

    @Autowired
    WfTemplateMapper wfTemplateMapperDao;

    @Autowired
    WfTemplateNodeMapper wfTemplateNodeMapperDao;

    @Autowired
    WfTemplateNodeApprovepartMapper wfTemplateNodeApprovepartMapper;

    @Autowired
    WfTemplateNodeApproveUserMapper wfTemplateNodeApproveUserMapper;




    private boolean hasInstanceNotDone(int templateid){
        return wfInstanceMapper.queryCountWithStatus(templateid)>0;
    }
    private WfTemplate hasData(int id){
        return wfTemplateMapperDao.selectByPrimaryKey(id);
    }

    @Override
    public boolean createTemplate(AddtemplateRequestBody addtemplateRequestBody, int userid){

        if (!mdCompanyService.isManagerForCompany(userid,addtemplateRequestBody.getCompid())){
            throw new PMRuntimeException(ERROR_PERMISSION);
        }


        //检查这个模板数据是否存在
        WfTemplate sourceData =  hasData(addtemplateRequestBody.getTemplateid());
        WfTemplate wfTemplate = FiledClone.cloneFiled(addtemplateRequestBody, WfTemplate.class);
        if (addtemplateRequestBody.getTemplateid() <=0 || sourceData == null){
            //节点不存在
            //创建一个模板
           int rId =  saveTemplate(wfTemplate,userid);
           addtemplateRequestBody.setTemplateid(rId);
        }else{
            //检查实力是否在使用
            if (!hasInstanceNotDone(addtemplateRequestBody.getTemplateid())){
                //更新这个模板
                updateTemplate(wfTemplate,userid);
            }else{
                throw new PMRuntimeException(HAS_INSTANE_NOT_DONE_STR);
            }
        }
        //删除createtype=2的节点模板
        deleteAllNodeWithTemplateId(addtemplateRequestBody.getTemplateid(),userid);
        //清空审批人

        List<AddtemplateRequestBody.Midnodelist> midnodelistList =  addtemplateRequestBody.getMidnodelist();
        if (ArrayUtils.isEmpty(midnodelistList)){
            throw new PMRuntimeException("节点数据为空!");
        }
        //强制最后节点类型=3
        for(int i=0;i<midnodelistList.size();i++){
            midnodelistList.get(i).setNotetype(2);
        }
        midnodelistList.get(midnodelistList.size()-1).setNotetype(3);
        //创建开始节点
        WfTemplateNode launchWfTemplateNode = FiledClone.merge(WfTemplateNode.class,midnodelistList.get(0),addtemplateRequestBody);
        launchWfTemplateNode.setNotetype(1);
        launchWfTemplateNode.setNodename("发起");
        int rank = 1;
        launchWfTemplateNode.setRank(rank);
        launchWfTemplateNode.setCreatetype(1);
        launchWfTemplateNode.setApprovetype(1);

        int startNodeid =  wfTemNodeService.saveTempNode(launchWfTemplateNode,userid);

        for(AddtemplateRequestBody.Midnodelist midnodelist:midnodelistList) {
            rank++;
            WfTemplateNode n = FiledClone.merge(WfTemplateNode.class, midnodelist, addtemplateRequestBody);
            n.setRank(rank);
            n.setCreatetype(2);
            int tid = wfTemNodeService.saveTempNode(n,userid);
            List<Integer> depids = midnodelist.getDeptidlist();
            for(Integer depid:depids){
                WfTemplateNodeApprovepart wfTemplateNodeApprovepart1 = FiledClone.merge(WfTemplateNodeApprovepart.class,midnodelistList.get(0),addtemplateRequestBody);
                wfTemplateNodeApprovepart1.setDeptid(depid);
                wfTemplateNodeApprovepart1.setTemplatenodeid(tid);
                int partId =  wfTemNodeApprovepartService.saveData(wfTemplateNodeApprovepart1,userid);
                saveShenpiren(depid,partId,addtemplateRequestBody.getCompid(),tid,userid,addtemplateRequestBody.getTemplateid());
            }
        }
        List<Integer> startdeptidlist = addtemplateRequestBody.getStartdeptidlist();
        for(Integer v:startdeptidlist){
            //create starup dep
            WfTemplateNodeApprovepart wfTemplateNodeApprovepart = FiledClone.merge(WfTemplateNodeApprovepart.class,midnodelistList.get(0),addtemplateRequestBody);
            wfTemplateNodeApprovepart.setTemplatenodeid(startNodeid);
            wfTemplateNodeApprovepart.setDeptid(v);
            wfTemplateNodeApprovepart.setApprovetype(1);
            int partId =  wfTemNodeApprovepartService.saveData(wfTemplateNodeApprovepart,userid);
            saveShenpiren(v,partId,addtemplateRequestBody.getCompid(),startNodeid,userid,addtemplateRequestBody.getTemplateid());
        }
        return false;
    }


    private int saveShenpiren(int depid,int partid,int compid,int tempnodeid,int userid,int tempid){
        List<MdEmployeeEntity> deptManagerByDeptid = deptService.getDeptManagerByDeptid(depid);
        for(int i=0;i<deptManagerByDeptid.size();i++){
            MdEmployeeEntity employeeEntity = deptManagerByDeptid.get(i);
            WfTemplateNodeApproveUser wfTemplateNodeApproveUser = new WfTemplateNodeApproveUser();
            wfTemplateNodeApproveUser.setUserid(employeeEntity.getUserid());
            wfTemplateNodeApproveUser.setCompid(compid);
            wfTemplateNodeApproveUser.setDeptid(depid);
            wfTemplateNodeApproveUser.setTemplateid(tempid);
            wfTemplateNodeApproveUser.setTemplatenodeid(tempnodeid);
            wfTemplateNodeApproveUser.setUserrole(1);
            wfTemplateNodeApproveUser.setTemplatepartid(partid);

            wfTemplateNodeApproveUser.setCreateby(userid);
            wfTemplateNodeApproveUser.setUpdateby(userid);
            wfTemplateNodeApproveUser.setCreatedate(new Date());
            wfTemplateNodeApproveUser.setUpdatedate(new Date());
            wfTemplateNodeApproveUserMapper.insert(wfTemplateNodeApproveUser);
        }
        return 0;
    }
    private int updateTemplate(WfTemplate data,int userid){
        data.setUpdateby(userid);
        data.setUpdatedate(new Date());
        return wfTemplateMapperDao.updateByPrimaryKeySelective(data);

    }

    @Override
    public int saveTemplate(WfTemplate data,int userid) {
        data.setCreateby(userid);
        data.setUpdateby(userid);
        data.setCreatedate(new Date());
        data.setUpdatedate(new Date());
        data.setType(99);
        data.setCreatetype(1);
        wfTemplateMapperDao.insert(data);
        return data.getTemplateid();
    }

    /**
     * 删除节点
     * @param templateId
     * @return
     */
    @Override
    public int deleteAllNodeWithTemplateId(int templateId,int userid) {
        //删除wf_template_node_approvepart ，wf_template_node
        wfTemNodeService.removeAllWithTemplateId(templateId);
        wfTemNodeApprovepartService.removeAllWithTemplateId(templateId);
        wfTemplateNodeApproveUserMapper.deleteAllWithTempid(templateId);
        return 0;
    }

    @Override
    public int deleteTemplate(int templateId,int userid) {
        WfTemplate wfTemplate = wfTemplateMapperDao.selectByPrimaryKey(templateId);
        if (!mdCompanyService.isManagerForCompany(userid,wfTemplate.getCompid())){
            throw new PMRuntimeException(ERROR_PERMISSION);
        }
        //是否有未完成的实力
        if(hasInstanceNotDone(templateId)){
            throw new PMRuntimeException(HAS_INSTANE_NOT_DONE_STR);
        }
        //destory row
        WfTemplate  template = new WfTemplate();
        template.setTemplateid(templateId);
        template.setStatus(99);
        template.setUpdateby(userid);
        template.setUpdatedate(new Date());
        return wfTemplateMapperDao.updateByPrimaryKeySelective(template);
    }

    private List<Startrightdeptlist> calculateStartrightdeptlist(List<WfTemplateNodeApprovepart> launchParts,List<WfTemplateNodeApproveUser> wfTemplateNodeApproveUsers)
    {
    	List<Startrightdeptlist> list = new ArrayList<Startrightdeptlist>();
    	
    	if (null==launchParts||wfTemplateNodeApproveUsers==null)
    		return list;
    	
    	for(WfTemplateNodeApprovepart part:launchParts)
    	{
    		for(WfTemplateNodeApproveUser user:wfTemplateNodeApproveUsers)
    		{
    			if (user.getTemplatepartid()==part.getTemplatepartid())
    			{
    				MdDepartment department =  deptService.getDeptById(part.getDeptid());
    				Startrightdeptlist startDept = new Startrightdeptlist();
    				startDept.setDeptid(department.getDeptid());
    				startDept.setDeptname(department.getDeptname());
    				list.add(startDept);
    				break;
    			}
    		}
    	}
    	
    	
    	return list;
    }
    
    @Override
    public List<RESPTemplateList> getTemplateList(RPTemplateBaseParams params, int userid) {
        List<WfTemplate> templates =  wfTemplateMapperDao.queryListByCompid(params.getCompid(),params.getType());
        List<RESPTemplateList> respTemplateLists = new ArrayList<>();
        
        List<WfTemplateNodeApprovepart> launchParts = null;
        List<WfTemplateNodeApproveUser> wfTemplateNodeApproveUsers = null;
        
        for(int i=0;i<templates.size();i++){
            WfTemplate wfTemplate = templates.get(i);
            RESPTemplateList respTemplateList = new RESPTemplateList();
            respTemplateList.setTemplateid(wfTemplate.getTemplateid());
            respTemplateList.setTemplatename(wfTemplate.getTemplatename());
            respTemplateList.setTemplateremark(wfTemplate.getTemplateremark());
            respTemplateList.setType(wfTemplate.getType());
            
            List<RESPTemplateList.SubNode> subNodes = new ArrayList<>();
            respTemplateList.setMidnodelist(subNodes);
            //查询发起节点
            List<WfTemplateNode> templateNodes =  wfTemplateNodeMapperDao.queryByTNCType(wfTemplate.getTemplateid(),params.getCompid(),1);


            boolean hasUser = false;
            launchParts = new ArrayList<>();
            
            for(WfTemplateNode node:templateNodes){//理论上一个节点模板只有一个发起节点
                //找出用户可查看的流程模板
            	wfTemplateNodeApproveUsers = wfTemplateNodeApproveUserMapper.queryBy2("templatenodeid", node.getTemplatenodeid() + "", "userid", userid + "", "compid", node.getCompid() + "");
                hasUser = !ArrayUtils.isEmpty(wfTemplateNodeApproveUsers);
                //获取发起节点所属的部门为启动部门
                launchParts = wfTemplateNodeApprovepartMapper.queryByCTT(node.getCompid(),node.getTemplateid(),node.getTemplatenodeid());

            }
            if (hasUser || params.getType() == 1){

                List<WfTemplateNode> allTemplateNodes =  wfTemplateNodeMapperDao.queryByTNCType(wfTemplate.getTemplateid(),params.getCompid(),null);
                for(WfTemplateNode node:allTemplateNodes){
                    if (node.getNotetype()==1){
                        continue;
                    }
                    StringBuffer launchDeptids = new StringBuffer();
                    StringBuffer approvedepts = new StringBuffer();
                    List<Integer> approvedeptsIDS = new ArrayList<Integer>();
                    List<Integer> launchDeptidsIDS = new ArrayList<Integer>();


                    //获取启动部门（查询发起节点的部门）
                    for(WfTemplateNodeApprovepart lPart :launchParts){
                        MdDepartment department =  deptService.getDeptById(lPart.getDeptid());
                        launchDeptids.append(department.getDeptname()+",");
                        launchDeptidsIDS.add(department.getDeptid());

                    }
                    //获取审批部门
                    if (node.getNotetype() != 1){
                        List<WfTemplateNodeApprovepart> shenpiparts = wfTemplateNodeApprovepartMapper.queryByCTT(node.getCompid(), node.getTemplateid(), node.getTemplatenodeid());
                        for(WfTemplateNodeApprovepart lPart :shenpiparts){
                            MdDepartment department =  deptService.getDeptById(lPart.getDeptid());
                            approvedepts.append(department.getDeptname()+",");
                            approvedeptsIDS.add(department.getDeptid());
                        }

                    }
                    respTemplateList.setStartdepts(launchDeptids.toString());
                    respTemplateList.setStartdeptidlist(launchDeptidsIDS);

                    RESPTemplateList.SubNode subNode = new RESPTemplateList.SubNode();
                    subNode.setApprovedepts(approvedepts.toString());
                    subNode.setDeptidlist(approvedeptsIDS);
                    
                    subNode.setNodename(node.getNodename());
                    subNode.setApprovetype(node.getApprovetype());
                    subNode.setTemplatenodeid(node.getTemplatenodeid());

                    subNodes.add(subNode);
                }
                
                respTemplateList.setStartrightdeptlist(calculateStartrightdeptlist(launchParts,wfTemplateNodeApproveUsers));
                respTemplateLists.add(respTemplateList);
            }

        }
        return respTemplateLists;
    }

    private boolean isNodePartManager(List<WfTemplateNodeApproveUser> wfUserList,int userid) {
    	for(WfTemplateNodeApproveUser approveUser:wfUserList)
    	{
    		if (userid==approveUser.getUserid()&&approveUser.getUserrole()==1)
    			return true;
    	}
    	return false;
    }
    
    public List<RESPTemplateDetailList> getTemplateInfo2ListByDeptidAndUserid(int deptid,int userid) {
    	
    	List<RESPTemplateDetailList> result = new ArrayList<>();
    	
    	List<Integer> templateIdList = this.wfTemplateNodeApproveUserMapper.selectTemplateIdListHasMgrRight(deptid, userid);
    	for(Integer templateid:templateIdList)
    	{
    		result.add(getTemplateInfo2ByTemplateid(templateid,deptid,userid));
    	}
    	return result;
    }
    
    public RESPTemplateDetailList getTemplateInfo2ByTemplateid(Integer templateid,int deptid,int userid)
    {
    	RESPTemplateDetailList templateInfo = new RESPTemplateDetailList();
    	WfTemplate wfTemplate = wfTemplateMapperDao.selectByPrimaryKey(templateid);
    	templateInfo.setTemplateid(wfTemplate.getTemplateid());
    	templateInfo.setTemplatename(wfTemplate.getTemplatename());
    	templateInfo.setTemplateremark(wfTemplate.getTemplateremark());
    	templateInfo.setType(wfTemplate.getType());
    	List<WfTemplateNode> templateNodes = wfTemplateNodeMapperDao.queryBy("templateid", templateid + "");
    	
    	List<RESPTemplateDetailNodeItem> nodeItemList = new ArrayList<>();
    	
    	for(WfTemplateNode node:templateNodes)
    	{
    		StringBuffer deptnames = new StringBuffer("");

            List<WfTemplateNodeApprovepart> shenpiparts = wfTemplateNodeApprovepartMapper.queryByCTT(node.getCompid(), node.getTemplateid(), node.getTemplatenodeid());
            for(WfTemplateNodeApprovepart lPart :shenpiparts){
                MdDepartment department =  deptService.getDeptById(lPart.getDeptid());
                deptnames.append(department.getDeptname()+",");
            }
    		
    		RESPTemplateDetailNodeItem nodeInfo = new RESPTemplateDetailNodeItem();
    		nodeInfo.setApprovetype(node.getApprovetype());
    		nodeInfo.setCreatetype(node.getCreatetype());
    		nodeInfo.setDeptapprovetype(0);
    		nodeInfo.setDeptnames(deptnames.toString());
    		nodeInfo.setNodeid(node.getTemplatenodeid());
    		nodeInfo.setNodename(node.getNodename());
    		nodeInfo.setNodetype(node.getNotetype());
    		nodeInfo.setUserrole(0);
    		nodeItemList.add(nodeInfo);
    		
    		int templatepartid=0;
    		
    		List<WfTemplateNodeApproveUser> wfUserList = this.wfTemplateNodeApproveUserMapper.queryBy1("templatenodeid", node.getTemplatenodeid()+"","deptid", deptid+"");
    		List<RESPTemplateDetailApproveuserItem> approveUserItemList = new ArrayList<>();
    		if (isNodePartManager(wfUserList,userid))
    		{
    			for(WfTemplateNodeApproveUser user:wfUserList)
    			{
    				RESPTemplateDetailApproveuserItem userItem = new RESPTemplateDetailApproveuserItem();
    				userItem.setDeptid(user.getDeptid());
    				MdDepartment department = deptService.getDeptById(user.getDeptid());
    				userItem.setDeptname(department.getDeptname());
    				
    				MdEmployeeEntity mdEmployeeEntity = employeeService.getEmployeeByUserId(user.getUserid());
    				userItem.setDispname(mdEmployeeEntity.getDispname());
    				userItem.setLogo(mdEmployeeEntity.getLogourl());
    				userItem.setPhone(mdEmployeeEntity.getPhone());
    				
    				userItem.setTemplatepartid(user.getTemplatepartid());
    				userItem.setUserid(user.getUserid());
    				userItem.setUserrole(user.getUserrole());
    				
    				templatepartid = user.getTemplatepartid();
    				
    				approveUserItemList.add(userItem);
    			}
    			
    			if (templatepartid>0)
    			{
    				WfTemplateNodeApprovepart part = this.wfTemplateNodeApprovepartMapper.selectByPrimaryKey(templatepartid);
    				nodeInfo.setDeptapprovetype(part.getApprovetype());	
    			}
   			
    			nodeInfo.setUserrole(1);
    		}
    		
    		nodeInfo.setApproveuserlist(approveUserItemList);
    		
    	}
    	templateInfo.setNodelist(nodeItemList);
    	
    	return templateInfo;
    	
    }
    
    @Override
    public RESPTempInfo getTemplateInfo(Integer templateid, int userid) {

        //获取模板信息
        WfTemplate wfTemplate = wfTemplateMapperDao.selectByPrimaryKey(templateid);
        RESPTempInfo info = new RESPTempInfo();
        info.setTemplateid(wfTemplate.getTemplateid());
        info.setTemplatename(wfTemplate.getTemplatename());
        info.setType(wfTemplate.getType());
        info.setTemplateremark(wfTemplate.getTemplateremark());
        
        //获取模板下所有的节点
        List<WfTemplateNode> templateNodes = wfTemplateNodeMapperDao.queryBy("templateid", templateid + "");
        List<BaseRespTemp.RESPTemplateNode> nodeList = new ArrayList<>();
        for(int i=0;i<templateNodes.size();i++){
            WfTemplateNode templateNode = templateNodes.get(i);

            BaseRespTemp.RESPTemplateNode respTemplateNode = FiledClone.cloneFiled(templateNode, BaseRespTemp.RESPTemplateNode.class);
            nodeList.add(respTemplateNode);
            List<BaseRespTemp.RESPDepment> respDepments = new ArrayList<>();

            //查找这个模板下得所有部门
            List<WfTemplateNodeApprovepart> templateidParts = wfTemplateNodeApprovepartMapper.queryBy("templateid", "" + templateid);
            //查找所有部门信息
            for(WfTemplateNodeApprovepart approvepart:templateidParts){
                MdDepartment department = deptService.getDeptById(approvepart.getDeptid());
                BaseRespTemp.RESPDepment respDepment = new BaseRespTemp.RESPDepment();
                respDepment.setApprovetype(approvepart.getApprovetype());
                respDepment.setDeptid(approvepart.getDeptid());
                respDepment.setDeptname(department.getDeptname());
                respDepment.setTemplatepartid(approvepart.getTemplatepartid());

                //查找部门审批人
                List<BaseRespTemp.RESPApproveusers> respApproveusers = new ArrayList<>();


                List<WfTemplateNodeApproveUser> approveUsers = wfTemplateNodeApproveUserMapper.queryBy("templateid", templateid + "");

                for(WfTemplateNodeApproveUser appuser:approveUsers){
                    MdEmployeeEntity mdEmployeeEntity = employeeService.getEmployeeByUserId(appuser.getUserid());
                    BaseRespTemp.RESPApproveusers user = new BaseRespTemp.RESPApproveusers();
                    user.setDispname(mdEmployeeEntity.getDispname());
                    user.setUserrole(appuser.getUserrole());
//                    user.setDeptrole(department.getde);
                    user.setLogo(mdEmployeeEntity.getLogourl());
                    user.setPhone(mdEmployeeEntity.getPhone());
                    user.setUserid(mdEmployeeEntity.getUserid());
                    respApproveusers.add(user);
                }

                respDepments.add(respDepment);
                respDepment.setApproveusers(respApproveusers);
            }
            respTemplateNode.setDeptlist(respDepments);

        }
        info.setNodelist(nodeList);
        return info;

    }

    public static final String HAS_INSTANE_NOT_DONE_STR = "存在未完成的实例";
    public static final String ERROR_PERMISSION = "权限不足!";

    private boolean isShouquanren(int userid,int depid){
        List<MdEmployeeEntity> deptManagerByDeptid = deptService.getDeptManagerByDeptid(depid);

        boolean isShouquanren = false;
        for(int i=0;i<deptManagerByDeptid.size();i++){
            if (userid == deptManagerByDeptid.get(i).getUserid()){
                isShouquanren = true;
            }
        }
        return isShouquanren;
    }
    @Override
    public int addApproveUser(RPAddApproveUser params,int userid) {

        WfTemplateNodeApprovepart approvepart = wfTemplateNodeApprovepartMapper.selectByPrimaryKey(params.getTemplatepartid());


        if (approvepart == null){
            throw  new PMRuntimeException("数据不存在!");
        }

        if(hasInstanceNotDone(approvepart.getTemplateid())){

            throw new PMRuntimeException(HAS_INSTANE_NOT_DONE_STR);

        }
        MdEmployeeEntity mdEmployeeEntity = employeeService.queryByPhone(params.getPhone());
        if (mdEmployeeEntity == null){
            throw  new PMRuntimeException(params.getPhone()+"的用户不存在!");
        }



        if (!isShouquanren(userid,approvepart.getDeptid())){
            throw  new PMRuntimeException(ERROR_PERMISSION);
        }

        List<WfTemplateNodeApproveUser> wfTemplateNodeApproveUsers = wfTemplateNodeApproveUserMapper.queryBy1("userid", mdEmployeeEntity.getUserid() + "", "templatepartid", params.getTemplatepartid() + "");
        if (!ArrayUtils.isEmpty(wfTemplateNodeApproveUsers)){
            throw new PMRuntimeException("授权人已存在");
        }
        WfTemplateNodeApproveUser insertUser = FiledClone.cloneFiled(approvepart, WfTemplateNodeApproveUser.class);
        insertUser.setUserrole(2);
        insertUser.setUserid(mdEmployeeEntity.getUserid());
        insertUser.setCreatedate(new Date());
        insertUser.setUpdatedate(new Date());
        insertUser.setCreateby(userid);
        insertUser.setUpdateby(userid);
        //插入部门授权人
        return wfTemplateNodeApproveUserMapper.insert(insertUser);
    }

    @Override
    public int removeApproveUser(RPAddApproveUser params,int userid) {


        WfTemplateNodeApprovepart approvepart = checkShouquanrenAndInstance(userid,params.getTemplatepartid());
        //不能移除授权人
        //获取这个部门的审批人
        List<WfTemplateNodeApproveUser> shouquanrens = wfTemplateNodeApproveUserMapper.queryBy2("userid", params.getUserid()+"","templatepartid",approvepart.getTemplatepartid()+"","deptid",approvepart.getDeptid()+"");
        if(!ArrayUtils.isEmpty(shouquanrens)){
            WfTemplateNodeApproveUser user = shouquanrens.get(0);
            if(user.getUserrole() == 1){
                throw new PMRuntimeException("不能移除授权人!");
            }
        }else{
            throw  new PMRuntimeException("用户不存在!");
        }
        //符合条件移除这个授权人
        wfTemplateNodeApproveUserMapper.deleteShouquanren(approvepart.getDeptid(),params.getUserid(),params.getTemplatepartid());
        return 0;
    }

    private WfTemplateNodeApprovepart checkShouquanrenAndInstance(int userid,int tpartid){
        WfTemplateNodeApprovepart approvepart = wfTemplateNodeApprovepartMapper.selectByPrimaryKey(tpartid);


        //不能存在未完成的实例
        if (hasInstanceNotDone(approvepart.getTemplateid())){
            throw new PMRuntimeException(HAS_INSTANE_NOT_DONE_STR);
        }

        //操作人必须是授权人

        if (!isShouquanren(userid,approvepart.getDeptid())){
            throw  new PMRuntimeException(ERROR_PERMISSION);
        }

        return approvepart;
    }
    @Override
    public int updateApprovetype(RPAddApproveUser params,int userid) {
        if(!(params.getApprovetype() == 1 || params.getApprovetype() ==2)){
            throw new PMRuntimeException("非法会签");
        }
        WfTemplateNodeApprovepart approvepart =  checkShouquanrenAndInstance(userid,params.getTemplatepartid());
        approvepart.setApprovetype(params.getApprovetype());
        approvepart.setUpdateby(userid);
        approvepart.setUpdatedate(new Date());
        return wfTemplateNodeApprovepartMapper.updateByPrimaryKey(approvepart);
    }

    @Override
    public int createFlow(WfTemplate wfTemplate, WfTemplateNode templateNode, WfTemplateNodeApprovepart wfTemplateNodeApprovepart, int userid) {

        try {

            Object[] objs = new Object[]{wfTemplate,templateNode,wfTemplateNodeApprovepart};
            for(Object object:objs){
                if (object == null){
                    throw new PMRuntimeException("创建流程失败!参数不能为空!");
                }
            }
            wfTemplateMapperDao.insert(wfTemplate);
            int templateId = wfTemplate.getTemplateid();
            templateNode.setTemplateid(templateId);
            wfTemplateNodeMapperDao.insert(templateNode);
            int templateNodeId = templateNode.getTemplatenodeid();
            wfTemplateNodeApprovepart.setTemplatenodeid(templateNodeId);
            wfTemplateNodeApprovepart.setTemplateid(templateId);
            wfTemplateNodeApprovepartMapper.insert(wfTemplateNodeApprovepart);
        }catch (Exception e){

            e.printStackTrace();
            throw new RuntimeException("请检查参数是否齐全!");
        }

        return 0;
    }

    @Override
    public int updateApproveUser(int updateUserId, int deptid, int sourceUser) {

        //更新授权人 可以简化成sql
        List<WfTemplateNodeApproveUser> wfTemplateNodeApproveUsers = wfTemplateNodeApproveUserMapper.queryBy("deptid", deptid + "");
        for(WfTemplateNodeApproveUser wfTemplateNodeApproveUser:wfTemplateNodeApproveUsers){
            wfTemplateNodeApproveUser.setUserid(updateUserId);
            //如果这个用户是其他授权人，那么要删除这个记录
            if (wfTemplateNodeApproveUser.getUserid() == updateUserId && wfTemplateNodeApproveUser.getUserrole()>1){
                //检查这个用户是否是其他授权人,如果是 则删除这条记录否则 直接更新授权人
                wfTemplateNodeApproveUserMapper.deleteByPrimaryKey(wfTemplateNodeApproveUser.getId());
            }

            wfTemplateNodeApproveUser.setUpdatedate(new Date());
            wfTemplateNodeApproveUser.setUpdateby(sourceUser);
            wfTemplateNodeApproveUserMapper.updateByPrimaryKey(wfTemplateNodeApproveUser);
        }
        return 0;
    }

    @Override
    public int hasTemplateNodeWithDepId(int deptid, int userid) {

        //检查模板节点表中是否存在
        List<WfTemplateNodeApprovepart> wfTemplateNodeApproveparts = wfTemplateNodeApprovepartMapper.queryBy("deptid", deptid + "");
        if (ArrayUtils.isEmpty(wfTemplateNodeApproveparts)){
            return 0;
        }
        //检查模板是否是99状态
        int count = 0;
        for(WfTemplateNodeApprovepart w:wfTemplateNodeApproveparts){
            WfTemplate wfTemplate = wfTemplateMapperDao.selectByPrimaryKey(w.getTemplateid());
            if (wfTemplate !=null && wfTemplate.getStatus() != 99){
                count++;
            }
        }
        return count;
    }


}
