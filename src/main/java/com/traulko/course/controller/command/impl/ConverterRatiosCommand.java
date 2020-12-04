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

import java.util.ArrayList;
import java.util.List;

public class ConverterRatiosCommand implements CustomCommand {
    private static final ConverterService converterService = new ConverterServiceImpl();
    private static final Logger LOGGER = LogManager.getLogger(ConverterRatiosCommand.class);

    @Override
    public Batch execute(Batch requestBatch) {
        List<Double> converterRatios = new ArrayList<>();
        String fromCurrencyParameter = requestBatch.getBatchMap().get(RequestParameter.FROM_CURRENCY).toString();
        String toCurrencyParameter = requestBatch.getBatchMap().get(RequestParameter.TO_CURRENCY).toString();
        double converterMinRatio = 0;
        double converterMaxRatio = 0;
        double converterRatioStep = 0;
        try {
            converterRatios = converterService.getAllConverterRatios(fromCurrencyParameter, toCurrencyParameter);
            converterMinRatio = converterService.calculateMinRatio(converterRatios);
            converterMaxRatio = converterService.calculateMaxRatio(converterRatios);
            converterRatioStep = converterService.calculateRatioStep(converterMinRatio, converterMaxRatio);
        } catch (ServiceException e) {
            LOGGER.log(Level.ERROR, "Error while finding converter ratios", e);
        }
        Batch batch = new Batch();
        batch.getBatchMap().put(RequestParameter.CONVERTER_RATIOS_LIST, converterRatios);
        batch.getBatchMap().put(RequestParameter.CONVERTER_MIN_RATIO, converterMinRatio);
        batch.getBatchMap().put(RequestParameter.CONVERTER_MAX_RATIO, converterMaxRatio);
        batch.getBatchMap().put(RequestParameter.CONVERTER_RATIO_STEP, converterRatioStep);
        return batch;
    }
}
