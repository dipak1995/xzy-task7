package com.ptt.utils;


import org.apache.log4j.Logger;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.annotation.Resource;
import java.io.File;
import java.io.FileNotFoundException;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations={"classpath:applicationContext.xml"})
public class TESTTTTT {

    @Autowired
    MailUtil mailUtil;

    @Autowired
    UpimageUtil upimageUtil;

    Logger logger = Logger.getLogger(TESTTTTT.class);
    @Test
    public void aa() throws Throwable {

        logger.info("一串中文"+mailUtil);
        //mailUtil.send("439900544@qq.com");

    }


}