package com.betel.estatemgmt.business.propertyapp.task.model;

import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 * 文件说明
 * </p>
 * Author: geyf  <br/>
 * Date: 2018/5/11 10:56 <br/>
 * Version: 1.0 <br/>
 */
public class AppTaskCount {


    private int waitRepairs;

    private int acceptRepairs;

    private int finishRepairs;

    private int cancelRepairs;

    private int allRepairs;

    private int finishPatrols;

    private int unFinishPatrols;

    private int OverTimePatrols;

    private int allPatrols;

    private int finishSecurity;

    private int unFinishSecurity;

    private int OverTimeSecurity;

    private int allSecurity;

    private int finishCleanings;

    private int unFinishCleanings;

    private int allCleanings;


    private List<CleanResp> cleanResps = new ArrayList<>();

    public int getWaitRepairs() {
        return waitRepairs;
    }

    public void setWaitRepairs(int waitRepairs) {
        this.waitRepairs = waitRepairs;
    }

    public int getAcceptRepairs() {
        return acceptRepairs;
    }

    public void setAcceptRepairs(int acceptRepairs) {
        this.acceptRepairs = acceptRepairs;
    }

    public int getFinishRepairs() {
        return finishRepairs;
    }

    public void setFinishRepairs(int finishRepairs) {
        this.finishRepairs = finishRepairs;
    }

    public int getCancelRepairs() {
        return cancelRepairs;
    }

    public void setCancelRepairs(int cancelRepairs) {
        this.cancelRepairs = cancelRepairs;
    }

    public int getAllRepairs() {
        return allRepairs;
    }

    public void setAllRepairs(int allRepairs) {
        this.allRepairs = allRepairs;
    }

    public int getFinishPatrols() {
        return finishPatrols;
    }

    public void setFinishPatrols(int finishPatrols) {
        this.finishPatrols = finishPatrols;
    }

    public int getUnFinishPatrols() {
        return unFinishPatrols;
    }

    public void setUnFinishPatrols(int unFinishPatrols) {
        this.unFinishPatrols = unFinishPatrols;
    }

    public int getOverTimePatrols() {
        return OverTimePatrols;
    }

    public void setOverTimePatrols(int overTimePatrols) {
        OverTimePatrols = overTimePatrols;
    }

    public int getAllPatrols() {
        return allPatrols;
    }

    public void setAllPatrols(int allPatrols) {
        this.allPatrols = allPatrols;
    }

    public int getFinishSecurity() {
        return finishSecurity;
    }

    public void setFinishSecurity(int finishSecurity) {
        this.finishSecurity = finishSecurity;
    }

    public int getUnFinishSecurity() {
        return unFinishSecurity;
    }

    public void setUnFinishSecurity(int unFinishSecurity) {
        this.unFinishSecurity = unFinishSecurity;
    }

    public int getOverTimeSecurity() {
        return OverTimeSecurity;
    }

    public void setOverTimeSecurity(int overTimeSecurity) {
        OverTimeSecurity = overTimeSecurity;
    }

    public int getAllSecurity() {
        return allSecurity;
    }

    public void setAllSecurity(int allSecurity) {
        this.allSecurity = allSecurity;
    }

    public int getFinishCleanings() {
        return finishCleanings;
    }

    public void setFinishCleanings(int finishCleanings) {
        this.finishCleanings = finishCleanings;
    }

    public int getUnFinishCleanings() {
        return unFinishCleanings;
    }

    public void setUnFinishCleanings(int unFinishCleanings) {
        this.unFinishCleanings = unFinishCleanings;
    }

    public int getAllCleanings() {
        return allCleanings;
    }

    public void setAllCleanings(int allCleanings) {
        this.allCleanings = allCleanings;
    }

    public List<CleanResp> getCleanResps() {
        return cleanResps;
    }

    public void setCleanResps(List<CleanResp> cleanResps) {
        this.cleanResps = cleanResps;
    }

    @Override
    public String toString() {
        return "AppTaskCount{" +
                "waitRepairs=" + waitRepairs +
                ", acceptRepairs=" + acceptRepairs +
                ", finishRepairs=" + finishRepairs +
                ", cancelRepairs=" + cancelRepairs +
                ", allRepairs=" + allRepairs +
                ", finishPatrols=" + finishPatrols +
                ", unFinishPatrols=" + unFinishPatrols +
                ", OverTimePatrols=" + OverTimePatrols +
                ", allPatrols=" + allPatrols +
                ", finishSecurity=" + finishSecurity +
                ", unFinishSecurity=" + unFinishSecurity +
                ", OverTimeSecurity=" + OverTimeSecurity +
                ", allSecurity=" + allSecurity +
                ", finishCleanings=" + finishCleanings +
                ", unFinishCleanings=" + unFinishCleanings +
                ", allCleanings=" + allCleanings +
                ", cleanResps=" + cleanResps +
                '}';
    }
}