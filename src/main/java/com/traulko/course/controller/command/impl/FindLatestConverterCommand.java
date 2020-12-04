package com.traulko.course.controller.command.impl;

import com.traulko.course.controller.RequestParameter;
import com.traulko.course.controller.command.CustomCommand;
import com.traulko.course.entity.Batch;
import com.traulko.course.entity.CustomConverter;
import com.traulko.course.exception.ServiceException;
import com.traulko.course.service.ConverterService;
import com.traulko.course.service.impl.ConverterServiceImpl;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class FindLatestConverterCommand implements CustomCommand {
    private static final ConverterService converterService = new ConverterServiceImpl();
    private static final Logger LOGGER = LogManager.getLogger(FindLatestConverterCommand.class);

    @Override
    public Batch execute(Batch requestBatch) {
        Optional<CustomConverter> converterOptional = Optional.empty();
        try {
            converterOptional = converterService.findLatestConverter();
        } catch (ServiceException e) {
            LOGGER.log(Level.ERROR, "Error while finding all converters", e);
        }
        Batch batch = new Batch();
        if (converterOptional.isPresent()) {
            batch.getBatchMap().put(RequestParameter.CONVERTER, converterOptional.get());
        }
        return batch;
    }
}
