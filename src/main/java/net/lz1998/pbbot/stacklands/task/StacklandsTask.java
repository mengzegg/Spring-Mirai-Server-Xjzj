package net.lz1998.pbbot.stacklands.task;

import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import net.lz1998.pbbot.stacklands.pojo.Stacklands;
import net.lz1998.pbbot.stacklands.pojo.build.StacklandsBuilds;
import net.lz1998.pbbot.stacklands.pojo.unit.monster.StacklandsMonster;
import net.lz1998.pbbot.stacklands.pojo.unit.monster.StacklandsUser;
import net.lz1998.pbbot.stacklands.service.StUserServer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class StacklandsTask {
  @Autowired
  Map<Long, StacklandsUser> userMap;
  @Autowired
  List<StacklandsBuilds> buildList;
  @Autowired
  List<StacklandsMonster> monsterList;
  @Autowired
  StUserServer userServer;
  public static final Logger logger = LoggerFactory.getLogger(StacklandsTask.class);
  /**
   * 每天七点到二十三点每五秒执行一次
   */
  @Scheduled(cron = "0/5 * * * * ?")
  public void runMin() {


  }
/*  @Scheduled(cron = "0 0/1 * * * ?")*/
  @Scheduled(cron = "0/5 * * * * ?")
  public void rumFen() {
    logger.info("当前剩余建筑："+buildList.size()+"，玩家:"+userMap.size());
    Iterator<StacklandsBuilds> it = buildList. iterator();
    while(it. hasNext()){
      StacklandsBuilds builds = it. next();
      //使用次数到了移除掉
      if (builds.isDie()){
        System.out.println("清除掉了"+builds.getName());
        it.remove();
        break;
      }
      for (Map.Entry<Long,StacklandsUser> entry : userMap.entrySet()) {
        StacklandsUser user = entry.getValue();
        if (user.hit(builds)) {
          System.out.println(user);
          userServer.addFood(user,builds.dropFood());
          userServer.addExp(user,0,builds.dropExperience());
          builds.dropResidueDegree(1);
          //使用次数到了移除掉
          if (builds.isDie()){
            it.remove();
            System.out.println("清除掉了"+builds.getName());
            break;
          }
        }
        }
      }
    }


    }

  /**
   * 每天凌晨1点执行定时
   */
/*  @Scheduled(cron = "0 0 1 * * ?")
  public void task2() {
    //获取上一天的日期
    String day = DateUtil.formatDate(DateUtil.addDays(new Date(), -1));
    dailyService.createStatisticsByDay(day);

  }*/


