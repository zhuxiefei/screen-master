package com.betel.estatemgmt.business.web.repair.model;

import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

public class Employee {
    private String employeeId;

    private String employeeNo;

    private String companyId;

    private String employeeName;

    private String idNum;

    private Integer gender;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date birthday;

    private String nationality;

    private String ethnic;

    private String residency;

    private Double height;

    private Double weight;

    private Integer maritalStatus;

    private Integer politicalStatus;

    private Integer highestEducation;

    private String graduatedFrom;

    private String major;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date graduatedDate;

    private String positionId;

    private String departmentId;

    private Integer employType;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date workDate;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date onBoardDate;

    private String address;

    private String phoneNum;

    private String email;

    private Integer isRegular;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date regularDate;

    private String emergencyContactPerson;

    private String emergencyContactNumber;

    private Integer isEnableAccount;

    private String password;

    private String shiroKey;

    private String roleId;

    private Date createTime;

    private Date updateTime;

    private String photo;

    private Long head;

    public String getEmployeeId() {
        return employeeId;
    }

    public void setEmployeeId(String employeeId) {
        this.employeeId = employeeId == null ? null : employeeId.trim();
    }

    public String getEmployeeNo() {
        return employeeNo;
    }

    public void setEmployeeNo(String employeeNo) {
        this.employeeNo = employeeNo == null ? null : employeeNo.trim();
    }

    public String getCompanyId() {
        return companyId;
    }

    public void setCompanyId(String companyId) {
        this.companyId = companyId == null ? null : companyId.trim();
    }

    public String getEmployeeName() {
        return employeeName;
    }

    public void setEmployeeName(String employeeName) {
        this.employeeName = employeeName == null ? null : employeeName.trim();
    }

    public String getIdNum() {
        return idNum;
    }

    public void setIdNum(String idNum) {
        this.idNum = idNum == null ? null : idNum.trim();
    }

    public Integer getGender() {
        return gender;
    }

    public void setGender(Integer gender) {
        this.gender = gender;
    }

    public Date getBirthday() {
        return birthday;
    }

    public void setBirthday(Date birthday) {
        this.birthday = birthday;
    }

    public String getNationality() {
        return nationality;
    }

    public void setNationality(String nationality) {
        this.nationality = nationality == null ? null : nationality.trim();
    }

    public String getEthnic() {
        return ethnic;
    }

    public void setEthnic(String ethnic) {
        this.ethnic = ethnic == null ? null : ethnic.trim();
    }

    public String getResidency() {
        return residency;
    }

    public void setResidency(String residency) {
        this.residency = residency == null ? null : residency.trim();
    }

    public Double getHeight() {
        return height;
    }

    public void setHeight(Double height) {
        this.height = height;
    }

    public Double getWeight() {
        return weight;
    }

    public void setWeight(Double weight) {
        this.weight = weight;
    }

    public Integer getMaritalStatus() {
        return maritalStatus;
    }

    public void setMaritalStatus(Integer maritalStatus) {
        this.maritalStatus = maritalStatus;
    }

    public Integer getPoliticalStatus() {
        return politicalStatus;
    }

    public void setPoliticalStatus(Integer politicalStatus) {
        this.politicalStatus = politicalStatus;
    }

    public Integer getHighestEducation() {
        return highestEducation;
    }

    public void setHighestEducation(Integer highestEducation) {
        this.highestEducation = highestEducation;
    }

    public String getGraduatedFrom() {
        return graduatedFrom;
    }

    public void setGraduatedFrom(String graduatedFrom) {
        this.graduatedFrom = graduatedFrom == null ? null : graduatedFrom.trim();
    }

    public String getMajor() {
        return major;
    }

    public void setMajor(String major) {
        this.major = major == null ? null : major.trim();
    }

    public Date getGraduatedDate() {
        return graduatedDate;
    }

    public void setGraduatedDate(Date graduatedDate) {
        this.graduatedDate = graduatedDate;
    }

    public String getPositionId() {
        return positionId;
    }

    public void setPositionId(String positionId) {
        this.positionId = positionId == null ? null : positionId.trim();
    }

    public String getDepartmentId() {
        return departmentId;
    }

    public void setDepartmentId(String departmentId) {
        this.departmentId = departmentId == null ? null : departmentId.trim();
    }

    public Integer getEmployType() {
        return employType;
    }

    public void setEmployType(Integer employType) {
        this.employType = employType;
    }

