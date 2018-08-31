package com.betel.estatemgmt.business.propertyapp.notice.model;

import java.util.Date;

/**
 * <p>
 * 用户查询通知列表返回的数据接收类
 * </p>
 * ClassName: ReplyNoticeVo <br/>
 * Author: geyf  <br/>
 * Date: 2017/5/16 14:13 <br/>
 * Version: 1.0 <br/>
 */
public class ReplyNoticeVo {

    private String authorId;

    public String getAuthorId() {
        return authorId;
    }

    public void setAuthorId(String authorId) {
        this.authorId = authorId;
    }

    private Long noticeId;

    private Long postId;

    private String fromName;

    private String fromHead;

    private String topicTitle;

    private String replyBrief;

    private Date createTime;

    private Integer position;

    private Integer noticeStatus;

    private Integer noticeType;

    public Integer getNoticeType() {
        return noticeType;
    }

    public void setNoticeType(Integer noticeType) {
        this.noticeType = noticeType;
    }

    public Integer getNoticeStatus() {
        return noticeStatus;
    }

    public void setNoticeStatus(Integer noticeStatus) {
        this.noticeStatus = noticeStatus;
    }

    public Integer getPosition() {
        return position;
    }

    public void setPosition(Integer position) {
        this.position = position;
    }

    public Long getNoticeId() {
        return noticeId;
    }

    public void setNoticeId(Long noticeId) {
        this.noticeId = noticeId;
    }

    public Long getPostId() {
        return postId;
    }

    public void setPostId(Long postId) {
        this.postId = postId;
    }

    public String getFromName() {
        return fromName;
    }

    public void setFromName(String fromName) {
        this.fromName = fromName;
    }

    public String getFromHead() {
        return fromHead;
    }

    public void setFromHead(String fromHead) {
        this.fromHead = fromHead;
    }

    public String getTopicTitle() {
        return topicTitle;
    }

    public void setTopicTitle(String topicTitle) {
        this.topicTitle = topicTitle;
    }

    public String getReplyBrief() {
        return replyBrief;
    }

    public void setReplyBrief(String replyBrief) {
        this.replyBrief = replyBrief;
    }

    public Date getCreateTime() {
        if(createTime == null){
            return null;
        }
        return (Date)createTime.clone();
    }

    public void setCreateTime(Date createTime) {
        if(createTime == null){
            this.createTime = null;
        }else {
            this.createTime = (Date)createTime.clone();
        }

    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("ReplyNoticeVo{");
        sb.append("authorId='").append(authorId).append('\'');
        sb.append(", noticeId=").append(noticeId);
        sb.append(", postId=").append(postId);
        sb.append(", fromName='").append(fromName).append('\'');
        sb.append(", fromHead='").append(fromHead).append('\'');
        sb.append(", topicTitle='").append(topicTitle).append('\'');
        sb.append(", replyBrief='").append(replyBrief).append('\'');
        sb.append(", createTime=").append(createTime);
        sb.append(", position=").append(position);
        sb.append(", noticeStatus=").append(noticeStatus);
        sb.append(", noticeType=").append(noticeType);
        sb.append('}');
        return sb.toString();
    }
}
