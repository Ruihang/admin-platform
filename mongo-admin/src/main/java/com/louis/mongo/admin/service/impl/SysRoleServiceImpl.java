package com.louis.mongo.admin.service.impl;

import com.louis.mongo.admin.constant.SysConstants;
import com.louis.mongo.admin.dao.SysMenuMapper;
import com.louis.mongo.admin.dao.SysRoleMapper;
import com.louis.mongo.admin.dao.SysRoleMenuMapper;
import com.louis.mongo.admin.model.SysMenu;
import com.louis.mongo.admin.model.SysRole;
import com.louis.mongo.admin.model.SysRoleMenu;
import com.louis.mongo.admin.service.SysRoleService;
import com.louis.mongo.core.page.MybatisPageHelper;
import com.louis.mongo.core.page.PageRequest;
import com.louis.mongo.core.page.PageResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class SysRoleServiceImpl implements SysRoleService {

    @Autowired
    private SysRoleMapper sysRoleMapper;
    @Autowired
    private SysMenuMapper sysMenuMapper;
    @Autowired
    private SysRoleMenuMapper sysRoleMenuMapper;

    @Override
    public int save(SysRole record) {
        if (record.getId() == null || record.getId() == 0) {
            return sysRoleMapper.insertSelective(record);
        }
        return sysRoleMapper.updateByPrimaryKeySelective(record);
    }

    @Override
    public int delete(SysRole record) {
        return sysRoleMapper.deleteByPrimaryKey(record.getId());
    }

    @Override
    public int delete(List<SysRole> records) {
        for (SysRole record : records) {
            delete(record);
        }
        return 1;
    }

    @Override
    public SysRole findById(Long id) {
        return sysRoleMapper.selectByPrimaryKey(id);
    }

    @Override
    public PageResult findPage(PageRequest pageRequest) {
        return MybatisPageHelper.findPage(pageRequest,sysRoleMapper);
    }

    @Override
    public List<SysRole> findAll() {
        return sysRoleMapper.findAll();
    }

    @Override
    public List<SysRole> findByName(String name) {
        return sysRoleMapper.findByName(name);
    }

    @Override
    public List<SysMenu> findRoleMenus(Long roleId) {
        SysRole role = sysRoleMapper.selectByPrimaryKey(roleId);
        if (role !=null && SysConstants.ADMIN.equalsIgnoreCase(role.getName())) {
            return sysMenuMapper.findAll();
        }
        return sysMenuMapper.findRoleMenu(roleId);
    }

    @Transactional
    @Override
    public int saveRoleMenus(List<SysRoleMenu> records) {
        if (records == null || records.isEmpty()) {
            return 1;
        }
        Long roleId = records.get(0).getRoleId();
        sysRoleMenuMapper.deleteByRoleId(roleId);
        for (SysRoleMenu record : records) {
            sysRoleMenuMapper.insertSelective(record);
        }
        return 1;
    }
}
