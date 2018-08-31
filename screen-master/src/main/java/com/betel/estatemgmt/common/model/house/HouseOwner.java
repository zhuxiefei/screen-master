package com.betel.estatemgmt.common.model.house;

import java.util.Date;

public class HouseOwner {
    private String ownerId;

    private String userId;

    private String realName;

    private String phoneNum;

    private String idNum;

    private Long idFrontSidePic;

    private Long idBackSidePic;

    private String realEstatePic;

    private String ethnic;

    private String residency;

    private Integer religion;

    private Integer ownerStatus;

    private Date createTime;

    private Date updateTime;

    public String getOwnerId() {
        return ownerId;
    }

    public void setOwnerId(String ownerId) {
        this.ownerId = ownerId == null ? null : ownerId.trim();
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId == null ? null : userId.trim();
    }

    public String getRealName() {
        return realName;
    }

    public void setRealName(String realName) {
        this.realName = realName == null ? null : realName.trim();
    }

    public String getPhoneNum() {
        return phoneNum;
    }

    public void setPhoneNum(String phoneNum) {
        this.phoneNum = phoneNum == null ? null : phoneNum.trim();
    }

    public String getIdNum() {
        return idNum;
    }

    public void setIdNum(String idNum) {
        this.idNum = idNum == null ? null : idNum.trim();
    }

    public Long getIdFrontSidePic() {
        return idFrontSidePic;
    }

    public void setIdFrontSidePic(Long idFrontSidePic) {
        this.idFrontSidePic = idFrontSidePic;
    }

    public Long getIdBackSidePic() {
        return idBackSidePic;
    }

    public void setIdBackSidePic(Long idBackSidePic) {
        this.idBackSidePic = idBackSidePic;
    }

    public String getRealEstatePic() {
        return realEstatePic;
    }

    public void setRealEstatePic(String realEstatePic) {
        this.realEstatePic = realEstatePic == null ? null : realEstatePic.trim();
    }

    public String getEthnic() {
        return ethnic;
    }

    public void setEthnic(String ethnic) {
        this.ethnic = ethnic == null ? null : ethnic.trim();
    }

    public String getResidency() {
        return residency;
    }

    public void setResidency(String residency) {
        this.residency = residency == null ? null : residency.trim();
    }

    public Integer getReligion() {
        return religion;
    }

    public void setReligion(Integer religion) {
        this.religion = religion;
    }

    public Integer getOwnerStatus() {
        return ownerStatus;
    }

    public void setOwnerStatus(Integer ownerStatus) {
        this.ownerStatus = ownerStatus;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }
}