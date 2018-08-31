package com.betel.estatemgmt.business.web.screen.model;

/**
 * <p>
 * Describe this class...
 * </p>
 * ClassName: TodayTaskData <br/>
 * Author: Xia.yx  <br/>
 * Date: 2018/1/13 19:01 <br/>
 * Version: 1.0 <br/>
 */
public class TodayTaskData {

    private Integer todayTasks;

    private Integer unfinishTasks;

    private Integer todayFinishTasks;

    private Integer todayUnFinishTasks;

    public Integer getTodayTasks() {
        return todayTasks;
    }

    public void setTodayTasks(Integer todayTasks) {
        this.todayTasks = todayTasks;
    }

    public Integer getUnfinishTasks() {
        return unfinishTasks;
    }

    public void setUnfinishTasks(Integer unfinishTasks) {
        this.unfinishTasks = unfinishTasks;
    }

    public Integer getTodayFinishTasks() {
        return todayFinishTasks;
    }

    public void setTodayFinishTasks(Integer todayFinishTasks) {
        this.todayFinishTasks = todayFinishTasks;
    }

    public Integer getTodayUnFinishTasks() {
        return todayUnFinishTasks;
    }

    public void setTodayUnFinishTasks(Integer todayUnFinishTasks) {
        this.todayUnFinishTasks = todayUnFinishTasks;
    }
}
