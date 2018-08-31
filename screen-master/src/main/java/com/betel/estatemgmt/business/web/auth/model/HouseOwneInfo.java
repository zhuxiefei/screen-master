package com.betel.estatemgmt.business.web.auth.model;

import java.util.List;

/**
 * <p>
 * 户主信息
 * </p>
 * ClassName: HouseOwneInfo <br/>
 * Author: zhanglei  <br/>
 * Date: 2017/6/15 17:13 <br/>
 * Version: 1.0 <br/>
 */
public class HouseOwneInfo {

    /**
     * 户主名称
     */
    private String houseOwneName;

    /**
     * 户主身份证号
     */
    private String houseOwneIdNum;

    /**
     * 户主身份证照正
     */
    private String idFrontSidePic;

    /**
     * 户主身份证照反
     */
    private String idBackSidePic;
    /**
     * 不动产权证照
     */
    private String realEstatePic;

    /**
     * 不动产权证图片的集合
     */
    private List<String> realEstatePics;

    public List<String> getRealEstatePics() {
        return realEstatePics;
    }

    public void setRealEstatePics(List<String> realEstatePics) {
        this.realEstatePics = realEstatePics;
    }

    public String getHouseOwneName() {
        return houseOwneName;
    }

    public void setHouseOwneName(String houseOwneName) {
        this.houseOwneName = houseOwneName == null ? null : houseOwneName.trim();
    }

    public String getHouseOwneIdNum() {
        return houseOwneIdNum;
    }

    public void setHouseOwneIdNum(String houseOwneIdNum) {
        this.houseOwneIdNum = houseOwneIdNum == null ? null : houseOwneIdNum.trim();
    }

    public String getIdFrontSidePic() {
        return idFrontSidePic;
    }

    public void setIdFrontSidePic(String idFrontSidePic) {
        this.idFrontSidePic = idFrontSidePic == null ? null : idFrontSidePic.trim();
    }

    public String getIdBackSidePic() {
        return idBackSidePic;
    }

    public void setIdBackSidePic(String idBackSidePic) {
        this.idBackSidePic = idBackSidePic == null ? null : idBackSidePic.trim();
    }

    public String getRealEstatePic() {
        return realEstatePic;
    }

    public void setRealEstatePic(String realEstatePic) {
        this.realEstatePic = realEstatePic == null ? null : realEstatePic.trim();
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("HouseOwneInfo{");
        sb.append("houseOwneName='").append(houseOwneName).append('\'');
        sb.append(", houseOwneIdNum='").append(houseOwneIdNum).append('\'');
        sb.append(", idFrontSidePic='").append(idFrontSidePic).append('\'');
        sb.append(", idBackSidePic='").append(idBackSidePic).append('\'');
        sb.append(", realEstatePic='").append(realEstatePic).append('\'');
        sb.append('}');
        return sb.toString();
    }



}
