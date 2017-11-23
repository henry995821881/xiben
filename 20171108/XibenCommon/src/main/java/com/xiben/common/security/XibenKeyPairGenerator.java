package com.xiben.common.security;

import java.security.KeyPair;
import java.security.KeyPairGenerator;

import com.xiben.common.codec.XibenBase64;



public class XibenKeyPairGenerator {
	
    private KeyPair keyPair;

    public XibenKeyPairGenerator() throws Exception {
        keyPair = initKey();
    }
    
    private KeyPair initKey() throws Exception {
        // 初始化密钥对生成器
        KeyPairGenerator keyPairGen = KeyPairGenerator.getInstance(SecurityConstants.KEY_ALGORITHM);
        // 实例化密钥对生成器
        keyPairGen.initialize(SecurityConstants.KEY_SIZE);
        // 实例化密钥对
        return keyPairGen.genKeyPair();
    }
    
    protected byte[] getPublicKey()
    {
    	return keyPair.getPublic().getEncoded();
    }
    
    protected byte[] getPrivateKey() {
        return keyPair.getPrivate().getEncoded();
    }
    
    public String getPublicKeyWithString()
    {
    	 return XibenBase64.encode(getPublicKey());
    }
    
    public String getPrivateKeyWithString() {
    	return XibenBase64.encode(getPrivateKey());
    }
}
