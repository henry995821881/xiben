package com.xiben.pm.utils;

import java.io.IOException;

import javax.servlet.http.HttpServletResponse;

import com.xiben.common.utils.JsonUtils;
import com.xiben.common.utils.StringUtils;
import com.xiben.pm.rest.response.EmptyResponseBody;
import com.xiben.pm.rest.response.ServiceResponse;

public class ResponseBuilder {
	public static ServiceResponse<EmptyResponseBody> buildErrorMessage(int errorCode,String errorMessage)
	{
		ServiceResponse<EmptyResponseBody> responseMsg = new ServiceResponse<EmptyResponseBody>();
		responseMsg.setCode(errorCode);
		responseMsg.setMsg(errorMessage);
		return responseMsg;
	}
	
	public static void buildErrorMessageToResponse(int errorCode,String errorMessage,HttpServletResponse response,String responseCode) throws IOException
	{
		ServiceResponse<EmptyResponseBody> responseMsg = buildErrorMessage(errorCode,errorMessage);
		
		String json = JsonUtils.toJson(responseMsg);
		
		response.setContentType("application/json;charset=utf-8");  
		if (!StringUtils.isEmpty(responseCode))
		{
			response.addHeader(Constants.ESBResponseCode.xibenesb_response_code, responseCode);
		}
		
		
        response.getWriter().println(json);  
        response.getWriter().flush();
	}
}
