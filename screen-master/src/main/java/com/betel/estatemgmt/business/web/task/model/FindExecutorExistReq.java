package com.betel.estatemgmt.business.web.task.model;

/**
 * <p>
 * Describe this class...
 * </p>
 * ClassName: FindExecutorExistReq <br/>
 * Author: Xia.yx  <br/>
 * Date: 2017/12/22 18:44 <br/>
 * Version: 1.0 <br/>
 */
public class FindExecutorExistReq {

    private String executor;

    public String getExecutor() {
        return executor;
    }

    public void setExecutor(String executor) {
        this.executor = executor;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("FindExecutorExistReq{");
        sb.append("executor='").append(executor).append('\'');
        sb.append('}');
        return sb.toString();
    }
}
