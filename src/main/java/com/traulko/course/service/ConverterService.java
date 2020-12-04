package com.traulko.course.service;

import com.traulko.course.entity.CustomConverter;
import com.traulko.course.exception.ServiceException;

import java.util.List;
import java.util.Optional;

public interface ConverterService {
    double calculateConvertedResult(String value, String fromCurrencyParameter,
                                    String toCurrencyParameter) throws ServiceException;

    boolean addConverter(String bynValue, String usdValue, String eurValue, String rubValue) throws ServiceException;

    List<CustomConverter> findAllConverters() throws ServiceException;

    Optional<CustomConverter> findLatestConverter() throws ServiceException;

    List<Double> getAllConverterRatios(String fromCurrencyParameter, String toCurrencyParameter) throws ServiceException;

    double calculateMinRatio(List<Double> ratioList);

    double calculateMaxRatio(List<Double> ratioList);

    double calculateRatioStep(double minRatio, double maxRatio);
}
