package com.traulko.course.entity;

import java.io.Serializable;
import java.time.LocalDate;

public class CustomConverter implements Serializable {
    private Integer converterId;
    private double bynValue;
    private double usdValue;
    private double eurValue;
    private double rubValue;
    private LocalDate creationDate;

    public CustomConverter(Integer converterId, double bynValue, double usdValue, double eurValue,
                           double rubValue, LocalDate creationDate) {
        this.converterId = converterId;
        this.bynValue = bynValue;
        this.usdValue = usdValue;
        this.eurValue = eurValue;
        this.rubValue = rubValue;
        this.creationDate = creationDate;
    }

    public LocalDate getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(LocalDate creationDate) {
        this.creationDate = creationDate;
    }

    public Integer getConverterId() {
        return converterId;
    }

    public void setConverterId(Integer converterId) {
        this.converterId = converterId;
    }

    public double getBynValue() {
        return bynValue;
    }

    public void setBynValue(double bynValue) {
        this.bynValue = bynValue;
    }

    public double getUsdValue() {
        return usdValue;
    }

    public void setUsdValue(double usdValue) {
        this.usdValue = usdValue;
    }

    public double getEurValue() {
        return eurValue;
    }

    public void setEurValue(double eurValue) {
        this.eurValue = eurValue;
    }

    public double getRubValue() {
        return rubValue;
    }

    public void setRubValue(double rubValue) {
        this.rubValue = rubValue;
    }
}
