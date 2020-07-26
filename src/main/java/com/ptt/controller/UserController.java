package com.ptt.controller;

import com.ptt.pojo.User;
import com.ptt.service.SendSms;
import com.ptt.service.UserService;
import com.ptt.utils.*;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import javax.annotation.Resource;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.HashMap;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

@Controller
//@RequestMapping("/user")
public class UserController {

    @Resource
    private UserService userService;

    @Autowired
    private MailUtil mailUtil;

    @Autowired
    private SendSms sendSms;

    @Autowired
    private UpimageUtil upimageUtil;

    private static Logger logger = Logger.getLogger(UserController.class);

    @Autowired
    private RedisTemplate<String, String> redisTemplate;

    //首页
    @RequestMapping("/main")
    public String main() {
        return "main";
    }

    //跳转到登陆页面
    @RequestMapping("/tologin")
    public String toLogin() throws Exception {
        return "login";
    }

    //登录验证
    @RequestMapping(value = "/login", method = RequestMethod.POST)
    public String login(@RequestParam(value = "name", required = false) String name,
                        @RequestParam(value = "password", required = false) String password, Model model,
                        HttpServletResponse httpServletResponse, HttpSession session) throws Exception {


        logger.info("输入的名字========" + name);

        //判断数据库中是否有该用户名
        if (userService.logincheck(name) != null) {
            //查询用户
            User user = userService.logincheck(name);
            user.setName(name);
            //获取用户密码
            String psw = user.getPassword();
            logger.debug(psw + "=====");

            logger.info(name + "====接收名字=======");
            logger.info(password + "==========接收密码====");
            //获取用户盐值
            String salt = user.getSalt();
            logger.info(salt + "==========数据库盐值========");
            //对盐值和密码加密
            String saltpassword = salt + password;
            String md5Digest = MD5Util.GetMD5Code(saltpassword);

            //设置密码
            user.setPassword(md5Digest);
            logger.info(md5Digest + "========加密加盐的密码=====");

            //判断用户密码与加密后的密码是否一样
            if (psw.equals(md5Digest)) {
                model.addAttribute("name", name);

                //根据用户名获取id
                Integer id = user.getId();
                //使用系统当前时间和id生成唯一token，格式为键值对
                String token = id + "=" + System.currentTimeMillis();
                //使用DES加密
                String tokenDES = DESUtil.getEncryptString(token);
                logger.info("加密后的token：" + tokenDES);
                //保存到cookies中
                Cookie cookie = new Cookie("token", tokenDES);
                //设置cookie过期时间 单位为秒
                cookie.setMaxAge(7000);

                logger.info("========cookie==========");
                //返回cookie
                httpServletResponse.addCookie(cookie);

                logger.info("返回的cookie值=====" + cookie);

                model.addAttribute("user", name);

                logger.info("=======登录成功======");
                //重定向进入学员表
                return "redirect:/user/allBmb";

            } else {
                model.addAttribute("error", "账号或密码错误");
                logger.info("=======账号密码错误======");
                return "login";
            }
        } else {
            //如果输入空值或未登录就访问则提示该消息
            model.addAttribute("error", "用户名或密码不能为空");
            logger.info("没有登录或者输入为空====");
            return "login";
        }
    }


    //转向手机注册页面
    @RequestMapping("/toregister")
    public String toregister() {
        return "register";
    }

    @RequestMapping(value = "/send1", method = RequestMethod.POST)
    public void code1(@RequestParam(value = "phone", required = false) String phone) {

        //生成验证码并存储到reids中
        String code = UUID.randomUUID().toString().substring(0, 4);

        redisTemplate.opsForValue().set(phone, code, 5000, TimeUnit.SECONDS);//5分钟过期

        HashMap<String, Object> param = new HashMap<>();
        param.put("code", code);

        boolean isSend = sendSms.send(phone, "SMS_196651183", param);

    }


    //手机注册
    @RequestMapping("/register")
    public String register(@RequestParam("name") String name,
                           @RequestParam("password") String password, Model model,
                           @RequestParam("phone") String phone,
                           //@RequestParam("mail") String mail,
                           @RequestParam("sms") String sms) {

        String code = redisTemplate.opsForValue().get(phone);
        logger.info(code);

        if (sms.equals(code)) {

            if (password != null && password != "") {
                //获取指定长度的盐值
                String salt = StringUtil.getRandomString(10);
                String saltpassword = salt + password;


                //创建对象
                User user = new User();
                //设置名字
                user.setName(name);
                logger.info("===注册设置的名字为：" + name);
                //设置盐值
                user.setSalt(salt);
                logger.info("======注册设置的盐值为：" + salt);
                //MD5加密加盐
                String md5Digest = MD5Util.GetMD5Code(saltpassword);
                //设置密码
                user.setPassword(md5Digest);
                logger.info("注册记录的密码为：" + md5Digest);

                user.setPhone(phone);
                logger.info(phone);

                userService.register(user);


                //user.setMail(to);

                //登录
                logger.info("=======注册成功===返回登录页面===");
                return "login";
            } else {
                model.addAttribute("error", "密码不能为空");
                return "register";
            }
        } else {
            model.addAttribute("error", "验证码错误");
            return "register";
        }

    }

