package com.louis.mongo.admin.dao;

import com.louis.mongo.admin.model.SysDict;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface SysDictMapper {
    int deleteByPrimaryKey(Long id);

    int insert(SysDict record);

    int insertSelective(SysDict record);

    SysDict selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(SysDict record);

    int updateByPrimaryKey(SysDict record);

    /**
     * 分页查询
     * @return
     */
    List<SysDict> findPage();

    /**
     * 根据标签名称查询
     * @param lable
     * @return
     */
    List<SysDict> findByLable(@Param(value="lable") String lable);

    /**
     * 根据标签名称分页查询
     * @param lable
     * @return
     */
    List<SysDict> findPageByLable(@Param(value = "lable") String lable);
}