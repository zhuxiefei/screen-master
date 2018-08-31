package com.betel.estatemgmt.business.web.house.model;

/**
 * <p>
 * 房屋列表入参
 * </p>
 * ClassName: HousePageReq <br/>
 * Author: xiayanxin <br/>
 * Date: 2017/6/19 14:27 <br/>
 * Version: 1.0 <br/>
 */
public class HousePageReq {
    private Long buildingId;
    private Long unitId;
    private String houseNum;
    private String typeName;
    private String ownerName;
    /**
     * 车位类型:车位类型：
     * 1为产权车位，
     * 2为租赁车位
     */
    private String isBuy;
    private String isRent;
    private String keyWord;
    private Integer isOthers;
    private Integer curPage = 1;
    private Integer pageSize = 10;
    private String estateId;

    public String getEstateId() {
        return estateId;
    }

    public void setEstateId(String estateId) {
        this.estateId = estateId;
    }

    public Long getBuildingId() {
        return buildingId;
    }

    public void setBuildingId(Long buildingId) {
        this.buildingId = buildingId;
    }

    public Long getUnitId() {
        return unitId;
    }

    public void setUnitId(Long unitId) {
        this.unitId = unitId;
    }

    public String getHouseNum() {
        return houseNum;
    }

    public void setHouseNum(String houseNum) {
        this.houseNum = houseNum;
    }

    public String getTypeName() {
        return typeName;
    }

    public void setTypeName(String typeName) {
        this.typeName = typeName;
    }

    public String getOwnerName() {
        return ownerName;
    }

    public void setOwnerName(String ownerName) {
        this.ownerName = ownerName;
    }

    public String getIsBuy() {
        return isBuy;
    }

    public void setIsBuy(String isBuy) {
        this.isBuy = isBuy;
    }

    public String getIsRent() {
        return isRent;
    }

    public void setIsRent(String isRent) {
        this.isRent = isRent;
    }

    public String getKeyWord() {
        return keyWord;
    }

    public void setKeyWord(String keyWord) {
        this.keyWord = keyWord;
    }

    public Integer getIsOthers() {
        return isOthers;
    }

    public void setIsOthers(Integer isOthers) {
        this.isOthers = isOthers;
    }

    public Integer getCurPage() {
        return curPage;
    }

    public void setCurPage(Integer curPage) {
        this.curPage = curPage;
    }

    public Integer getPageSize() {
        return pageSize;
    }

    public void setPageSize(Integer pageSize) {
        this.pageSize = pageSize;
    }

    @Override
    public String toString() {
        return "HousePageReq{" +
                "buildingId=" + buildingId +
                ", unitId=" + unitId +
                ", houseNum='" + houseNum + '\'' +
                ", typeName='" + typeName + '\'' +
                ", ownerName='" + ownerName + '\'' +
                ", isBuy='" + isBuy + '\'' +
                ", isRent='" + isRent + '\'' +
                ", keyWord='" + keyWord + '\'' +
                ", isOthers=" + isOthers +
                ", curPage=" + curPage +
                ", pageSize=" + pageSize +
                ", estateId='" + estateId + '\'' +
                '}';
    }
}
