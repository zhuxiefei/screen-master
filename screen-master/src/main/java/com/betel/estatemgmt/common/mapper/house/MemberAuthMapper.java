package com.betel.estatemgmt.common.mapper.house;

import com.betel.estatemgmt.business.userapp.auth.model.MemberAuthInfo;
import com.betel.estatemgmt.business.userapp.house.model.AuthHouse;
import com.betel.estatemgmt.common.model.house.MemberAuth;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface MemberAuthMapper {
    int deleteByPrimaryKey(Long authId);

    int insert(MemberAuth record);

    int insertSelective(MemberAuth record);

    MemberAuth selectByPrimaryKey(Long authId);

    int updateByPrimaryKeySelective(MemberAuth record);

    int updateByPrimaryKey(MemberAuth record);

    List<MemberAuth> selectByHouseId(String houseId);

    void deleteAuthList(List<Long> authIds);

    /**
     * <p>
     * 判断是否申请过改房屋
     * @param houseId 成员id
     * @param phoneNum 号码
     * </p>
     * Author: zhouye <br/>
     * Date: 2017/11/14 17:27
     */
    MemberAuth findMemberAuth(@Param("houseId") String houseId, @Param("phoneNum") String phoneNum);

    /**
     * <p>
     * 根据认证id查询认证信息
     * </p>
     * Author: zhouye <br/>
     * Date: 2017/11/14 22:37
     */
    MemberAuthInfo findMemberAuthInfo(Long authId);

    /**
     * 根据用户ID查询所有未删除的认证
     * @param userId
     * @return
     */
    List<MemberAuth> selectByUserId(String[] userId);

    /**
     * <p>
     * 根据userId和houseId查询待审核的认证
     * </p>
     * Author: Xia.yx <br/>
     * Date: 2017/11/16 14:33
     * */
    MemberAuth findUnMemberAuth(@Param("houseId") String houseId, @Param("userId") String userId);

    /**
     * 查询所有未删除的成员
     * @param userId
     * @return
     */
    List<AuthHouse> findMyHomeList(String userId);

    /**
     * <p>
     * 根据房屋ID查询待认证的成员
     * </p>
     * Author: Xia.yx <br/>
     * Date: 2017/11/17 14:20
     * */
    List<MemberAuth> findByHouseId(String houseId);

    int deletOldHouseMemberAuthHouseId(@Param("houseId") String houseId);
}