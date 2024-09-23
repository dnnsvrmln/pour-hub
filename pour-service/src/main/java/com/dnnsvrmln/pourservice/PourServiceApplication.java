package com.dnnsvrmln.pourservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@EnableDiscoveryClient
@SpringBootApplication
public class PourServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(PourServiceApplication.class, args);
    }

}
