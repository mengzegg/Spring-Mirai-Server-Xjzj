package net.lz1998.pbbot.plugin.users;

import java.util.Iterator;
import java.util.Map;
import java.util.Random;
import java.util.Set;
import javax.annotation.Resource;
import net.lz1998.pbbot.bot.Bot;
import net.lz1998.pbbot.bot.BotPlugin;
import net.lz1998.pbbot.dto.users.Sigin;
import net.lz1998.pbbot.dto.users.User;
import net.lz1998.pbbot.service.users.UserService;
import net.lz1998.pbbot.service.users.UserSiginService;
import net.lz1998.pbbot.utils.Msg;
import onebot.OnebotApi.GetGroupMemberInfoResp;
import onebot.OnebotEvent;
import org.jetbrains.annotations.NotNull;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

@Component
public class SiginPlugin extends BotPlugin {
  @Resource
  private UserService userService;
  @Resource
  private UserSiginService userSiginService;

  @Override
  public int onGroupMessage(@NotNull Bot bot, @NotNull OnebotEvent.GroupMessageEvent event) {
    long groupId = event.getGroupId();
    long qq=event.getUserId();
    String text = event.getRawMessage();

    User user=userService.getUserByQq(qq);
    Msg msg=Msg.builder();
    /*用户注册*/
    if (".打卡".equals(text)) {
      Sigin sigin=new Sigin();
      Random rd=new Random();
      sigin.setUserQq(qq);
      sigin.setSignMoney(rd.nextInt(30)+50);
      sigin.setSignExp(50);
      if (userSiginService.insertSiginLog(sigin)){
        msg.image("https://gchat.qpic.cn/gchatpic_new/1305856104/571352366-2416391060-A2CA8C108ACA4ABDFEF6F12D1921ED71/0?term=2");
        msg.image("https://gchat.qpic.cn/gchatpic_new/1305856104/571352366-2416391060-A2CA8C108ACA4ABDFEF6F12D1921ED71/0?term=2");
        msg.image("https://gchat.qpic.cn/gchatpic_new/1305856104/571352366-2416391060-A2CA8C108ACA4ABDFEF6F12D1921ED71/0?term=2");
        msg.text("打卡成功，获得\n");
        msg.face(158);
        msg.text("："+sigin.getSignMoney()+"\n经验：");
        msg.text(sigin.getSignExp()+"");
      }else {
        msg.text("打卡失败，明天再来吧");
      }
      bot.sendGroupMsg(groupId, msg, false);
      return MESSAGE_BLOCK;
    }
    if (".打卡排行".equals(text)){
      Map<String,String> map = userSiginService.getSignRankList();
      Set set = map.keySet();
      Iterator iterator = set.iterator();
      msg.text(" ✨✨✨✨");
      msg.text("<face id=\"277\"/>");
      msg.text("打卡排行");
      msg.text("✨✨✨✨\n");
      for (int i = 0; i < map.size(); i++) {
        Object next = iterator.next();
        GetGroupMemberInfoResp groupMember=bot.getGroupMemberInfo(groupId,Long.parseLong(next.toString()),true);
        msg.face(144);
        msg.text("第"+(i+1)+"名   \uD83C\uDF80"+groupMember.getNickname()+"\uD83C\uDF80");
        for (int j = 0; j < 15-groupMember.getNickname().length()*3; j++) {
          msg.text(" ");
        }
        msg.text("打卡次数:"+map.get(next)+"\n");
      }

      bot.sendGroupMsg(groupId, msg, false);
      return MESSAGE_BLOCK;
    }

    return MESSAGE_IGNORE;
  }
}
