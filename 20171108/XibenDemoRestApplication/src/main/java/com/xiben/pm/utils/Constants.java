package com.xiben.pm.utils;

public class Constants {
	public static class HTTPRequestHeader{
		public static final String xibenesb_client_id ="xibenesb-client-id"; 
		public static final String xibenesb_request_serialno ="xibenesb-request-serialno"; 
		public static final String xibenesb_accesstoken ="xibenesb-accesstoken"; 
	}
	
	public static class ESBResponseCode{
		public static final String xibenesb_response_code = "xibenesb-response-code";
		public static final String ERR_ACCESSTOKEN = "ERR_ACCESSTOKEN";
		public static final String BIZ_AUTHORIZATION_ERR = "BIZ_AUTHORIZATION_ERR";
		public static final String BIZ_ACCESSTOKEN_TIMEOUT = "BIZ_ACCESSTOKEN_TIMEOUT";
	}
}
