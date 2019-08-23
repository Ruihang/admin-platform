package com.louis.mongo.admin.controller;

import com.louis.mongo.admin.model.SysMenu;
import com.louis.mongo.admin.service.SysMenuService;
import com.louis.mongo.core.http.HttpResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("menu")
public class SysMenuController {

    @Autowired
    private SysMenuService sysMenuService;

    @PostMapping(value = "/save")
    public HttpResult save(@RequestBody SysMenu record){
        return HttpResult.ok(sysMenuService.save(record));
    }

    @PostMapping(value = "/delete")
    public HttpResult delete(@RequestBody List<SysMenu> records){
        return HttpResult.ok(sysMenuService.delete(records));
    }

    @PostMapping(value = "/findNavTree")
    public HttpResult findNavTree(@RequestParam(value = "userName") String userName){
        return HttpResult.ok(sysMenuService.findTree(userName, 1));
    }

    @PostMapping(value = "/findMenuTree")
    public HttpResult findMenuTree(){
        return HttpResult.ok(sysMenuService.findTree(null, 0));
    }
}
