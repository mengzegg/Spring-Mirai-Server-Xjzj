package net.lz1998.pbbot.stacklands.plugin;

import java.util.List;
import java.util.Map;
import net.lz1998.pbbot.bot.Bot;
import net.lz1998.pbbot.bot.BotPlugin;
import net.lz1998.pbbot.stacklands.pojo.Stacklands;
import net.lz1998.pbbot.stacklands.pojo.build.StacklandsBuilds;
import net.lz1998.pbbot.stacklands.pojo.unit.monster.StacklandsMonster;
import net.lz1998.pbbot.stacklands.pojo.unit.monster.StacklandsUser;
import net.lz1998.pbbot.stacklands.service.StUserServer;
import net.lz1998.pbbot.utils.Msg;
import onebot.OnebotApi.GetGroupMemberInfoResp;
import onebot.OnebotEvent;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class StacklandsPlugin extends BotPlugin {
  @Autowired
  Map<Long, StacklandsUser> userMap;
  @Autowired
  List<StacklandsBuilds> buildList;
  @Autowired
  List<StacklandsMonster> monsterList;
  @Autowired
  StUserServer userServer;
  @Override
  public int onGroupMessage(@NotNull Bot bot, @NotNull OnebotEvent.GroupMessageEvent event) {
    long groupId = event.getGroupId();
    Long qq=event.getUserId();
    String text = event.getRawMessage();
    Msg msg=Msg.builder();
    if (!text.startsWith(".")){
      return MESSAGE_BLOCK;
    }
    if ("大陆菜单".equals(text)) {
      msg.text("出生\n");
      msg.text("我的角色\n");
      msg.text("上/下/左/右 如：.左\n");
      msg.text("生成灵果 5\n");
      msg.text("开始修仙\n");
      msg.text("突破(需要lv9)\n");
      msg.text("修炼\n");
      msg.text("升级攻击\n");
      msg.text("升级血量\n");
      bot.sendGroupMsg(groupId, msg, false);
      return MESSAGE_BLOCK;
    }
    StacklandsUser user=userMap.get(qq);
    /*用户注册*/
    if ("出生".equals(text)) {
      if (user==null) {
        user=userServer.insterUser(qq);
        GetGroupMemberInfoResp groupMember = bot.getGroupMemberInfo(groupId, qq, true);
        user.setName(groupMember.getNickname());
        userMap.put(qq,user);
        userServer.insertJGC(5);
        msg.image("http://localhost:8081/getStacklandsWorldImage?qq=" + qq);
      }else {
        msg.text("出生失败，角色已存在");
      }
      bot.sendGroupMsg(groupId, msg, false);
      return MESSAGE_BLOCK;
    }
    if (user==null){
      userServer.insterUser(qq);
      user= userMap.get(qq);
      return MESSAGE_BLOCK;
    }
    if ("突破".equals(text)) {
      if (userServer.upRealm(user)){
        msg.text("突破成功");
        msg.image("http://localhost:8081/getStacklandsImage?qq="+qq);
        msg.text("自由点数:"+user.getLibertyAttributes());
      }else {
        msg.text("突破失败");
      }
      bot.sendGroupMsg(groupId, msg, false);
      return MESSAGE_BLOCK;
    }
    if ("修炼".equals(text)) {
      if (user.getFood()>=1){

        msg.image("http://localhost:8081/getStacklandsWorldImage?qq="+qq);
      }else {
        msg.text("食物不足,请至少准备1点食物");
      }
      bot.sendGroupMsg(groupId, msg, false);
      return MESSAGE_BLOCK;
    }
    if (text.startsWith("生成灵果 ")) {
      int sum=Integer.parseInt(text.substring(5,text.length()));
      userServer.insertJGC(sum);
      bot.sendGroupMsg(groupId, "添加成功", false);
      return MESSAGE_BLOCK;
    }
    if ("我的角色".equals(text)) {
      msg.image("http://localhost:8081/getStacklandsImage?qq="+qq);
      msg.text("自由点数:"+user.getLibertyAttributes());
      bot.sendGroupMsg(groupId, msg, false);
      return MESSAGE_BLOCK;
    }

    int goLength=300;
    if ("左".equals(text)) {
      Stacklands stacklands=userMap.get(qq);
      stacklands.setWorldX(stacklands.getWorldX()+goLength);
      msg.image("http://localhost:8081/getStacklandsWorldImage?qq="+qq);
      bot.sendGroupMsg(groupId, msg, false);
      return MESSAGE_BLOCK;
    }
    if ("右".equals(text)) {
      Stacklands stacklands=userMap.get(qq);
      stacklands.setWorldX(stacklands.getWorldX()-goLength);
      msg.image("http://localhost:8081/getStacklandsWorldImage?qq="+qq);
      bot.sendGroupMsg(groupId, msg, false);
      return MESSAGE_BLOCK;
    }
    if ("上".equals(text)) {
      Stacklands stacklands=userMap.get(qq);
      stacklands.setWorldY(stacklands.getWorldY()+goLength);
      msg.image("http://localhost:8081/getStacklandsWorldImage?qq="+qq);
      bot.sendGroupMsg(groupId, msg, false);
      return MESSAGE_BLOCK;
    }
    if ("下".equals(text)) {
      Stacklands stacklands=userMap.get(qq);
      stacklands.setWorldY(stacklands.getWorldY()-goLength);
      msg.image("http://localhost:8081/getStacklandsWorldImage?qq="+qq);
      bot.sendGroupMsg(groupId, msg, false);
      return MESSAGE_BLOCK;
    }
    if (text.startsWith("升级")) {
      String str=text.substring(2);
      int stut=0;
      if (str.equals("攻击")) {
        stut=userServer.addLibertyAttributes(user,1);
      }
      if (str.equals("血量")) {
        stut=userServer.addLibertyAttributes(user,2);
      }
      if (stut==1){
        msg.text("升级成功");
      }else if (stut==0){
        msg.text("升级失败，自由点数不足");
      }
      msg.image("http://localhost:8081/getStacklandsImage?qq="+qq);
      msg.text("自由点数:"+user.getLibertyAttributes());
      bot.sendGroupMsg(groupId, msg, false);
      return MESSAGE_BLOCK;
    }


    return MESSAGE_IGNORE;
  }
}
