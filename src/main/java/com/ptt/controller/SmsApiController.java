package com.ptt.controller;

import com.aliyuncs.utils.StringUtils;
import com.ptt.service.SendSms;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

//@RestController
//@Controller
////@CrossOrigin//跨域支持
//public class SmsApiController {
//
//    @Autowired
//    private SendSms sendSms;
//
//    @Autowired
//    private RedisTemplate<String,String> redisTemplate;
//
//    @RequestMapping(value = "/send1",method = RequestMethod.POST)
//    public String code(@RequestParam(value = "phone",required = false)String phone, Model model){
//        //调用发送方法 （模拟真实业务 redis）
//        //String code = redisTemplate.opsForValue().get(phone);
//        //判断验证码是否过期，如果不为空 ！ 直接返回
////        if (!StringUtils.isEmpty(code)){
//            //return phone + ":" + code + "已存在，还没有过期";
//
////        }
//        //生成验证码并存储到reids中
//        String code = UUID.randomUUID().toString().substring(0, 4);
//
//        HashMap<String,Object> param = new HashMap<>();
//        param.put("code",code);
//
//        boolean isSend = sendSms.send(phone,"SMS_196651183",param);
//
//        if (isSend){
//            redisTemplate.opsForValue().set(phone,code,5000, TimeUnit.SECONDS);//5分钟过期
//            //return phone + ":" + code + "发送成功！";
//
//        }else {
//            model.addAttribute("error","请检查电话号码");
//            return null;
//            //return "发送失败";
//        }
//        return null;
//    }
//}

























