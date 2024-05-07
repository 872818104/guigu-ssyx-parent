package com.crh.ssyx.product.controller;

import com.crh.ssyx.common.result.Result;
import com.crh.ssyx.product.service.FileUploadService;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

/**
 * @program: guigu-ssyx-parent
 * @description: 文件上传
 * @author: caoruhao
 * @create: 2023-11-12 17:40
 **/
@Api(tags = "文件上传接口")
@RestController
@RequestMapping("admin/product")
public class FileUploadController {
    @Autowired
    private FileUploadService fileUploadService;

    //文件上传
    @PostMapping("fileUpload")
    public Result fileUpload(MultipartFile file) throws Exception{
        return Result.ok(fileUploadService.fileUpload(file));
    }
}
