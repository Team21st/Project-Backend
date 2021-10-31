package com.CS353.cs353project.controller;

import cn.hutool.core.codec.Base64;
import cn.hutool.core.io.FileUtil;
import com.CS353.cs353project.anotation.PassToken;
import com.CS353.cs353project.param.model.Oss.AliyunOssResultModel;
import com.CS353.cs353project.utils.AliyunOSSUtil;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.InputStream;
import java.util.List;

/**
 * 阿里云OSS测试接口
 *
 * @author jason
 */
@RestController
@RequestMapping("/oss")
public class OssTestController {

    /**
     * 测试-上传
     * -自定义文件
     * -自定义OSS存储路径
     * http://localhost:8081/oss/uploadFile
     */
    @GetMapping("/uploadFile")
    public AliyunOssResultModel uploadFile() {//上传本项目下的png
        return AliyunOSSUtil.uploadFile(new File("src/main/resources/xxx.png"), "png/重命名.png");
    }

    /**
     * 上传文件-指定目录
     */
    @PassToken
    @PostMapping("/uploadTest")
    public AliyunOssResultModel uploadTest(@RequestParam("file") MultipartFile file) {
        return AliyunOSSUtil.uploadFile(file, "oss/" + file.getOriginalFilename());
    }

    /**
     * 删除文件
     * http://localhost:8081/oss/deleteFile?fileName=TEST/png/重命名.png
     */
    @GetMapping("/deleteFile")
    public boolean deleteFile(@RequestParam String fileName) {
        return AliyunOSSUtil.deleteFile(fileName);
    }

    /**
     * oss路径下获取文件带有效期的url，获取的url可下载
     * http://localhost:8081/oss/getOssUrl?fileName=TEST/png/重命名.png
     */
    @GetMapping("/getOssUrl")
    public String getOssUrl(@RequestParam String fileName) {
        return AliyunOSSUtil.getOssUrlExpire(fileName);
    }

    /**
     * 预览PDF或图片
     * http://localhost:8081/oss/preview?fileName=TEST/png/重命名.png
     */
    @GetMapping("/preview")
    public ResponseEntity<InputStreamResource> previewFile(@RequestParam String fileName) {
        // 获取文件类型
        String contentType = FileUtil.extName(fileName);
        // 获取文件流
        InputStream inputStream = AliyunOSSUtil.getInputStream(fileName);
        // 预览
        HttpHeaders httpHeaders = new HttpHeaders();
        if ("pdf".equalsIgnoreCase(contentType)) {
            httpHeaders.add("Content-Type", MediaType.APPLICATION_PDF_VALUE);
        } else if ("jpg".equalsIgnoreCase(contentType) || "png".equalsIgnoreCase(contentType) || "jpeg".equalsIgnoreCase(contentType)) {
            httpHeaders.add("Content-Type", MediaType.IMAGE_JPEG_VALUE);
        }
        InputStreamResource inputStreamResource = new InputStreamResource(inputStream);
        return new ResponseEntity<>(inputStreamResource, httpHeaders, HttpStatus.OK);
    }

    /**
     * 列举所有的文件url
     * http://localhost:8081/oss/urlList
     */
    @GetMapping("/urlList")
    public List<String> urlList() {
        return AliyunOSSUtil.urlList();
    }

    /**
     * 图片转base64
     * http://localhost:8081/oss/base64
     */
    @GetMapping("/base64")
    public String base64() {
        return Base64.encode(AliyunOSSUtil.getInputStream("png/重命名.png"));
    }

    /**
     * 文件转byte[]
     * http://localhost:8081/oss/bytes
     */
    @GetMapping("/bytes")
    public byte[] bytes() {
        return AliyunOSSUtil.getBytes("png/重命名.png");
    }

}
