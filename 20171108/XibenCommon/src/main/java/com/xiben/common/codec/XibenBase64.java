package com.xiben.common.codec;

import org.apache.commons.codec.binary.Base64;

public class XibenBase64 {
	public static byte[] decode(String content)
	{
		return Base64.decodeBase64(content);
	}
	
	public static String encode(byte[] data)
	{
		return Base64.encodeBase64URLSafeString(data);
	}
}