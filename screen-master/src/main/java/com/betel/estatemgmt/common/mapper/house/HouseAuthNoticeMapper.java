package com.betel.estatemgmt.common.mapper.house;

import com.betel.estatemgmt.business.userapp.auth.model.AppMemberAuth;
import com.betel.estatemgmt.common.model.house.House;
import com.betel.estatemgmt.common.model.house.HouseAuthNotice;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.session.RowBounds;

import java.util.List;

public interface HouseAuthNoticeMapper {
    int deleteByPrimaryKey(Long noticeId);

    int insert(HouseAuthNotice record);

    int insertSelective(HouseAuthNotice record);

    HouseAuthNotice selectByPrimaryKey(Long noticeId);

    int updateByPrimaryKeySelective(HouseAuthNotice record);

    int updateByPrimaryKey(HouseAuthNotice record);

    void deleteByAuthId(Long authID);

    /**
     * <p>
     * 根据用户ID 房屋ID 查询通知信息
     * </p>
     * Author: geyf <br/>
     * Date: 2017/6/23 10:47
     *
     * @param
     * @return List<MemberAuth> 通知信息
     */
    List<AppMemberAuth> findAllAuthNotice(RowBounds rowBounds, @Param("noticeUserId") String noticeUserId, @Param("houses") List<House> houses);

    void deleteNoticeList(List<Long> list);

    /**
     * <p>
     * 查询认证未读数量
     * </p>
     * Author: geyf <br/>
     * Date: 2017/8/2 10:45
     *
     * @param userId 用户ID
     */
    int findAuthNoticeNumber(String userId);
}