package com.louis.mongo.common.utils;

import javax.servlet.http.HttpServletResponse;
import java.io.*;

public class FileUtils {
    public static void downloadFile(HttpServletResponse response, File file, String newFileName) {
        try {
            response.setHeader("Content-Disposition","attachment; fileName=" +
                    new String(newFileName.getBytes("ISO-8859-1"),"UTF-8"));
            InputStream is = new FileInputStream(file.getAbsoluteFile());
            BufferedInputStream bis = new BufferedInputStream(is);
            BufferedOutputStream bos = new BufferedOutputStream(response.getOutputStream());
            int length = 0;
            byte[] temp = new byte[1024 * 10];
            while ((length = bis.read(temp)) != -1) {
                bos.write(temp,0,length);
            }
            bos.flush();
            bis.close();
            bos.close();
            is.close();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void deleteFile(File file) {
        // 判断是否为目录，不是的话跳过，直接删除；如果是目录，先将目录清空
        if (file.isDirectory()) {
            // 获取目录下的文件/目录
            File[] subFiles = file.listFiles();
            // 遍历该目录
            for (File subFile : subFiles) {
                // 递归删除
                deleteFile(subFile);
            }
        }
        // 删除空目录或文件
        file.delete();
    }
}
