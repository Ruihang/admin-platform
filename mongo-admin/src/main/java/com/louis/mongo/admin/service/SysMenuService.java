package com.louis.mongo.admin.service;

import com.louis.mongo.admin.model.SysMenu;
import com.louis.mongo.core.service.CurdService;

import java.util.List;

public interface SysMenuService extends CurdService<SysMenu> {

    List<SysMenu> findTree(String userName, int menuType);

    List<SysMenu> findByUser(String userName);
}
