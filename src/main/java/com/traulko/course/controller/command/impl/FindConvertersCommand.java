package com.traulko.course.controller.command.impl;

import com.traulko.course.controller.RequestParameter;
import com.traulko.course.controller.command.CustomCommand;
import com.traulko.course.entity.Batch;
import com.traulko.course.entity.CustomConverter;
import com.traulko.course.entity.User;
import com.traulko.course.exception.ServiceException;
import com.traulko.course.service.ConverterService;
import com.traulko.course.service.UserService;
import com.traulko.course.service.impl.ConverterServiceImpl;
import com.traulko.course.service.impl.UserServiceImpl;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.ArrayList;
import java.util.List;

public class FindConvertersCommand implements CustomCommand {
    private static final ConverterService converterService = new ConverterServiceImpl();
    private static final Logger LOGGER = LogManager.getLogger(FindConvertersCommand.class);

    @Override
    public Batch execute(Batch requestBatch) {
        List<CustomConverter> converterList = new ArrayList<>();
        try {
            converterList = converterService.findAllConverters();
        } catch (ServiceException e) {
            LOGGER.log(Level.ERROR, "Error while finding all converters", e);
        }
        Batch batch = new Batch();
        batch.getBatchMap().put(RequestParameter.CONVERTER_LIST, converterList);
        return batch;
    }
}
