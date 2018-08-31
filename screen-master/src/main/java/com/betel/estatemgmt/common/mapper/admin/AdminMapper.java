package com.betel.estatemgmt.common.mapper.admin;
import com.betel.estatemgmt.business.web.login.model.AdminAdmin;
import com.betel.estatemgmt.business.web.login.model.ReLoginInfo;
import com.betel.estatemgmt.common.model.admin.Admin;
import org.apache.ibatis.session.RowBounds;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AdminMapper {
    int deleteByPrimaryKey(String adminId);

    int insert(Admin record);

    int insertSelective(Admin record);

    int updateByPrimaryKeySelective(Admin record);

    int updateByPrimaryKey(Admin record);

    Admin selectByPrimaryKey(String adminId);

    Admin findByAdminId(String adminId);

    int deleteByAdminIds(String[] adminIds);

    ReLoginInfo findAdminLoginInfoById(String adminId);

    int updateAdminPwd(AdminAdmin adminAdmin);

    int resetPwdByAdminId(AdminAdmin adminAdmin);

    Admin findByAcctName(String acctName);
}