    //转向邮箱注册页面
    @RequestMapping("/tomailregister")
    public String toregister2() {
        return "mailregister";
    }

    @RequestMapping(value = "/send2", method = RequestMethod.POST)
    public String code(@RequestParam(value = "address", required = false) String address) {

        logger.info("a");


        try {
            boolean isSend = mailUtil.send(address);
        } catch (Throwable throwable) {

            throwable.printStackTrace();
        }

        logger.info("aa");
        return null;
    }

    //注册
    @RequestMapping("/mailregister")
    public String register2(@RequestParam("name") String name,
                            @RequestParam("password") String password, Model model,
                            //@RequestParam("phone") String phone,
                            @RequestParam("address") String address,
                            @RequestParam("sms") String sms) {


        String code = redisTemplate.opsForValue().get(address);

        if (sms.equals(code)) {

            if (password != null && password != "") {
                //获取指定长度的盐值
                String salt = StringUtil.getRandomString(10);
                String saltpassword = salt + password;


                //创建对象
                User user = new User();
                //设置名字
                user.setName(name);
                logger.info("===注册设置的名字为：" + name);
                //设置盐值
                user.setSalt(salt);
                logger.info("======注册设置的盐值为：" + salt);
                //MD5加密加盐
                String md5Digest = MD5Util.GetMD5Code(saltpassword);
                //设置密码
                user.setPassword(md5Digest);
                logger.info("注册记录的密码为：" + md5Digest);

                //user.setPhone(phone);
                user.setMail(address);
                logger.info(address);

                userService.register(user);


                //登录
                logger.info("=======注册成功===返回登录页面===");
                return "login";
            } else {
                model.addAttribute("error", "密码不能为空");
                return "mailregister";
            }
        } else {
            model.addAttribute("error", "验证码错误");
            return "mailregister";
        }

    }

    @RequestMapping(value = "/upimage", method = RequestMethod.POST)
    public String image(MultipartFile file, HttpSession session, HttpServletRequest httpServletRequest) {

        String fileName = file.getOriginalFilename();
        logger.info(fileName);

        String picture = session.getServletContext().getRealPath("/upimage") + File.separator + fileName;
        logger.info("图片路径=====" + picture);

        String url = null;
        try {
            url = upimageUtil.upload(fileName, file);
            logger.info("图片上传成功======");
        } catch (FileNotFoundException e) {

            logger.info("图片上传失败，打印堆栈信息" + e);
        }

        //获取cookies
        Cookie[] cookies = httpServletRequest.getCookies();

        if (cookies != null) {
            //System.out.println("开始遍历");
            //System.out.println(cookies);
            // 遍历
            for (Cookie cookie : cookies) {
                if (cookie.getName().equals("token")) {
                    String tokenDES = cookie.getValue();
                    String token = DESUtil.getDecryptString(tokenDES);//解密
                    logger.info("=====token的解密value:" + token);

                    // 分割字符串 获取id
                   Integer id = Integer.valueOf(token.split("=")[0]);

                   User user = new User();

                   user.setId(id);
                   user.setPicture(url);


                  int a = userService.updatebyId(user);
                  if(a!=0){
                      logger.info("成功");
                  }else {
                      logger.info("失败");
                  }

                }
            }

        }
        return "redirect:/user/allBmb";
    }


    //注销
    @RequestMapping("/logout")
    public String logout(HttpServletResponse httpServletResponse,
                         HttpServletRequest httpServletRequest) {
        //获取cookies
        Cookie[] cookies = httpServletRequest.getCookies();

        if (cookies != null) {
            //System.out.println("开始遍历");
            //System.out.println(cookies);
            // 遍历
            for (Cookie cookie : cookies) {
                if (cookie.getName().equals("token")) {
                    System.out.println(cookie);
                    //设置时间为0秒
                    cookie.setMaxAge(0);

                    //重置cookie
                    httpServletResponse.addCookie(cookie);
                    logger.info("注销重置后的cookie为："+cookie);
                    //回到首页
                    return "login";
                }
            }
        }return "login";
    }

    //注销
//    @RequestMapping("/img")
//    public String image(HttpServletResponse httpServletResponse,
//                         HttpServletRequest httpServletRequest){
//        ImagerFilter imagerFilter = null;
//        imagerFilter.doFilter(httpServletResponse,httpServletRequest,);
//
//    }

}