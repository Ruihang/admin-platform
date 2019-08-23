package com.louis.mongo.admin.dao;

import com.louis.mongo.admin.model.SysDict;
import com.louis.mongo.admin.model.SysLog;

import java.util.List;

public interface SysLogMapper {
    int deleteByPrimaryKey(Long id);

    int insert(SysLog record);

    int insertSelective(SysLog record);

    SysLog selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(SysLog record);

    int updateByPrimaryKey(SysLog record);

    /**
     * 分页查询
     * @return
     */
    List<SysDict> findPage();
}