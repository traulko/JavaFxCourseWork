package com.traulko.course.service.impl;

import com.traulko.course.builder.ConverterBuilder;
import com.traulko.course.dao.ConverterDao;
import com.traulko.course.dao.impl.ConverterDaoImpl;
import com.traulko.course.entity.CustomConverter;
import com.traulko.course.exception.DaoException;
import com.traulko.course.exception.ServiceException;
import com.traulko.course.service.ConverterService;
import com.traulko.course.validator.ConverterValidator;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

public class ConverterServiceImpl implements ConverterService {
    private final ConverterDao converterDao = ConverterDaoImpl.getInstance();

    private static final int STEPS_COUNT = 5;

    @Override
    public double calculateConvertedResult(String value, String fromCurrencyParameter,
                                           String toCurrencyParameter) throws ServiceException {
        double result = 0;
        try {
            if (ConverterValidator.isCurrencyValueValid(value)) {
                double currencyRatio = converterDao
                        .calculateCurrencyRatio(fromCurrencyParameter, toCurrencyParameter);
                result = Double.parseDouble(value) * currencyRatio;
            }
        } catch (DaoException e) {
            throw new ServiceException("Error while caculating converted valued", e);
        }
        return result;
    }

    @Override
    public boolean addConverter(String bynValue, String usdValue, String eurValue, String rubValue) throws ServiceException {
        boolean result = false;
        try {
            if (ConverterValidator.isCurrencyValueValid(bynValue)
                    && ConverterValidator.isCurrencyValueValid(usdValue)
                    && ConverterValidator.isCurrencyValueValid(eurValue)
                    && ConverterValidator.isCurrencyValueValid(rubValue)) {
                double bynParsedValue = Double.parseDouble(bynValue);
                double usdParsedValue = Double.parseDouble(usdValue);
                double eurParsedValue = Double.parseDouble(eurValue);
                double rubParsedValue = Double.parseDouble(rubValue);
                LocalDate creationDate = LocalDate.now();
                ConverterBuilder converterBuilder = new ConverterBuilder();
                converterBuilder.setBynValue(bynParsedValue);
                converterBuilder.setUsdValue(usdParsedValue);
                converterBuilder.setEurValue(eurParsedValue);
                converterBuilder.setRubValue(rubParsedValue);
                converterBuilder.setCreationDate(creationDate);
                CustomConverter converter = converterBuilder.getConverter();
                result = converterDao.add(converter);
            }
        } catch (DaoException e) {
            throw new ServiceException("Error while adding new converter", e);
        }
        return result;
    }

    @Override
    public List<CustomConverter> findAllConverters() throws ServiceException {
        List<CustomConverter> converterList = new ArrayList<>();
        try {
            converterList = converterDao.findAll();
        } catch (DaoException e) {
            throw new ServiceException("Error while finding all converters", e);
        }
        return converterList;
    }

    @Override
    public Optional<CustomConverter> findLatestConverter() throws ServiceException {
        Optional<CustomConverter> converterOptional = Optional.empty();
        try {
            converterOptional = converterDao.findLatestConverter();
        } catch (DaoException e) {
            throw new ServiceException("Error while finding all converters", e);
        }
        return converterOptional;
    }

    @Override
    public List<Double> getAllConverterRatios(String fromCurrencyParameter, String toCurrencyParameter) throws ServiceException {
        List<Double> converterRatios = new ArrayList<>();
        try {
            converterRatios = converterDao.findAllCurrencyRatios(fromCurrencyParameter, toCurrencyParameter);
        } catch (DaoException e) {
            throw new ServiceException("Error while finding add converter ratios", e);
        }
        return converterRatios;
    }

    @Override
    public double calculateMinRatio(List<Double> ratioList) {
        double minRatio = Collections.min(ratioList);
        return minRatio - minRatio / 10;
    }

    @Override
    public double calculateMaxRatio(List<Double> ratioList) {
        double maxRatio = Collections.max(ratioList);
        return maxRatio + maxRatio / 10;
    }

    @Override
    public double calculateRatioStep(double minRatio, double maxRatio) {
        return (maxRatio - minRatio) / STEPS_COUNT;
    }
}
