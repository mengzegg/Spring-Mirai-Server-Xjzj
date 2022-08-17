package net.lz1998.pbbot.controller;

import java.io.File;
import java.util.Map;
import net.lz1998.pbbot.stacklands.pojo.Stacklands;
import net.lz1998.pbbot.stacklands.pojo.build.StacklandsBuilds;
import net.lz1998.pbbot.stacklands.pojo.unit.StacklandsUnit;
import net.lz1998.pbbot.stacklands.pojo.unit.monster.StacklandsUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.http.MediaType;
import org.springframework.http.converter.BufferedImageHttpMessageConverter;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.net.URL;

@RestController
public class ImageController {
    @Autowired
    Map<Long,Stacklands> stacklandsMap;
    // 如果需要返回BufferedImage，必须有下面的converter。如果没有converter只能返回[]byte。
    @Bean
    public BufferedImageHttpMessageConverter bufferedImageHttpMessageConverter() {
        return new BufferedImageHttpMessageConverter();
    }

    // 生成图片，默认访问地址 http://localhost:8081/getImage?qq=10000
    @RequestMapping(value = "/getImage", produces = {MediaType.IMAGE_JPEG_VALUE, MediaType.IMAGE_GIF_VALUE, MediaType.IMAGE_PNG_VALUE})
    public BufferedImage getImage(@RequestParam(defaultValue = "10000") Long qq) throws IOException {
        // 创建400*300图片
        BufferedImage bufferedImage = new BufferedImage(100, 125, BufferedImage.TYPE_INT_RGB);
        // 获取画笔
        Graphics2D g = bufferedImage.createGraphics();

        // 背景填充白色
        g.setColor(Color.WHITE);
        g.fillRect(0, 0, bufferedImage.getWidth(), bufferedImage.getHeight());

        // 写QQ 黑色字体 字号30。中文可能需要自己加载字体文件
        g.setFont(new Font(null, Font.PLAIN, 15));
        g.setColor(Color.BLACK);
        g.drawString(qq.toString(), 10, 120);

        // 画头像
        g.drawImage(getAvatar(qq), 0, 0, null);
        return bufferedImage;
    }

    // 画头像
    public BufferedImage getAvatar(Long qq) throws IOException {
        URL url = new URL("https://q2.qlogo.cn/headimg_dl?dst_uin=" + qq.toString() + "&spec=3");
        return ImageIO.read(url);
    }
    // 画卡片，默认访问地址 http://localhost:8081/getStacklandsImage?imageName=123
    @RequestMapping(value = "/getStacklandsImage", produces = {MediaType.IMAGE_JPEG_VALUE, MediaType.IMAGE_GIF_VALUE, MediaType.IMAGE_PNG_VALUE})
    public BufferedImage getStacklandsImage(@RequestParam(defaultValue = "10000")long qq) throws IOException {
        Stacklands stacklands=stacklandsMap.get(qq);
        // 创建700*800图片
        BufferedImage bufferedImage = new BufferedImage(stacklands.getWidth(), stacklands.getHeight(), BufferedImage.TYPE_INT_RGB);
        // 获取画笔
        Graphics2D g = bufferedImage.createGraphics();
        // 背景填充绿色
        g.setColor(Color.green);
        g.fillRect(0, 0, bufferedImage.getWidth(), bufferedImage.getHeight());
        g.setFont(new Font(null, Font.PLAIN, 40));
        //如果是单位
        if (stacklands instanceof StacklandsUnit){
            StacklandsUnit stacklandsUnit=(StacklandsUnit)stacklands;
            // 画背景
            g.drawImage(getStacklandsAvatar("血量卡片.png"),0,0, null);
            //画血量
            g.setColor(Color.white);
            g.drawString(stacklandsUnit.getNowHealth()+"", 217, 285);
            //画攻击力
            g.setColor(Color.RED);
            g.drawString("\uD83D\uDDE1"+stacklandsUnit.getAtk()+"", 30, 285);
            if (stacklands instanceof StacklandsUser) {
                //画食物
                g.setColor(Color.black);
                g.setFont(new Font(null, Font.PLAIN, 40));
                g.drawString("肉:" + ((StacklandsUser) stacklands).getFood(), 180, 100);
            }
        }
        //如果是建筑
        if (stacklands instanceof StacklandsBuilds){
            StacklandsBuilds stacklandsBuilds=(StacklandsBuilds)stacklands;
            // 画背景
            g.drawImage(getStacklandsAvatar("空白卡片.png"),0,0, null);
            //画血量
            g.setColor(Color.black);
            //画备注
            //g.drawString("获得食物+1", 40, 285);
        }
        // 黑色字体 字号30。中文可能需要自己加载字体文件
        g.setColor(Color.BLACK);
        //画名字
        g.drawString(stacklands.getName()+"", 40, 50);
        //画角色
        g.drawImage(getStacklandsAvatar(stacklands.getImageName()),70 ,100 ,140,140, null);

        return bufferedImage;
    }

