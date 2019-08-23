package com.louis.mongo.admin.service.impl;

import com.louis.mongo.admin.dao.SysDictMapper;
import com.louis.mongo.admin.model.SysDict;
import com.louis.mongo.admin.service.SysDictService;
import com.louis.mongo.core.page.MybatisPageHelper;
import com.louis.mongo.core.page.PageRequest;
import com.louis.mongo.core.page.PageResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SysDictServiceImpl implements SysDictService {

    @Autowired
    private SysDictMapper sysDictMapper;

    @Override
    public int save(SysDict record) {
        if (record.getId() == null || record.getId() == 0) {
            return sysDictMapper.insertSelective(record);
        }
        return sysDictMapper.updateByPrimaryKeySelective(record);
    }

    @Override
    public int delete(SysDict record) {
        return sysDictMapper.deleteByPrimaryKey(record.getId());
    }

    @Override
    public int delete(List<SysDict> records) {
        for (SysDict record : records) {
            delete(record);
        }
        return 1;
    }

    @Override
    public SysDict findById(Long id) {
        return sysDictMapper.selectByPrimaryKey(id);
    }

    @Override
    public PageResult findPage(PageRequest pageRequest) {
        Object lable = pageRequest.getParam("lable");
        if (lable != null) {
            return MybatisPageHelper.findPage(pageRequest,sysDictMapper,"findPageByLable",lable);
        }
        return MybatisPageHelper.findPage(pageRequest,sysDictMapper);
    }

    @Override
    public List<SysDict> findByLable(String lable) {
        return sysDictMapper.findByLable(lable);
    }
}
