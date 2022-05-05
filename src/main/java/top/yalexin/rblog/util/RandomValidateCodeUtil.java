package top.yalexin.rblog.util;


import org.apache.commons.codec.binary.Base64;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.stereotype.Component;
import sun.misc.BASE64Encoder;


import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.geom.Line2D;
import java.awt.image.BufferedImage;


import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.*;

public class RandomValidateCodeUtil {

    // 随机生成颜色
    static Color getRandColor(int start, int end) {
        Random random = new Random();
        if (start > 255)
            start = 255;
        if (end > 255)
            end = 255;
//        随机生成三个颜色通道的颜色值
        int r, g, b;
        r = start + random.nextInt(end - start);
        g = start + random.nextInt(end - start);
        b = start + random.nextInt(end - start);
        return new Color(r, g, b);
    }

    public static HashMap<String, String>getRandomValidateCode(int codeLength){
        // 图片宽度和高度
        int width = 80, height = 35;
        // 创建BufferedImage对象,其作用相当于一图片
        BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);

        // 画笔相关
        Graphics g = image.getGraphics();
        Graphics2D g2d = (Graphics2D) g;
        Font imgFont = new Font("Consolas", Font.BOLD, 25);
        g.setColor(getRandColor(200, 250));
        g.fillRect(0, 0, width, height);
        g.setFont(imgFont);
        g.setColor(getRandColor(180, 200));

        // 绘制100条随机产生的线条,用于造成背景干扰
        Random random = new Random();
        for (int i = 0; i < 100; i++) {
            int x = random.nextInt(width - 1);
            int y = random.nextInt(height - 1);
            int x1 = random.nextInt(6) + 1;
            int y1 = random.nextInt(12) + 1;
            BasicStroke bs = new BasicStroke(2f, BasicStroke.CAP_BUTT, BasicStroke.JOIN_BEVEL); // 定制线条样式
            Line2D line = new Line2D.Double(x, y, x + x1, y + y1);
            g2d.setStroke(bs);
            g2d.draw(line); // 绘制直线
        }

        // 随机生成指定长度的字符
        StringBuilder stringBuffer = new StringBuilder();
        // 随机字符集合中不应该包括0和o，O，1和l，因为这些不易区分
        final String codes = "23456789abcdefghijkmnpqrstuvwxyzABCDEFGHIJKMNPQRSTUVWXYXZ";
        final int alternativeLen = codes.length();

        for (int i = 0; i < codeLength; ++i) {
            int index = random.nextInt(alternativeLen);
            String sTemp = String.valueOf(codes.charAt(index));
            stringBuffer.append(sTemp);

            Color color = new Color(20 + random.nextInt(110), 20 + random.nextInt(110), random.nextInt(110));
            g.setColor(color);
            // 将生成的随机数进行随机缩放并旋转制定角度

            /* 将文字旋转制定角度 */
            Graphics2D g2d_word = (Graphics2D) g;
            AffineTransform trans = new AffineTransform();
            trans.rotate((20) * 3.14 / 180, 15 * i + 8, 7);

            /* 缩放文字 */
            float scaleSize = random.nextFloat() + 0.8f;
            if (scaleSize > 1f)
                scaleSize = 1f;
            trans.scale(scaleSize, scaleSize);
            g2d_word.setTransform(trans);
            // 坐标原点在左上角，往右是x的正方向，往下是y的正方向
            g.drawString(sTemp, 15 * i + 2, height / 2 + 3);

        }
        HashMap<String, String> map = new HashMap<>();
        map.put("code", stringBuffer.toString());
        System.out.println("code = " + stringBuffer.toString());
        map.put("base64img", bufferedImage2base64(image));
        return map;
    }

    // 将图片转为Base64字符串
    static String bufferedImage2base64(BufferedImage bufferedImage){

        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        try {
            //写入流中
            ImageIO.write(bufferedImage, "png", baos);
        } catch (IOException e) {
            e.printStackTrace();
        }
        //转换成字节
        byte[] bytes = baos.toByteArray();
        BASE64Encoder encoder = new BASE64Encoder();
        //转换成base64串
        String png_base64 = encoder.encodeBuffer(bytes).trim();
        png_base64 = png_base64.replaceAll("\n", "").replaceAll("\r", "");//删除 \r\n
        return "data:image/jpg;base64," + png_base64;
    }
}




