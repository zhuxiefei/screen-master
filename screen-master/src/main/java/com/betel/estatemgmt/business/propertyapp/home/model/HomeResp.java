package com.betel.estatemgmt.business.propertyapp.home.model;

import javax.naming.ldap.PagedResultsControl;

/**
 * Created by zhangjian on 2018/1/4.
 */
public class HomeResp {
    private String todayNewAdd;
    private String allUnassign;
    private String todayAssign;
    private String finish;

    private String disposeFinish;
    private String assignMe;

    @Override
    public String toString() {
        return "HomeResp{" +
                "todayNewAdd='" + todayNewAdd + '\'' +
                ", allUnassign='" + allUnassign + '\'' +
                ", todayAssign='" + todayAssign + '\'' +
                ", finish='" + finish + '\'' +
                ", disposeFinish='" + disposeFinish + '\'' +
                ", assignMe='" + assignMe + '\'' +
                '}';
    }

    public String getDisposeFinish() {
        return disposeFinish;
    }

    public void setDisposeFinish(String disposeFinish) {
        this.disposeFinish = disposeFinish;
    }

    public String getAssignMe() {
        return assignMe;
    }

    public void setAssignMe(String assignMe) {
        this.assignMe = assignMe;
    }

    public String getTodayNewAdd() {
        return todayNewAdd;
    }

    public void setTodayNewAdd(String todayNewAdd) {
        this.todayNewAdd = todayNewAdd;
    }

    public String getAllUnassign() {
        return allUnassign;
    }

    public void setAllUnassign(String allUnassign) {
        this.allUnassign = allUnassign;
    }

    public String getTodayAssign() {
        return todayAssign;
    }

    public void setTodayAssign(String todayAssign) {
        this.todayAssign = todayAssign;
    }

    public String getFinish() {
        return finish;
    }

    public void setFinish(String finish) {
        this.finish = finish;
    }
}
