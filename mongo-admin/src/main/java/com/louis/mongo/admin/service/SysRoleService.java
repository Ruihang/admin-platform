package com.louis.mongo.admin.service;

import com.louis.mongo.admin.model.SysMenu;
import com.louis.mongo.admin.model.SysRole;
import com.louis.mongo.admin.model.SysRoleMenu;
import com.louis.mongo.core.service.CurdService;

import java.util.List;

public interface SysRoleService extends CurdService<SysRole> {
    List<SysRole> findAll();

    List<SysRole> findByName(String name);

    List<SysMenu> findRoleMenus(Long roleId);

    /**
     * 保存角色菜单
     * @param records
     * @return
     */
    int saveRoleMenus(List<SysRoleMenu> records);
}
