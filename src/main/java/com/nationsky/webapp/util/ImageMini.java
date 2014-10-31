package com.nationsky.webapp.util;

import com.sun.image.codec.jpeg.JPEGCodec;
import com.sun.image.codec.jpeg.JPEGImageEncoder;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileOutputStream;
import java.io.PrintStream;
import javax.imageio.ImageIO;

public class ImageMini
{
  public static void createThumbnail(String src, String dist, float width, float height)
  {
    int newWidth = 0;
    int newHeight = 0;
    try {
      File srcfile = new File(src);
      BufferedImage image = ImageIO.read(srcfile);

      if ((width > 0.0F) && (height > 0.0F)) {
        newWidth = (int)width;
        newHeight = (int)height;
      } else if ((width > 0.0F) && (height <= 0.0F)) {
        double ratio = 1.0D;
        if (image.getWidth() > width) {
          if (image.getHeight() > width)
            ratio = image.getHeight() / width;
          else {
            ratio = width / image.getWidth();
          }

        }

        newWidth = (int)width;
        newHeight = (int)(image.getHeight() / ratio);
      } else if ((height > 0.0F) && (width <= 0.0F)) {
        double ratio = 1.0D;
        if (image.getHeight() > height) {
          if (image.getWidth() > height)
            ratio = image.getWidth() / height;
          else {
            ratio = height / image.getHeight();
          }

        }

        newWidth = (int)(image.getWidth() / ratio);
        newHeight = (int)height;
      }

      BufferedImage bfImage = new BufferedImage(newWidth, newHeight, 
        1);
      bfImage.getGraphics().drawImage(
        image.getScaledInstance(newWidth, newHeight, 
        4), 0, 0, null);

      FileOutputStream os = new FileOutputStream(dist);
      JPEGImageEncoder encoder = JPEGCodec.createJPEGEncoder(os);
      encoder.encode(bfImage);
      os.close();
      System.out.println("创建缩略图成功");
    } catch (Exception e) {
      System.out.println("创建缩略图发生异常" + e.getMessage());
    }
  }

  public static void main(String[] args) {
    createThumbnail("F:\\11111.jpg", "D:\\好心情2(2).jpg", 0.0F, 100.0F);
  }
}