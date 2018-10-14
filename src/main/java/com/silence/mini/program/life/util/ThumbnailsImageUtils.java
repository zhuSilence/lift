package com.silence.mini.program.life.util;

import net.coobird.thumbnailator.Thumbnails;
import net.coobird.thumbnailator.geometry.Positions;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.geom.Ellipse2D;
import java.awt.image.BufferedImage;
import java.awt.image.ConvolveOp;
import java.awt.image.Kernel;
import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * <br>
 * <b>功能：</b><br>
 * <b>作者：</b>@author Silence<br>
 * <b>日期：</b>2018-05-25 16:17<br>
 * <b>详细说明：</b>使用google开源工具Thumbnailator实现图片的一系列处理<br>
 */
public class ThumbnailsImageUtils {


    /**
     * 不按照比例，指定大小进行缩放
     *
     * @param sourceFilePath 原始图片路径
     * @param width          指定图片宽度
     * @param height         指定图片高度
     * @param targetFilePath 目标图片路径
     *                       keepAspectRatio(false) 默认是按照比例缩放的
     * @return 目标图片路径
     * @throws IOException
     */
    public static String size(String sourceFilePath, Integer width, Integer height, float quality, String targetFilePath) throws IOException {
        Thumbnails.of(sourceFilePath)
                .size(width, height)
                .outputQuality(quality)
                .keepAspectRatio(false)
                .toFile(targetFilePath);
        return targetFilePath;
    }


    /**
     * @param sourceFilePath 原始图片路径
     * @param width          指定图片宽度
     * @param height         指定图片高度
     * @param position       水印位置
     * @param waterFile      水印文件
     * @param opacity        水印透明度
     * @param quality        输出文件的质量
     * @param targetFilePath 目标图片路径
     * @return 目标图片路径
     * @throws IOException
     */
    public static String watermark(String sourceFilePath, Integer width, Integer height, Positions position, String waterFile, float opacity, float quality, String targetFilePath) throws IOException {
        Thumbnails.of(sourceFilePath)
                .size(width, height)
                .watermark(position, ImageIO.read(new File(waterFile)), opacity)
                .outputQuality(quality)
                .toFile(targetFilePath);
        return targetFilePath;
    }


