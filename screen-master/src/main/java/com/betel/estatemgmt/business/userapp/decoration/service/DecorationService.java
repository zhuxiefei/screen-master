package com.betel.estatemgmt.business.userapp.decoration.service;

import com.betel.estatemgmt.business.propertyapp.decoration.model.DoDecorationReq;
import com.betel.estatemgmt.business.propertyapp.decoration.model.QueryDecorationReq;
import com.betel.estatemgmt.business.userapp.decoration.model.AddDecorationReq;
import com.betel.estatemgmt.common.model.decoration.DecoApplyRecord;
import com.betel.estatemgmt.common.model.system.Notice;
import com.betel.estatemgmt.utils.pagination.model.Paging;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * <p>
 * 装修
 * </p>
 * ClassName: DecorationService <br/>
 * Author: cuixx  <br/>
 * Date: 2018/1/23 17:17 <br/>
 * Version: 1.0 <br/>
 */
public interface DecorationService {

    /**
     * <p>
     * 添加装修申请
     * </p>
     * Author: Cui.xx <br/>
     * Date: 2018/1/23 11:52
     * @param addDecorationReq
     */
    String addDecoration(AddDecorationReq addDecorationReq)throws Exception;

    /**
     * <p>
     * 查询我的装修申请列表
     * </p>
     * Author: Cui.xx <br/>
     * Date: 2018/1/25 8:59
     * @param userId
     * @return
     */
    List<DecoApplyRecord> findMyDecorationList(String userId,String estateId);

    /**
     * <p>
     * 查询装修申请详情
     * </p>
     * Author: Cui.xx <br/>
     * Date: 2018/1/25 9:00
     * @param recordId
     * @return
     */
    DecoApplyRecord findDecorationInfo(String recordId,String estateId);

    /**
     * <p>
     * 更新装修申请
     * </p>
     * Author: Cui.xx <br/>
     * Date: 2018/1/25 9:00
     * @param decoApplyRecord
     */
    void updateDecoration(DecoApplyRecord decoApplyRecord);

    /**
     * <p>
     * 用户再次提交装修申请
     * </p>
     * Author: Cui.xx <br/>
     * Date: 2018/1/25 9:01
     * @param addDecorationReq
     */
    void submitDecorationAgain(AddDecorationReq addDecorationReq);

    /**
     * <p>
     * 物业处理装修申请（同意、拒绝、取消）
     * </p>
     * Author: Cui.xx <br/>
     * Date: 2018/1/25 9:01
     * @param doDecorationReq
     * @param userId
     * @param type
     * @throws Exception
     */
    void doDecoration(DoDecorationReq doDecorationReq,String userId,String type,HttpServletRequest request)throws Exception;

    /**
     * <p>
     * 物业App查询所有的装修申请列表
     * </p>
     * Author: Cui.xx <br/>
     * Date: 2018/1/25 9:02
     * @param queryReq
     * @return
     */
    List<DecoApplyRecord> findAllDecorationList(QueryDecorationReq queryReq,Paging<DecoApplyRecord> pager);

    void insertNotices(List<Notice> notices);

    DecoApplyRecord findDecoration(String recordId);

    boolean houseIsDeleted(String houseId, String estateId);
}
