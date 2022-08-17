package net.lz1998.pbbot.plugin.cultivation;

import java.util.Iterator;
import java.util.Map;
import java.util.Random;
import java.util.Set;
import net.lz1998.pbbot.bot.Bot;
import net.lz1998.pbbot.bot.BotPlugin;
import net.lz1998.pbbot.utils.Msg;
import onebot.OnebotApi.GetGroupMemberInfoResp;
import onebot.OnebotEvent;
import org.jetbrains.annotations.NotNull;
import org.springframework.stereotype.Component;

@Component
public class CultivationWorldPlugin extends BotPlugin {

  @Override
  public int onGroupMessage(@NotNull Bot bot, @NotNull OnebotEvent.GroupMessageEvent event) {
    long groupId = event.getGroupId();
    long qq = event.getUserId();
    String text = event.getRawMessage();
    Msg msg=Msg.builder();
    if (".生成世界".equals(text)){
      Random rd=new Random();

      bot.sendGroupMsg(groupId, msg, false);
      return MESSAGE_BLOCK;
    }

    return MESSAGE_IGNORE;
  }
}
