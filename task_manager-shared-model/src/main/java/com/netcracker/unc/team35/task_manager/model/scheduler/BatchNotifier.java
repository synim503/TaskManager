package com.netcracker.unc.team35.task_manager.model.scheduler;

import java.util.ArrayList;
import java.util.List;

public class BatchNotifier implements INotifier {

    List<TaskNotificationInfo> notificationInfos = new ArrayList<>();

    @Override
    public void notify(TaskNotificationInfo taskNotificationInfo) {
        notificationInfos.add(taskNotificationInfo);
    }

    public List<TaskNotificationInfo> getNotificationInfos() {
        List<TaskNotificationInfo> notificationInfos = new ArrayList<>(this.notificationInfos);
        this.notificationInfos.clear();
        return notificationInfos;
    }
}