    public Date getWorkDate() {
        return workDate;
    }

    public void setWorkDate(Date workDate) {
        this.workDate = workDate;
    }

    public Date getOnBoardDate() {
        return onBoardDate;
    }

    public void setOnBoardDate(Date onBoardDate) {
        this.onBoardDate = onBoardDate;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address == null ? null : address.trim();
    }

    public String getPhoneNum() {
        return phoneNum;
    }

    public void setPhoneNum(String phoneNum) {
        this.phoneNum = phoneNum == null ? null : phoneNum.trim();
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email == null ? null : email.trim();
    }

    public Integer getIsRegular() {
        return isRegular;
    }

    public void setIsRegular(Integer isRegular) {
        this.isRegular = isRegular;
    }

    public Date getRegularDate() {
        return regularDate;
    }

    public void setRegularDate(Date regularDate) {
        this.regularDate = regularDate;
    }

    public String getEmergencyContactPerson() {
        return emergencyContactPerson;
    }

    public void setEmergencyContactPerson(String emergencyContactPerson) {
        this.emergencyContactPerson = emergencyContactPerson == null ? null : emergencyContactPerson.trim();
    }

    public String getEmergencyContactNumber() {
        return emergencyContactNumber;
    }

    public void setEmergencyContactNumber(String emergencyContactNumber) {
        this.emergencyContactNumber = emergencyContactNumber == null ? null : emergencyContactNumber.trim();
    }

    public Integer getIsEnableAccount() {
        return isEnableAccount;
    }

    public void setIsEnableAccount(Integer isEnableAccount) {
        this.isEnableAccount = isEnableAccount;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getShiroKey() {
        return shiroKey;
    }

    public void setShiroKey(String shiroKey) {
        this.shiroKey = shiroKey;
    }

    public String getRoleId() {
        return roleId;
    }

    public void setRoleId(String roleId) {
        this.roleId = roleId;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    public String getPhoto() {
        return photo;
    }

    public void setPhoto(String photo) {
        this.photo = photo == null ? null : photo.trim();
    }

    public Long getHead() {
        return head;
    }

    public void setHead(Long head) {
        this.head = head;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("Employee{");
        sb.append("employeeId='").append(employeeId).append('\'');
        sb.append(", employeeNo='").append(employeeNo).append('\'');
        sb.append(", companyId='").append(companyId).append('\'');
        sb.append(", employeeName='").append(employeeName).append('\'');
        sb.append(", idNum='").append(idNum).append('\'');
        sb.append(", gender=").append(gender);
        sb.append(", birthday=").append(birthday);
        sb.append(", nationality='").append(nationality).append('\'');
        sb.append(", ethnic='").append(ethnic).append('\'');
        sb.append(", residency='").append(residency).append('\'');
        sb.append(", height=").append(height);
        sb.append(", weight=").append(weight);
        sb.append(", maritalStatus=").append(maritalStatus);
        sb.append(", politicalStatus=").append(politicalStatus);
        sb.append(", highestEducation=").append(highestEducation);
        sb.append(", graduatedFrom='").append(graduatedFrom).append('\'');
        sb.append(", major='").append(major).append('\'');
        sb.append(", graduatedDate=").append(graduatedDate);
        sb.append(", positionId='").append(positionId).append('\'');
        sb.append(", departmentId='").append(departmentId).append('\'');
        sb.append(", employType=").append(employType);
        sb.append(", workDate=").append(workDate);
        sb.append(", onBoardDate=").append(onBoardDate);
        sb.append(", address='").append(address).append('\'');
        sb.append(", phoneNum='").append(phoneNum).append('\'');
        sb.append(", email='").append(email).append('\'');
        sb.append(", isRegular=").append(isRegular);
        sb.append(", regularDate=").append(regularDate);
        sb.append(", emergencyContactPerson='").append(emergencyContactPerson).append('\'');
        sb.append(", emergencyContactNumber='").append(emergencyContactNumber).append('\'');
        sb.append(", isEnableAccount=").append(isEnableAccount);
        sb.append(", password='").append(password).append('\'');
        sb.append(", shiroKey='").append(shiroKey).append('\'');
        sb.append(", roleId='").append(roleId).append('\'');
        sb.append(", createTime=").append(createTime);
        sb.append(", updateTime=").append(updateTime);
        sb.append(", photo='").append(photo).append('\'');
        sb.append(", head=").append(head);
        sb.append('}');
        return sb.toString();
    }
}