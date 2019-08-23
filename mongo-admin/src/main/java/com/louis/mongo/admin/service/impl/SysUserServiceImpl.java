package com.louis.mongo.admin.service.impl;

import com.louis.mongo.admin.dao.SysRoleMapper;
import com.louis.mongo.admin.dao.SysUserMapper;
import com.louis.mongo.admin.dao.SysUserRoleMapper;
import com.louis.mongo.admin.model.SysMenu;
import com.louis.mongo.admin.model.SysRole;
import com.louis.mongo.admin.model.SysUser;
import com.louis.mongo.admin.model.SysUserRole;
import com.louis.mongo.admin.service.SysMenuService;
import com.louis.mongo.admin.service.SysRoleService;
import com.louis.mongo.admin.service.SysUserService;
import com.louis.mongo.common.utils.DateTimeUtils;
import com.louis.mongo.common.utils.PoiUtils;
import com.louis.mongo.core.page.MybatisPageHelper;
import com.louis.mongo.core.page.PageRequest;
import com.louis.mongo.core.page.PageResult;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.File;
import java.util.*;

@Service
public class SysUserServiceImpl implements SysUserService {

    @Autowired
    private SysUserMapper sysUserMapper;
    @Autowired
    private SysMenuService sysMenuService;
    @Autowired
    private SysRoleMapper sysRoleMapper;
    @Autowired
    private SysUserRoleMapper sysUserRoleMapper;

    @Override
    public List<SysUser> findAll() {
        return sysUserMapper.findAll();
    }

    @Override
    public List<SysUser> findByName(String name) {
        return sysUserMapper.findByName(name);
    }

    @Override
    public Set<String> findPermissions(String name) {
        Set<String> perms = new HashSet<>();
        List<SysMenu> menus = sysMenuService.findByUser(name);
        for (SysMenu menu : menus) {
            if (menu.getPerms() !=null && !"".equals(menu.getPerms())) {
                perms.add(menu.getPerms());
            }
        }
        return perms;
    }

    @Override
    public File createUserExcelFile(PageRequest pageRequest) {
        PageResult pageResult = findPage(pageRequest);
        return createUserExcelFile(pageResult.getContent());
    }

    private File createUserExcelFile(List<?> records) {
        if (records == null) {
            records = new ArrayList<>();
        }
        Workbook workbook = new XSSFWorkbook();
        Sheet sheet = workbook.createSheet();
        Row row0 = sheet.createRow(0);
        int columnIndex = 0;
        row0.createCell(columnIndex).setCellValue("No");
        row0.createCell(++columnIndex).setCellValue("ID");
        row0.createCell(++columnIndex).setCellValue("用户名");
        row0.createCell(++columnIndex).setCellValue("昵称");
        row0.createCell(++columnIndex).setCellValue("机构");
        row0.createCell(++columnIndex).setCellValue("角色");
        row0.createCell(++columnIndex).setCellValue("邮箱");
        row0.createCell(++columnIndex).setCellValue("手机号");
        row0.createCell(++columnIndex).setCellValue("状态");
        row0.createCell(++columnIndex).setCellValue("头像");
        row0.createCell(++columnIndex).setCellValue("创建人");
        row0.createCell(++columnIndex).setCellValue("创建时间");
        row0.createCell(++columnIndex).setCellValue("最后更新人");
        row0.createCell(++columnIndex).setCellValue("最后更新时间");
        for (int i = 0; i < records.size(); i++) {
            SysUser user = (SysUser) records.get(i);
            Row row = sheet.createRow(i+1);
            for (int j = 0; j < columnIndex; j++) {
                row.createCell(j);
            }
            columnIndex = 0;
            row.createCell(columnIndex).setCellValue(i + 1);
            row.createCell(++columnIndex).setCellValue(user.getId());
            row.createCell(++columnIndex).setCellValue(user.getName());
            row.createCell(++columnIndex).setCellValue(user.getNick_name());
            row.createCell(++columnIndex).setCellValue(user.getDeptName());
            row.createCell(++columnIndex).setCellValue(user.getRoleNames());
            row.createCell(++columnIndex).setCellValue(user.getEmail());
            row.createCell(++columnIndex).setCellValue(user.getMobile());
            row.createCell(++columnIndex).setCellValue(user.getStatus());
            row.createCell(++columnIndex).setCellValue(user.getAvatar());
            row.createCell(++columnIndex).setCellValue(user.getCreate_by());
            row.createCell(++columnIndex).setCellValue(DateTimeUtils.getDateTime(user.getCreate_time()));
            row.createCell(++columnIndex).setCellValue(user.getLast_update_by());
            row.createCell(++columnIndex).setCellValue(DateTimeUtils.getDateTime(user.getLast_update_time()));
        }
        return PoiUtils.createExcelFile(workbook, "download_user");
    }

    @Override
    public int save(SysUser record) {
        if (record.getId() == null || record.getId() == 0) {
            return sysUserMapper.insertSelective(record);
        }
        return sysUserMapper.updateByPrimaryKeySelective(record);
    }

    @Override
    public int delete(SysUser record) {
        return sysUserMapper.deleteByPrimaryKey(record.getId());
    }

    @Override
    public int delete(List<SysUser> records) {
        for (SysUser record : records) {
            delete(record);
        }
        return 1;
    }

    @Override
    public SysUser findById(Long id) {
        return null;
    }

    @Override
    public PageResult findPage(PageRequest pageRequest) {
        PageResult pageResult = null;
        Object name = pageRequest.getParam("name");
        Object email = pageRequest.getParam("email");
        if (name!=null) {
            if (email!=null) {
                System.out.println(email);
                pageResult = MybatisPageHelper.findPage(pageRequest,sysUserMapper,"findPageByNameAndEmail",name,email);
            } else  {
                System.out.println(name);
                pageResult = MybatisPageHelper.findPage(pageRequest,sysUserMapper,"findPageByName",name);
            }
        } else {
            pageResult = MybatisPageHelper.findPage(pageRequest, sysUserMapper);
        }
        findUserRoles(pageResult);
        return pageResult;
    }

    private void findUserRoles(PageResult pageResult) {
        List<?> content = pageResult.getContent();
        for (Object obj : content) {
            SysUser sysUser = (SysUser) obj;
            List<SysUserRole> sysUserRoles = findUserRoles(sysUser.getId());
            sysUser.setUserRoles(sysUserRoles);
            sysUser.setRoleNames(getRoleNames(sysUserRoles));
        }
    }

    private String getRoleNames(List<SysUserRole> sysUserRoles) {
        StringBuffer sb = new StringBuffer();
        for (Iterator<SysUserRole> iterator = sysUserRoles.iterator(); iterator.hasNext();) {
            SysUserRole sysUserRole = iterator.next();
            SysRole role = sysRoleMapper.selectByPrimaryKey(sysUserRole.getRoleId());
            if (role == null) {
                continue;
            }
            sb.append(role.getRemark());
            if (iterator.hasNext()) {
                sb.append(", ");
            }
        }
        return sb.toString();
    }

    private List<SysUserRole> findUserRoles(Long userId) {
        return sysUserRoleMapper.findByUserId(userId);
    }
}
