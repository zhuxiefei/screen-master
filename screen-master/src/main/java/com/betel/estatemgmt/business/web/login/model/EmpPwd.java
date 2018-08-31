package com.betel.estatemgmt.business.web.login.model;

/**
 * <p>
 * 修改密码入参
 * </p>
 * ClassName: EmpPwd <br/>
 * Author: Xia.yx  <br/>
 * Date: 2017/12/7 10:57 <br/>
 * Version: 1.0 <br/>
 */
public class EmpPwd {

    private String oldPwd;

    private String newPwd;

    public String getOldPwd() {
        return oldPwd;
    }

    public void setOldPwd(String oldPwd) {
        this.oldPwd = oldPwd;
    }

    public String getNewPwd() {
        return newPwd;
    }

    public void setNewPwd(String newPwd) {
        this.newPwd = newPwd;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("EmpPwd{");
        sb.append("oldPwd='").append(oldPwd).append('\'');
        sb.append(", newPwd='").append(newPwd).append('\'');
        sb.append('}');
        return sb.toString();
    }
}
