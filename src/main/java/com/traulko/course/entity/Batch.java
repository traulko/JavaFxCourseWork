package com.traulko.course.entity;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Map;

public class Batch implements Serializable {
    private Map<String, Object> batchMap;

    public Batch(Map<String, Object> batchMap) {
        this.batchMap = batchMap;
    }

    public Map<String, Object> getBatchMap() {
        return batchMap;
    }

    public void setBatchMap(Map<String, Object> batchMap) {
        this.batchMap = batchMap;
    }
}