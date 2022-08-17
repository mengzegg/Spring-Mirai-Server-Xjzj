package net.lz1998.pbbot.stacklands.pojo.unit.monster;

import com.fasterxml.jackson.annotation.JsonFormat;
import java.util.Date;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;
import net.lz1998.pbbot.stacklands.pojo.Experience;
import net.lz1998.pbbot.stacklands.pojo.Food;
import net.lz1998.pbbot.stacklands.pojo.Stacklands;
import net.lz1998.pbbot.stacklands.pojo.unit.StacklandsUnit;
import org.springframework.format.annotation.DateTimeFormat;

@Data
@Accessors(chain = true)
@NoArgsConstructor
@AllArgsConstructor
public class StacklandsUser extends StacklandsUnit implements Food, Experience {
  private Long userId;
  private Integer userType;//1.狩猎中2.修炼中
  private Integer agility;//敏捷
  private Integer food;//食物储备
  private Integer experience;//经验
  private Integer level;//等级
  private Integer libertyAttributes;//剩余自由属性点
  @DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss")
  @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
  private Date userTypeStartTime;
  @DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss")
  @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
  private Date userTypeOverTime;

  /*掉落食物并清空背包*/
  @Override
  public int dropFood() {
    int nowFood=getFood();
    setFood(0);
    return nowFood;
  }
  /*获得经验*/
  void addExperience(Integer addExperience){
    experience+=addExperience;
  }
  /**
   * 获得食物
   *
   * @param addFood 获得数量
   * @return 如果背包满了返回false
   */
  boolean addFood(Integer addFood){
    food+=addFood;
    if (food>=level*10){
      food=level*10;
      return false;
    }
    return true;
  }
  /*突破*/
  void addLevel(Integer addLevel){
    //此处传一个物品对象，需要大于当前等级，提升成功清空经验
    experience+=addLevel;
  }

  @Override
  public int dropExperience() {
    int nowExperience=getExperience();
    setExperience(0);
    return nowExperience/2;
  }




}
