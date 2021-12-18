package com.stellagosa.demo.seurity.session.utils;

import org.thymeleaf.util.StringUtils;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.Random;

public class ValidateCode {

    public static ImageCode createImage() {
        int width = 100;
        int height = 40;
        int length = 4;
        int fontSize = 30;
        int expireInt = 60;

        BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
        Graphics g = image.getGraphics();

        g.fillRect(0, 0, width, height);

        Random random = new Random();

        for (int i = 0; i < 20; i++) {
            int x = random.nextInt(width);
            int y = random.nextInt(height);
            int x1 = random.nextInt(width);
            int y1 = random.nextInt(height);
            g.setColor(getRandomColor(100, 200));
            g.drawLine(x, y, x1, y1);
        }

        g.setFont(new Font("Times New Roman", Font.ITALIC, fontSize));
        StringBuilder builder = new StringBuilder();
        for (int i = 0; i < length; i++) {
            String s = StringUtils.toString(random.nextInt(10));
            builder.append(s);
            int x = (width / length) * i + 6;
            int y = fontSize + random.nextInt(height - fontSize);
            g.setColor(getRandomColor(10, 100));
            g.drawString(s, x, y);
        }
        g.dispose();

        return new ImageCode(image, builder.toString(), expireInt);
    }

    private static Color getRandomColor(int from, int to) {
        Random random = new Random();

        int r = from + random.nextInt(to - from);
        int g = from + random.nextInt(to - from);
        int b = from + random.nextInt(to - from);

        return new Color(r, g, b);
    }
}
