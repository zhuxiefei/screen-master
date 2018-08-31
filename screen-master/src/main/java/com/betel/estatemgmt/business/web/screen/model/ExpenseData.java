package com.betel.estatemgmt.business.web.screen.model;

/**
 * <p>
 * Describe this class...
 * </p>
 * ClassName: ExpenseData <br/>
 * Author: Xia.yx  <br/>
 * Date: 2018/1/9 10:35 <br/>
 * Version: 1.0 <br/>
 */
public class ExpenseData {

    private String allPaidProperty;

    private String allUnPaidProperty;

    private String allPaidEnergy;

    private String allUnPaidEnergy;

    private String allPaidPublic;

    private String allUnPaidPublic;

    private String allPaidSpace;

    private String allUnPaidSpace;

    private Integer paidPropertyHouses;

    private Integer unPaidPropertyHouses;

    private Integer paidEnergyHouses;

    private Integer unPaidEnergyHouses;

    private Integer paidPublicHouses;

    private Integer unPaidPublicHouses;

    private Integer paidSpaceHouses;

    private Integer unPaidSpaceHouses;

    private String onlinePercentage;

    private Integer overDueHouses;

    private String paidTotal;

    private String unPaidTotal;

    public Integer getOverDueHouses() {
        return overDueHouses;
    }

    public void setOverDueHouses(Integer overDueHouses) {
        this.overDueHouses = overDueHouses;
    }

    public String getAllPaidProperty() {
        return allPaidProperty;
    }

    public void setAllPaidProperty(String allPaidProperty) {
        this.allPaidProperty = allPaidProperty;
    }

    public String getAllUnPaidProperty() {
        return allUnPaidProperty;
    }

    public void setAllUnPaidProperty(String allUnPaidProperty) {
        this.allUnPaidProperty = allUnPaidProperty;
    }

    public String getAllPaidEnergy() {
        return allPaidEnergy;
    }

    public void setAllPaidEnergy(String allPaidEnergy) {
        this.allPaidEnergy = allPaidEnergy;
    }

    public String getAllUnPaidEnergy() {
        return allUnPaidEnergy;
    }

    public void setAllUnPaidEnergy(String allUnPaidEnergy) {
        this.allUnPaidEnergy = allUnPaidEnergy;
    }

    public String getAllPaidPublic() {
        return allPaidPublic;
    }

    public void setAllPaidPublic(String allPaidPublic) {
        this.allPaidPublic = allPaidPublic;
    }

    public String getAllUnPaidPublic() {
        return allUnPaidPublic;
    }

    public void setAllUnPaidPublic(String allUnPaidPublic) {
        this.allUnPaidPublic = allUnPaidPublic;
    }

    public String getAllPaidSpace() {
        return allPaidSpace;
    }

    public void setAllPaidSpace(String allPaidSpace) {
        this.allPaidSpace = allPaidSpace;
    }

    public String getAllUnPaidSpace() {
        return allUnPaidSpace;
    }

    public void setAllUnPaidSpace(String allUnPaidSpace) {
        this.allUnPaidSpace = allUnPaidSpace;
    }

    public String getPaidTotal() {
        return paidTotal;
    }

    public void setPaidTotal(String paidTotal) {
        this.paidTotal = paidTotal;
    }

    public String getUnPaidTotal() {
        return unPaidTotal;
    }

    public void setUnPaidTotal(String unPaidTotal) {
        this.unPaidTotal = unPaidTotal;
    }

    public Integer getPaidPropertyHouses() {
        return paidPropertyHouses;
    }

    public void setPaidPropertyHouses(Integer paidPropertyHouses) {
        this.paidPropertyHouses = paidPropertyHouses;
    }

    public Integer getUnPaidPropertyHouses() {
        return unPaidPropertyHouses;
    }

    public void setUnPaidPropertyHouses(Integer unPaidPropertyHouses) {
        this.unPaidPropertyHouses = unPaidPropertyHouses;
    }

    public Integer getPaidEnergyHouses() {
        return paidEnergyHouses;
    }

    public void setPaidEnergyHouses(Integer paidEnergyHouses) {
        this.paidEnergyHouses = paidEnergyHouses;
    }

    public Integer getUnPaidEnergyHouses() {
        return unPaidEnergyHouses;
    }

    public void setUnPaidEnergyHouses(Integer unPaidEnergyHouses) {
        this.unPaidEnergyHouses = unPaidEnergyHouses;
    }

    public Integer getPaidPublicHouses() {
        return paidPublicHouses;
    }

    public void setPaidPublicHouses(Integer paidPublicHouses) {
        this.paidPublicHouses = paidPublicHouses;
    }

    public Integer getUnPaidPublicHouses() {
        return unPaidPublicHouses;
    }

    public void setUnPaidPublicHouses(Integer unPaidPublicHouses) {
        this.unPaidPublicHouses = unPaidPublicHouses;
    }

    public Integer getPaidSpaceHouses() {
        return paidSpaceHouses;
    }

    public void setPaidSpaceHouses(Integer paidSpaceHouses) {
        this.paidSpaceHouses = paidSpaceHouses;
    }

    public Integer getUnPaidSpaceHouses() {
        return unPaidSpaceHouses;
    }

    public void setUnPaidSpaceHouses(Integer unPaidSpaceHouses) {
        this.unPaidSpaceHouses = unPaidSpaceHouses;
    }

    public String getOnlinePercentage() {
        return onlinePercentage;
    }

    public void setOnlinePercentage(String onlinePercentage) {
        this.onlinePercentage = onlinePercentage;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("ExpenseData{");
        sb.append("allPaidProperty=").append(allPaidProperty);
        sb.append(", allUnPaidProperty=").append(allUnPaidProperty);
        sb.append(", allPaidEnergy=").append(allPaidEnergy);
        sb.append(", allUnPaidEnergy=").append(allUnPaidEnergy);
        sb.append(", allPaidPublic=").append(allPaidPublic);
        sb.append(", allUnPaidPublic=").append(allUnPaidPublic);
        sb.append(", allPaidSpace=").append(allPaidSpace);
        sb.append(", allUnPaidSpace=").append(allUnPaidSpace);
        sb.append(", paidPropertyHouses=").append(paidPropertyHouses);
        sb.append(", unPaidPropertyHouses=").append(unPaidPropertyHouses);
        sb.append(", paidEnergyHouses=").append(paidEnergyHouses);
        sb.append(", unPaidEnergyHouses=").append(unPaidEnergyHouses);
        sb.append(", paidPublicHouses=").append(paidPublicHouses);
        sb.append(", unPaidPublicHouses=").append(unPaidPublicHouses);
        sb.append(", paidSpaceHouses=").append(paidSpaceHouses);
        sb.append(", unPaidSpaceHouses=").append(unPaidSpaceHouses);
        sb.append(", onlinePercentage='").append(onlinePercentage).append('\'');
        sb.append(", overDueHouses=").append(overDueHouses);
        sb.append(", paidTotal=").append(paidTotal);
        sb.append(", unPaidTotal=").append(unPaidTotal);
        sb.append('}');
        return sb.toString();
    }
}
