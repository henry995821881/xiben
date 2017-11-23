package com.xiben.pm.ar.service;



public interface PushService {
    boolean pushWithTag(String content,String usertag);
    boolean pushAll(String content);
}
