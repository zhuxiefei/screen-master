package com.betel.estatemgmt.common.mapper.admin;

import com.betel.estatemgmt.business.web.login.model.Menu;
import com.betel.estatemgmt.common.model.admin.AdminPrivilege;
import com.betel.estatemgmt.common.model.admin.AdminRolePriRela;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface AdminRolePriRelaMapper {
    int deleteByPrimaryKey(Long rpRela);

    int insert(AdminRolePriRela record);

    int insertSelective(AdminRolePriRela record);

    AdminRolePriRela selectByPrimaryKey(Long rpRela);

    int updateByPrimaryKeySelective(AdminRolePriRela record);

    int updateByPrimaryKey(AdminRolePriRela record);
    /**
     * <p>
     * 根据roleId查询管理员的菜单
     * </p>
     * Author: Cui.xx <br/>
     * Date: 2017/5/15 13:45
     * @param roleId
     * @return
     */
    List<Menu> findMenuListByRoleIdAndParentId(@Param("roleId") Long roleId, @Param("parentId") Long parentId);
    /**
     * <p>
     * 根据userId查询管理员的权限
     * </p>
     * Author: Cui.xx <br/>
     * Date: 2017/5/15 13:45
     * @param userId
     * @return
     */
    List<AdminPrivilege> findPermissionListByUserId(@Param("userId") String userId,@Param("estateId")String estateId);

    /**
     * <p>
     * 新增角色时，新增权限
     * </p>
     * Author: geyf <br/>
     * Date: 2017/8/2 14:28
     *
     * @param adminRolePriRelas 权限集合
     */
    void insertRolePrivileges(List<AdminRolePriRela> adminRolePriRelas);

    /**
     * <p>
     * 根据角色ID清除关系数据
     * </p>
     * Author: geyf <br/>
     * Date: 2017/8/2 15:18
     *
     * @param roleId 角色id
     */
    void deteleByRoleId(Long roleId);

    /**
     * 查询该角色的系统权限
     * @param roleId
     * @return
     */
    List<AdminPrivilege> findSystemByRoleId(Long roleId);

    /**
     * 根据一级菜单查询所有二级菜单
     * @param menus
     * @return
     */
    List<Menu> findChildMenu(List<Menu> menus);

    /**
     * 查询common权限
     * @param roleId
     * @return
     */
    Menu findCommon(Long roleId);
}