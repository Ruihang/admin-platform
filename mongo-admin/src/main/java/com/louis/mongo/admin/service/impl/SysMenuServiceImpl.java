package com.louis.mongo.admin.service.impl;

import com.louis.mongo.admin.constant.SysConstants;
import com.louis.mongo.admin.dao.SysMenuMapper;
import com.louis.mongo.admin.model.SysMenu;
import com.louis.mongo.admin.service.SysMenuService;
import com.louis.mongo.core.page.PageRequest;
import com.louis.mongo.core.page.PageResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

@Service
public class SysMenuServiceImpl implements SysMenuService {

    @Autowired
    private SysMenuMapper sysMenuMapper;

    @Override
    public int save(SysMenu record) {
        if (record.getId() == null || record.getId() == 0) {
            return sysMenuMapper.insertSelective(record);
        }
        return sysMenuMapper.updateByPrimaryKeySelective(record);
    }

    @Override
    public int delete(SysMenu record) {
        return sysMenuMapper.deleteByPrimaryKey(record.getId());
    }

    @Override
    public int delete(List<SysMenu> records) {
        for (SysMenu record : records) {
            delete(record);
        }
        return 1;
    }

    @Override
    public SysMenu findById(Long id) {
        return null;
    }

    @Override
    public PageResult findPage(PageRequest pageRequest) {
        return null;
    }

    @Override
    public List<SysMenu> findTree(String userName, int menuType) {
        List<SysMenu> sysMenus = new ArrayList<>();
        List<SysMenu> menus = findByUser(userName);
        for (SysMenu menu : menus) {
            if (menu.getParent_id() == null || menu.getParent_id() == 0) {
                menu.setLevel(0);
                if (!exists(sysMenus,menu)) {
                    sysMenus.add(menu);
                }
            }
        }
        sysMenus.sort(Comparator.comparing(SysMenu::getOrder_num));
        findChildren(sysMenus,menus,menuType);
        return sysMenus;
    }

    @Override
    public List<SysMenu> findByUser(String userName) {
        if (userName == null || "".equals(userName) || SysConstants.ADMIN.equals(userName)) {
            return sysMenuMapper.findAll();
        }
        return sysMenuMapper.findByUserName(userName);
    }

    private void findChildren(List<SysMenu> sysMenus, List<SysMenu> menus, int menuType) {
        for (SysMenu sysMenu : sysMenus) {
            List<SysMenu> children = new ArrayList<>();
            for (SysMenu menu : menus) {
                if (menuType == 1 && menu.getType() == 2) continue;
                if (menu.getParent_id() != null && menu.getParent_id().equals(sysMenu.getId())) {
                    menu.setParentName(sysMenu.getName());
                    menu.setLevel(sysMenu.getLevel() + 1);
                    if (!exists(sysMenus,menu)) {
                        children.add(menu);
                    }
                }
            }
            sysMenu.setChildren(children);
            children.sort(Comparator.comparing(SysMenu::getOrder_num));
            findChildren(children, menus,menuType);
        }
    }

    private boolean exists(List<SysMenu> menus, SysMenu menu) {
        for (SysMenu sysMenu : menus) {
            if (sysMenu.getId().equals(menu.getId())){
                return true;
            }
        }
        return false;
    }


}
