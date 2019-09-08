package com.jacksonfang.house;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

/**
 * @author Jacky
 * Date:   2019/5/21
 * Time:   11:12
 */
@SpringBootApplication
@EnableDiscoveryClient
public class HouseServiceAppplication {
    public static void main(String[] args) {
        SpringApplication.run(HouseServiceAppplication.class, args);
    }
}
