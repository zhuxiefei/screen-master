package com.betel.estatemgmt.business.web.screen.model;

import net.sf.json.JSONObject;

/**
 * <p>
 * Describe this class...
 * </p>
 * ClassName: FindDataResp <br/>
 * Author: Xia.yx  <br/>
 * Date: 2018/1/9 9:42 <br/>
 * Version: 1.0 <br/>
 */
public class FindDataResp {

    private HouseData houseData;

    private RepairData repairData;

    private PatrolData patrolData;

    private ExpenseData expenseData;

    private TaskData taskData;

    public HouseData getHouseData() {
        return houseData;
    }

    public void setHouseData(HouseData houseData) {
        this.houseData = houseData;
    }

    public RepairData getRepairData() {
        return repairData;
    }

    public void setRepairData(RepairData repairData) {
        this.repairData = repairData;
    }

    public PatrolData getPatrolData() {
        return patrolData;
    }

    public void setPatrolData(PatrolData patrolData) {
        this.patrolData = patrolData;
    }

    public ExpenseData getExpenseData() {
        return expenseData;
    }

    public void setExpenseData(ExpenseData expenseData) {
        this.expenseData = expenseData;
    }

    public TaskData getTaskData() {
        return taskData;
    }

    public void setTaskData(TaskData taskData) {
        this.taskData = taskData;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("FindDataResp{");
        sb.append("houseData=").append(houseData);
        sb.append(", repairData=").append(repairData);
        sb.append(", patrolData=").append(patrolData);
        sb.append(", expenseData=").append(expenseData);
        sb.append(", taskData=").append(taskData);
        sb.append('}');
        return sb.toString();
    }
}
