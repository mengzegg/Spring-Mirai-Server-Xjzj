package net.lz1998.pbbot;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import net.lz1998.pbbot.stacklands.pojo.Stacklands;
import net.lz1998.pbbot.stacklands.pojo.build.StacklandsBuilds;
import net.lz1998.pbbot.stacklands.pojo.unit.monster.StacklandsMonster;
import net.lz1998.pbbot.stacklands.pojo.unit.monster.StacklandsUser;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.scheduling.annotation.EnableScheduling;

@EnableAspectJAutoProxy
@SpringBootApplication
@EnableScheduling
public class SpringMiraiServerApplication {

    public static void main(String[] args) {
        SpringApplication.run(SpringMiraiServerApplication.class, args);
    }
    @Bean
    Map<Long, StacklandsUser> userMap(){
        return new HashMap<>();
    }
    @Bean
    List<StacklandsBuilds> buildList(){
        return new ArrayList<>();
    }
    @Bean
    List<StacklandsMonster> monsterList(){
        return new ArrayList<>();
    }
}
