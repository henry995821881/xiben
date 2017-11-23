package com.xiben.common.security;

import com.xiben.common.codec.XibenBase64;

public class XibenKeyPairReader {
	public static byte[] getPublicKey(String publicKey)
	{
		return XibenBase64.decode(publicKey);
	}
	public static byte[] getPrivateKey(String privateKey)
	{
		return XibenBase64.decode(privateKey);
	}
}
