package com.CS353.cs353project.controller;

import com.CS353.cs353project.anotation.PassToken;
import com.CS353.cs353project.utils.FileTypeUtils;
import com.CS353.cs353project.utils.JudgeLegalFile;
import com.fasterxml.jackson.databind.util.ClassUtil;
import io.swagger.annotations.ApiOperation;
import org.springframework.stereotype.Controller;
import org.springframework.util.ClassUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;

@Controller
public class FileUploadController {
    // 映射"/"请求
    @RequestMapping("/")
    public String index() {
        // 根据Thymeleaf默认模板，将返回resources/templates/index.html
        return "index";
    }

    /**
     * 上传头像接口
     */
    @PassToken
    @ResponseBody
    @ApiOperation(value = "上传文件到接口", notes = "")
    @RequestMapping(value = "/upload", method = RequestMethod.POST)
    public String upload(HttpServletRequest request, @RequestParam("file") MultipartFile file) throws Exception {
        String userEmail = (String) request.getAttribute("userEmail");
        String filename="";
        // 如果文件不为空，写入上传路径
        if (!file.isEmpty()) {
            //判断文件类型
            File file1 = null;
            file1 = File.createTempFile("temp", null);
            file.transferTo(file1);
            if(JudgeLegalFile.checkType(JudgeLegalFile.FileTypeName.JPG,file1)){
                filename = "Head_" + userEmail + ".jpg";
            }else if(JudgeLegalFile.checkType(JudgeLegalFile.FileTypeName.PNG,file1)){
                filename = "Head_" + userEmail + ".png";
            }else{
                return "we don't support this type of image, we need: png or jpg";
            }
            // 上传文件路径
            String path = System.getProperty("user.dir") + "/src/main/resources/static/images/" + userEmail;
            System.out.println("path = " + path);
            // 上传文件名
//            String filename = file.getOriginalFilename();

            File filepath = new File(path, filename);
            // 判断路径是否存在，如果不存在就创建一个
            if (!filepath.getParentFile().exists()) {
                filepath.getParentFile().mkdirs();
            }
            // 将上传文件保存到一个目标文件当中
            file1.renameTo(new File(path + File.separator + filename));
            file1.deleteOnExit();//??
            return "success";
        } else {
            return "error";
        }
    }

}