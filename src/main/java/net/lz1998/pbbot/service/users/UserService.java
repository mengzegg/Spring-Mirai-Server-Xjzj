package net.lz1998.pbbot.service.users;

import javax.annotation.Resource;
import net.lz1998.pbbot.dao.users.UserDao;
import net.lz1998.pbbot.dto.users.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
public class UserService {
  public static final Logger logger = LoggerFactory.getLogger(UserService.class);
  @Resource
  private UserDao userDao;

  /*注册用户*/
  public void insertUserByQq(long qq) {
    userDao.insertUserByQq(qq);
  }
  /**
   * 获取用户对象
   * @param qq 用户qq
   * @return 用户对象
   */
  public User getUserByQq(long qq) {
    return userDao.getUserByQq(qq);
  }
  /*增加钱*/
  public Integer addMoney(Integer money,long qq) {
    return userDao.addMoneyByQq(money,qq);
  }
  /*扣除钱*/
  public String subMoney(Integer money,long qq) {
    int nowMoney=userDao.getMoneyByQq(qq);
    if (nowMoney-money<0){
      return "余额不足";
    }
    return ""+userDao.subMoneyByQq(money,qq);
  }
  /**
   * 获得经验
   * @param exp 增加的经验
   * @param qq 用户qq
   * @return 增加后经验
   */
  public Integer addExp(Integer exp,long qq) {
    return userDao.addExpByQq(exp,qq);
  }
}
