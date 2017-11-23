package com.xiben.sso.client.utils;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;

public class HttpPostUtils {
	public static String doPost(String uri,String content)  
    {  
        String result = "";  
        HttpPost httpRequst = new HttpPost(uri); 
        try {  
        	httpRequst.addHeader("Content-Type", "application/json");
        	StringEntity entity = new StringEntity(content,"utf-8");//解决中文乱码问题    
        	httpRequst.setEntity(entity);
  
            HttpResponse httpResponse = new DefaultHttpClient().execute(httpRequst);  
            
            if(httpResponse.getStatusLine().getStatusCode() == 200)  
            {  
                HttpEntity httpEntity = httpResponse.getEntity();  
                result = EntityUtils.toString(httpEntity); 
            }else
            {
            	System.out.println(httpResponse.getStatusLine().getStatusCode());
            }
        }
        catch (Exception e) {  
            // TODO Auto-generated catch block  
            e.printStackTrace();  
            result = e.getMessage().toString();  
        }  
        return result;  
    }  
}
