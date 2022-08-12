package net.lz1998.pbbot.dto.users;

import com.fasterxml.jackson.annotation.JsonFormat;
import java.util.Date;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.jdbc.core.JdbcTemplate;

@Data
@Accessors(chain = true)
@NoArgsConstructor
@AllArgsConstructor
public class User {
  private Long userQq;
  private Integer userMoney;
  private Integer userLevel;
  private Integer userExp;
  private Integer userPvp;
  private Integer userVip;
  @DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss")
  @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
  private Date userInfo;

  @Override
  public String toString() {
    return
        "\n账户：" + userQq +
        "\n余额：" + userMoney +
        "\n当前等级：" + userLevel +
        "\n当前经验：" + userExp +
        /*", userPvp=" + userPvp +*/
        /*", userVip=" + userVip +*/
        "\n注册时间：" + userInfo.toString().substring(0,userInfo.toString().lastIndexOf("-")) ;
  }
}
