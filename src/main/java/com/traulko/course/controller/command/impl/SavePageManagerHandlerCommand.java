package com.traulko.course.controller.command.impl;

import com.traulko.course.controller.PageManagerHandler;
import com.traulko.course.controller.RequestParameter;
import com.traulko.course.controller.command.CustomCommand;
import com.traulko.course.entity.Batch;

public class SavePageManagerHandlerCommand implements CustomCommand {
    @Override
    public Batch execute(Batch requestBatch) {
        String value = String.valueOf(requestBatch.getBatchMap().get(RequestParameter.PAGE_MANAGER_HANDLER_VALUE));
        PageManagerHandler.initInstance(value);
        return new Batch();
    }
}
