package com.louis.mongo.admin.dao;

import com.louis.mongo.admin.model.SysDict;
import com.louis.mongo.admin.model.SysLoginLog;

import java.util.List;

public interface SysLoginLogMapper {
    int deleteByPrimaryKey(Long id);

    int insert(SysLoginLog record);

    int insertSelective(SysLoginLog record);

    SysLoginLog selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(SysLoginLog record);

    int updateByPrimaryKey(SysLoginLog record);

    /**
     * 分页查询
     * @return
     */
    List<SysDict> findPage();
}