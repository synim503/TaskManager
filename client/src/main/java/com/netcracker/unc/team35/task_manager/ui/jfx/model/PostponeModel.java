package com.netcracker.unc.team35.task_manager.ui.jfx.model;

import java.time.temporal.TemporalAmount;

public class PostponeModel {
    private TemporalAmount postponingPeriod;
    private String value;

    public PostponeModel(TemporalAmount postponingPeriod, String value) {
        this.postponingPeriod = postponingPeriod;
        this.value = value;
    }

    public TemporalAmount getPostponingPeriod() {
        return postponingPeriod;
    }

    public void setPostponingPeriod(TemporalAmount postponingPeriod) {
        this.postponingPeriod = postponingPeriod;
    }

    @Override
    public String toString() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }
}