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
}
