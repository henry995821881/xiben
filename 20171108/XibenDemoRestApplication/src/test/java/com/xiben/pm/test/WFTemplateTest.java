package com.xiben.pm.test;

import com.xiben.pm.ar.request.REPBaseArchive;
import com.xiben.pm.ar.response.RESPArchiveInfo;
import com.xiben.pm.ar.service.ArService;
import com.xiben.pm.wf.request.AddtemplateRequestBody;
import com.xiben.pm.wf.request.RPAddApproveUser;
import com.xiben.pm.wf.request.RPInstance;
import com.xiben.pm.wf.request.RPTemplateBaseParams;
import com.xiben.pm.wf.response.*;
import com.xiben.pm.wf.service.WFInstanceService;
import com.xiben.pm.wf.service.WFTemplateServie;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration
        ({"classpath:spring-context.xml","classpath:spring-mybatis.xml","classpath:spring-transaction.xml"})
public class WFTemplateTest {

    @Autowired
    WFTemplateServie wfTemplateServie;

    @Autowired
    WFInstanceService instanceService;


    @Autowired
    ArService arService;




    @Test
    public void getFlowListWithUserid(){

        List<IndexInfo> flowListWithUserid = instanceService.getFlowListWithUserid(21);
        System.out.println(flowListWithUserid);
    }
    @Test
    public void approvearchiveread(){

        REPBaseArchive archive = new REPBaseArchive();
        archive.setAppid(1);
        archive.setOptype(2);
        arService.approvearchiveread(archive,21);
    }

    @Test
    public void archiveread(){

        Map<String, String> archiveread = arService.archiveread(1, 37);
        System.out.println(archiveread);

    }

    @Test
    public void  applyarchiveread(){

        REPBaseArchive archive = new REPBaseArchive();
        archive.setRemark("测试申请");
        archive.setArchiveid(1);
        arService.applyarchiveread(archive, 37);
    }

    @Test
    public void getFilelist(){

        REPBaseArchive repBaseArchive = new REPBaseArchive();
        repBaseArchive.setCompid(100);
        repBaseArchive.setKeyword("测试");
        repBaseArchive.setCurpageno(1);
        repBaseArchive.setPagesize(10);
        List<RESPArchiveInfo> respArchiveInfos = arService.archiveList(repBaseArchive, 21);
        System.out.println(respArchiveInfos);

//        arService.archiveList()

    }



    @Test
    public void getInstanceInfo(){

        RESPInstanceInfo respInstanceInfo = instanceService.instanceInfo(1, 21);
        System.out.println(respInstanceInfo);
    }


    @Test
    public void getInstanceList(){

        RPInstance instance = new RPInstance();
        instance.setCurpageno(1);
        instance.setPagesize(Integer.MAX_VALUE);
        instance.setCompid(100);
        instance.setType(1);
        RESPInstancePager pager = instanceService.instanceList(instance, 21);
        System.out.println(pager);
    }

    @Test
    public void updateApprovetype(){
        RPAddApproveUser rpAddApproveUser = new RPAddApproveUser();
        rpAddApproveUser.setTemplatepartid(6);
        rpAddApproveUser.setApprovetype(2);
        wfTemplateServie.updateApprovetype(rpAddApproveUser,59);
    }

    @Test
    public void deleteShouquanren(){

        RPAddApproveUser rpAddApproveUser = new RPAddApproveUser();
        rpAddApproveUser.setTemplatepartid(6);
        rpAddApproveUser.setUserid(59);
        wfTemplateServie.removeApproveUser(rpAddApproveUser,21);
    }

    @Test
    public void insertShouquanren(){

        RPAddApproveUser rpAddApproveUser = new RPAddApproveUser();
        rpAddApproveUser.setTemplatepartid(6);
        rpAddApproveUser.setPhone("13916929615");
        int i = wfTemplateServie.addApproveUser(rpAddApproveUser, 59);
        System.out.println(i);

    }

    @Test
    public void getTemplateInfo(){
        RESPTempInfo templateInfo = wfTemplateServie.getTemplateInfo(1, 21);
        System.out.println(templateInfo);
    }

    @Test
    public void testQuery(){


        RPTemplateBaseParams params = new RPTemplateBaseParams();
        params.setCompid(108);
        params.setType(2);
        List<RESPTemplateList> templateList = wfTemplateServie.getTemplateList(params, 21);
        System.out.println(templateList);
    }

    @Test
    public void test(){
        AddtemplateRequestBody addtemplateRequestBody = new AddtemplateRequestBody();
        addtemplateRequestBody.setCompid(100);
        addtemplateRequestBody.setStatus(2);
        addtemplateRequestBody.setTemplateid(1);
        addtemplateRequestBody.setTemplatename("请假3天,compid=100,templateid=1,status=2");


        List<Integer> startNodeDeptList = new ArrayList<>();
        startNodeDeptList.add(8);





        addtemplateRequestBody.setStartdeptidlist(startNodeDeptList);

        List<AddtemplateRequestBody.Midnodelist> midnodelists = new ArrayList<>();

        //Add Node1
        AddtemplateRequestBody.Midnodelist node1 = new  AddtemplateRequestBody.Midnodelist();
        //node1.setNotetype(1);

        List<Integer> depIds1 = new ArrayList<>();
        depIds1.add(8);

        node1.setNodename("部门经理或审批,deptid=8");
        node1.setApprovetype(1);
        node1.setDeptidlist(depIds1);

        midnodelists.add(node1);

        //Add Node2
        AddtemplateRequestBody.Midnodelist node2 = new  AddtemplateRequestBody.Midnodelist();
        //node1.setNotetype(1);

        List<Integer> depIds2 = new ArrayList<>();
        depIds2.add(8);

        node2.setNodename("部门经理会签审批,deptid=8");
        node2.setApprovetype(2);
        node2.setDeptidlist(depIds2);

        midnodelists.add(node2);


        //Add Node3
        AddtemplateRequestBody.Midnodelist node3 = new  AddtemplateRequestBody.Midnodelist();

        List<Integer> depIds3 = new ArrayList<>();
        depIds3.add(8);
        depIds3.add(9);

        node3.setNodename("人事部或审批,deptid in (8,9)");
        node3.setApprovetype(1);
        node3.setDeptidlist(depIds3);

        midnodelists.add(node3);

        //Add Node4
        AddtemplateRequestBody.Midnodelist node4 = new  AddtemplateRequestBody.Midnodelist();

        List<Integer> depIds4 = new ArrayList<>();
        depIds4.add(8);
        depIds4.add(9);

        node4.setNodename("会签审批,deptid in (8,9)");
        node4.setApprovetype(2);
        node4.setDeptidlist(depIds4);

        midnodelists.add(node4);

        addtemplateRequestBody.setMidnodelist(midnodelists);
        wfTemplateServie.createTemplate(addtemplateRequestBody,21);

    }

}
