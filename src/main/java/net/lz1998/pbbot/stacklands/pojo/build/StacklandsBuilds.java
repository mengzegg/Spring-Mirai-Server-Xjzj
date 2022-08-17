package net.lz1998.pbbot.stacklands.pojo.build;

import java.awt.image.BufferedImage;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;
import net.lz1998.pbbot.stacklands.pojo.Food;
import net.lz1998.pbbot.stacklands.pojo.Stacklands;
@Data
@Accessors(chain = true)
public class StacklandsBuilds extends Stacklands implements Food {

  @Override
  public int dropFood() {
    return 1;
  }

}
