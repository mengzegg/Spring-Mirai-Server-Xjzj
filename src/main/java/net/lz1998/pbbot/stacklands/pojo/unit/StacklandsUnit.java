package net.lz1998.pbbot.stacklands.pojo.unit;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;
import net.lz1998.pbbot.stacklands.pojo.Experience;
import net.lz1998.pbbot.stacklands.pojo.Food;
import net.lz1998.pbbot.stacklands.pojo.Stacklands;

@Data
@Accessors(chain = true)
@NoArgsConstructor
@AllArgsConstructor
public class StacklandsUnit extends Stacklands implements Food, Experience {
  private Integer health;//血量
  private Integer nowHealth;//血量
  private Integer atk;//攻击力
  private Integer agility;//敏捷
  private Integer brains;//智力
  /**
   * lower Health
   *
   * @param lowerHealth 扣除的血量
   * @return 本次扣除的血量是否导致角色死亡，死亡：true
   */
  boolean lowerHealth(Integer lowerHealth){
    nowHealth-=lowerHealth;
    return isDie();
  }
  boolean isDie(){
    return nowHealth<=0;
  }

  @Override
  public int dropExperience() {
    return 0;
  }

  @Override
  public int dropFood() {
    return 0;
  }
  void moveTo(int toX,int toY){
    setWorldX(toX);
    setWorldY(toY);
  }
  public boolean addHealth(Integer addHealth){
    health+=addHealth;
    return true;
  }
  public boolean addNowHealth(Integer addNowHealth){
    nowHealth+=addNowHealth;
    return true;
  }
}
