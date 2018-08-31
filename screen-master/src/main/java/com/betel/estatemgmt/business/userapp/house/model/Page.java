package com.betel.estatemgmt.business.userapp.house.model;

/**
 * <p>
 * Describe this class...
 * </p>
 * ClassName: Page <br/>
 * Author: zhouye  <br/>
 *te: 2017/6/26 19:56 <br/>
 * Version: 1.0 <br/>
 */
public class Page{
    private Long functionId;
    private String houseId;
    private int curPage;
    private int pageSize;

    public int getCurPage() {
        return curPage;
    }

    public void setCurPage(int curPage) {
        this.curPage = curPage;
    }

    public int getPageSize() {
        return pageSize;
    }

    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }

    public Long getFunctionId() {
        return functionId;
    }

    public void setFunctionId(Long functionId) {
        this.functionId = functionId;
    }

    public String getHouseId() {
        return houseId;
    }

    public void setHouseId(String houseId) {
        this.houseId = houseId;
    }
}
