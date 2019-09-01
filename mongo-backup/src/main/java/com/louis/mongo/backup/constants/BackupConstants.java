package com.louis.mongo.backup.constants;

import java.io.File;

public interface BackupConstants {
    /** 默认备份还原目录名称 */
    String DEFAULT_BACKUP_NAME = "backup";
    /**日期格式*/
    String DATE_FORMAT = "yy-MM-dd_HHmmss";
    /** SQL扩展名称 */
    String SQL_EXT = ".sql";
    /** 默认备份文件名 */
    String BACKUP_FILE_NAME = "mongo" + SQL_EXT;
    /** 备份目录名称 */
    String BACKUP_FOLDER_NAME = "_mongo_backup";
    /** 备份目录 */
    String BACKUP_FOLDER = System.getProperty("user.home") + File.separator + BACKUP_FOLDER_NAME + File.separator;
    /** 还原目录，默认是备份目录 */
    String RESTORE_FOLDER = BACKUP_FOLDER;
    /** 默认备份还原文件名 */
    String DEFAULT_RESTORE_FILE = BACKUP_FOLDER + BACKUP_FOLDER_NAME + File.separator + BACKUP_FILE_NAME;
}
