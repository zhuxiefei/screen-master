package com.betel.estatemgmt.common.mapper.house;

import com.betel.estatemgmt.business.userapp.house.model.AuthHouse;
import com.betel.estatemgmt.business.userapp.house.model.Member;
import com.betel.estatemgmt.business.web.house.model.MemberInfo;
import com.betel.estatemgmt.business.web.housestatus.model.HouseStatusOwner;
import com.betel.estatemgmt.business.web.housestatus.model.HouseStatusReq;
import com.betel.estatemgmt.business.web.user.model.Members;
import com.betel.estatemgmt.common.model.house.HouseOwner;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface HouseOwnerMapper {
    int deleteByPrimaryKey(String ownerId);

    int insert(HouseOwner record);

    int insertSelective(HouseOwner record);

    HouseOwner selectByPrimaryKey(String ownerId);

    int updateByPrimaryKeySelective(HouseOwner record);

    int updateByPrimaryKey(HouseOwner record);

    HouseOwner selectByHouseId(String houseId);

    void deleteOwnerList(List<String> ownerIds);

    /**
     * <p>
     * 查询房屋户主
     * </p>
     * Author: zhouye <br/>
     * Date: 2017/11/14 19:27
     */
    Member findMemberByHouseId(String houseId);

    List<Members> selectByUserIds(String[] userIds);

    //    =======================房屋状态=============新增户主==================开始===================
    int addHouseStatusOwner(HouseStatusReq houseStatusReq);

    /**
     * 查询房屋下的所有户主信息
     *
     * @param houseId
     * @return
     * @author jians.z
     */
    List<HouseStatusOwner> findHouseStatusOwner(@Param("houseId") String houseId);

    /**
     * 查询激活和待激活
     *
     * @param houseStatusReq
     * @return
     */
    List<HouseStatusOwner> findHouseStatusOwnerOnLine(HouseStatusReq houseStatusReq);

    /**
     * 判断户主是否存在
     *
     * @param houseStatusReq
     * @return
     * @throws Exception
     * @author jians.z
     */
    HouseOwner isOwnerExist(HouseStatusReq houseStatusReq);

    List<HouseOwner> findOwner(String houseId);

    /**
     * 让房屋下的旧户主失效：状态为3
     *
     * @param ownerIds
     * @return
     * @throws Exception
     * @author jians.z
     */
    int setOldOwenerOver(List<String> ownerIds);

//    =======================房屋状态=============新增户主==================结束===================

    /**
     * <p>
     * 根据房屋 姓名 手机号 查询待激活的户主信息
     * </p>
     * Author: Xia.yx <br/>
     * Date: 2017/11/15 19:43
     */
    HouseOwner selectBySmsReq(String phone, String houseId, String ownerName, Integer status);

    /**
     * 查询所有已激活的户主
     *
     * @param userId
     * @return
     */
    List<AuthHouse> findMyHomeList(String userId);

    /**
     * <p>
     * 根据房屋ID判断是否存在户主
     * </p>
     * Author: Xia.yx <br/>
     * Date: 2017/11/17 13:05
     */
    HouseOwner findByHouseId(String houseId);

    /**
     * 查询户主
     * @param houseId
     * @return
     */
    MemberInfo findInfoByHouseId(String houseId);
}