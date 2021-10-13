package com.CS353.cs353project.utils;


import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class JudgeLegalFile {

    private static final Map<FileTypeName, String> FILE_TYPE_MAP = new HashMap<>();

    static {
        // 初始化文件类型信息
        initAllFileType();
    }



    //以下为已被启动检测的文件类型
    public enum FileTypeName {

        JPG("jpg"),

        PNG("png"),

        GIF("gif"),

        // ....根据自己需要添加更多
        ;

        private String fileTypeName;

        FileTypeName(String fileTypeName) {
            this.fileTypeName = fileTypeName;
        }

        @Override
        public String toString() {
            return fileTypeName;
        }
    }


    // 若文件合法return true 否则return false

    public static Boolean checkType(FileTypeName fileTypeName, File file) {
        try {
            FileInputStream fileInputStream = new FileInputStream(file);
            byte[] b = new byte[10];
            // 读取文件前10个字节
            int read = fileInputStream.read(b, 0, b.length);
            if (read != -1) {
                // 将字节转换为16进制字符串
                String fileCode = bytesToHexString(b).toUpperCase();
                // 获取对应文件类型的文件头
                String fileTypeHead = FILE_TYPE_MAP.get(fileTypeName);
                fileInputStream.close();
                return fileCode.startsWith(fileTypeHead) || fileTypeHead.startsWith(fileCode);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return false;
    }

    public static String getFileTypeBySuffix(String fileName) {
        return fileName.substring(fileName.lastIndexOf(".") + 1, fileName.length());
    }



    //返回文件类型，若获取不到类型 return "-1"

    public static String getType(File file) {
        try {
            FileInputStream is = new FileInputStream(file);
            byte[] b = new byte[10];
            int read = is.read(b, 0, b.length);
            if (read != -1) {
                String fileCode = bytesToHexString(b).toUpperCase();
                for (Map.Entry<FileTypeName, String> next : FILE_TYPE_MAP.entrySet()) {
                    String fileTypeHead = next.getValue();
                    if (fileTypeHead.startsWith(fileCode) || fileCode.startsWith(fileTypeHead)) {
                        return next.getKey().toString();
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return "-1";
    }


    // 初始化常见文件头信息

    private static void initAllFileType() {
        // JPEG (jpg)
        FILE_TYPE_MAP.put(FileTypeName.JPG, "FFD8FF");

        // PNG (png)
        FILE_TYPE_MAP.put(FileTypeName.PNG, "89504E47");

        // GIF (gif)
        FILE_TYPE_MAP.put(FileTypeName.GIF, "47494638");

        // 可添加更多类型，文件头要编码用大写
    }


    // 将字节数组转换成16进制字符串

    //传入的是待转换字节数组

    private static String bytesToHexString(byte[] bytes) {
        StringBuilder stringBuilder = new StringBuilder();
        for (byte b : bytes) {
            int v = b & 0xFF;
            String hv = Integer.toHexString(v);
            if (hv.length() < 2) {
                stringBuilder.append(0);
            }
            stringBuilder.append(hv);
        }
        return stringBuilder.toString();
    }


//如下使用
//        // 验证 JPG
//        File jpgImg = new File("D:\\uploadFileTypeTest\\壁纸1.jpg");
//        System.out.println(checkType(FileTypeName.JPG, jpgImg));
//        System.out.println(getType(jpgImg));


}