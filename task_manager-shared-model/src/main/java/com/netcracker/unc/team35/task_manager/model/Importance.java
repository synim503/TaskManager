package com.netcracker.unc.team35.task_manager.model;

import java.time.Duration;
import java.time.Period;
import java.time.temporal.TemporalAmount;

/**
 * The ENUM class with the importance of tasks
 *  @author unc 21-22
 *  @version 1.0
 */
public enum Importance {
    /**
     * Urgent importance.
     */
    URGENT(1, Duration.ofHours(2)),
    /**
     * High importance.
     */
    HIGH(3, Period.ofDays(1)),
    /**
     * Low importance.
     */
    LOW(5, Period.ofDays(3));

    Importance(int allowedPostpones, TemporalAmount postponingPeriod) {

        this.allowedPostpones = allowedPostpones;
        this.postponingPeriod = postponingPeriod;
    }

    /**
     * Gets allowed postpones.
     *
     * @return the allowed postpones
     */
    public int getAllowedPostpones() {
        return allowedPostpones;
    }

    /**
     * Gets postponing period.
     *
     * @return the postponing period
     */
    public TemporalAmount getPostponingPeriod() {
        return postponingPeriod;
    }

    private int allowedPostpones;

    private TemporalAmount postponingPeriod;
}
