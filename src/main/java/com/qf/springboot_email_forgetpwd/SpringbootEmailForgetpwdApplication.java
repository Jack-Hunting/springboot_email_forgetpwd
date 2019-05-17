package com.qf.springboot_email_forgetpwd;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@MapperScan("com.qf.dao")
@SpringBootApplication(scanBasePackages = "com.qf")
public class SpringbootEmailForgetpwdApplication {

    public static void main(String[] args) {
        SpringApplication.run(SpringbootEmailForgetpwdApplication.class, args);
    }

}
