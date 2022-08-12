package net.lz1998.pbbot.service.users;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.annotation.Resource;
import net.lz1998.pbbot.bot.Bot;
import net.lz1998.pbbot.dao.users.UserDao;
import net.lz1998.pbbot.dao.users.UserSiginDao;
import net.lz1998.pbbot.dto.users.Sigin;
import onebot.OnebotApi.GetGroupMemberInfoResp;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

@Service
public class UserSiginService {
  public static final Logger logger = LoggerFactory.getLogger(UserService.class);
  @Resource
  private UserDao userDao;
  @Resource
  private UserSiginDao userSiginDao;


  public boolean insertSiginLog(Sigin sigin) {
    Date date=userSiginDao.getMaxSiginByQq(sigin.getUserQq());
    if (date!=null){
      try {
/*        long dateTime =date.getTime();
        long nowTime1=System.currentTimeMillis();
        long difference=nowTime1-dateTime;
        if (difference/1000/60/60 < 18){
          return "打卡失败，还有"+(18-difference/1000/60/60)+"小时再次打卡";
        }*/
        if (date.getDay() == new Date().getDay()){
          return false;
        }
      }catch (Exception e){
        logger.error("打卡异常",e);
        return false;
      }
    }
    //增加钱
    userSiginDao.insertSiginLog(sigin);
    userDao.addMoneyByQq(sigin.getSignMoney(),sigin.getUserQq());
    userDao.addExpByQq(sigin.getSignExp(),sigin.getUserQq());
    return true;
  }

  public Map<String,String> getSignRankList(){
    List<Map<String, Object>>  list =userSiginDao.getSignRankList();
    Map<String,String> map=new HashMap<>();
    for (Map<String, Object> stringObjectMap : list) {
      map.put(stringObjectMap.get("qq").toString(),stringObjectMap.get("count").toString());
    }
    return map;
  }
}
