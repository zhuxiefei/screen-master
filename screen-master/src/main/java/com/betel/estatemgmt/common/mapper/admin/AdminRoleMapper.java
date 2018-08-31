package com.betel.estatemgmt.common.mapper.admin;

import com.betel.estatemgmt.common.model.admin.AdminRole;

public interface AdminRoleMapper {
    int deleteByPrimaryKey(Long roleId);

    int insert(AdminRole record);

    int insertSelective(AdminRole record);

    AdminRole selectByPrimaryKey(Long roleId);

    int updateByPrimaryKeySelective(AdminRole record);

    int updateByPrimaryKey(AdminRole record);

    /**
     * <p>
     * 根据角色名称查询角色信息
     * </p>
     * Author: geyf <br/>
     * Date: 2017/8/2 15:04
     *
     * @param roleName 角色名称
     */
    AdminRole findRoleIsExistence(String roleName);
}