    // 画角色
    public BufferedImage getStacklandsAvatar(String imageName) throws IOException {
        File file = new File("C:\\Users\\13058\\Desktop\\jqr\\Xjzj\\images\\" + imageName);//本地图片
        return (BufferedImage)ImageIO.read(file);
    }
    // 生成图片，默认访问地址 http://localhost:8081/getStacklandsWorldImage?name=123
    @RequestMapping(value = "/getStacklandsWorldImage", produces = {MediaType.IMAGE_JPEG_VALUE, MediaType.IMAGE_GIF_VALUE, MediaType.IMAGE_PNG_VALUE})
    public BufferedImage getWorldImage(@RequestParam(defaultValue = "10000")long qq) throws IOException {
        // 创建700*800图片
        BufferedImage bufferedImage = new BufferedImage(2000, 2000, BufferedImage.TYPE_INT_RGB);
        // 获取画笔
        Graphics2D g = bufferedImage.createGraphics();
        // 背景填充绿色
        g.setColor(Color.green);
        g.fillRect(0, 0, bufferedImage.getWidth(), bufferedImage.getHeight());
        // 画背景
        g.drawImage(getStacklandsAvatar("世界背景.png"),0,0, 2000,2000,null);
        StacklandsUser user=(StacklandsUser) stacklandsMap.get(qq);
        for (Map.Entry<Long,Stacklands> entry : stacklandsMap.entrySet()) {
            System.out.println("key= " + entry.getKey() + " and value= " + entry.getValue());
            Stacklands stacklands=entry.getValue();
            if (user == stacklands){
                //如果是自己跳过
                continue;
            }
            if (user.getWorldX()-stacklands.getWorldX()>500 || user.getWorldX()-stacklands.getWorldX()<500){
                if (user.getWorldY()-stacklands.getWorldY()>500 || user.getWorldY()-stacklands.getWorldY()<500){
                    g.drawImage(getStacklandsWorldAvatar(stacklands.getId()),1000-user.getWidth()/2+ user
                        .getWorldX()-stacklands.getWorldX(), 1000-user.getHeight()/2 + user
                        .getWorldY()-stacklands.getWorldY(),null);
                    if (stacklands instanceof StacklandsBuilds){
                        if (user.hit(stacklands)){
                            //stacklandsMap.remove(stacklands.getId());
                            user.setFood(user.getFood()+((StacklandsBuilds) stacklands).dropFood());
                            g.setColor(Color.GREEN);
                            g.setFont(new Font(null, Font.PLAIN, 60));
                            g.drawString("获得食物"+((StacklandsBuilds) stacklands).dropFood(), 1000-user.getWidth()/2+ user
                                .getWorldX()-stacklands.getWorldX()+200, 1000-user.getHeight()/2 + user
                                .getWorldY()-stacklands.getWorldY()+100);
                        }
                    }
                }
            }
        }
        //画角色
        g.drawImage(getStacklandsWorldAvatar(qq), 1000-user.getWidth()/2, 1000-user.getHeight()/2, null);
        return bufferedImage;
    }

    // 画角色
    public BufferedImage getStacklandsWorldAvatar(long qq) throws IOException {
        URL url = new URL("http://localhost:8081/getStacklandsImage?qq=" + qq );
        return ImageIO.read(url);
    }
}
