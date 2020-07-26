package com.ptt.utils;

import com.aliyun.oss.OSS;
import com.aliyun.oss.OSSClientBuilder;
import com.aliyun.oss.model.*;
import org.apache.log4j.Logger;
import org.junit.Test;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;

public class UpimageUtil {

    Logger logger = Logger.getLogger(Test.class);

    // Endpoint以杭州为例，其它Region请按实际情况填写。
    String endpoint;
    // 阿里云主账号AccessKey拥有所有API的访问权限，风险很高。强烈建议您创建并使用RAM账号进行API访问或日常运维，请登录 https://ram.console.aliyun.com 创建RAM账号。
    String accessKeyId;
    String accessKeySecret;


    public UpimageUtil(String endpoint, String accessKeyId, String accessKeySecret) {
        this.endpoint = endpoint;
        this.accessKeyId = accessKeyId;
        this.accessKeySecret = accessKeySecret;
    }

    String bucketName = "2020720";

    public String upload(String filename, MultipartFile file) throws FileNotFoundException {

// <yourObjectName>上传文件到OSS时需要指定包含文件后缀在内的完整路径，例如abc/efg/123.jpg。
        //file = "D:\\IDEA_Projects\\JAVA\\xzy-task7\\src\\main\\webapp\\WEB-INF\\image\\boy2.jpg";

        logger.info("转换文件格式");
        File file1 = transferToFile(file);
        logger.info("输出文件名" + filename);

        // 创建OSSClient实例。
        OSS ossClient = new OSSClientBuilder().build(endpoint, accessKeyId, accessKeySecret);

        //InputStream inputStream = new FileInputStream(file);

        // 创建PutObjectRequest对象。
        PutObjectRequest putObjectRequest = new PutObjectRequest(bucketName, filename, file1);

        ObjectMetadata objectMetadata = new ObjectMetadata();
        objectMetadata.setContentType("image/jpg");

        putObjectRequest.setMetadata(objectMetadata);
        // 上传文件到指定的存储空间（bucketName）并将其保存为指定的文件名称（objectName）。

        ossClient.putObject(putObjectRequest);

        //存储到阿里云的url
        String url = bucketName + "." + endpoint + "/" + filename;
        logger.info("图片上传存储的url：" + url);

        //删除临时文件
        File del = new File(file1.toURI());
        // 关闭OSSClient。
        if (del.delete()) {
            logger.info("删除成功");
        } else {
            logger.info("删除失败");
        }

        ossClient.shutdown();

        return url;
    }



    //上传本地流
    public String upload(String file) throws FileNotFoundException {
//获取文件名
        file.trim();
        String fileName = file.substring(file.lastIndexOf("\\")+1);

        logger.info("文件名为：" + fileName);

        // 创建OSSClient实例。
        OSS ossClient = new OSSClientBuilder().build(endpoint, accessKeyId, accessKeySecret);
        //上传文件流
        InputStream inputStream = new FileInputStream(file);
        //设置上传文件格式
        ObjectMetadata objectMetadata = new ObjectMetadata();
        objectMetadata.setContentType("image/jpg");
        ossClient.putObject(bucketName,fileName,inputStream,objectMetadata);
        //图片url
        String url = bucketName + "." + endpoint + "/" + fileName;
        // 关闭OSSClient。
        ossClient.shutdown();
        return url;
    }

//    private File transferToFile(MultipartFile multipartFile) {
////        选择用缓冲区来实现这个转换即使用java 创建的临时文件 使用 MultipartFile.transferto()方法 。
//        File file = null;
//        try {
//            String originalFilename = multipartFile.getOriginalFilename();
//            String[] filename = originalFilename.split(".");
//            file=File.createTempFile(filename[0], filename[1]);
//            multipartFile.transferTo(file);
//            file.deleteOnExit();
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//        return file;
//    }

    private File transferToFile(MultipartFile multipartFile){
//文件上传前的名称
        String name = multipartFile.getOriginalFilename();
        File file = new File(name);
        OutputStream out = null;
        try{
//获取文件流，以文件流的方式输出到新文件
            //InputStream in = multipartFile.getInputStream();
            out = new FileOutputStream(file);
            byte[] ss = multipartFile.getBytes();
            for(int i = 0; i < ss.length; i++){
                out.write(ss[i]);
            }
        }catch(IOException e){
            e.printStackTrace();
        }finally {
            if (out != null){
                try {
                    out.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return file;
    }


}
