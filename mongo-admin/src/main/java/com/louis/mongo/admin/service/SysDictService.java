package com.louis.mongo.admin.service;

import com.louis.mongo.admin.model.SysDict;
import com.louis.mongo.core.service.CurdService;

import java.util.List;

/**
 * 字典管理
 */
public interface SysDictService extends CurdService<SysDict> {
    /**
     * 根据名称查询
     * @param lable
     * @return
     */
    List<SysDict> findByLable(String lable);
}
