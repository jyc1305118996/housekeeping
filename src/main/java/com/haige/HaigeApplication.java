package com.haige;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import org.springframework.web.client.RestTemplate;


/**
 * @author Archie
 * @date 2019/8/2 22:37
 */

@SpringBootApplication
public class HaigeApplication {
    public static void main(String[] args) {
        SpringApplication.run(HaigeApplication.class, args);
    }
    /**
     * 映射硬盘目录文件
     * 下面代码通过tomcat映射 先不采用
     * @throws
     */

//    @Bean
//    RouterFunction<ServerResponse> staticResourceRouter() throws MalformedURLException {
//        File path = null;
//        try {
//            path = new File(ResourceUtils.getURL("classpath:").getPath());
//        } catch (FileNotFoundException e) {
//            e.printStackTrace();
//        }
//        if (!path.exists()) path = new File("");
//        File upload = new File(path.getAbsolutePath(), "static/base/upload/");
//        if (!upload.exists()) upload.mkdirs();
//        System.out.println("upload url:" + upload.getAbsolutePath());
//        String basePath = upload.getAbsolutePath()+"/";
//        return RouterFunctions.resources("/file/**",
//                new FileUrlResource(basePath));
//    }

    @Bean
    public RestTemplate getRestTemplate(){
        return new RestTemplate();
    }
}
