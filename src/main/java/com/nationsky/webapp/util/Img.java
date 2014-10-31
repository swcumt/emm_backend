package com.nationsky.webapp.util;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.channels.FileChannel;
import java.util.UUID;

public class Img
{
  public static String receiveImg(String imgName, String imgPath, File source, float width, float height)
    throws Exception
  {
    String newimgName = null;
    try
    {
      File f = new File( imgPath);
      if (!f.exists()) {
        f.mkdirs();
      }
      
      String newName = getUUID();
      String tmpName = imgName.substring(imgName.lastIndexOf("."), 
        imgName.length());
      newimgName = newName + tmpName;
      FileInputStream fis = new FileInputStream(source);

      FileOutputStream fos = new FileOutputStream(imgPath+"/"+newimgName);

      FileChannel src = fis.getChannel();

      FileChannel tar = fos.getChannel();

      src.transferTo(0L, fis.available(), tar);

      tar.close();

      src.close();

      fos.close();

      fis.close();
    }
    catch (Exception ex) {
      ex.printStackTrace();
    }

    if ((width > 0.0F) || (height > 0.0F)) {
      ImageMini.createThumbnail(imgPath + newimgName,imgPath  + newimgName, width, height);
    } else {
      File yfile = new File(imgPath+ newimgName);
      File xfile = new File(imgPath+ newimgName);
      copyFile(yfile, xfile);
    }
    return newimgName;
  }

  public static boolean isPicture(String pImgeFlag)
    throws Exception
  {
    if (pImgeFlag == null) {
      return false;
    }

    String tmpName = pImgeFlag.substring(pImgeFlag.lastIndexOf(".") + 1, 
      pImgeFlag.length());

    String[][] imgeArray = { { "bmp", "0" }, { "dib", "1" }, 
      { "gif", "2" }, { "jfif", "3" }, { "jpe", "4" }, 
      { "jpeg", "5" }, { "jpg", "6" }, { "png", "7" }, 
      { "tif", "8" }, { "tiff", "9" }, { "ico", "10" } };

    for (int i = 0; i < imgeArray.length; i++)
    {
      if (imgeArray[i][0].equals(tmpName.toLowerCase())) {
        return true;
      }
    }
    return false;
  }

  public static void copyFile(File sourceFile, File targetFile) throws IOException
  {
    BufferedInputStream inBuff = null;
    BufferedOutputStream outBuff = null;
    try
    {
      inBuff = new BufferedInputStream(new FileInputStream(sourceFile));

      outBuff = new BufferedOutputStream(new FileOutputStream(targetFile));

      byte[] b = new byte[5120];
      int len;
      while ((len = inBuff.read(b)) != -1)
      {
        len = 0;
        outBuff.write(b, 0, len);
      }

      outBuff.flush();
    }
    finally {
      if (inBuff != null)
        inBuff.close();
      if (outBuff != null)
        outBuff.close();
    }
  }

  public static String getUUID() {
    String s = UUID.randomUUID().toString();

    return s.substring(0, 8) + s.substring(9, 13) + s.substring(14, 18) + s.substring(19, 23) + s.substring(24);
  }
}