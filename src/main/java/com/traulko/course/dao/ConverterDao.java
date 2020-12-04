package com.traulko.course.dao;

import com.traulko.course.entity.CustomConverter;
import com.traulko.course.exception.DaoException;

import java.util.List;
import java.util.Optional;

public interface ConverterDao {
    Optional<CustomConverter> findLatestConverter() throws DaoException;

    List<Double> findAllCurrencyRatios(String fromCurrencyValue, String toCurrencyValue) throws DaoException;

    List<CustomConverter> findAll() throws DaoException;

    double calculateCurrencyRatio(String fromCurrencyValue, String toCurrencyValue) throws DaoException;

    boolean add(CustomConverter converter) throws DaoException;
}
