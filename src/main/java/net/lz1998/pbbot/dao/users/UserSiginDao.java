package net.lz1998.pbbot.dao.users;

import java.util.Date;
import java.util.List;
import java.util.Map;
import net.lz1998.pbbot.dto.users.Sigin;

public interface UserSiginDao {
  void insertSiginLog(Sigin sigin);
  Date getMaxSiginByQq(long qq);
  List<Sigin> getAllSign(long qq);
  List<Map<String, Object>> getSignRankList();
}
