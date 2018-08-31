package com.betel.estatemgmt.common.mapper.house;

import com.betel.estatemgmt.business.userapp.house.model.AuthHouse;
import com.betel.estatemgmt.common.model.house.HouseMemberRela;
import com.betel.estatemgmt.common.model.house.HouseMemberRelaKey;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface HouseMemberRelaMapper {
    int deleteByPrimaryKey(HouseMemberRelaKey key);

    int insert(HouseMemberRela record);

    int insertSelective(HouseMemberRela record);

    HouseMemberRela selectByPrimaryKey(HouseMemberRelaKey key);

    int updateByPrimaryKeySelective(HouseMemberRela record);

    int updateByPrimaryKey(HouseMemberRela record);

    void deleteRelaList(String[] houseIds);

    /**
     * <p>
     * 根据userId，查询是其成员的房屋
     * </p>
     * Author: zhouye <br/>
     * Date: 2017/11/14 15:55
     */
    List<AuthHouse> findAuthHouse(String userId);

    /**
     * <p>
     * 查询成员房屋关系表
     * </p>
     * Author: zhouye <br/>
     * Date: 2017/11/14 20:29
     */
    HouseMemberRela findByKey(String key);

    /**
     * <p>
     * 删除成员关系表
     * </p>
     * Author: zhouye <br/>
     * Date: 2017/11/14 21:02
     */
    void deleteByHouseIdAndMemberId(@Param("houseId") String houseId, @Param("memberId") String memberId);

    /**
     * 删除成员关系表
     *
     * @param houseId
     * @return
     */
    int deleteByHouseId(@Param("houseId") String houseId);

    /**
     * 删除房屋旧户主下旧成员关系
     *
     * @param houseId
     * @return
     * @author jians.z
     */
    int deleteOldMemberByHouseId(String houseId);

    void deleteByMemberIds(List<String> memberIds);
}