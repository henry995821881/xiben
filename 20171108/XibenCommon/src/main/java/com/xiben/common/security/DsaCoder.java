package com.xiben.common.security;

import java.security.KeyFactory;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.Signature;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import com.xiben.common.codec.XibenBase64;

public class DsaCoder{

    public DsaCoder(){

    }

    private byte[] signature(byte[] data, byte[] privateKey) throws Exception {
        PKCS8EncodedKeySpec keySpec = new PKCS8EncodedKeySpec(privateKey);
        KeyFactory keyFactory = KeyFactory.getInstance(SecurityConstants.KEY_ALGORITHM);
        PrivateKey key =keyFactory.generatePrivate(keySpec);

        Signature signature = Signature.getInstance(SecurityConstants.DsaTypeEn.SHA1withDSA.name());
        signature.initSign(key);
        signature.update(data);
        return signature.sign();
    }

    private boolean verify(byte[] data, byte[] publicKey, byte[] sign) throws Exception {
        X509EncodedKeySpec keySpec = new X509EncodedKeySpec(publicKey);
        KeyFactory keyFactory = KeyFactory.getInstance(SecurityConstants.KEY_ALGORITHM);
        PublicKey key =keyFactory.generatePublic(keySpec);

        Signature signature = Signature.getInstance(SecurityConstants.DsaTypeEn.SHA1withDSA.name());
        signature.initVerify(key);
        signature.update(data);
        return signature.verify(sign);
    }

    public String sign(String msg,String privateKey) throws Exception
    {
        byte[] sign = signature(msg.getBytes(), XibenKeyPairReader.getPrivateKey(privateKey));
        return XibenBase64.encode(sign);
    }

    public boolean verify(String msg,String sign,String publicKey)
    {
    	try
    	{
	        boolean flag = verify(msg.getBytes(), XibenKeyPairReader.getPublicKey(publicKey), XibenBase64.decode(sign));
	        return flag;
    	}catch(Exception e)
    	{
    		
    	}
    	return false;
    }
    
    public static void main(String[] args) throws Exception {
    	
//      XibenKeyPairGenerator keypairGenerator = new XibenKeyPairGenerator();
//      String publicKey = keypairGenerator.getPublicKeyWithString();
//      String privateKey = keypairGenerator.getPrivateKeyWithString();
//      System.out.println("publicKey="+publicKey);
//      System.out.println("privateKey="+privateKey);
    	
    	String msg = "dd54714f271e4adfbef76bb0b389aa5a";
    	DsaCoder dsa = new DsaCoder();
    	String sign = dsa.sign(msg, "MIIBSwIBADCCASwGByqGSM44BAEwggEfAoGBAP1_U4EddRIpUt9KnC7s5Of2EbdSPO9EAMMeP4C2USZpRV1AIlH7WT2NWPq_xfW6MPbLm1Vs14E7gB00b_JmYLdrmVClpJ-f6AR7ECLCT7up1_63xhv4O1fnxqimFQ8E-4P208UewwI1VBNaFpEy9nXzrith1yrv8iIDGZ3RSAHHAhUAl2BQjxUjC8yykrmCouuEC_BYHPUCgYEA9-GghdabPd7LvKtcNrhXuXmUr7v6OuqC-VdMCz0HgmdRWVeOutRZT-ZxBxCBgLRJFnEj6EwoFhO3zwkyjMim4TwWeotUfI0o4KOuHiuzpnWRbqN_C_ohNWLx-2J6ASQ7zKTxvqhRkImog9_hWuWfBpKLZl6Ae1UlZAFMO_7PSSoEFgIUQte3V2MF2KfLnd8ReCng8dwUTa8");
    	System.out.println(sign);
    	
//        String msg = "Hello World";
//        DsaCoder dsa = new DsaCoder();
//        XibenKeyPairGenerator keypairGenerator = new XibenKeyPairGenerator();
//        String publicKey = keypairGenerator.getPublicKeyWithString();
//        String privateKey = keypairGenerator.getPrivateKeyWithString();
//        
//        System.out.println("publicKey="+publicKey);
//        System.out.println("privateKey="+privateKey);
//        
//        String sign = dsa.sign(msg, privateKey);
//        
//        System.out.println("sign="+sign);
//        
//        boolean flag = dsa.verify("Hello World", sign, publicKey);
//        System.out.println("flag="+flag);
    }
}
