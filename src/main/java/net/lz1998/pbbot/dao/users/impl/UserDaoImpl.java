package net.lz1998.pbbot.dao.users.impl;

import java.util.List;
import javax.annotation.Resource;
import net.lz1998.pbbot.dao.users.UserDao;
import net.lz1998.pbbot.dto.users.User;
import net.lz1998.pbbot.utils.dao.JdbcDaoSupport4mysql;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class UserDaoImpl extends JdbcDaoSupport4mysql implements UserDao {

  @Resource
  private JdbcTemplate jdbcTemplate;
  @Resource
  private NamedParameterJdbcTemplate namedParameterJdbcTemplate;

  @Override
  public List<User> getAll() {
    String sql = "SELECT " +
        "user_qq,user_money,user_level,user_exp,user_pvp,user_vip,user_info" +
        "FROM " +
        "user  " ;
    List<User> list = this.getJdbcTemplate().query(sql, new BeanPropertyRowMapper<>(User.class));
    return list;
  }

  @Override
  public void insertUserByQq(long qq) {
    String sql = "INSERT INTO " +
        "user(user_qq,user_money,user_level,user_exp,user_pvp,user_vip,user_info) " +
        "VALUES(?,300,0,0,0,0,now()) ";
    this.getJdbcTemplate().update(sql, qq);
  }

  @Override
  public void insertUserByUser(User user) {
    String sql = "INSERT INTO " +
        "user(user_qq,user_money,user_level,user_exp,user_pvp,user_vip,user_info) " +
        "VALUES(?,?,?,?,?,?,?) ";
    this.getJdbcTemplate().update(sql,
        user.getUserQq(),user.getUserMoney(),user.getUserLevel(),user.getUserExp(),user.getUserPvp(),
        user.getUserVip(),user.getUserInfo()
    );
  }

  @Override
  public User getUserByQq(long qq) {
    try {
      String sql = "SELECT " +
          "* " +
          "FROM " +
          "user  " +
          "WHERE " +
          "user_qq = ? ";
      return this.getJdbcTemplate().queryForObject(sql, new BeanPropertyRowMapper<>(User.class), qq);
    }catch (EmptyResultDataAccessException e){
      return null;
    }
  }
  @Override
  public Integer getMoneyByQq(long qq) {
    String sql = "SELECT " +
        "user_money " +
        "FROM " +
        "user  " +
        "WHERE " +
        "user_qq = ? ";
    return this.getJdbcTemplate().queryForObject(sql, new Object[]{qq}, Integer.class);
  }
  @Override
  public Integer setMoneyByQq(Integer money,long qq) {
    String sql = "UPDATE  " +
        "user " +
        "set " +
        "user_money = ?  " +
        "WHERE " +
        "user_qq = ? ";
    return this.getJdbcTemplate().update(sql, money,qq);
  }
  @Override
  public Integer addMoneyByQq(Integer money,long qq) {
    String sql = "UPDATE  " +
        "user " +
        "set " +
        "user_money = user_money + ?  " +
        "WHERE " +
        "user_qq = ? ";
    return this.getJdbcTemplate().update(sql, money,qq);
  }
  @Override
  public Integer subMoneyByQq(Integer money,long qq) {
    String sql = "UPDATE  " +
        "user " +
        "set " +
        "user_money = user_money - ?  " +
        "WHERE " +
        "user_qq = ? ";
    return this.getJdbcTemplate().update(sql, money,qq);
  }
  @Override
  public Integer setLevelByQq(Integer level,long qq) {
    String sql = "UPDATE  " +
        "user " +
        "set " +
        "user_level = ?  " +
        "WHERE " +
        "user_qq = ? ";
    return this.getJdbcTemplate().update(sql, level,qq);
  }
  @Override
  public Integer getExpByQq(long qq) {
    String sql = "SELECT " +
        "user_exp " +
        "FROM " +
        "user  " +
        "WHERE " +
        "user_qq = ? ";
    return this.getJdbcTemplate().queryForObject(sql, new Object[]{qq}, Integer.class);
  }
  @Override
  public Integer setExpByQq(Integer exp,long qq) {
    String sql = "UPDATE  " +
        "user " +
        "set " +
        "user_exp = ?  " +
        "WHERE " +
        "user_qq = ? ";
    return this.getJdbcTemplate().update(sql, exp,qq);
  }
  @Override
  public Integer addExpByQq(Integer exp,long qq) {
    String sql = "UPDATE  " +
        "user " +
        "set " +
        "user_exp = ?  " +
        "WHERE " +
        "user_qq = ? ";
    return this.getJdbcTemplate().update(sql, exp,qq);
  }
 /* @Override
  public List<User> getAll() {
    String sql = "SELECT " +
        "s.BUSI_SHEET_NO," +
        "s.COMPLAINT_WORKSHEET_ID," +
        "DATE_FORMAT(" +
        "w.createtime," +
        " '%Y-%m-%d %H:%i:%S' " +
        ") AS starttime " +
        "FROM " +
        "csp_busi_sheet s " +
        "LEFT JOIN wf_activityinst w ON s.CURR_ACTIVITY_ID = w.ACTIVITYINSTID " +
        "LEFT JOIN csp_sheet_comp_rsn_cfg r on s.duty_rsn=r.reason_id " +
        "WHERE " +
        "s.BUSI_SHEET_NO = ? ";
    List<User> list = this.getJdbcTemplate().query(sql, new Object[]{},
        new BeanPropertyRowMapper<>(User.class));
    return list;
  }

  @Override
  public User getUserByQq(long qq) {
    String sql = "SELECT " +
        "s.BUSI_SHEET_NO," +
        "s.COMPLAINT_WORKSHEET_ID," +
        "DATE_FORMAT(" +
        "w.createtime," +
        " '%Y-%m-%d %H:%i:%S' " +
        ") AS starttime " +
        "FROM " +
        "csp_busi_sheet s " +
        "LEFT JOIN wf_activityinst w ON s.CURR_ACTIVITY_ID = w.ACTIVITYINSTID " +
        "LEFT JOIN csp_sheet_comp_rsn_cfg r on s.duty_rsn=r.reason_id " +
        "WHERE " +
        "s.BUSI_SHEET_NO = ? ";
    return this.getJdbcTemplate().queryForObject(sql, new Object[]{qq}, User.class);
  }*/

}
