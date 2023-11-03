package com.netcracker.unc.team35.task_manager.model.scheduler;

import com.netcracker.unc.team35.task_manager.model.Importance;

import java.time.Duration;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class NotificationsConfig {
    static Map<Importance, ImportanceNotificationConfig> cfg = new HashMap<>();

    static {
        cfg.put(Importance.URGENT, new ImportanceNotificationConfig(4, Duration.ofHours(1)));
        cfg.put(Importance.HIGH, new ImportanceNotificationConfig(2, Duration.ofMinutes(40)));
        cfg.put(Importance.LOW, new ImportanceNotificationConfig(2, Duration.ofMinutes(10)));
    }

    public static List<LocalDateTime> calculateNotificationsSchedule(Importance importance, LocalDateTime dueDate) {
        List<LocalDateTime> res = new ArrayList<>();
        ImportanceNotificationConfig importanceNotificationConfig = cfg.get(importance);
        LocalDateTime notificationDateTime = dueDate.minus(importanceNotificationConfig.notificationsDuration)
                .truncatedTo(ChronoUnit.MINUTES);
        Duration notificationFrequency = importanceNotificationConfig.notificationsDuration.dividedBy(importanceNotificationConfig.notificationsCount);

        do {
            res.add(notificationDateTime);
            notificationDateTime = notificationDateTime.plus(notificationFrequency).truncatedTo(ChronoUnit.MINUTES);
        } while (notificationDateTime.isBefore(dueDate));
        return res;
    }


}
