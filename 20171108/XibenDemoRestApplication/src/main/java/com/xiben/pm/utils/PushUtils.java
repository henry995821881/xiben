package com.xiben.pm.utils;

import cn.jiguang.common.ClientConfig;
import cn.jiguang.common.resp.APIConnectionException;
import cn.jiguang.common.resp.APIRequestException;
import cn.jpush.api.JPushClient;
import cn.jpush.api.push.PushResult;
import cn.jpush.api.push.model.Platform;
import cn.jpush.api.push.model.PushPayload;
import cn.jpush.api.push.model.audience.Audience;
import cn.jpush.api.push.model.notification.Notification;

public class PushUtils {


    public static final String MASTER_SECRET = "aa48aed84a44fa5486793920";
    public static final String APP_KEY = "8331da82162fde50da5348ea";
    private  static  JPushClient jpushClient;
    static {
        jpushClient= new JPushClient(MASTER_SECRET, APP_KEY, null, ClientConfig.getInstance());
    }



    public static PushPayload buildPushObject_ios_audienceMore_messageWithExtras(String content,String tag) {
        return PushPayload.newBuilder()
                .setPlatform(Platform.all())
                .setAudience(Audience.tag(tag))
                .setNotification(Notification.alert(content))
                .build();
    }
    private static PushPayload buildPushObject_all_all_alert(String content) {
        return PushPayload.alertAll(content);
    }
    public static boolean pushWithTag(String content,String tag){
        PushPayload payload = buildPushObject_ios_audienceMore_messageWithExtras(content,tag);
        return push(payload);
    }
    public static boolean pushAll(String content){
        PushPayload payload = buildPushObject_all_all_alert(content);
        return push(payload);
    }
    private static boolean push(PushPayload pushPayload){
        try {
            PushResult result = jpushClient.sendPush(pushPayload);
            if (result == null){
                return false;
            }
            if (result.getResponseCode() == 0){
                return true;
            }
            System.out.println("Got result - " + result);
        } catch (APIConnectionException e) {
            // Connection error, should retry later
            System.out.println("Connection error, should retry later:"+ e);

        } catch (APIRequestException e) {
            // Should review the error, and fix the request
            System.out.println("Should review the error, and fix the request:"+ e);
            System.out.println("HTTP Status: " + e.getStatus());
            System.out.println("Error Code: " + e.getErrorCode());
            System.out.println("Error Message: " + e.getErrorMessage());
        }

        return false;
    }

}
