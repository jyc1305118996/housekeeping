package com.haige.util;

import org.aspectj.util.FileUtil;
import org.springframework.http.codec.multipart.FilePart;
import org.springframework.stereotype.Component;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.*;

/**
 * @author : Aaron
 * create at:  2019-10-31  22:38
 * @description: 通用文件操作类
 */
@Component
public class FileUtils {

    private static String BASE_FILE_PATH = "/Users/huxianming/IdeaProjects/";//注意加一个upload目录
    private static String BASE_FILE_HTTP_PATH = "";//映射的目录

    {

        File upload = new File(BASE_FILE_PATH);
        if (!upload.exists()) upload.mkdirs();
    }

    public List<Map<String, String>> uploadFiles(FilePart filePart[]) throws Exception {
        if (filePart == null || filePart.length <= 0) {
            return new ArrayList<>();
        } else {
            int len = filePart.length;
            List fileList = new ArrayList(len);
            for (int i = 0; i < len; i++) {
                fileList.add(this.uploadFile(filePart[i]));

            }
            return fileList;
        }


    }


    public HashMap<String, String> uploadFile(FilePart filePart) throws Exception {
        String CurrentDateTime = TimeUtil.getCurrentDateTime(TimeUtil.TimeFormat.SHORT_DATE_PATTERN_NONE).toString() + "/";
        HashMap<String, String> fileMap = new HashMap<>();

        String fileName = filePart.filename();
        String prefix = fileName.substring(fileName.lastIndexOf("."));//获取文件后缀
        Path tempFile = Files.createTempFile("", prefix);
        filePart.transferTo(tempFile.toFile());
        String path = CurrentDateTime;
        File upload = new File(BASE_FILE_PATH , path);
        if (!upload.exists()) upload.mkdirs();
        FileUtil.copyFile(new File(tempFile.toFile().getPath()), upload);
        fileMap.put("realPath", BASE_FILE_PATH + path);
        fileMap.put("path", BASE_FILE_HTTP_PATH+path);
        fileMap.put("fileName",  filePart.filename());
        fileMap.put("fileRealName", tempFile.getFileName().toString());
        return fileMap;
    }

    private static String getUUID() {
        String s = UUID.randomUUID().toString();
        // 去掉“-”符号
        return s.substring(0, 8) + s.substring(9, 13) + s.substring(14, 18) + s.substring(19, 23) + s.substring(24);
    }


}

