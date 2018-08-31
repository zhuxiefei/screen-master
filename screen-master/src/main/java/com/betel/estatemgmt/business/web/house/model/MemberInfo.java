package com.betel.estatemgmt.business.web.house.model;

/**
 * <p>
 * Describe this class...
 * </p>
 * ClassName: MemberInfo <br/>
 * Author: Xia.yx  <br/>
 * Date: 2018/1/24 16:06 <br/>
 * Version: 1.0 <br/>
 */
public class MemberInfo {

    private String name;

    private String phone;

    private String residency;

    private String ethnic;

    private Integer religion;

    private Integer status;

    /**
     * 1户主，2成员，3租户
     */
    private Integer flag;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getResidency() {
        return residency;
    }

    public void setResidency(String residency) {
        this.residency = residency;
    }

    public String getEthnic() {
        return ethnic;
    }

    public void setEthnic(String ethnic) {
        this.ethnic = ethnic;
    }

    public Integer getReligion() {
        return religion;
    }

    public void setReligion(Integer religion) {
        this.religion = religion;
    }

    public Integer getFlag() {
        return flag;
    }

    public void setFlag(Integer flag) {
        this.flag = flag;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("MemberInfo{");
        sb.append("name='").append(name).append('\'');
        sb.append(", phone='").append(phone).append('\'');
        sb.append(", residency='").append(residency).append('\'');
        sb.append(", ethnic='").append(ethnic).append('\'');
        sb.append(", religion=").append(religion);
        sb.append(", status=").append(status);
        sb.append(", flag=").append(flag);
        sb.append('}');
        return sb.toString();
    }
}
