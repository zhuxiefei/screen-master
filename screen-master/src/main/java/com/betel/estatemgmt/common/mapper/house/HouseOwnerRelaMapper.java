package com.betel.estatemgmt.common.mapper.house;

import com.betel.estatemgmt.business.userapp.house.model.AuthHouse;
import com.betel.estatemgmt.common.model.house.HouseOwnerRela;
import com.betel.estatemgmt.common.model.house.HouseOwnerRelaKey;

import java.util.List;

public interface HouseOwnerRelaMapper {
    int deleteByPrimaryKey(HouseOwnerRelaKey key);

    int insert(HouseOwnerRela record);

    int insertSelective(HouseOwnerRela record);

    HouseOwnerRela selectByPrimaryKey(String memberId);

    int updateByPrimaryKeySelective(HouseOwnerRela record);

    int updateByPrimaryKey(HouseOwnerRela record);

    void deleteRelaList(String[] houseIds);

    /**
     * <p>
     * 查询用户是户主的房屋
     * </p>
     * Author: zhouye <br/>
     * Date: 2017/11/14 17:59
     */
    List<AuthHouse> findAuthHouse(String userId);

    /**
     * <p>
     * 根据ownerId查询房屋信息
     * </p>
     * Author: Xia.yx <br/>
     * Date: 2017/11/23 10:50
     * */
    String findByOwnerId(String ownerId);

    void deleteByOwnerIds(List<String> ownerIds);
}