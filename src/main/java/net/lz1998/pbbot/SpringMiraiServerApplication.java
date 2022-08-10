package net.lz1998.pbbot;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.EnableAspectJAutoProxy;

@EnableAspectJAutoProxy
@SpringBootApplication
@MapperScan("net.lz1998.pbbot.dao")
public class SpringMiraiServerApplication {

    public static void main(String[] args) {
        SpringApplication.run(SpringMiraiServerApplication.class, args);
    }

}
