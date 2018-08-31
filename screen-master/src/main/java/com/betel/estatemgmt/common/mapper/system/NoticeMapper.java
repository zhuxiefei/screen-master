package com.betel.estatemgmt.common.mapper.system;

import com.betel.estatemgmt.business.propertyapp.notice.model.SystemNoticeInfo;
import com.betel.estatemgmt.common.model.system.Notice;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.session.RowBounds;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface NoticeMapper {
    int deleteByPrimaryKey(Long noticeId);

    int insert(Notice record);

    int insertSelective(Notice record);
    int addNotice(List<Notice> noticeList);

    Notice selectByPrimaryKey(Long noticeId);

    int updateByPrimaryKeySelective(Notice record);

    int updateByPrimaryKey(Notice record);

    /**
     * <p>
     * 批量添加系统通知
     * </p>
     * Author: geyf <br/>
     * Date: 2017/7/17 15:06
     */
    int insertNotices(List<Notice> notices);


    /**
     * <p>
     * 查询系统通知列表
     * </p>
     * Author: geyf <br/>
     * Date: 2017/8/2 9:10
     *
     * @param userId 当前用户ID
     */
    List<SystemNoticeInfo> findAllSystemNotices(RowBounds rowBounds, @Param("userId") String userId);


    /**
     * <p>
     * 查询系统通知未读数
     * </p>
     * Author: geyf <br/>
     * Date: 2017/8/2 10:40
     *
     * @param userId 用户ID
     */
    int findSystemNoticeNumber(String userId);

    /**
     * ======================================内容审核========================开始====================================
     */
         int insertAuditNotice(List<Notice> notices);
    /**
     * ======================================内容审核========================结束====================================
     */

}