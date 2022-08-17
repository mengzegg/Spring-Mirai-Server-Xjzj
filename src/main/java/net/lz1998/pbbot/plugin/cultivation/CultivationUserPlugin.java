package net.lz1998.pbbot.plugin.cultivation;

import net.lz1998.pbbot.bot.Bot;
import net.lz1998.pbbot.bot.BotPlugin;
import net.lz1998.pbbot.utils.Msg;
import onebot.OnebotEvent;
import org.jetbrains.annotations.NotNull;
import org.springframework.stereotype.Component;

@Component
public class CultivationUserPlugin extends BotPlugin {

  @Override
  public int onGroupMessage(@NotNull Bot bot, @NotNull OnebotEvent.GroupMessageEvent event) {
    long groupId = event.getGroupId();
    long qq = event.getUserId();
    String text = event.getRawMessage();
    Msg msg=Msg.builder();
    if (".打卡排行".equals(text)){

      bot.sendGroupMsg(groupId, msg, false);
      return MESSAGE_BLOCK;
    }

    return MESSAGE_IGNORE;
  }
}