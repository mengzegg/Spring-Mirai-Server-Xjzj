package net.lz1998.pbbot.utils.dao;

import java.util.List;
import java.util.Map;
import javax.annotation.Resource;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;


@Repository
public class JdbcDaoSupport4mysql {
  @Resource
  private JdbcTemplate jdbcTemplate;

  public JdbcDaoSupport4mysql() {
  }

  public final JdbcTemplate getJdbcTemplate() {
    return this.jdbcTemplate;
  }

  public List<Map<String, Object>> queryByPageForList(String sql, PageObj pageObj, Object... params) {
    int startIndex = (pageObj.getPage() - 1) * pageObj.getRows();
    pageObj.setTotal((Integer)this.getJdbcTemplate().queryForObject("select count(*) total from (" + sql + ")page_", params, Integer.class));
    String pageSql = "select page_.* from(" + sql + " )page_ limit " + startIndex + "," + pageObj.getRows();
    return this.jdbcTemplate.queryForList(pageSql, params);
  }

  public List<Map<String, Object>> queryByPageForList(String sql, PageObj pageObj) {
    int startIndex = (pageObj.getPage() - 1) * pageObj.getRows();
    pageObj.setTotal((Integer)this.getJdbcTemplate().queryForObject("select count(*) total from (" + sql + ")page_", Integer.class));
    String pageSql = "select page_.* from(" + sql + " )page_ limit " + startIndex + "," + pageObj.getRows();
    return this.jdbcTemplate.queryForList(pageSql);
  }

  public <T> List<T> queryByPage(String sql, Object[] params, RowMapper<T> rowMapper, PageObj pageObj) {
    int startIndex = (pageObj.getPage() - 1) * pageObj.getRows();
    pageObj.setTotal((Integer)this.getJdbcTemplate().queryForObject("select count(*) total from (" + sql + ") page_", params, Integer.class));
    String pageSql = "select page_.* from(" + sql + " )page_ limit " + startIndex + "," + pageObj.getRows();
    return this.jdbcTemplate.query(pageSql, params, rowMapper);
  }

  public <T> List<T> queryByPage(String sql, RowMapper<T> rowMapper, PageObj pageObj) {
    int startIndex = (pageObj.getPage() - 1) * pageObj.getRows();
    pageObj.setTotal((Integer)this.getJdbcTemplate().queryForObject("select count(*) total from (" + sql + ")page_", Integer.class));
    String pageSql = "select page_.* from(" + sql + " )page_ limit " + startIndex + "," + pageObj.getRows();
    return this.jdbcTemplate.query(pageSql, rowMapper);
  }
}