    public static BufferedImage watermarkList(BufferedImage buffImg, int length, File[] waterFileArray) throws IOException {
        int x = 0;
        int y = 0;
        if (buffImg == null) {
            // 获取底图
            buffImg = new BufferedImage(1200, 1200, BufferedImage.SCALE_SMOOTH);
        } else {
            x = (length % 30) * 40;
            y = (length / 30) * 40;
        }
        // 创建Graphics2D对象，用在底图对象上绘图
        Graphics2D g2d = buffImg.createGraphics();
        // 将图像填充为白色
        g2d.setColor(Color.WHITE);
        g2d.fillRect(x, y, 1200, 40 * (waterFileArray.length + length));
        for (int i = 0; i < waterFileArray.length; i++) {
            // 获取层图
            BufferedImage waterImg = ImageIO.read(waterFileArray[i]);
            // 获取层图的宽度
            int waterImgWidth = waterImg.getWidth();
            // 获取层图的高度
            int waterImgHeight = waterImg.getHeight();
            // 在图形和图像中实现混合和透明效果
            g2d.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_ATOP, 1));
            // 绘制
            Integer j = i / 30;
            Integer index = i - j * 30;
            g2d.drawImage(waterImg, waterImgWidth * index + 1, waterImgHeight * j, waterImgWidth, waterImgHeight, null);

        }

        // 释放图形上下文使用的系统资源
        g2d.dispose();
        return buffImg;
    }

    /**
     * 按固定宽高缩放图片.
     *
     * @param inputPath
     * @param outputPath
     * @param width
     * @param height
     */
    public static void resize(String inputPath, String outputPath, int width, int height) throws Exception {
        Thumbnails.of(inputPath).size(width, height).toFile(outputPath);
    }

    public static void resizeImage(String inputPath, String outputPath, int width, int height) {
        File src = new File(inputPath);
        try {
            Image i = ImageIO.read(src);
            // 获取真实宽高
            int owidth = (int) i.getWidth(null);
            int oheight = (int) i.getHeight(null);

            if (owidth > width) {
                oheight = oheight / (owidth / width);
                owidth = width;
            } else {
                owidth = width;
            }
            if (oheight > height) {
                owidth = owidth / (oheight / height);
                oheight = height;
            } else {
                oheight = height;
            }

            Image resizedImage = i.getScaledInstance(owidth, oheight, Image.SCALE_SMOOTH);
            // This code ensures that all the pixels in the image are loaded.
            Image temp = new ImageIcon(resizedImage).getImage();
            // Create the buffered image.
            BufferedImage bufferedImage = new BufferedImage(temp.getWidth(null), temp.getHeight(null), BufferedImage.SCALE_SMOOTH);
            // Copy image to buffered image.
            Graphics g = bufferedImage.createGraphics();
            // Clear background and paint the image.
            g.setColor(Color.white);
            g.fillRect(0, 0, temp.getWidth(null), temp.getHeight(null));
            g.drawImage(temp, 0, 0, null);
            g.dispose();
            // Soften.
            float softenFactor = 0.01f;
            float[] softenArray = {0, softenFactor, 0, softenFactor, 1 - (softenFactor * 4), softenFactor, 0, softenFactor, 0};
            Kernel kernel = new Kernel(3, 3, softenArray);
            ConvolveOp cOp = new ConvolveOp(kernel, ConvolveOp.EDGE_NO_OP, null);
            bufferedImage = cOp.filter(bufferedImage, null);

            String formatName = outputPath.substring(outputPath.lastIndexOf(".") + 1);
            ImageIO.write(bufferedImage, formatName, new File(outputPath));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static String makeRoundedCorner(File srcImageFile, File result, int cornerRadius) {
        try {
            BufferedImage bi1 = ImageIO.read(srcImageFile);

            // 根据需要是否使用 BufferedImage.TYPE_INT_ARGB
            BufferedImage image = new BufferedImage(bi1.getWidth(), bi1.getHeight(),
                    BufferedImage.TYPE_INT_ARGB);

            Ellipse2D.Double shape = new Ellipse2D.Double(0, 0, bi1.getWidth(), bi1
                    .getHeight());

            Graphics2D g2 = image.createGraphics();
            image = g2.getDeviceConfiguration().createCompatibleImage(bi1.getWidth(), bi1.getHeight(), Transparency.TRANSLUCENT);
            g2 = image.createGraphics();
            g2.setComposite(AlphaComposite.Clear);
            g2.fill(new Rectangle(image.getWidth(), image.getHeight()));
            g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC, 1.0f));
            g2.setClip(shape);
            // 使用 setRenderingHint 设置抗锯齿
            g2 = image.createGraphics();
            g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
            g2.fillRoundRect(0, 0, bi1.getWidth(), bi1.getHeight(), cornerRadius, cornerRadius);
            g2.setComposite(AlphaComposite.SrcIn);
            g2.drawImage(bi1, 0, 0, bi1.getWidth(), bi1.getHeight(), null);
            g2.dispose();
            ImageIO.write(image, "png", result);
            return result.getAbsolutePath();
        } catch (Exception e) {
            // TODO: handle exception
        }
        return null;
    }

    /**
     * 从网络中下载图片保存本地
     *
     * @param targetUrl
     * @param outFile
     */
    public static void saveToFile(String targetUrl, String outFile) {
        FileOutputStream fos = null;
        BufferedInputStream bis = null;
        HttpURLConnection httpUrl = null;
        URL url = null;
        int BUFFER_SIZE = 1024;
        byte[] buf = new byte[BUFFER_SIZE];
        int size = 0;
        try {
            url = new URL(targetUrl);
            httpUrl = (HttpURLConnection) url.openConnection();
            httpUrl.connect();
            bis = new BufferedInputStream(httpUrl.getInputStream());
            fos = new FileOutputStream(outFile);
            while ((size = bis.read(buf)) != -1) {
                fos.write(buf, 0, size);
            }
            fos.flush();
        } catch (IOException e) {
        } catch (ClassCastException e) {
        } finally {
            try {
                fos.close();
                bis.close();
                httpUrl.disconnect();
            } catch (IOException e) {
            } catch (NullPointerException e) {
            }
        }
    }


    public static void addWaterMark(String srcImgPath, String tarImgPath, String waterMarkContent) {
        try {
            Font font = new Font("微软雅黑", Font.PLAIN, 55);
//            Font font = new Font("Arial", Font.PLAIN, 55);
            Color color=new Color(0,0,0,128);
            // 读取原图片信息
            File srcImgFile = new File(srcImgPath);
            Image srcImg = ImageIO.read(srcImgFile);
            int srcImgWidth = srcImg.getWidth(null);
            int srcImgHeight = srcImg.getHeight(null);
            // 加水印
            BufferedImage bufImg = new BufferedImage(srcImgWidth, srcImgHeight, BufferedImage.TYPE_INT_RGB);
            Graphics2D g = bufImg.createGraphics();
            g.drawImage(srcImg, 0, 0, srcImgWidth, srcImgHeight, null);
            g.setColor(color);
            g.setFont(font);
            //设置水印的坐标
            int x = srcImgWidth - 2 * getWatermarkLength(waterMarkContent, g);
            int y = 2 * getWatermarkLength(waterMarkContent, g);
            g.drawString(waterMarkContent, 200, 380);
            g.dispose();
            // 输出图片
            FileOutputStream outImgStream = new FileOutputStream(tarImgPath);
            ImageIO.write(bufImg, "png", outImgStream);
            outImgStream.flush();
            outImgStream.close();

        } catch (Exception e) {
            // TODO: handle exception
        }
    }

    private static int getWatermarkLength(String waterMarkContent, Graphics2D g) {
        return g.getFontMetrics(g.getFont()).charsWidth(waterMarkContent.toCharArray(), 0, waterMarkContent.length());
    }

    public static void main(String[] args) {
        String sourceFilePath = "/Users/silence/Downloads/广告素材/1920x1080.jpg";
//        String sourceFilePath = "/Users/silence/Downloads/广告素材/开机图片广告/1920*1080.png";
//        String targetFilePath = "/Users/silence/Downloads/1920*1080.jpg";
        String tmp = "/Users/silence/Downloads/1920*180";

        try {
            ThumbnailsImageUtils.size(sourceFilePath, 1920, 1080, 0.8f, "/Users/silence/Downloads/ddd1920*1080.jpg");
            ThumbnailsImageUtils.size(sourceFilePath, 1366, 768, 0.8f, "/Users/silence/Downloads/ddd1366*768.jpg");
            ThumbnailsImageUtils.size(sourceFilePath, 1280, 720, 0.8f, "/Users/silence/Downloads/ddd1280*720.jpg");
//            ThumbnailsImageUtils.changeFormat(sourceFilePath, "png", targetFilePath);
//            ThumbnailsImageUtils.changeFormat(sourceFilePath, 1280, 720, "png", tmp);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }


}
