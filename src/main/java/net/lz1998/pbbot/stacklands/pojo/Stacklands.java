package net.lz1998.pbbot.stacklands.pojo;

import java.awt.image.BufferedImage;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
@NoArgsConstructor
@AllArgsConstructor
public class Stacklands {
  private Integer worldX;
  private Integer worldY;
  private Integer Type;//0.玩家1.丛林2.灵气地界3.怪物
  private BufferedImage image;
  private String imageName="村民.png";//图片名称如：村民.png
  private String name="村民";//名字
  private long id;//为角色时候是qq，建筑为数据库id
  //不保存数据库字段
  public Integer width = 280;
  public Integer height = 320;

  public boolean hit(Stacklands other) { // 碰撞算法

    int x1 = other.getWorldX() - this.getWidth() / 2;
    int x2 = other.getWorldX() + other.getWidth() + this.getWidth() / 2;
    int y1 = other.getWorldY() - this.getHeight() / 2;
    int y2 = other.getWorldY() + other.getHeight() + this.getHeight() / 2;
    return this.getWorldX() + this.getWidth() / 2 > x1 && this.getWorldX() + this.getWidth() / 2 < x2
        && this.getWorldY() + this.getHeight() / 2 > y1
        && this.getWorldY() + this.getWidth() / 2 < y2;
  }
  public boolean hitSee(Stacklands other) { // 可视算法
    if (this.getWorldX()-other.getWorldX()>500 || this.getWorldX()-other.getWorldX()<500){
      if (this.getWorldY()-other.getWorldY()>500 || this.getWorldY()-other.getWorldY()<500) {
        return true;
      }else {
        return false;
      }
    }else {
      return false;
    }
  }

}
