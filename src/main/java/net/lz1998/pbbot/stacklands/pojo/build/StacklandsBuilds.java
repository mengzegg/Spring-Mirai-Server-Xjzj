package net.lz1998.pbbot.stacklands.pojo.build;

import java.awt.image.BufferedImage;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;
import net.lz1998.pbbot.stacklands.pojo.Experience;
import net.lz1998.pbbot.stacklands.pojo.Food;
import net.lz1998.pbbot.stacklands.pojo.Stacklands;
@Data
@Accessors(chain = true)
public class StacklandsBuilds extends Stacklands implements Food, Experience {
  /*剩余使用次数*/
  private Integer residueDegree;
  /*每次给多少食物*/
  private Integer seedFood;
  /*每次给多少经验*/
  private Integer seedExp;
  @Override
  public int dropFood() {
    return seedFood;
  }

  @Override
  public int dropExperience() {
    return seedExp;
  }
  public boolean isDie(){
    return residueDegree<=0;
  }
  public void dropResidueDegree(int dropResidueDegree){
    residueDegree-=dropResidueDegree;
  }
}
