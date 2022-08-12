package net.lz1998.pbbot.plugin.users;

import javax.annotation.Resource;
import net.lz1998.pbbot.bot.Bot;
import net.lz1998.pbbot.bot.BotPlugin;
import net.lz1998.pbbot.dto.users.User;
import net.lz1998.pbbot.service.users.UserService;
import net.lz1998.pbbot.utils.Msg;
import onebot.OnebotApi.GetGroupMemberInfoResp;
import onebot.OnebotEvent;
import org.jetbrains.annotations.NotNull;
import org.springframework.stereotype.Component;

@Component
public class UserPlugin  extends BotPlugin {
  @Resource
  private UserService userService;
  @Override
  public int onGroupMessage(@NotNull Bot bot, @NotNull OnebotEvent.GroupMessageEvent event) {
    long groupId = event.getGroupId();
    long qq=event.getUserId();
    String text = event.getRawMessage();
    /*只有.的指令继续判断*/
    if (!text.startsWith(".")){
      return MESSAGE_BLOCK;
    }
    User user=userService.getUserByQq(qq);
    Msg msg=Msg.builder();
    /*菜单*/
    if (".菜单".equals(text)) {
      msg.text(".菜单");
      msg.text("\n.注册");
      msg.text("\n.我的账户");

      msg.text("\n.打卡");
      bot.sendGroupMsg(groupId, msg, false);
      return MESSAGE_BLOCK;
    }
    /*用户注册*/
    if (".注册".equals(text)) {
      if (user!=null){
        msg.text("已注册，无须重复注册");
        bot.sendGroupMsg(groupId, msg, false);
        return MESSAGE_BLOCK;
      }
      userService.insertUserByQq(qq);
      msg.text("恭喜，用户："+qq+"注册成功");
      bot.sendGroupMsg(groupId, msg, false);
      return MESSAGE_BLOCK;
    }
    /*没注册不继续执行*/
    if (user==null){
      msg.text("您还未注册，请输入.注册");
      bot.sendGroupMsg(groupId, msg, false);
      return MESSAGE_BLOCK;
    }
    if (".我的账户".equals(text)){
      /*msg.at(qq);*/
      GetGroupMemberInfoResp groupMember=bot.getGroupMemberInfo(groupId,qq,true);
      msg.image("http://localhost:8081/getImage?qq=" + qq);
      msg.text(groupMember.getNickname()+"\n");
      msg.face(158);
      msg.face(158);
      msg.text( "：" + user.getUserMoney());
      msg.text( "\n当前等级：" + user.getUserLevel());
      msg.text( "\n当前经验：" + user.getUserExp());
      msg.text( "\n注册时间：" + user.getUserInfo().toString().substring(0,user.getUserInfo().toString().lastIndexOf("-")));
      bot.sendGroupMsg(groupId, msg, false);
      return MESSAGE_BLOCK;
    }

    return MESSAGE_IGNORE;
  }
}
