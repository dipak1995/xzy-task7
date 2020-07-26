package com.ptt.utils;

import com.aliyun.oss.OSS;
import com.aliyun.oss.OSSClientBuilder;
import com.aliyun.oss.model.*;
import org.apache.log4j.Logger;
import org.junit.Test;

import java.io.*;
import java.security.Key;


public class Test1 {

    Logger logger = Logger.getLogger(Test.class);

    // Endpoint以杭州为例，其它Region请按实际情况填写。
    String endpoint = "http://oss-cn-beijing.aliyuncs.com";
    // 阿里云主账号AccessKey拥有所有API的访问权限，风险很高。强烈建议您创建并使用RAM账号进行API访问或日常运维，请登录 https://ram.console.aliyun.com 创建RAM账号。
    String accessKeyId = "LTAI4G7F1aoDRve8P9nGMapQ";
    String accessKeySecret = "dsHYtvo0ZGp9e9bSH8eqWPszavj2J1";
    String bucketName = "2020720";

    @Test
    public void upload() throws FileNotFoundException {

// <yourObjectName>上传文件到OSS时需要指定包含文件后缀在内的完整路径，例如abc/efg/123.jpg。
        String file = "D:\\IDEA_Projects\\JAVA\\xzy-task7\\src\\main\\webapp\\WEB-INF\\image\\boy2.jpg";

        // 创建OSSClient实例。
        OSS ossClient = new OSSClientBuilder().build(endpoint, accessKeyId, accessKeySecret);

        InputStream inputStream = new FileInputStream(file);

        ObjectMetadata objectMetadata = new ObjectMetadata();
        objectMetadata.setContentType("image/jpg");
        // 上传文件到指定的存储空间（bucketName）并将其保存为指定的文件名称（objectName）。

        ossClient.putObject(bucketName,"a.jpg",inputStream,objectMetadata);

        // 关闭OSSClient。
        ossClient.shutdown();
    }

    @Test
    public void delete() {
// <yourObjectName>表示删除OSS文件时需要指定包含文件后缀在内的完整路径，例如abc/efg/123.jpg。
        //String objectName = "${pageContext.request.contextPath}/a.jpg";
        String objectName = "https://2020720.oss-cn-beijing.aliyuncs.com/a.jpg";

        // 创建OSSClient实例。
        OSS ossClient = new OSSClientBuilder().build(endpoint, accessKeyId, accessKeySecret);

        // 删除文件。
        ossClient.deleteObject(bucketName, objectName);

        // 关闭OSSClient。
        ossClient.shutdown();
    }

    @Test
    public void download() throws IOException {
// <yourObjectName>从OSS下载文件时需要指定包含文件后缀在内的完整路径，例如abc/efg/123.jpg。
        //String objectName = "a.jpg";
        String objectName = "https://2020720.oss-cn-beijing.aliyuncs.com/a.jpg";

        // 创建OSSClient实例。
        OSS ossClient = new OSSClientBuilder().build(endpoint, accessKeyId, accessKeySecret);

        logger.info(ossClient);
        // 调用ossClient.getObject返回一个OSSObject实例，该实例包含文件内容及文件元信息。
        //OSSObject ossObject = ossClient.getObject(bucketName, objectName);
        ossClient.getObject(new GetObjectRequest(bucketName, objectName),new File("D:\\IDEA_Projects\\JAVA\\xzy-task7\\src\\main\\webapp\\WEB-INF\\image"));
        // 调用ossObject.getObjectContent获取文件输入流，可读取此输入流获取其内容。
//        InputStream content = ossObject.getObjectContent();
//        if (content != null) {
//            BufferedReader reader = new BufferedReader(new InputStreamReader(content));
//            while (true) {
//                String line = reader.readLine();
//                if (line == null) break;
//                System.out.println("\n" + line);
//            }
// 数据读取完成后，获取的流必须关闭，否则会造成连接泄漏，导致请求无连接可用，程序无法正常工作。
//            content.close();
//        }

// 关闭OSSClient。
        ossClient.shutdown();
    }

    @Test
    public void select(){
// 创建OSSClient实例。
        OSS ossClient = new OSSClientBuilder().build(endpoint, accessKeyId, accessKeySecret);

        // ossClient.listObjects返回ObjectListing实例，包含此次listObject请求的返回结果。
        ObjectListing objectListing = ossClient.listObjects(bucketName);
        // objectListing.getObjectSummaries获取所有文件的描述信息。
        for (OSSObjectSummary objectSummary : objectListing.getObjectSummaries()) {
            System.out.println(" - " + objectSummary.getKey() + "  " +
                    "(size = " + objectSummary.getSize() + ")");
        }

// 关闭OSSClient。
        ossClient.shutdown();
    }

}


