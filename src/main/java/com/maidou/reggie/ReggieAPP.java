package com.maidou.reggie;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletComponentScan;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.transaction.annotation.EnableTransactionManagement;

/**
 * @author MaiDou
 * @version 1.0
 * @date 2022/11/5 17:30
 **/
@Slf4j
@SpringBootApplication
@ServletComponentScan
@EnableTransactionManagement
@EnableCaching
public class ReggieAPP {
    public static void main(String[] args) {
        SpringApplication.run(ReggieAPP.class);
        log.info("程序启动成功~");
        /**
         *  @Slf4j 注解是lombok提供的日志注解,可以使用log.info打印信息
         */
    }
}
