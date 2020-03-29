package com.audioapp.cms.task;


import cn.hutool.core.date.DateUtil;
import com.audioapp.cms.constant.MessageConstant;
import com.audioapp.cms.dto.*;
import com.audioapp.cms.mapper.AppUserDTOMapper;
import com.audioapp.cms.service.MessageService;
import com.audioapp.cms.service.RedisService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.net.InetAddress;
import java.util.*;

@Component
@Configuration      //1.主要用于标记配置类，兼备Component的效果。
@EnableScheduling   // 2.开启定时任务
public class MessagePushTimeTask {

    @Autowired
    private MessageService messageService;

    @Autowired
    private RedisService redisService;


    //3.添加定时任务
    @Scheduled(cron = "30 0/3 * * * ?")
    private void configureTasks() {
        System.err.println("消息PUSH任务开始: " + DateUtil.now());
        if(redisService.set("sendMessageTimeTask", "locked", 100L)) {
            System.err.println("MessagePushTimeTask thread id: "+Thread.currentThread().getId());

            messageService.pushMessage();

            InetAddress address = null;
            try {
                address = InetAddress.getLocalHost();
            }catch (Exception e){
                e.printStackTrace();;
            }
            redisService.remove("sendMessageTimeTask");
            System.err.println("MessagePushTimeTask host ip: " + address.getHostAddress());
        }
        System.err.println("消息PUSH任务结束: " + DateUtil.now());
    }
}
