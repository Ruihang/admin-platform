package com.louis.mongo.admin.controller;

import com.louis.mongo.admin.model.SysDept;
import com.louis.mongo.admin.model.SysDict;
import com.louis.mongo.admin.service.SysDeptService;
import com.louis.mongo.core.http.HttpResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("dept")
public class SysDeptController {

    @Autowired
    private SysDeptService sysDeptService;

    @PostMapping(value = "/save")
    public HttpResult save(@RequestBody SysDept record){
        return HttpResult.ok(sysDeptService.save(record));
    }

    @PostMapping(value = "/delete")
    public HttpResult delete(@RequestBody List<SysDept> records){
        return HttpResult.ok(sysDeptService.delete(records));
    }

    @PostMapping(value = "/findTree")
    public HttpResult findTree(){
        return HttpResult.ok(sysDeptService.findTree());
    }
}
