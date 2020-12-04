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

public class AddConverterCommand implements CustomCommand {
    private static final ConverterService converterService = new ConverterServiceImpl();
    private static final Logger LOGGER = LogManager.getLogger(AddConverterCommand.class);

    @Override
    public Batch execute(Batch requestBatch) {
        boolean result = false;
        String usdValue = requestBatch.getBatchMap().get(RequestParameter.USD_CURRENCY).toString();
        String eurValue = requestBatch.getBatchMap().get(RequestParameter.EUR_CURRENCY).toString();
        String rubValue = requestBatch.getBatchMap().get(RequestParameter.RUB_CURRENCY).toString();
        try {
            result = converterService.addConverter(usdValue, eurValue, rubValue);
        } catch (ServiceException e) {
            LOGGER.log(Level.ERROR, "Error while adding converter", e);
        }
        Batch batch = new Batch();
        batch.getBatchMap().put(RequestParameter.BOOLEAN_RESULT, result);
        return batch;
    }
}
