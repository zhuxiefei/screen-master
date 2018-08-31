package com.betel.estatemgmt.business.web.auth.service.impl;

import com.betel.estatemgmt.business.web.auth.service.AuthService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * <p>
 * 文件说明
 * </p>
 * ClassName: AuthServiceImpl <br/>
 * Author: zhanglei  <br/>
 * Date: 2017/6/20 9:57 <br/>
 * Version: 1.0 <br/>
 */
@Service("AuthService")
@Transactional
public class AuthServiceImpl implements AuthService {
    private static final Logger LOG = LoggerFactory.getLogger(AuthServiceImpl.class);
//    @Autowired
//    private HouseAuthMapper houseAuthMapper;
//
//    @Autowired
//    private HouseMapper houseMapper;
//
//    @Autowired
//    private NoticeMapper noticeMapper;
//
//    @Autowired
//    private HouseMemberMapper houseMemberMapper;
//
//    @Autowired
//    private UserAccountMapper userAccountMapper;
//
//    @Autowired
//    private PictureMapper pictureMapper;
//
//
//    @Override
//    public List<HouseOwneAuth> findAllHouseOwneAuth(Paging paging, HouseOwnePageReq houseOwnePageReq) {
//        return houseAuthMapper.findAllHouseOwneAuth(paging.getRowBounds(), houseOwnePageReq);
//    }
//
//    @Override
//    public HouseOwneAuthInfo findHouseOwneAuthDetail(HouseAuth houseAuth) {
////        HouseOwneAuthInfo houseOwneAuthInfo = new HouseOwneAuthInfo();
////        houseAuth = houseAuthMapper.selectByPrimaryKey(houseAuth.getAuthId());
////        // 通过房屋ID，查询房屋信息
////        House house = houseMapper.selectByPrimaryKey(houseAuth.getHouseId());
////        //获取文件服务器路径
////        String path = HouseAuthConstant.PRIVACY_FILE_DIR_PATH;
////        // 认证中的户主认证，判断房屋是否存在户主，如果不存在户主，则原户主为空；如果存在，则原户主不为空
////        if(houseAuth.getAuthStatus()==HouseAuthConstant.AUTH_STATUS_UNDERWAY && houseAuth.getReviewStatus()==HouseAuthConstant.AUTH_STATUS_UNDERWAY){
////            if (!StringUtil.isEmpty(house.getHouseOwner())) {
////                // 通过房屋户主ID和房屋ID，查询房屋户主认证信息
////                HouseOwneInfo oldHouseOwneInfo = houseAuthMapper.findHouseAuthByUserIdAndHouseId(house.getHouseOwner(), house.getHouseId(), HouseAuthConstant.AUTH_STATUS_UNDERWAY,HouseAuthConstant.AUTH_STATUS_SUCCEED);
////                oldHouseOwneInfo.setIdBackSidePic(readTxt(path+oldHouseOwneInfo.getIdBackSidePic()));
////                oldHouseOwneInfo.setIdFrontSidePic(readTxt(path+oldHouseOwneInfo.getIdFrontSidePic()));
////                //获取房产证的图片
////                List<String> realEstatePics = new ArrayList<>();
////                String[] str= oldHouseOwneInfo.getRealEstatePic().split(",");
////                for (int i=0;i<str.length;i++) {
////                    Picture picture = pictureMapper.selectByPicId(str[i]);
////                    realEstatePics.add(readTxt(path+picture.getPictureUrl()));
////                }
////                oldHouseOwneInfo.setRealEstatePics(realEstatePics);
////                houseOwneAuthInfo.setOldHouseOwneInfo(oldHouseOwneInfo);
////            }
////        }
////        // 通过申请人ID和房屋ID，查询申请人信息
////        HouseOwneInfo newHouseOwneInfo = new HouseOwneInfo();
////        newHouseOwneInfo.setHouseOwneName(houseAuth.getApplicantName());
////        newHouseOwneInfo.setHouseOwneIdNum(houseAuth.getApplicantIdNum());
////        newHouseOwneInfo.setIdBackSidePic(readTxt(path+pictureMapper.selectByPrimaryKey(houseAuth.getIdBackSidePic()).getPictureUrl()));
////        newHouseOwneInfo.setIdFrontSidePic(readTxt(path+pictureMapper.selectByPrimaryKey(houseAuth.getIdFrontSidePic()).getPictureUrl()));
////        String[] realId = houseAuth.getRealEstatePic().split(",");
////        String realurl = "";
////        for (int i = 0; i < realId.length; i++) {
////            Picture picture = pictureMapper.selectByPrimaryKey(Long.valueOf(realId[i]));
////            if(picture!=null){
////                if(i == 0){
////                    realurl = readTxt(path+picture.getPictureUrl());//( zy --房产的图片url拼接)
////                }else{
////                    realurl = realurl+"-"+ readTxt(path+picture.getPictureUrl());
////                }
////            }
////        }
////        newHouseOwneInfo.setRealEstatePic(realurl);
////        houseOwneAuthInfo.setNewHouseOwneInfo(newHouseOwneInfo);
////        // 设置认证
////        houseOwneAuthInfo.setAuthId(houseAuth.getAuthId());
////        houseOwneAuthInfo.setReviewStatus(houseAuth.getReviewStatus());
////        houseOwneAuthInfo.setAuthStatus(houseAuth.getAuthStatus());
////        houseOwneAuthInfo.setReviewAdvice(houseAuth.getReviewAdvice());
////        return houseOwneAuthInfo;
//        return null;
//    }
//
//    /**
//     * <p>
//     * 读取txt
//     * </p>
//     * Author: Xia.yx <br/>
//     * Date: 2017/9/4 9:57
//     *
//     * @Param filePath 文件路径
//     * @Return 文件内容
//     *
//     * */
//    public String readTxt(String filePath){
//        StringBuilder result = null;
//        try {
//            File file = new File(filePath);
//            result = new StringBuilder();
//            BufferedReader br = new BufferedReader(new FileReader(file));
//            String s = null;
//            while ((s = br.readLine()) != null){
//                result.append(s);
//            }
//        } catch (IOException e) {
//            LOG.error("=========readTxt error=========",e);
//        }
//        return result.toString();
//    }
//
//    @Override
//    public HouseAuth findHouseAuth(Long authId) {
//        return houseAuthMapper.selectByPrimaryKey(authId);
//    }
//
//    @Override
//    public void dealWithAuth(DealWithAuth dealWithAuth) throws Exception {
////        // 通过认证ID，查询认证信息
////        HouseAuth houseAuth = houseAuthMapper.selectByPrimaryKey(dealWithAuth.getAuthId());
////        if(LOG.isDebugEnabled()){
////            LOG.debug("---------houseAuth-----------"+houseAuth);
////        }
////        // 通过房屋ID查询房屋信息
////        House house = houseMapper.selectByPrimaryKey(houseAuth.getHouseId());
////        if(LOG.isDebugEnabled()){
////            LOG.debug("---------house-----------"+house);
////        }
////        //保留房屋原房主id
////        String houseOwner = house.getHouseOwner();
////        Notice notice = new Notice();
////        notice.setCreateTime(new Date());
////        notice.setNoticeStatus(NOTICE_STATUS_UNREAD);
////        notice.setNoticeType(3);
////        notice.setNoticeUserId(houseAuth.getApplicantId());
////        String title = HouseConstant.MEMBER_SEND_TITLE;
////        StringBuilder sb = new StringBuilder();
////        sb.append("尊敬的" + houseAuth.getApplicantName() + "用户，");
////        //判断用户是否房屋成员
////        boolean flag = false;
////        if (AUTH_STATUS_SUCCEED == dealWithAuth.getAuthStatus()) {
////            sb.append("恭喜您已成功认证为" + dealAuth(houseAuth.getHouseId()) + "户主！");
////            // 修改房屋户主信息
////            house.setHouseOwner(houseAuth.getApplicantId());
////            houseMapper.updateByPrimaryKeySelective(house);
////            //验证申请人是否是房屋成员
////            HouseMember houseMember = houseMemberMapper.findHouseMemberByHouseIdAndUserId(houseAuth);
////            if(LOG.isDebugEnabled()){
////                LOG.debug("---------houseMember-----------"+houseMember);
////            }
////            if(null != houseMember){
////                //删除认证记录
////                houseAuthMapper.deleteAuthByMemberId(houseMember.getHmId());
////                //删除成员
////                houseMemberMapper.deleteByPrimaryKey(houseMember.getHmId());
////                flag = true;
////            }
////        } else {
////            sb.append("您认证" + dealAuth(houseAuth.getHouseId()) + "户主失败！");
////        }
////        sb.append(System.getProperty("line.separator"));
////        sb.append("管理员意见");
////        if(StringUtil.isEmpty(dealWithAuth.getAuthContent())){
////            sb.append("    " + dealWithAuth.getAuthContent());
////        } else{
////            sb.append("    " + dealWithAuth.getAuthContent().trim());
////        }
////        notice.setNoticeContent(IllegalwordUtil.wordFilter(sb.toString().trim()));
////        if(LOG.isDebugEnabled()){
////            LOG.debug("---------notice-----------"+notice);
////        }
////        noticeMapper.insertSelective(notice);
////        UserAccount userAccount = userAccountMapper.selectByUserId(notice.getNoticeUserId());
////        //发送通知
////        MsgPushUtils.push(userAccount.getAcctName(), sendAuth(notice, title));
////        if (AUTH_STATUS_SUCCEED == dealWithAuth.getAuthStatus() && !StringUtil.isEmpty(houseOwner)) {
////            notice = new Notice();
////            notice.setCreateTime(new Date());
////            notice.setNoticeStatus(NOTICE_STATUS_UNREAD);
////            notice.setNoticeType(NOTICE_TYPE_AUTH);
////            notice.setNoticeUserId(house.getHouseOwner());
////            String str = "您的" + dealAuth(houseAuth.getHouseId()) + "房子户主信息已变更，如有疑问，请及时联系物业";
////            notice.setNoticeContent(str);
////            noticeMapper.insertSelective(notice);
////            sendAuth(notice, title);
////            //查询原房主的信息
////            UserAccount userAccount1 = userAccountMapper.selectByUserId(houseOwner);
////            HouseAuth houseAuth1 = new HouseAuth();
////            houseAuth1.setApplicantId(houseOwner);
////            houseAuth1.setHouseId(house.getHouseId());
////            //删除原房主的认证记录
////            houseAuthMapper.deleteAuthByhouseIdAndAply(houseAuth1);
////            //发送信息给原房主
////            MsgPushUtils.push(userAccount1.getAcctName(), sendAuth(notice, title));
////            // 房屋变更，删除房屋成员关联
////            if(flag==false){
////                houseMemberMapper.deleteByHouseId(house.getHouseId());
////                houseAuthMapper.deleteByHouseId(house.getHouseId());
////            }
////        }
////        // 修改认证状态
////        houseAuth.setReviewStatus(dealWithAuth.getAuthStatus());
////        houseAuth.setReviewTime(new Date());
////        houseAuth.setReviewAdvice(dealWithAuth.getAuthContent());
////        houseAuth.setOperatorId(ActiveUserInfo.getActiveUserId());
////        if(LOG.isDebugEnabled()){
////            LOG.debug("---------houseAuth-----------"+houseAuth);
////        }
////        houseAuthMapper.updateByPrimaryKeySelective(houseAuth);
//    }
//
//    /**
//     * <p>
//     * 发送信息
//     * </p>
//     * Author: zhanglei <br/>
//     * Date: 2017/6/26 9:52
//     *
//     * @param notice 通知信息
//     * @param title 通知标题
//     */
//    private String sendAuth(Notice notice, String title) {
//        SendMessage sendMessage = new SendMessage();
//        sendMessage.setSendType(HouseAuthConstant.SEND_TYPE_AUTH);
//        sendMessage.setSendNo(HouseAuthConstant.SEND_NO_HOUSEOWNER);
//        sendMessage.setSendContent(notice.getNoticeContent());
//        sendMessage.setSendId(notice.getNoticeId().toString());
//        sendMessage.setSendTitle(title);
//       return GsonUtil.object2Gson(sendMessage);
//    }
///**
// * <p>
// * 返回房屋的xx楼宇xx单元xx号
// * </p>
// * Author: zhouye <br/>
// * Date: 2017/6/29 13:53
// *return String
// */
//    private String  dealAuth(Long houseId) {
//        HouseInformation houseInformation =  houseMapper.selectHouseInformationByHouseId(houseId);
//        String buildingName = houseInformation.getBuildingName();
//        String houseNum = houseInformation.getHouseNum();
//        String unitName = houseInformation.getUnitName();
//        if (StringUtil.isEmpty(buildingName)) {
//            buildingName = "";
//        }
//        if (StringUtil.isEmpty(unitName)) {
//            unitName = "";
//        }
//        if (StringUtil.isEmpty(houseNum)) {
//            houseNum = "";
//        }
//        return buildingName+unitName+houseNum;
//    }
}
