package com.traulko.course.entity;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

public class Batch implements Serializable {
    private Map<String, Object> batchMap;

    public Batch() {
        this.batchMap = new HashMap<>();
    }

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