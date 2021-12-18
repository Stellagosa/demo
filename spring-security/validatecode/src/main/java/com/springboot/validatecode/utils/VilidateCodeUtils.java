package com.springboot.validatecode.utils;

import com.springboot.validatecode.entity.ImageCode;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.Random;

public class VilidateCodeUtils {

    public static ImageCode createImageCode() {
        int width = 100; //验证码长度
        int height = 40; //验证码宽度
        int length = 4; //验证码字母数
        int expireInt = 60; //过期时间60s

        BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
        Graphics g = image.getGraphics();
        Random random = new Random();

        g.setColor(getRandomColor(200, 250));
        g.fillRect(0, 0, width, height);
        g.setFont(new Font("Times New Roman", Font.ITALIC, 30));
        g.setColor(getRandomColor(160, 200));
        for (int i = 0; i < 200; i++) {
            int x = random.nextInt(width);
            int y = random.nextInt(height);
            int x1 = random.nextInt(20);
            int y1 = random.nextInt(20);
            g.drawLine(x, y, x + x1, y + y1);
        }

        StringBuilder builder = new StringBuilder();
        for (int i = 0; i < length; i++) {
            String rand = String.valueOf(random.nextInt(10));
            builder.append(rand);
            g.setColor(getRandomColor(20, 130));
            g.drawString(rand, 22 * i + 6, 33);
        }

        g.dispose();
        return new ImageCode(image, builder.toString(), expireInt);
    }

    // 返回rgb值在 from ~ to 的随机颜色
    private static Color getRandomColor(int from, int to) {
        Random random = new Random();
        if (from > 255) from = 255;
        if (to > 255) to = 255;

        int r = from + random.nextInt(to - from);
        int g = from + random.nextInt(to - from);
        int b = from + random.nextInt(to - from);

        return new Color(r, g, b);
    }
}
