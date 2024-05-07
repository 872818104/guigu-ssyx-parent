package com.crh.ssyx.product.service.impl;

import com.aliyun.oss.ClientException;
import com.aliyun.oss.OSS;
import com.aliyun.oss.OSSClientBuilder;
import com.aliyun.oss.OSSException;
import com.aliyun.oss.model.PutObjectRequest;
import com.aliyun.oss.model.PutObjectResult;
import com.crh.ssyx.product.service.FileUploadService;
import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.InputStream;
import java.util.UUID;

/**
 * @program: guigu-ssyx-parent
 * @description:
 * @author: caoruhao
 * @create: 2023-11-12 18:01
 **/
@Service
public class FileUploadServiceImpl implements FileUploadService {


    @Value("${aliyun.endpoint}")
    private String endPoint;
    @Value("${aliyun.keyid}")
    private String accessKey;
    @Value("${aliyun.keysecret}")
    private String secretKey;
    @Value("${aliyun.bucketname}")
    private String bucketName;

    @Override
    public String fileUpload(MultipartFile file) throws Exception {

        // 创建OSSClient实例。
        OSS ossClient = new OSSClientBuilder().build(endPoint, accessKey, secretKey);
        try {
            // 上传文件流。
            InputStream inputStream = file.getInputStream();
            //获取文件实际名称
            String objectName = file.getOriginalFilename();
            String uuid = UUID.randomUUID().toString().replace("-", "");
            objectName = uuid + objectName;
            //根据上传文件进行分组
            String currentDateTime = new DateTime().toString("YYYY/mm/dd");
            objectName = currentDateTime + "/" + objectName;

            //创建PutObjectRequest对象。
            //第一个参数bucketName：bucket名称
            //第一个参数objectName：文件路径 + 名称
            PutObjectRequest putObjectRequest = new PutObjectRequest(bucketName, objectName, inputStream);
            putObjectRequest.setProcess("true");
            //创建PutObject请求。
            PutObjectResult result = ossClient.putObject(putObjectRequest);
            System.out.println(result.getResponse().getStatusCode());
            System.out.println(result.getResponse().getErrorResponseAsString());
            System.out.println(result.getResponse().getUri());
            //返回上传图片在阿里云路径
            return result.getResponse().getUri();
        } catch (OSSException oe) {
            System.out.println("Caught an OSSException, which means your request made it to OSS, "
                    + "but was rejected with an error response for some reason.");
            System.out.println("Error Message:" + oe.getErrorMessage());
            System.out.println("Error Code:" + oe.getErrorCode());
            System.out.println("Request ID:" + oe.getRequestId());
            System.out.println("Host ID:" + oe.getHostId());
        } catch (ClientException ce) {
            System.out.println("Error Message:" + ce.getMessage());
        } finally {
            if (ossClient != null) {
                ossClient.shutdown();
            }
        }
        return null;
    }
}
