package net.lz1998.pbbot.stacklands.plugin;

import java.awt.image.BufferedImage;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Random;
import java.util.Set;
import java.util.UUID;
import net.lz1998.pbbot.bot.Bot;
import net.lz1998.pbbot.bot.BotPlugin;
import net.lz1998.pbbot.dto.users.Sigin;
import net.lz1998.pbbot.dto.users.User;
import net.lz1998.pbbot.stacklands.pojo.Stacklands;
import net.lz1998.pbbot.stacklands.pojo.build.StacklandsBuilds;
import net.lz1998.pbbot.stacklands.pojo.unit.monster.StacklandsUser;
import net.lz1998.pbbot.utils.Msg;
import onebot.OnebotApi.GetGroupMemberInfoResp;
import onebot.OnebotEvent;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

@Component
public class StacklandsPlugin extends BotPlugin {
  @Autowired
  Map<Long,Stacklands> stacklandsMap;

  @Override
  public int onGroupMessage(@NotNull Bot bot, @NotNull OnebotEvent.GroupMessageEvent event) {
    long groupId = event.getGroupId();
    Long qq=event.getUserId();
    String text = event.getRawMessage();
    Msg msg=Msg.builder();
    if (".大陆菜单".equals(text)) {
      msg.text(".出生\n");
      msg.text(".我的角色\n");
      msg.text(".上/下/左/右 如：.左\n");
      msg.text(".生成浆果丛\n");
      msg.text(".开始修仙\n");
      msg.text(".修炼\n");
      bot.sendGroupMsg(groupId, msg, false);
      return MESSAGE_BLOCK;
    }
    /*用户注册*/
    if (".出生".equals(text)) {
      StacklandsUser stacklands=new StacklandsUser();
      stacklands.setWorldX(500);
      stacklands.setWorldY(500);
      stacklands.setFood(0);
      stacklands.setHealth(3);
      stacklands.setNowHealth(3);
      stacklands.setAtk(1);
      stacklands.setFood(0);
      stacklands.setImageName("村民.png");
      stacklands.setId(qq);
      stacklands.setExperience(0);
      stacklands.setName("村民");
      stacklandsMap.put(qq,stacklands);
      msg.image("http://localhost:8081/getStacklandsWorldImage?qq="+qq);
      bot.sendGroupMsg(groupId, msg, false);
      return MESSAGE_BLOCK;
    }
    if (".开始修仙".equals(text)) {
      StacklandsUser stacklands= (StacklandsUser) stacklandsMap.get(qq);
      stacklands.setHealth(5);
      stacklands.setNowHealth(5);
      stacklands.setAtk(2);
      stacklands.setImageName("修仙01.png");
      GetGroupMemberInfoResp groupMember=bot.getGroupMemberInfo(groupId,qq,true);
      stacklands.setName(groupMember.getNickname());
      msg.image("http://localhost:8081/getStacklandsWorldImage?qq="+qq);
      bot.sendGroupMsg(groupId, msg, false);
      return MESSAGE_BLOCK;
    }
    if (".修炼".equals(text)) {
      StacklandsUser stacklands= (StacklandsUser) stacklandsMap.get(qq);
      if (stacklands.getFood()>=1){
        stacklands.setFood(stacklands.getFood()-1);
        stacklands.setHealth(stacklands.getHealth()+1);
        stacklands.setNowHealth(stacklands.getNowHealth()+1);
        stacklands.setAtk(stacklands.getAtk()+1);
        stacklands.setExperience(stacklands.getExperience()+10);
        GetGroupMemberInfoResp groupMember=bot.getGroupMemberInfo(groupId,qq,true);
        stacklands.setName(groupMember.getNickname());
        msg.image("http://localhost:8081/getStacklandsWorldImage?qq="+qq);
      }else {
        msg.text("食物不足,请至少准备1点食物");
        bot.sendGroupMsg(groupId, msg, false);
      }
      return MESSAGE_BLOCK;
    }
    if (".生成浆果丛".equals(text)) {
      Random random = new Random();
      for (int i = 0; i < 5; i++) {
        Long uuid=(long) random.nextInt(1000000)+1;
        StacklandsBuilds stacklands=new StacklandsBuilds();
        stacklands.setWorldX(200+random.nextInt(1000));
        stacklands.setWorldY(200+random.nextInt(1000));
        stacklands.setName("浆果丛");
        stacklands.setImageName("浆果丛.png");
        stacklands.setId(uuid);
        stacklandsMap.put(uuid,stacklands);
      }
      bot.sendGroupMsg(groupId, "添加成功", false);
      return MESSAGE_BLOCK;
    }
    if (".我的角色".equals(text)) {
      msg.image("http://localhost:8081/getStacklandsImage?qq="+qq);
      bot.sendGroupMsg(groupId, msg, false);
      return MESSAGE_BLOCK;
    }

    int goLength=300;
    if (".左".equals(text)) {
      if (stacklandsMap==null){
      }
      Stacklands stacklands=stacklandsMap.get(qq);
      stacklands.setWorldX(stacklands.getWorldX()+goLength);
      msg.image("http://localhost:8081/getStacklandsWorldImage?qq="+qq);
      bot.sendGroupMsg(groupId, msg, false);
      return MESSAGE_BLOCK;
    }
    if (".右".equals(text)) {
      if (stacklandsMap==null){
      }
      Stacklands stacklands=stacklandsMap.get(qq);
      stacklands.setWorldX(stacklands.getWorldX()-goLength);
      msg.image("http://localhost:8081/getStacklandsWorldImage?qq="+qq);
      bot.sendGroupMsg(groupId, msg, false);
      return MESSAGE_BLOCK;
    }
    if (".上".equals(text)) {
      if (stacklandsMap==null){
      }
      Stacklands stacklands=stacklandsMap.get(qq);
      stacklands.setWorldY(stacklands.getWorldY()+goLength);
      msg.image("http://localhost:8081/getStacklandsWorldImage?qq="+qq);
      bot.sendGroupMsg(groupId, msg, false);
      return MESSAGE_BLOCK;
    }
    if (".下".equals(text)) {
      if (stacklandsMap==null){
      }
      Stacklands stacklands=stacklandsMap.get(qq);
      stacklands.setWorldY(stacklands.getWorldY()-goLength);
      msg.image("http://localhost:8081/getStacklandsWorldImage?qq="+qq);
      bot.sendGroupMsg(groupId, msg, false);
      return MESSAGE_BLOCK;
    }
    return MESSAGE_IGNORE;
  }
}
