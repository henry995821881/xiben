package com.xiben.common.qiniu;

import java.net.URLEncoder;

import com.qiniu.util.Auth;

public class DownLoadTools {
	public static String getPublicFileDownloadUrl(String filekey)
	{
		try
		{
			String encodedFileName = URLEncoder.encode(filekey, "utf-8");
			String finalUrl = String.format("%s/%s", Config.getPublicDomain(), encodedFileName);
		return finalUrl;
		}catch(Exception e)
		{
			return "";
		}
	}
	
	public static String getPrivateFileDownloadUrl(String filekey)
	{
		try
		{
			String encodedFileName = URLEncoder.encode(filekey, "utf-8");
			String publicUrl = String.format("%s/%s", Config.getPrivateDomain(), encodedFileName);
			Auth auth = Auth.create(Config.getAccessKey(), Config.getAccessKey());
			long expireInSeconds = 3600;//1小时，可以自定义链接过期时间
			String finalUrl = auth.privateDownloadUrl(publicUrl, expireInSeconds);
			return finalUrl;
		}catch(Exception e)
		{
			return "";
		}
	}
	
	public static void main(String[] args)
	{
		String url = getPublicFileDownloadUrl("f7ed559f93f14c5682b187fa680a34f3");
		System.out.println(url);
	}
}
