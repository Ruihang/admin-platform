package com.louis.mongo.admin.service;

import com.louis.mongo.admin.model.SysConfig;
import com.louis.mongo.core.service.CurdService;

import java.util.List;

public interface SysConfigService extends CurdService<SysConfig> {
    List<SysConfig> findByLable(String lable);
}
