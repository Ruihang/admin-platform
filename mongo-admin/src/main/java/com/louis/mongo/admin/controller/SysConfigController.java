package com.louis.mongo.admin.controller;

import com.louis.mongo.admin.model.SysConfig;
import com.louis.mongo.admin.model.SysDict;
import com.louis.mongo.admin.service.SysConfigService;
import com.louis.mongo.admin.service.SysDictService;
import com.louis.mongo.core.http.HttpResult;
import com.louis.mongo.core.page.PageRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "config")
public class SysConfigController {
    @Autowired
    private SysConfigService sysConfigService;

    @PostMapping(value = "/save")
    public HttpResult save(@RequestBody SysConfig record){
        return HttpResult.ok(sysConfigService.save(record));
    }

    @PostMapping(value = "/delete")
    public HttpResult delete(@RequestBody List<SysConfig> records){
        return HttpResult.ok(sysConfigService.delete(records));
    }

    @PostMapping(value = "/findPage")
    public HttpResult findPage(@RequestBody PageRequest pageRequest){
        return HttpResult.ok(sysConfigService.findPage(pageRequest));
    }

    @GetMapping(value = "/findByLable")
    public HttpResult findByLable(@RequestParam String lable){
        return HttpResult.ok(sysConfigService.findByLable(lable));
    }
}
