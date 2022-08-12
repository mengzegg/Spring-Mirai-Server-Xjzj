package net.lz1998.pbbot.dao.users.impl;

import java.util.Date;
import java.util.List;
import java.util.Map;
import javax.annotation.Resource;
import net.lz1998.pbbot.dao.users.UserSiginDao;
import net.lz1998.pbbot.dto.users.Sigin;
import net.lz1998.pbbot.dto.users.User;
import net.lz1998.pbbot.utils.dao.JdbcDaoSupport4mysql;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class UserSiginDaoImpl extends JdbcDaoSupport4mysql implements UserSiginDao {

  @Resource
  private JdbcTemplate jdbcTemplate;
  @Resource
  private NamedParameterJdbcTemplate namedParameterJdbcTemplate;

  @Override
  public void insertSiginLog(Sigin sigin) {
    String sql = "INSERT INTO " +
        "user_sign_log(id,user_qq,sign_time,sign_money,sign_exp) " +
        "VALUES(null,?,now(),?,?) ";
    this.getJdbcTemplate().update(sql, sigin.getUserQq(),sigin.getSignMoney(),sigin.getSignExp());
  }
  /*取最后打卡日期*/
  @Override
  public Date getMaxSiginByQq(long qq) {
    try {
      String sql = "SELECT " +
          "max(sign_time) " +
          "FROM " +
          "user_sign_log  " +
          "WHERE " +
          "user_qq = ? ";
      return this.getJdbcTemplate().queryForObject(sql, new Object[]{qq}, Date.class);
    }catch (EmptyResultDataAccessException e){
      return null;
    }
  }
  @Override
  public List<Sigin> getAllSign(long qq) {
    try {
      String sql = "SELECT " +
          "* " +
          "FROM " +
          "user_sign_log  " +
          "WHERE " +
          "user_qq = ? ";
      List<Sigin> list = this.getJdbcTemplate().query(sql, new BeanPropertyRowMapper<>(Sigin.class),qq);
      return list;
    }catch (EmptyResultDataAccessException e){
      return null;
    }
  }
  @Override
  public List<Map<String, Object>> getSignRankList() {
    try {
      String sql = "SELECT user_qq qq,count(user_qq) count FROM user_sign_log  GROUP BY user_qq LIMIT 10";
      return this.jdbcTemplate.queryForList(sql);
    }catch (EmptyResultDataAccessException e){
      return null;
    }
  }

}
