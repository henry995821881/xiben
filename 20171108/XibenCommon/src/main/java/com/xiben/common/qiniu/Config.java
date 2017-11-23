package com.xiben.common.qiniu;

import java.io.IOException;
import java.util.Properties;

public class Config {

	public static Properties prop = null;
	static
	{
		prop = new Properties();
		try {
			
			prop.load(Config.class.getClassLoader().getResourceAsStream("qiniu.properties"));
		} catch (IOException e) {
			throw new RuntimeException("config read error!",e);
		}
	}
	
	private static String getValue(String key)
	{
		String value = prop.getProperty(key);
		return value;
	}
	
	public static String getAccessKey()
	{
		return getValue("ACCESS_KEY");
	}
	public static String getSecretKey()
	{
		return getValue("SECRET_KEY");
	}
	public  static String getPrivateTablename()
	{
		return getValue("RsTableName");
	}
	
	public static String getPublicTablename()
	{
		return getValue("RsTableLogo");
	}
	
	public static String getPublicDomain() {
		String tableName = getPublicTablename();
		return  getValue("tb_"+tableName);
	}
	public static String getPrivateDomain() {
		String tableName = getPrivateTablename();
		return  getValue("tb_"+tableName);
	}
}
