package com.louis.mongo.admin.dao;

import com.louis.mongo.admin.model.SysMenu;

import java.util.List;

public interface SysMenuMapper {
    int deleteByPrimaryKey(Long id);

    int insert(SysMenu record);

    int insertSelective(SysMenu record);

    SysMenu selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(SysMenu record);

    int updateByPrimaryKey(SysMenu record);

    List<SysMenu> findByUserName(String userName);

    List<SysMenu> findAll();

    List<SysMenu> findRoleMenu(Long roleId);
}