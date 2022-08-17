package net.lz1998.pbbot.stacklands.pojo.unit.monster;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;
import net.lz1998.pbbot.stacklands.pojo.Experience;
import net.lz1998.pbbot.stacklands.pojo.Food;
import net.lz1998.pbbot.stacklands.pojo.unit.StacklandsUnit;

@Data
@Accessors(chain = true)
public class StacklandsMonster extends StacklandsUnit implements Food, Experience {

  @Override
  public int dropFood() {
    return (getHealth()+1)/2;
  }

  @Override
  public int dropExperience() {
    return getHealth()+getAtk();
  }
}
