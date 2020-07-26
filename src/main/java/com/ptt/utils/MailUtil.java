package com.ptt.utils;

import com.sendcloud.sdk.builder.SendCloudBuilder;
import com.sendcloud.sdk.core.SendCloud;
import com.sendcloud.sdk.model.MailAddressReceiver;
import com.sendcloud.sdk.model.MailBody;
import com.sendcloud.sdk.model.SendCloudMail;
import com.sendcloud.sdk.model.TextContent;
import com.sendcloud.sdk.util.ResponseData;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.TimeUnit;


public class MailUtil {

    @Autowired
    RedisTemplate redisTemplate;


    public boolean send(String address) throws Throwable {

        MailAddressReceiver receiver = new MailAddressReceiver();
        receiver.addTo(address);

        MailBody body = new MailBody();
        // 设置 From
        body.setFrom("danjiayitask7@jnshu.com");
        // 设置 FromName
        body.setFromName("task7mail");
        // 设置 ReplyTo
        body.setReplyTo("danjiayitask7@jnshu.com");
        // 设置标题
        body.setSubject("验证码");

//        //生成随机验证码
        String code = UUID.randomUUID().toString().substring(0, 4);

        TextContent content = new TextContent();
        content.setContent_type(TextContent.ScContentType.html);
        content.setText("<html><p>您的验证码为："+ code +"。请不要泄露出去</p></html>");

        redisTemplate.opsForValue().set(address,code,5000, TimeUnit.SECONDS);//5分钟过期

        SendCloudMail mail = new SendCloudMail();
        mail.setTo(receiver);
        mail.setBody(body);
        mail.setContent(content);

        SendCloud sc = SendCloudBuilder.build();

        ResponseData res = sc.sendMail(mail);

        System.out.println(res.getResult());
        System.out.println(res.getStatusCode());
        System.out.println(res.getMessage());
        System.out.println(res.getInfo());
        return res.getResult();
    }

}
