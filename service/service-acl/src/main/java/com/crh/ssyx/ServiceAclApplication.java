package com.crh.ssyx;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

/**
 * @program: guigu-ssyx-parent
 * @description:
 * @author: caoruhao
 * @create: 2023-11-01 11:09
 **/
@SpringBootApplication
@EnableDiscoveryClient
public class ServiceAclApplication {

    public static void main(String[] args) {
        SpringApplication.run(ServiceAclApplication.class, args);
    }
}
