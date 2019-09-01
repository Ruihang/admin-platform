package com.louis.mongo.backup.util;

import java.io.File;
import java.io.IOException;

public class MySqlBackupRestoreUtils {
    public static boolean backup(String host, String userName, String password, String backupFolderPath, String fileName, String database) throws Exception {
        File backupFolderFile = new File(backupFolderPath);
        if (!backupFolderFile.exists()) {
            // 如果目录不存在则创建
            backupFolderFile.mkdirs();
        }
        // 如果目录结尾没有斜线，则追加斜线。
        if (!backupFolderPath.endsWith(File.separator) && !backupFolderPath.endsWith("/")) {
            backupFolderPath = backupFolderPath + File.separator;
        }
        // 拼接命令行的命令
        String backupFilePath = backupFolderPath + fileName;
        StringBuffer sb = new StringBuffer();
        sb.append("mysqldump --opt")
                .append(" --add-drop-database ")
                .append(" --add-drop-table ");
        sb.append(" -h").append(host)
                .append(" -u").append(userName)
                .append(" -p").append(password);
        sb.append(" --result-file=").append(backupFilePath)
                .append(" --default-character-set=utf8 ").append(database);
        System.out.println(sb.toString());
        // 调用外部执行exe文件的Api
        Process process = Runtime.getRuntime().exec(getCommand(sb.toString()));
        if (process.waitFor() == 0) {
            System.out.println("数据已经备份到 " + backupFolderPath + " 文件中");
            return true;
        }
        return false;
    }

    private static String[] getCommand(String command) {
        String os = System.getProperty("os.name");
        String shell = "/bin/bash";
        String c = "-c";
        if (os.toLowerCase().startsWith("win")) {
            shell = "cmd";
            c = "/c";
        }
        String[] cmd = {shell, c, command};
        return cmd;
    }

    /**
     * 还原数据库
     * @param restoreFilePath 数据库备份的脚本路径
     * @param host IP地址
     * @param userName 用户名
     * @param password 密码
     * @param database 数据库名称
     */
    public static boolean restore(String restoreFilePath, String host, String userName, String password, String database) {
        File restoreFile = new File(restoreFilePath);
        // 在文件目录下查找sql脚本文件
        if (restoreFile.isDirectory()) {
            for (File file : restoreFile.listFiles()) {
                if (file.exists() && file.getPath().endsWith(".sql")) {
                    restoreFilePath = file.getAbsolutePath();
                    break;
                }
            }
        }
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("mysql -h").append(host)
                .append(" -u").append(userName)
                .append(" -p").append(password)
                .append(" ").append(database).append("<").append(restoreFilePath);

        try {
            Process process = Runtime.getRuntime().exec(getCommand(stringBuilder.toString()));
            if (process.waitFor() == 0) {
                System.out.println("数据已从 " + restoreFilePath + " 导入到数据库中");
            }
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }
}
