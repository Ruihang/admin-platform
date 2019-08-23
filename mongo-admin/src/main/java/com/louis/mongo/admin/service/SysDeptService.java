package com.louis.mongo.admin.service;

import com.louis.mongo.admin.model.SysDept;
import com.louis.mongo.core.service.CurdService;

import java.util.List;

public interface SysDeptService extends CurdService<SysDept> {
    List<SysDept> findTree();
}
