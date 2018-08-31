package com.betel.estatemgmt.business.propertyapp.task.code;

/**
 * <p>
 * 任务code码
 * </p>
 * ClassName: TaskCode <br/>
 * Author: Xia.yx  <br/>
 * Date: 2017/12/21 17:22 <br/>
 * Version: 1.0 <br/>
 */
public interface TaskCode {

    /**
     * 工作类型为空
     */
    String TASK_TYPE_NULL = "T0001";

    /**
     * 工作类型格式错误
     */
    String TASK_TYPE_RULE = "T0002";

    /**
     * 工作类型已存在
     */
    String TASK_TYPE_EXIST = "T0003";

    /**
     * 工作类型被删除
     */
    String TASK_TYPE_DELETE = "T0004";

    /**
     * 工作概述为空
     */
    String TASK_DESC_NULL = "T0005";

    /**
     * 工作概述格式错误
     */
    String TASK_DESC_RULE = "T0006";

    /**
     * 指派人员为空
     */
    String EXECUTOR_NULL  = "T0007";

    /**
     * 指派人员被删除
     */
    String EXECUTOR_DELETE  = "T0008";

    /**
     * 指派人员格式错误
     */
    String EXECUTOR_RULE  = "T0009";

    /**
     * 任务已经指派给别人
     */
    String TASK_ASSIGN_OTHER  = "T0010";

    /**
     * 拒绝理由为空
     */
    String REASON_NULL  = "T0011";

    /**
     * 任务被删除
     */
    String TASK_DELETE  = "T0012";

    /**
     * 任务状态已完成
     */
    String TASK_STATUS_FINISH  = "T0013";

    /**
     * 任务状态进行中
     */
    String TASK_STATUS_DOING  = "T0014";

    /**
     * 拒绝理由长度过长
     */
    String REASON_MAX  = "T0015";

    /**
     * 任务超时未完成
     */
    String TASK_STATUS_OVERTIME = "T0016";

    /**
     * 任务待接受
     */
    String TASK_STATUS_WAIT = "T0019";

    /**
     * 头像名称过长（120以内）
     */
    String IMAGE_NAME_LENGTH_OUT = "U0010";

    /**
     * 头像格式错误（bmp/jpg/jpeg/png）
     */
    String IMAGE_FORMAT_WRONG="U0011";

    /**
     * 头像大小超过20M
     */
    String IMAGE_OUT_SIZE="U0012";
}
