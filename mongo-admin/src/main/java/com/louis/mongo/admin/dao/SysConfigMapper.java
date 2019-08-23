package com.louis.mongo.admin.dao;

import com.louis.mongo.admin.model.SysConfig;
import com.louis.mongo.admin.model.SysDict;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface SysConfigMapper {
    int deleteByPrimaryKey(Long id);

    int insert(SysConfig record);

    int insertSelective(SysConfig record);

    SysConfig selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(SysConfig record);

    int updateByPrimaryKey(SysConfig record);

    /**
     * 分页查询
     * @return
     */
    List<SysDict> findPage();

    List<SysConfig> findByLable(String lable);

    /**
     * 根据标签名称分页查询
     * @param lable
     * @return
     */
    List<SysConfig> findPageByLable(@Param(value = "lable") String lable);
}