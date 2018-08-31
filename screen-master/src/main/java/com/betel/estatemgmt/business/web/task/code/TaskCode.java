package com.betel.estatemgmt.business.web.task.code;

/**
 * <p>
 * 任务code码
 * </p>
 * ClassName: TaskCode <br/>
 * Author: Xia.yx  <br/>
 * Date: 2017/12/21 8:55 <br/>
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
     * 时间格式错误
     */
    String TIME_RULE  = "T0010";

    /**
     * 起始时间大于结束时间
     */
    String START_LATE_END  = "T0011";

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
     * 员工姓名格式错误
     */
    String EMP_NAME_RULE  = "T0015";

    /**
     * 员工编号格式错误
     */
    String EMP_NO_RULE  = "T0016";

    /**
     * 员工手机号格式错误
     */
    String EMP_PHONE_RULE  = "T0017";

    /**
     * 发布人格式错误
     */
    String CREATE_USER_RULE  = "T0018";

    /**
     * 任务待接受
     */
    String TASK_STATUS_WAIT = "T0019";

    /**
     * 已指派该人员进行该任务
     */
    String TASK_IS_SAME = "T0020";

    /**
     * 任务编号格式错误
     */
    String TASK_NO_RULE = "T0021";

    String CLOSE_TIME_IS_NULL="T0022";

    /**
     * 任务状态超时未完成
     */
    String TASK_STATUS_OVERTIME  = "T0024";
}
