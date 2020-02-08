package com.haige;

import com.haige.common.bean.IdWorker;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import org.springframework.context.annotation.Bean;

import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.security.config.annotation.web.reactive.EnableWebFluxSecurity;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.server.SecurityWebFilterChain;
import org.springframework.web.client.RestTemplate;

import java.nio.charset.Charset;
import java.util.List;

/**
 * @author Archie
 * @date 2019/8/2 22:37
 */

// 排除默认的安全登陆配置

@SpringBootApplication(exclude = {SecurityAutoConfiguration.class})
public class HaigeApplication {
    public static void main(String[] args) {
        SpringApplication.run(HaigeApplication.class, args);
    }

    /**
     * 映射硬盘目录文件
     * 下面代码通过tomcat映射 先不采用
     *
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
    public RestTemplate getRestTemplate() {
        // 移除resttemplate的默认转换器，添加自定义转换器，解决格式乱码问题
        RestTemplate restTemplate = new RestTemplate();
        List<HttpMessageConverter<?>> converterList = restTemplate.getMessageConverters();
        HttpMessageConverter<?> converterTarget = null;
        for (HttpMessageConverter<?> item : converterList) {
            if (item.getClass() == StringHttpMessageConverter.class) {
                converterTarget = item;
                break;
            }
        }
        if (converterTarget != null) {
            converterList.remove(converterTarget);
        }
        HttpMessageConverter<?> converter = new StringHttpMessageConverter(Charset.forName("UTF-8"));
        restTemplate.getMessageConverters().add(converter);
        return restTemplate;
    }

    @Bean
    public IdWorker idWorker() {
        return new IdWorker(1, 1);
    }

    /**
     * 创建加密对象
     *
     * @return
     */
    @Bean
    public BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    /**
     * 关闭spring 自己的安全,关闭csrf攻击
     * @param httpSecurity
     * @return
     */
    @Bean
    public SecurityWebFilterChain securityWebFilterChain(final ServerHttpSecurity httpSecurity) {
        return httpSecurity
                .authorizeExchange().anyExchange().permitAll().and().csrf().disable()
                .build();
    }
}

