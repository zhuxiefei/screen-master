package com.betel.estatemgmt.business.web.screen.model;

/**
 * <p>
 * Describe this class...
 * </p>
 * ClassName: HouseData <br/>
 * Author: Xia.yx  <br/>
 * Date: 2018/1/9 9:43 <br/>
 * Version: 1.0 <br/>
 */
public class HouseData {

    private Integer fullHouses;

    private Integer rentHouses;

    private Integer emptyHouses;

    private String fullPercentage;

    private String rentPercentage;

    private String emptyPercentage;

    public Integer getFullHouses() {
        return fullHouses;
    }

    public void setFullHouses(Integer fullHouses) {
        this.fullHouses = fullHouses;
    }

    public Integer getRentHouses() {
        return rentHouses;
    }

    public void setRentHouses(Integer rentHouses) {
        this.rentHouses = rentHouses;
    }

    public Integer getEmptyHouses() {
        return emptyHouses;
    }

    public void setEmptyHouses(Integer emptyHouses) {
        this.emptyHouses = emptyHouses;
    }

    public String getFullPercentage() {
        return fullPercentage;
    }

    public void setFullPercentage(String fullPercentage) {
        this.fullPercentage = fullPercentage;
    }

    public String getRentPercentage() {
        return rentPercentage;
    }

    public void setRentPercentage(String rentPercentage) {
        this.rentPercentage = rentPercentage;
    }

    public String getEmptyPercentage() {
        return emptyPercentage;
    }

    public void setEmptyPercentage(String emptyPercentage) {
        this.emptyPercentage = emptyPercentage;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("HouseData{");
        sb.append("fullHouses=").append(fullHouses);
        sb.append(", rentHouses=").append(rentHouses);
        sb.append(", emptyHouses=").append(emptyHouses);
        sb.append(", fullPercentage=").append(fullPercentage);
        sb.append(", rentPercentage=").append(rentPercentage);
        sb.append(", emptyPercentage=").append(emptyPercentage);
        sb.append('}');
        return sb.toString();
    }
}
