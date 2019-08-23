package com.louis.mongo.admin.controller;

import com.louis.mongo.admin.model.SysDict;
import com.louis.mongo.admin.model.SysLoginLog;
import com.louis.mongo.admin.service.SysLoginLogService;
import com.louis.mongo.core.http.HttpResult;
import com.louis.mongo.core.page.PageRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping(value = "loginlog")
public class SysLoginLogController {

    @Autowired
    private SysLoginLogService sysLoginLogService;

    @PostMapping(value = "/delete")
    public HttpResult delete(@RequestBody List<SysLoginLog> records){
        return HttpResult.ok(sysLoginLogService.delete(records));
    }

    @PostMapping(value = "/findPage")
    public HttpResult findPage(@RequestBody PageRequest pageRequest){
        return HttpResult.ok(sysLoginLogService.findPage(pageRequest));
    }
}
