package com.netcracker.unc.team35.task_manager.model.scheduler;

import java.time.Duration;

class ImportanceNotificationConfig {
    public ImportanceNotificationConfig(int notificationsCount, Duration notificationsDuration) {
        this.notificationsCount = notificationsCount;
        this.notificationsDuration = notificationsDuration;
    }

    int notificationsCount;
    Duration notificationsDuration;
}
