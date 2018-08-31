package com.betel.estatemgmt.business.propertyapp.cleaning.model;

import com.betel.estatemgmt.common.Page;

/**
 * <p>
 * Describe this class...
 * </p>
 * ClassName: FindAssessmentListReq <br/>
 * Author: Cui.xx  <br/>
 * Date: 2018/2/28 14:42 <br/>
 * Version: 1.0 <br/>
 */
public class FindAssessmentListReq extends Page{

    private String contentId;

    private String isStandard;

    private String estateId;

    public String getEstateId() {
        return estateId;
    }

    public void setEstateId(String estateId) {
        this.estateId = estateId;
    }

    public String getContentId() {
        return contentId;
    }

    public void setContentId(String contentId) {
        this.contentId = contentId;
    }

    public String getIsStandard() {
        return isStandard;
    }

    public void setIsStandard(String isStandard) {
        this.isStandard = isStandard;
    }

    @Override
    public String toString() {
        return "FindAssessmentListReq{" +
                "contentId='" + contentId + '\'' +
                ", isStandard='" + isStandard + '\'' +
                ", estateId='" + estateId + '\'' +
                '}';
    }
}
