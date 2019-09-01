package com.louis.mongo.backup.service.impl;

import com.louis.mongo.backup.util.MySqlBackupRestoreUtils;
import com.louis.mongo.backup.service.MysqlBackupService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class MysqlBackupServiceImpl implements MysqlBackupService {
    @Override
    public boolean backup(String host, String userName, String password, String backupFolderPath, String fileName, String database) throws Exception {
        return MySqlBackupRestoreUtils.backup(host,userName,password,backupFolderPath,fileName,database);
    }

    @Override
    public boolean restore(String restoreFilePath, String host, String userName, String password, String database) throws Exception {
        return MySqlBackupRestoreUtils.restore(restoreFilePath,host,userName,password,database);
    }
}
