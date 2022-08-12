package net.lz1998.pbbot.dao.users;

import java.util.List;
import net.lz1998.pbbot.dto.users.User;

public interface UserDao {
  List<User> getAll();
  void insertUserByQq(long qq);
  void insertUserByUser(User user);
  User getUserByQq(long qq);
  Integer getMoneyByQq(long qq);
  Integer addMoneyByQq(Integer money,long qq);
  Integer subMoneyByQq(Integer money,long qq);
  Integer setMoneyByQq(Integer money,long qq);
  Integer setLevelByQq(Integer level,long qq);
  Integer getExpByQq(long qq);
  Integer setExpByQq(Integer exp,long qq);
  Integer addExpByQq(Integer exp,long qq);

}
