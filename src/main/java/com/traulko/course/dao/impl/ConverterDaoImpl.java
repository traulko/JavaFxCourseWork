package com.traulko.course.dao.impl;

import com.traulko.course.dao.ColumnName;
import com.traulko.course.dao.ConverterDao;
import com.traulko.course.dao.connection.ConnectionPool;
import com.traulko.course.entity.CustomConverter;
import com.traulko.course.exception.ConnectionDatabaseException;
import com.traulko.course.exception.DaoException;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class ConverterDaoImpl implements ConverterDao {
    private static final ConverterDaoImpl INSTANCE = new ConverterDaoImpl();

    private static final String FIND_LATEST_CONVERTER = "SELECT converter_id, byn, usd, eur, rub, converter_creation_date" +
            " FROM converter order by converter_id desc limit 1";
    private static final String FIND_ALL = "SELECT converter_id, byn, usd, eur, rub, converter_creation_date" +
            " FROM converter";
    private static final String ADD_CONVERTER = "INSERT into converter (byn, usd, eur, rub, converter_creation_date) values" +
            " (?, ?, ?, ?, ?)";

    private ConverterDaoImpl() {
    }

    /**
     * Gets instance.
     *
     * @return the instance
     */
    public static ConverterDaoImpl getInstance() {
        return INSTANCE;
    }

    @Override
    public Optional<CustomConverter> findLatestConverter() throws DaoException {
        Optional<CustomConverter> converterOptional = Optional.empty();
        try (Connection connection = ConnectionPool.getInstance().getConnection();
             PreparedStatement statement = connection.prepareStatement(FIND_LATEST_CONVERTER)) {
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                CustomConverter converter = createConverterFromResultSet(resultSet);
                converterOptional = Optional.of(converter);
            }
        } catch (SQLException | ConnectionDatabaseException e) {
            throw new DaoException("Error finding latest converter", e);
        }
        return converterOptional;
    }

    @Override
    public List<Double> findAllCurrencyRatios(String fromCurrencyValue, String toCurrencyValue) throws DaoException {
        try (Connection connection = ConnectionPool.getInstance().getConnection();
             PreparedStatement statement = connection.prepareStatement(FIND_ALL)) {
            List<Double> currencyRatios = new ArrayList<>();
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                double fromCurrencyResultValue = resultSet.getDouble(fromCurrencyValue);
                double toCurrencyResultValue = resultSet.getDouble(toCurrencyValue);
                double currencyRatio = toCurrencyResultValue/fromCurrencyResultValue;
                currencyRatios.add(currencyRatio);
            }
            return currencyRatios;
        } catch (SQLException | ConnectionDatabaseException e) {
            throw new DaoException("Error finding all converters", e);
        }
    }

    @Override
    public List<CustomConverter> findAll() throws DaoException {
        List<CustomConverter> converterList = new ArrayList<>();
        try (Connection connection = ConnectionPool.getInstance().getConnection();
             PreparedStatement statement = connection.prepareStatement(FIND_ALL)) {
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                CustomConverter converter = createConverterFromResultSet(resultSet);
                converterList.add(converter);
            }
        } catch (SQLException | ConnectionDatabaseException e) {
            throw new DaoException("Finding all converters error", e);
        }
        return converterList;
    }

    @Override
    public double calculateCurrencyRatio(String fromCurrencyValue, String toCurrencyValue) throws DaoException {
        try (Connection connection = ConnectionPool.getInstance().getConnection();
             PreparedStatement statement = connection.prepareStatement(FIND_LATEST_CONVERTER)) {
            double currencyRatio = 0;
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                double fromCurrencyResultValue = resultSet.getDouble(fromCurrencyValue);
                double toCurrencyResultValue = resultSet.getDouble(toCurrencyValue);
                currencyRatio = toCurrencyResultValue/fromCurrencyResultValue;
            }
            return currencyRatio;
        } catch (SQLException | ConnectionDatabaseException e) {
            throw new DaoException("Error finding latest converter", e);
        }
    }

    @Override
    public boolean add(CustomConverter converter) throws DaoException {
        try (Connection connection = ConnectionPool.getInstance().getConnection();
             PreparedStatement statement = connection.prepareStatement(ADD_CONVERTER)) {
            statement.setDouble(1, converter.getBynValue());
            statement.setDouble(2, converter.getUsdValue());
            statement.setDouble(3, converter.getEurValue());
            statement.setDouble(4, converter.getRubValue());
            Date creationDate = Date.valueOf(converter.getCreationDate());
            statement.setLong(5, creationDate.getTime());
            return statement.executeUpdate() > 0;
        } catch (SQLException | ConnectionDatabaseException e) {
            throw new DaoException("Error while adding converter", e);
        }
    }

    private CustomConverter createConverterFromResultSet(ResultSet resultSet) throws SQLException {
        Integer id = resultSet.getInt(ColumnName.CONVERTER_ID);
        double byn = resultSet.getDouble(ColumnName.CONVERTER_BYN);
        double usd = resultSet.getDouble(ColumnName.CONVERTER_USD);
        double eur = resultSet.getDouble(ColumnName.CONVERTER_EUR);
        double rub = resultSet.getDouble(ColumnName.CONVERTER_RUB);
        long creationDateLong = resultSet.getLong(ColumnName.CONVERTER_CREATION_DATE);
        LocalDate creationDate = new Date(creationDateLong).toLocalDate();
        return new CustomConverter(id, byn, usd, eur, rub, creationDate);
    }
}
