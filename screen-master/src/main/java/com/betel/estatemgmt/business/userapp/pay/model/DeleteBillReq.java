package com.betel.estatemgmt.business.userapp.pay.model;

/**
 * <p>
 * Describe this class...
 * </p>
 * ClassName: DeleteBillReq <br/>
 * Author: Xia.yx  <br/>
 * Date: 2018/1/25 11:20 <br/>
 * Version: 1.0 <br/>
 */
public class DeleteBillReq {

    private String billNo;

    /**
     * 1为删除账单记录
     * 2为删除预交费账单
     */
    private Integer type;

    public String getBillNo() {
        return billNo;
    }

    public void setBillNo(String billNo) {
        this.billNo = billNo;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("DeleteBillReq{");
        sb.append("billNo='").append(billNo).append('\'');
        sb.append(", type=").append(type);
        sb.append('}');
        return sb.toString();
    }
}
