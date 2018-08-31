package com.betel.estatemgmt.business.userapp.pay.model;

import com.betel.estatemgmt.utils.pagination.model.Paging;

/**
 * <p>
 * Describe this class...
 * </p>
 * ClassName: PayPage <br/>
 * Author: zhouye  <br/>
 * Date: 2017/9/18 13:21 <br/>
 * Version: 1.0 <br/>
 */
public class PayPage extends Paging{

    /*
    欠缴费:1
    已缴费:2
    预交费:3
    未缴费:4
     */
    private Integer expenseType;
    /*
    1为物业费，
    2为公摊水电费
     */
    private Integer itemType;
    /*
    房屋id
     */
    private String houseId;

    /*
  房屋ids
   */
    private String[] houseIds;

    public String[] getHouseIds() {
        return houseIds;
    }

    public void setHouseIds(String[] houseIds) {
        this.houseIds = houseIds;
    }

    public PayPage() {
    }
    public PayPage(int curPage, int pageSize) {
        super(curPage, pageSize);
    }

    public PayPage(int curPage, int pageSize, Integer expenseType, Integer itemType, String houseId) {
        super(curPage, pageSize);
        this.expenseType = expenseType;
        this.itemType = itemType;
        this.houseId = houseId;
    }

    public Integer getExpenseType() {
        return expenseType;
    }

    public void setExpenseType(Integer expenseType) {
        this.expenseType = expenseType;
    }

    public Integer getItemType() {
        return itemType;
    }

    public void setItemType(Integer itemType) {
        this.itemType = itemType;
    }

    public String getHouseId() {
        return houseId;
    }

    public void setHouseId(String houseId) {
        this.houseId = houseId;
    }

    @Override
    public String toString() {
        return "PayPage{" +
                "expenseType=" + expenseType +
                ", itemType=" + itemType +
                ", houseId=" + houseId +
                '}';
    }
}
