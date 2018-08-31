package com.betel.estatemgmt.common.mapper.house;

import com.betel.estatemgmt.business.userapp.house.model.GetSmsReq;
import com.betel.estatemgmt.business.userapp.house.model.Member;
import com.betel.estatemgmt.business.web.house.model.MemberInfo;
import com.betel.estatemgmt.business.web.house.model.MemberNameSupport;
import com.betel.estatemgmt.business.web.housestatus.model.HouseStatusMember;
import com.betel.estatemgmt.business.web.housestatus.model.HouseStatusReq;
import com.betel.estatemgmt.business.web.user.model.Members;
import com.betel.estatemgmt.common.model.house.HouseMember;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface HouseMemberMapper {
    int deleteByPrimaryKey(String memberId);

    int insert(HouseMember record);

    int insertSelective(HouseMember record);

    HouseMember selectByPrimaryKey(String memberId);

    int updateByPrimaryKeySelective(HouseMember record);

    int updateByPrimaryKey(HouseMember record);

    List<HouseMember> selectHouseMemberByHouseId(String houseId);

    void deleteMemberList(List<String> memberIds);

    /**
     * 通过userid查询成员信息
     *
     * @param userIds
     * @return
     */
    List<Members> selectByUserIds(String[] userIds);

    /**
     * <p>
     * 查询房屋成员
     * </p>
     * Author: zhouye <br/>
     * Date: 2017/11/14 19:07
     */
    List<Member> findMemberByHouseId(String houseId);

    /**
     * <p>
     * 根据成员id查询房屋的户主
     * </p>
     * Author: zhouye <br/>
     * Date: 2017/11/14 20:10
     */
    String findHouseOwnerByMemberId(@Param("memberId") String memberId);


    int addHouseStatusMember(HouseStatusReq houseStatusReq);

    /**
     * 查询成员是否存在
     *
     * @param houseStatusReq
     * @return
     * @throws Exception
     * @author jians.z
     */
    int isMemberExist(HouseStatusReq houseStatusReq);

    int setOldMemberOver(String houseId);

    List<HouseStatusMember> findHouseStatusMember(String houseId);

    /**
     * <p>
     * 根据房屋 姓名 手机号 查询已激活的成员信息
     * </p>
     * Author: Xia.yx <br/>
     * Date: 2017/11/16 9:59
     */
    HouseMember findMemBySmsReq(GetSmsReq smsReq);
    //===========================房屋状态管理==============开始======================

    /**
     * 查询房屋下的旧成员
     *
     * @param houseId
     * @return
     * @throws Exception
     * @author jians.z
     */
    List<HouseMember> findOldMemberByHouseId(@Param("houseId") String houseId);

    /**
     * 查询房屋下的旧成员
     *
     * @param houseId
     * @return
     * @throws Exception
     * @author jians.z
     */
    List<HouseMember> findOldValidMemberByHouseId(@Param("houseId") String houseId);

    /**
     * 删除房屋旧户主下旧成员
     *
     * @param memberIds
     * @return
     * @author jians.z
     */
    int deleteOldMemberByMemberIds(List<String> memberIds);
    //===========================房屋状态管理==============结束======================

    /**
     * <p>
     * 根据手机号和房屋ID查询待激活的成员
     * </p>
     * Author: Xia.yx <br/>
     * Date: 2017/11/17 14:00
     */
    HouseMember findByHouseIdAndPhone(String phone, String houseId);

    List<Members> findMembersByHouseId(String houseId);

    List<MemberInfo> findInfoByHouseId(String houseId);
}