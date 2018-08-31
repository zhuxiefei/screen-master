package com.betel.estatemgmt.business.web.home.model;

import java.util.List;

/**
 * Created by zhangjian on 2017/9/14.
 */
public class HomeLineChart {
    public List<String> getTimes() {
        return times;
    }

    public void setTimes(List<String> times) {
        this.times = times;
    }

    private List<String> times;
    private List<Integer> topicToLineChar;
    private List<Integer> activityToLineChar;
    private List<Integer> tradingToLineChar;

    public List<Integer> getTopicToLineChar() {
        return topicToLineChar;
    }

    public void setTopicToLineChar(List<Integer> topicToLineChar) {
        this.topicToLineChar = topicToLineChar;
    }

    public List<Integer> getActivityToLineChar() {
        return activityToLineChar;
    }

    public void setActivityToLineChar(List<Integer> activityToLineChar) {
        this.activityToLineChar = activityToLineChar;
    }

    public List<Integer> getTradingToLineChar() {
        return tradingToLineChar;
    }

    public void setTradingToLineChar(List<Integer> tradingToLineChar) {
        this.tradingToLineChar = tradingToLineChar;
    }

    @Override
    public String toString() {
        return "HomeLineChart{" +
                "topicToLineChar='" + topicToLineChar + '\'' +
                ", activityToLineChar='" + activityToLineChar + '\'' +
                ", tradingToLineChar='" + tradingToLineChar + '\'' +
                '}';
    }


}
