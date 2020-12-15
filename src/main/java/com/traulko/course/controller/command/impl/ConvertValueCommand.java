package com.traulko.course.controller.command.impl;

import com.traulko.course.controller.RequestParameter;
import com.traulko.course.controller.command.CustomCommand;
import com.traulko.course.entity.Batch;
import com.traulko.course.exception.ServiceException;
import com.traulko.course.service.ConverterService;
import com.traulko.course.service.impl.ConverterServiceImpl;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class ConvertValueCommand implements CustomCommand {
    private static final ConverterService converterService = new ConverterServiceImpl();
    private static final Logger LOGGER = LogManager.getLogger(ConvertValueCommand.class);

    @Override
    public Batch execute(Batch requestBatch) {
        double convertedResult = 0;
        String value = requestBatch.getBatchMap().get(RequestParameter.CONVERTER_VALUE).toString();
        String fromCurrencyParameter = requestBatch.getBatchMap().get(RequestParameter.FROM_CURRENCY).toString();
        String toCurrencyParameter = requestBatch.getBatchMap().get(RequestParameter.TO_CURRENCY).toString();
        try {
            convertedResult = converterService
                    .calculateConvertedResult(value, fromCurrencyParameter, toCurrencyParameter);
        } catch (ServiceException e) {
            LOGGER.log(Level.ERROR, "Error while converting value", e);
        }
        Batch batch = new Batch();
        batch.getBatchMap().put(RequestParameter.CONVERTER_RESULT, convertedResult);
        return batch;
    }
}
