package com.betel.estatemgmt.business.web.task.model;

/**
 * Created by Administrator on 2018/5/9/009.
 */
public class TaskCountResp {

    private Integer waitRepairs;

    private Integer acceptRepairs;

    private Integer finishRepairs;

    private Integer cancelRepairs;

    private Integer allRepairs;

    private Integer finishCleanings;

    private Integer unFinishCleanings;

    private Integer allCleanings;

    private Integer finishPatrols;

    private Integer unFinishPatrols;

    private Integer OverTimePatrols;

    private Integer allPatrols;

    private Integer finishSecurity;

    private Integer unFinishSecurity;

    private Integer OverTimeSecurity;

    private Integer allSecurity;

    public Integer getWaitRepairs() {
        return waitRepairs;
    }

    public void setWaitRepairs(Integer waitRepairs) {
        this.waitRepairs = waitRepairs;
    }

    public Integer getAcceptRepairs() {
        return acceptRepairs;
    }

    public void setAcceptRepairs(Integer acceptRepairs) {
        this.acceptRepairs = acceptRepairs;
    }

    public Integer getFinishRepairs() {
        return finishRepairs;
    }

    public void setFinishRepairs(Integer finishRepairs) {
        this.finishRepairs = finishRepairs;
    }

    public Integer getCancelRepairs() {
        return cancelRepairs;
    }

    public void setCancelRepairs(Integer cancelRepairs) {
        this.cancelRepairs = cancelRepairs;
    }

    public Integer getAllRepairs() {
        return allRepairs;
    }

    public void setAllRepairs(Integer allRepairs) {
        this.allRepairs = allRepairs;
    }

    public Integer getFinishCleanings() {
        return finishCleanings;
    }

    public void setFinishCleanings(Integer finishCleanings) {
        this.finishCleanings = finishCleanings;
    }

    public Integer getUnFinishCleanings() {
        return unFinishCleanings;
    }

    public void setUnFinishCleanings(Integer unFinishCleanings) {
        this.unFinishCleanings = unFinishCleanings;
    }

    public Integer getAllCleanings() {
        return allCleanings;
    }

    public void setAllCleanings(Integer allCleanings) {
        this.allCleanings = allCleanings;
    }

    public Integer getFinishPatrols() {
        return finishPatrols;
    }

    public void setFinishPatrols(Integer finishPatrols) {
        this.finishPatrols = finishPatrols;
    }

    public Integer getUnFinishPatrols() {
        return unFinishPatrols;
    }

    public void setUnFinishPatrols(Integer unFinishPatrols) {
        this.unFinishPatrols = unFinishPatrols;
    }

    public Integer getOverTimePatrols() {
        return OverTimePatrols;
    }

    public void setOverTimePatrols(Integer overTimePatrols) {
        OverTimePatrols = overTimePatrols;
    }

    public Integer getAllPatrols() {
        return allPatrols;
    }

    public void setAllPatrols(Integer allPatrols) {
        this.allPatrols = allPatrols;
    }

    public Integer getFinishSecurity() {
        return finishSecurity;
    }

    public void setFinishSecurity(Integer finishSecurity) {
        this.finishSecurity = finishSecurity;
    }

    public Integer getUnFinishSecurity() {
        return unFinishSecurity;
    }

    public void setUnFinishSecurity(Integer unFinishSecurity) {
        this.unFinishSecurity = unFinishSecurity;
    }

    public Integer getOverTimeSecurity() {
        return OverTimeSecurity;
    }

    public void setOverTimeSecurity(Integer overTimeSecurity) {
        OverTimeSecurity = overTimeSecurity;
    }

    public Integer getAllSecurity() {
        return allSecurity;
    }

    public void setAllSecurity(Integer allSecurity) {
        this.allSecurity = allSecurity;
    }

    @Override
    public String toString() {
        return "TaskCountResp{" +
                "waitRepairs=" + waitRepairs +
                ", acceptRepairs=" + acceptRepairs +
                ", finishRepairs='" + finishRepairs + '\'' +
                ", cancelRepairs=" + cancelRepairs +
                ", allRepairs=" + allRepairs +
                ", finishCleanings=" + finishCleanings +
                ", unFinishCleanings=" + unFinishCleanings +
                ", allCleanings=" + allCleanings +
                ", finishPatrols=" + finishPatrols +
                ", unFinishPatrols=" + unFinishPatrols +
                ", OverTimePatrols=" + OverTimePatrols +
                ", allPatrols=" + allPatrols +
                ", finishSecurity=" + finishSecurity +
                ", unFinishSecurity=" + unFinishSecurity +
                ", OverTimeSecurity=" + OverTimeSecurity +
                ", allSecurity=" + allSecurity +
                '}';
    }
}
