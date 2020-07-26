package com.ptt.service;

import com.alibaba.fastjson.JSONObject;
import com.aliyuncs.CommonRequest;
import com.aliyuncs.CommonResponse;
import com.aliyuncs.DefaultAcsClient;
import com.aliyuncs.IAcsClient;
import com.aliyuncs.exceptions.ClientException;
import com.aliyuncs.exceptions.ServerException;
import com.aliyuncs.http.MethodType;
import com.aliyuncs.profile.DefaultProfile;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@Service
public class SendSmsImpl implements SendSms {

//    @Autowired
//    private RedisTemplate<String,String> redisTemplate;


    @Override
    public boolean send(String phoneNum, String templateCode, Map<String, Object> code) {
        //连接阿里云                                                                                LTAI4GCZATVrQw4yHpUaZuBP
        DefaultProfile profile = DefaultProfile.getProfile("cn-hangzhou", "LTAI4G7F1aoDRve8P9nGMapQ", "dsHYtvo0ZGp9e9bSH8eqWPszavj2J1");
        //DefaultProfile profile = DefaultProfile.getProfile(regionId, accessKeyId, secret);
        IAcsClient client = new DefaultAcsClient(profile);

        //构建请求
        CommonRequest request = new CommonRequest();

        request.setSysMethod(MethodType.POST);
        request.setSysDomain("dysmsapi.aliyuncs.com");//不要动
        request.setSysVersion("2017-05-25");//不要动

        request.setSysAction("SendSms");

        //自定义参数（手机号，验证码，签名，模板）
        request.putQueryParameter("PhoneNumbers", phoneNum);
        request.putQueryParameter("SignName", "ABC商城");
        request.putQueryParameter("TemplateCode", templateCode);
        //request.putQueryParameter("RegionId", "cn-hangzhou");

        //构建一个短信验证码
//        HashMap<String,Object> map = new HashMap<>();
//        map.put("code",2233);
        request.putQueryParameter("TemplateParam", JSONObject.toJSONString(code));

        try {
            CommonResponse response = client.getCommonResponse(request);
            System.out.println(response.getData());
            //成功返回
            return response.getHttpResponse().isSuccess();
        } catch (ServerException e) {
            e.printStackTrace();
        } catch (ClientException e) {
            e.printStackTrace();
        }
        //失败返回
        return false;
    }
}
