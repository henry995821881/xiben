package com.xiben.common.qiniu;

import com.qiniu.util.Auth;

public class UploadTools {
	public static String getPublicUploadToken()
	{
		Auth auth = Auth.create(Config.getAccessKey(), Config.getSecretKey());
		String upToken = auth.uploadToken(Config.getPublicTablename());
		return upToken;
	}
	
	public static String getPrivateUploadToken()
	{
		Auth auth = Auth.create(Config.getAccessKey(), Config.getSecretKey());
		String upToken = auth.uploadToken(Config.getPrivateTablename());
		return upToken;
	}
	
	public static void main(String[] agrs) {
		System.out.println(getPrivateUploadToken());
	}
}
