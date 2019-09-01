package com.louis.mongo.admin.dao;

import com.louis.mongo.admin.model.SysUser;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface SysUserMapper {
    int deleteByPrimaryKey(Long id);

    int insert(SysUser record);

    int insertSelective(SysUser record);

    SysUser selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(SysUser record);

    int updateByPrimaryKey(SysUser record);

    /**
     * 查询全部
     * @return
     */
    List<SysUser> findAll();

    /**
     * 分页查询
     * @return
     */
    List<SysUser> findPage();

    /**
     * 根据名称查询
     * @return
     */
    SysUser findByName(@Param("name") String name);

    List<SysUser> findPageByNameAndEmail(@Param("name") String name,@Param("email") String email);

    List<SysUser> findPageByName(@Param("name") String name);
}