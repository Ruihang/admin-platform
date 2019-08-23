package com.louis.mongo.core.page;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.util.ReflectionUtils;

import java.lang.reflect.Method;
import java.util.List;

/**
 * 分页查询助手
 */
public class MybatisPageHelper {

    public static final String findPage = "findPage";

    public static PageResult findPage(PageRequest pageRequest, Object mapper){
        return findPage(pageRequest,mapper,findPage);
    }

    public static PageResult findPage(PageRequest pageRequest, Object mapper, String queryMethodName, Object... args) {
        //设置分页参数
        int pageNum = pageRequest.getPageNum();
        int pageSize = pageRequest.getPageSize();
        PageHelper.startPage(pageNum,pageSize); //使用com.github.pagehelper依赖
        //利用反射调用查询方法
        Object result = ReflectionUtils
                .invokeMethod(getMethod(mapper,queryMethodName,args),mapper,args); //TODO 分页反射方法可能需要修改
        return getPageResult(pageRequest,new PageInfo((List) result));
    }

    private static Method getMethod(Object target, String methodName, Object... args){
        Class<?>[] parameterTypes;
        if (args == null) {
            parameterTypes = new Class<?>[0];
        } else {
            parameterTypes = new Class<?>[args.length];
            for (int i = 0; i < args.length; i++) {
                parameterTypes[i] = args[i].getClass();
            }
        }
       return ReflectionUtils.findMethod(target.getClass(),methodName,parameterTypes);
    }

    public static PageResult getPageResult(PageRequest pageRequest, PageInfo<?> pageInfo) {
        PageResult pageResult = new PageResult();
        pageResult.setPageNum(pageInfo.getPageNum());
        pageResult.setPageSize(pageInfo.getPageSize());
        pageResult.setTotalSize(pageInfo.getTotal());
        pageResult.setContent(pageInfo.getList());
        return pageResult;
    }
}
