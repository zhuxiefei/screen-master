package com.betel.estatemgmt.business.web.expenses.model;

import java.util.List;

/**
 * <p>
 * 查询物业费/停车费的计费周期回参
 * </p>
 * ClassName: FindItemCycleResp <br/>
 * Author: Xia.yx  <br/>
 * Date: 2017/10/13 17:12 <br/>
 * Version: 1.0 <br/>
 */
public class FindItemCycleResp {

    private List<Integer> monthList;

    private Long itemId;

    private Integer itemType;

    public List<Integer> getMonthList() {
        return monthList;
    }

    public void setMonthList(List<Integer> monthList) {
        this.monthList = monthList;
    }

    public Long getItemId() {
        return itemId;
    }

    public void setItemId(Long itemId) {
        this.itemId = itemId;
    }

    public Integer getItemType() {
        return itemType;
    }

    public void setItemType(Integer itemType) {
        this.itemType = itemType;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("FindItemCycleResp{");
        sb.append("monthList=").append(monthList);
        sb.append(", itemId=").append(itemId);
        sb.append(", itemType=").append(itemType);
        sb.append('}');
        return sb.toString();
    }
}
