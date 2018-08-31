package com.betel.estatemgmt.business.web.home.model;

/**
 * Created by zhangjian on 2017/9/14.
 */
public class MonthNum {
    public int getNum() {
        return num;
    }

    public void setNum(int num) {
        this.num = num;
    }

    private  int num;
    private String yearmonth;

    @Override
    public String toString() {
        return "MonthNum{" +
                "num=" + num +
                ", yearmonth='" + yearmonth + '\'' +
                '}';
    }

    public String getYearmonth() {
        return yearmonth;
    }

    public void setYearmonth(String yearmonth) {
        this.yearmonth = yearmonth;
    }


}
