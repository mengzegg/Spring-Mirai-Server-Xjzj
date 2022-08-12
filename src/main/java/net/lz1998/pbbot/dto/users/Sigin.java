package net.lz1998.pbbot.dto.users;

import com.fasterxml.jackson.annotation.JsonFormat;
import java.io.Serializable;
import java.util.Date;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;
import org.springframework.format.annotation.DateTimeFormat;

@Data
@Accessors(chain = true)
@NoArgsConstructor
@AllArgsConstructor
public class Sigin  implements Serializable {
  private static final long serialVersionUID = 1L;
  private int id;
  private Long userQq;
  private Integer signMoney;
  private Integer signExp;
  @DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss")
  @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
  private Date signTime;
}
