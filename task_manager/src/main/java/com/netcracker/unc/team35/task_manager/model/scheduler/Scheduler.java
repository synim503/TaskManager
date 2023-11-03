package com.netcracker.unc.team35.task_manager.model.scheduler;

import com.google.common.collect.HashMultimap;
import com.google.common.collect.Multimap;
import com.netcracker.unc.team35.task_manager.model.ITaskRepository;
import com.netcracker.unc.team35.task_manager.model.TaskModel;
import org.springframework.beans.factory.annotation.Autowired;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.Collection;
import java.util.List;
import java.util.TimerTask;

public class Scheduler extends TimerTask {
    private Multimap<LocalDateTime, String> time2taskId = HashMultimap.create();
    private Multimap<String, LocalDateTime> taskId2time = HashMultimap.create();
    private ITaskRepository taskRepository;
    private final INotifier notifier;

    @Autowired
    public Scheduler(ITaskRepository repository, INotifier notifier) {
        this.taskRepository = repository;
        this.notifier = notifier;
    }


    @Override
    public void run() {
        LocalDateTime now = LocalDateTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        System.out.println("сейчас: " + now.format(formatter));
        LocalDateTime localDateTime = now.truncatedTo(ChronoUnit.MINUTES);
        Collection<String> taskIds = time2taskId.get(localDateTime);
        if (taskIds.isEmpty()) {
            System.out.println("        нет напоминаний");
            return;
        }
        for (String taskId : taskIds) {
            TaskModel byId = (TaskModel) taskRepository.findById(taskId);
            String descr = byId.getDescr();
            System.out.println("напоминаем о необходимости выполнить задачу:" + descr + " до " + byId.getDueDate());
            notifier.notify(new TaskNotificationInfo(taskId, descr, byId.getDueDate(), byId.getImportance().name()));
        }
    }

    public void schedule(String taskId, List<LocalDateTime> notificationsSchedule) {
        removeTaskSchedule(taskId);
        if (!notificationsSchedule.isEmpty()) {
            for (LocalDateTime localDateTime : notificationsSchedule) {
                time2taskId.put(localDateTime, taskId);
            }
            taskId2time.putAll(taskId, notificationsSchedule);
        }
    }

    private void removeTaskSchedule(String taskId) {
        Collection<LocalDateTime> localDateTimes = taskId2time.get(taskId);
        for (LocalDateTime localDateTime : localDateTimes) {
            time2taskId.remove(localDateTime, taskId);
        }
        taskId2time.removeAll(taskId);
    }
}
