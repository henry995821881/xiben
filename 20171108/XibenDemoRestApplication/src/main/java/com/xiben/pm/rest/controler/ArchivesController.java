package com.xiben.pm.rest.controler;

import com.xiben.pm.ar.request.REPBaseArchive;
import com.xiben.pm.ar.response.RESPArchiveInfo;
import com.xiben.pm.ar.service.ArService;
import com.xiben.pm.rest.request.ServiceRequest;
import com.xiben.pm.rest.response.ServiceResponse;
import com.xiben.pm.utils.ThreadLocalHelper;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.io.File;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/security/archive/")
public class ArchivesController {


    private static final Logger logger = Logger.getLogger(WFTemplateController.class);
    @Autowired
    ArService arService;
    @RequestMapping(value="getarchivelist",method = RequestMethod.POST)
    public ServiceResponse<List<RESPArchiveInfo>> getarchivelist(@RequestBody ServiceRequest<REPBaseArchive> request) {
        ServiceResponse<List<RESPArchiveInfo>> response = new ServiceResponse<>();
        response.setPrivatefield(request.getPrivatefield());
        try {
            List<RESPArchiveInfo> respArchiveInfos = arService.archiveList(request.getReqdata(), ThreadLocalHelper.getUser().getUserid());
            response.setResdata(respArchiveInfos);
        }catch (Exception e){
            logger.error(e.getMessage(), e);
            response.setCode(2000);
            response.setMsg(e.getMessage());

        }
        return response;

    }
    @RequestMapping(value="applyarchiveread",method = RequestMethod.POST)
    public ServiceResponse<Void> applyarchiveread(@RequestBody ServiceRequest<REPBaseArchive> request) {
        ServiceResponse<Void> response = new ServiceResponse<>();
        response.setPrivatefield(request.getPrivatefield());
        try {
            arService.applyarchiveread(request.getReqdata(), ThreadLocalHelper.getUser().getUserid());
        }catch (Exception e){
            logger.error(e.getMessage(), e);
            response.setCode(2000);
            response.setMsg(e.getMessage());

        }
        return response;

    }
    @RequestMapping(value="archiveread",method = RequestMethod.POST)
    public ServiceResponse<Map<String, String>> archiveread(@RequestBody ServiceRequest<REPBaseArchive> request) {
        ServiceResponse<Map<String, String>> response = new ServiceResponse<>();
        response.setPrivatefield(request.getPrivatefield());
        try {
            Map<String, String> archiveread = arService.archiveread(request.getReqdata().getArchiveid(), ThreadLocalHelper.getUser().getUserid());
            response.setResdata(archiveread);
        }catch (Exception e){
            logger.error(e.getMessage(), e);
            response.setCode(2000);
            response.setMsg(e.getMessage());

        }
        return response;

    }
    @RequestMapping(value="approvearchiveread",method = RequestMethod.POST)
    public ServiceResponse<Void> approvearchiveread(@RequestBody ServiceRequest<REPBaseArchive> request) {
        ServiceResponse<Void> response = new ServiceResponse<>();
        response.setPrivatefield(request.getPrivatefield());
        try {
            arService.approvearchiveread(request.getReqdata(), ThreadLocalHelper.getUser().getUserid());
        }catch (Exception e){
            logger.error(e.getMessage(), e);
            response.setCode(2000);
            response.setMsg(e.getMessage());
        }
        return response;

    }

}
