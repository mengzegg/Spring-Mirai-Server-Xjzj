package net.lz1998.pbbot.stacklands.service;

import java.util.List;
import java.util.Map;
import java.util.Random;
import net.lz1998.pbbot.stacklands.pojo.Stacklands;
import net.lz1998.pbbot.stacklands.pojo.build.StacklandsBuilds;
import net.lz1998.pbbot.stacklands.pojo.unit.monster.StacklandsMonster;
import net.lz1998.pbbot.stacklands.pojo.unit.monster.StacklandsUser;
import onebot.OnebotApi.GetGroupMemberInfoResp;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class StUserServer {
  @Autowired
  Map<Long, StacklandsUser> userMap;
  @Autowired
  List<StacklandsBuilds> buildList;
  @Autowired
  List<StacklandsMonster> monsterList;
  public StacklandsUser insterUser(long qq){
    Random random = new Random();
    StacklandsUser stacklands=new StacklandsUser();
    stacklands.setWorldX(500+random.nextInt(1000));
    stacklands.setWorldY(500+random.nextInt(1000));
    stacklands.setFood(0);
    stacklands.setHealth(3);
    stacklands.setNowHealth(3);
    stacklands.setAtk(1);
    stacklands.setRealm(1);
    stacklands.setImageName("凡人01.png");
    stacklands.setId(qq);
    stacklands.setExperience(0);
    stacklands.setLevel(0);
    stacklands.setLibertyAttributes(3);
    return stacklands;
  }
  //生成浆果丛
  public void insertJGC(int num){
    Random random = new Random();
    for (int i = 0; i < num; i++) {
      Long uuid=(long) random.nextInt(1000000)+1;
      StacklandsBuilds stacklands=new StacklandsBuilds();
      stacklands.setWorldX(500+random.nextInt(1000));
      stacklands.setWorldY(500+random.nextInt(1000));
      stacklands.setResidueDegree(2);
      stacklands.setSeedFood(1);
      stacklands.setSeedExp(1);
      stacklands.setName("灵果");
      stacklands.setImageName("灵果.png");
      stacklands.setId(uuid);
      buildList.add(stacklands);
    }
  }
  /*突破*/
  public boolean upRealm(StacklandsUser user){
    if (user.getLevel()>=9) {
      user.setRealm(user.getRealm()+1);
      user.setLevel(1);
      user.setHealth(user.getHealth()+3);
      user.setNowHealth(user.getNowHealth()+3);
      user.setAtk(user.getHealth()+2);
      user.setLibertyAttributes(user.getLibertyAttributes()+user.getRealm()*5);
      user.setImageName("凡人0"+user.getRealm()+".png");
      return true;
    }else {
     return false;
    }
  }
  /**
   * 消耗食物增加经验
   *
   * @param needFood 扣除的食物量
   * @param addExp 添加多少经验
   * @return 返回0食物不足，返回1正常添加经验，返回2升级成功，返回3经验和等级满了
   */
  public int addExp(StacklandsUser user,int needFood,int addExp){
    if (user.getFood()>=needFood) {
      user.setFood(user.getFood() + -needFood);
      user.setExperience(user.getExperience()+addExp);
      if (user.getExperience()>=user.getLevel()*user.getRealm()*10){
        user.setExperience(0);
        user.setLevel(user.getLevel()+1);
        if (user.getLevel()>=9){
          user.setLevel(0);
          return 3;
        }
        return 2;
      }
      return 1;
    }
    return 0;
  }
  public int addFood(StacklandsUser user,int addFood){
    user.setFood(user.getFood()+addFood);
    if (user.getFood()>=user.getLevel()*user.getRealm()*10){
      user.setFood(user.getLevel()*user.getRealm()*10);
      return 2;
    }
    return 1;
  }
  /*0点数不足，1升级成功*/
  public int addLibertyAttributes(StacklandsUser user,int type){
    if (user.getLibertyAttributes()>=1){
      if (type==1){
        user.setAtk(user.getAtk()+1);

      }else if (type == 2){
        user.setHealth(user.getHealth()+1);
        user.setNowHealth(user.getNowHealth()+1);
      }
      user.setLibertyAttributes(user.getLibertyAttributes()-1);
      return 1;
    }

    return 0;
  }

/*  *//**
   * 获得食物
   *
   * @param addFood 获得数量
   * @return 如果背包满了返回false
   *//*
  public boolean addFood(Integer addFood){
    this.setFood(this.getFood()+addFood);
    if (this.getFood()>=level*10){
      food=level*10;
      return false;
    }
    return true;
  }
  *//**
   * 获得食物
   *
   * @param addLibertyAttributes 获得数量
   * @return 如果背包满了返回false
   *//*
  public boolean addLibertyAttributes(Integer addLibertyAttributes){
    libertyAttributes+=addLibertyAttributes;
    return true;
  }

  *//*返回1正常添加经验，返回2升级成功，返回3经验和等级满了*//*
  public int addExperience(int addExperience) {
    experience+=addExperience;
    if (experience<=level*10){
      experience+=addExperience;
      return 1;
    }else {
      experience=0;
      return addLevel();
    }
  }

  public int addLevel(){
    if (level<=9) {
      addLibertyAttributes(1);
      return 2;
    }else {
      level=9;
    }
    return 3;
  }*/
}
