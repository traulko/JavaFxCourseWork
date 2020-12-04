package com.traulko.course.builder;

import com.traulko.course.entity.CustomConverter;
import com.traulko.course.entity.User;

import java.time.LocalDate;

/**
 * The {@code UserBuilder} class represents user builder.
 *
 * @author Yan Traulko
 * @version 1.0
 */
public class ConverterBuilder {
    private Integer converterId;
    private double bynValue;
    private double usdValue;
    private double eurValue;
    private double rubValue;
    private LocalDate creationDate;

    /**
     * Sets converter id.
     *
     * @param converterId the converter id
     */
    public void setConverterId(Integer converterId) {
        this.converterId = converterId;
    }

    /**
     * Sets byn value.
     *
     * @param bynValue the byn value
     */
    public void setBynValue(double bynValue) {
        this.bynValue = bynValue;
    }

    /**
     * Sets usd value.
     *
     * @param usdValue the usd value
     */
    public void setUsdValue(double usdValue) {
        this.usdValue = usdValue;
    }

    /**
     * Sets eur value.
     *
     * @param eurValue the eur value
     */
    public void setEurValue(double eurValue) {
        this.eurValue = eurValue;
    }

    /**
     * Sets rub value.
     *
     * @param rubValue the rub value
     */
    public void setRubValue(double rubValue) {
        this.rubValue = rubValue;
    }

    /**
     * Sets creation date.
     *
     * @param creationDate the creation date
     */
    public void setCreationDate(LocalDate creationDate) {
        this.creationDate = creationDate;
    }

    /**
     * Gets converter.
     *
     * @return the converter
     */
    public CustomConverter getConverter() {
        return new CustomConverter(converterId, bynValue, usdValue, eurValue, rubValue, creationDate);
    }
}
