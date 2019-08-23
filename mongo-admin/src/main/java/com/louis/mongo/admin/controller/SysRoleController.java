package com.louis.mongo.admin.controller;

import com.louis.mongo.admin.constant.SysConstants;
import com.louis.mongo.admin.model.SysRole;
import com.louis.mongo.admin.model.SysRoleMenu;
import com.louis.mongo.admin.service.SysRoleService;
import com.louis.mongo.core.http.HttpResult;
import com.louis.mongo.core.page.PageRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("role")
public class SysRoleController {

    @Autowired
    private SysRoleService sysRoleService;

    @PostMapping(value = "/save")
    public HttpResult save(@RequestBody SysRole record){
        SysRole role = sysRoleService.findById(record.getId());
        if (role != null) {
            if (SysConstants.ADMIN.equalsIgnoreCase(role.getName())) {
                return HttpResult.error("超级管理员不允许修改！");
            }
        }
        //新增角色
        if ((record.getId() == null || record.getId() == 0) && !sysRoleService.findByName(record.getName()).isEmpty()) {
            return HttpResult.error("角色名称已存在！");
        }
        return HttpResult.ok(sysRoleService.save(record));
    }

    @PostMapping(value = "/delete")
    public HttpResult delete(@RequestBody List<SysRole> records){
        return HttpResult.ok(sysRoleService.delete(records));
    }

    @PostMapping(value = "/findPage")
    public HttpResult findPage(@RequestBody PageRequest pageRequest){
        return HttpResult.ok(sysRoleService.findPage(pageRequest));
    }

    @PostMapping(value = "/findAll")
    public HttpResult findAll(){
        return HttpResult.ok(sysRoleService.findAll());
    }

    @PostMapping(value = "/findRoleMenus")
    public HttpResult findRoleMenus(@RequestParam(value = "roleId") Long roleId){
        return HttpResult.ok(sysRoleService.findRoleMenus(roleId));
    }

    /**
     * 保存角色菜单
     * @param records
     * @return
     */
    @PostMapping(value = "/saveRoleMenus")
    public HttpResult saveRoleMenus(@RequestBody List<SysRoleMenu> records){
        for (SysRoleMenu record : records) {
            SysRole role = sysRoleService.findById(record.getRoleId());
            if (SysConstants.ADMIN.equalsIgnoreCase(role.getName())) {
                return HttpResult.error("超级管理员拥有所有菜单权限，不允许修改！");
            }
        }
        return HttpResult.ok(sysRoleService.saveRoleMenus(records));
    }
}
