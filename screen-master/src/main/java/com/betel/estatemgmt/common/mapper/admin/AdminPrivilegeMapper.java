package com.betel.estatemgmt.common.mapper.admin;

import com.betel.estatemgmt.business.oa.admin.model.PrivilegeInfo;
import com.betel.estatemgmt.common.model.admin.AdminPrivilege;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface AdminPrivilegeMapper {
    int deleteByPrimaryKey(Long privilegeId);

    int insert(AdminPrivilege record);

    int insertSelective(AdminPrivilege record);

    AdminPrivilege selectByPrimaryKey(Long privilegeId);

    int updateByPrimaryKeySelective(AdminPrivilege record);

    int updateByPrimaryKey(AdminPrivilege record);

    List<AdminPrivilege> findPrivilegesByParentId(Long id);


    /**
     * <p>
     * 根据ids查询权限信息
     * </p>
     * Author: zhouye <br/>
     * Date: 2017/8/18 10:31
     *return response
     */
    List<AdminPrivilege> findfindPrivilegesByIds(String[] ids);

    /**
     * 根据一级菜单查询所有二级菜单
     * @param menus
     * @return
     */
    List<PrivilegeInfo> findChildMenu(List<PrivilegeInfo> menus);

    /**
     * 查询所有系统权限
     * @return
     */
    List<PrivilegeInfo> findSystems(@Param("roleId") String roleId);
}