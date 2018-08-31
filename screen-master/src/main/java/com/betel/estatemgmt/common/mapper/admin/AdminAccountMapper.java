package com.betel.estatemgmt.common.mapper.admin;

import com.betel.estatemgmt.common.model.admin.AdminAccount;

public interface AdminAccountMapper {
    int deleteByPrimaryKey(String acctName);

    int insert(AdminAccount record);

    int insertSelective(AdminAccount record);

    int updateByPrimaryKeySelective(AdminAccount record);

    int updateByPrimaryKey(AdminAccount record);

    AdminAccount selectByPrimaryKey(String adminId);

    AdminAccount selectByAcctName(String acctName);

    void deleteAdminAccountByAdminIds(String[] adminIds);
}