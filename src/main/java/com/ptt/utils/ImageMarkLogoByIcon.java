package com.ptt.utils;

import org.junit.Test;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStream;

public class ImageMarkLogoByIcon {

    /**
     * 给图片添加水印
     * @param iconPath 水印图片路径
     * @param srcImgPath 源图片路径
     * @param targerPath 目标图片路径
     */
    public static void markImageByIcon(String iconPath, String srcImgPath,
                                       String targerPath) {
        markImageByIcon(iconPath, srcImgPath, targerPath, null);
    }

    /**
     * 给图片添加水印、可设置水印图片旋转角度
     * @param iconPath 水印图片路径
     * @param srcImgPath 源图片路径
     * @param targerPath 目标图片路径
     * @param degree 水印图片旋转角度
     */
    public static void markImageByIcon(String iconPath, String srcImgPath,
                                       String targerPath, Integer degree) {

        OutputStream os = null;
        try {
            Image srcImg = ImageIO.read(new File(srcImgPath));


            BufferedImage buffImg = new BufferedImage(srcImg.getWidth(null),
                    srcImg.getHeight(null), BufferedImage.TYPE_INT_RGB);

            // 得到画笔对象
            // Graphics g= buffImg.getGraphics();
            Graphics2D g = buffImg.createGraphics();

            // 设置对线段的锯齿状边缘处理
            g.setRenderingHint(RenderingHints.KEY_INTERPOLATION,
                    RenderingHints.VALUE_INTERPOLATION_BILINEAR);

            g.drawImage(srcImg.getScaledInstance(srcImg.getWidth(null), srcImg
                    .getHeight(null), Image.SCALE_SMOOTH), 0, 0, null);

            if (null != degree) {

                g.rotate(Math.toRadians(degree),
                        (double) buffImg.getWidth() / 2,
                        (double) buffImg.getHeight() / 2);

                // 设置水印旋转
                g.rotate(Math.toRadians(degree),
                        (double) buffImg.getWidth() / 2, (double) buffImg
                                .getHeight() / 2);
            }

            // 水印图象的路径 水印一般为gif或者png的，这样可设置透明度
            ImageIcon imgIcon = new ImageIcon(iconPath);

            // 得到Image对象。
            Image img = imgIcon.getImage();

//            int watermarkImageWidth=img.getWidth(null);
//            int watermarkImageHeight=img.getHeight(null);
//            double dmarkWidth = width*0.4;// 设置水印的宽度为图片宽度的0.4倍
//            double dmarkHeight = dmarkWidth * ((double)watermarkImageHeight/(double)watermarkImageWidth);//强转为double计算宽高比例
//            int imarkWidth = (int) dmarkWidth;


            float alpha = 0.5f; // 透明度
            g.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_ATOP,
                    alpha));

            // 表示水印图片的位置
            g.drawImage(img, 150, 300, null);

            g.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER));

            g.dispose();

            os = new FileOutputStream(targerPath);

            // 生成图片
            ImageIO.write(buffImg, "JPG", os);

            System.out.println("图片完成添加Icon印章。。。。。。");
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (null != os)
                    os.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * @param args
     */

    /**
     * 给图片添加水印、可设置水印图片旋转角度
     * @iconPath 水印图片路径
     * @srcImgPath 源图片路径
     * @targerPath 加水印后的目标图片路径
     * @degree 水印图片旋转角度
     *
     */
    @Test
    public static void main(String[] args) {
        String srcImgPath = "D:\\IDEA_Projects\\JAVA\\xzy-task7\\src\\main\\webapp\\image\\明星1.jpg";
        String iconPath = "D:\\IDEA_Projects\\JAVA\\xzy-task7\\src\\main\\webapp\\image\\aa.jpg";
        String targerPath = "D:\\IDEA_Projects\\JAVA\\xzy-task7\\src\\main\\webapp\\image\\水印.jpg";
        String targerPath2 = "D:\\IDEA_Projects\\JAVA\\xzy-task7\\src\\main\\webapp\\image\\水印1.jpg";
        // 给图片添加水印
        ImageMarkLogoByIcon.markImageByIcon(iconPath, srcImgPath, targerPath);
        // 给图片添加水印,水印旋转-45
        ImageMarkLogoByIcon.markImageByIcon(iconPath, srcImgPath, targerPath2,
                -45);

    }


}
