package com.xiben.pm.ar.service.impl;

import com.xiben.pm.utils.PushUtils;

import org.springframework.stereotype.Service;

import com.xiben.pm.ar.service.PushService;
@Service
public class PushServiceimpl implements PushService{
    @Override
    public boolean pushWithTag(String coentnt,String usertag) {

        return PushUtils.pushWithTag(coentnt,usertag);
    }

    @Override
    public boolean pushAll(String content) {
        return PushUtils.pushAll(content);
    }
}
