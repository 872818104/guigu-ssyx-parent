package com.crh.ssyx;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;

/**
 * @program: guigu-ssyx-parent
 * @description:
 * @author: caoruhao
 * @create: 2023-12-07 17:33
 **/
@SpringBootApplication(exclude = DataSourceAutoConfiguration.class)//取消数据源自动配置
@EnableDiscoveryClient
@EnableFeignClients
public class ServiceHomeApplication {
    public static void main(String[] args) {
        SpringApplication.run(ServiceHomeApplication.class, args);
    }
}
