package com.louis.mongo.admin.service;

import com.louis.mongo.admin.model.SysUser;
import com.louis.mongo.core.page.PageRequest;
import com.louis.mongo.core.service.CurdService;

import java.io.File;
import java.util.List;
import java.util.Set;

public interface SysUserService extends CurdService<SysUser> {
    /**
     * 查找所有用户
     * @return
     */
    List<SysUser> findAll();

    /**
     * 根据名称查询
     * @return
     */
    SysUser findByName(String name);

    /**
     * 查询用户权限
     * @param name
     * @return
     */
    Set<String> findPermissions(String name);

    /**
     * 生成用户信息Excel文件
     * @param pageRequest 要导出的分页查询参数
     * @return
     */
    File createUserExcelFile(PageRequest pageRequest);
}
