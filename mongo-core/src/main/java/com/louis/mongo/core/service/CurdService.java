package com.louis.mongo.core.service;

import com.louis.mongo.core.page.PageRequest;
import com.louis.mongo.core.page.PageResult;

import java.util.List;

public interface CurdService<T> {

    /**
     * 保持操作
     * @param record
     * @return
     */
    int save(T record);

    /**
     * 删除操作
     * @param record
     * @return
     */
    int delete(T record);

    /**
     * 批量删除操作
     * @param records
     * @return
     */
    int delete(List<T> records);

    /**
     * 根据ID查询
     * @param id
     * @return
     */
    T findById(Long id);

    PageResult findPage(PageRequest pageRequest);
}
