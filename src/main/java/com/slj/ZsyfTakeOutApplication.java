package com.slj;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@Slf4j
@SpringBootApplication
@EnableTransactionManagement
//@EnableCaching
public class ZsyfTakeOutApplication {

    public static void main(String[] args) {
        SpringApplication.run(ZsyfTakeOutApplication.class, args);
        log.info("启动成功");
         log.info("git测sadfsdfdsfed试");

    }

}
