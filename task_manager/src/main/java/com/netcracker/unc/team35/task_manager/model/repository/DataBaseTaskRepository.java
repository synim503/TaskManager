package com.netcracker.unc.team35.task_manager.model.repository;

import com.netcracker.unc.team35.task_manager.model.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

/**
 * A class for working with the database repository
 * @author unc 21-22
 * @version 1.0
 */
@Service
public class DataBaseTaskRepository extends AbstractTaskRepository {

    private final DataBaseBridge mySqlBridge;
    /**
     * Instantiates a new Data base task repository.
     *
     * @param mySqlBridge the my sql bridge
     */
    @Autowired
    public DataBaseTaskRepository(DataBaseBridge mySqlBridge) {
        this.mySqlBridge = mySqlBridge;
    }

    @Override
    public Task findById(String id) {
        return mySqlBridge.findById(
            Long.parseLong(id)).get();
    }

    @Override
    public Collection<Task> getAllTasks() {
        return StreamSupport.stream(mySqlBridge.findAll().spliterator(), false)
                .collect(Collectors.toList());
    }

    @Override
    public Task add(String descr, LocalDateTime dateTime, Importance importance) {
        TaskModel task = new TaskModel(descr, dateTime, importance, Status.AWAITING);
        mySqlBridge.save(task);
        return task;
    }

    @Override
    public void cancel(String id) {
        update(id, new TaskUpdate().status(Status.CANCELLED));
    }

    @Override
    public void complete(String id) {
        update(id, new TaskUpdate().status(Status.COMPLETED));
    }

    @Override
    public void update(String id, TaskUpdate taskUpdate) {
        final Task byId = findById(id);
        final Status status = taskUpdate.getStatus();
        if (status != null)
            byId.setStatus(status);
        final LocalDateTime dueDate = taskUpdate.getDueDate();
        if (dueDate != null)
            byId.setDueDate(dueDate);
        final Importance importance = taskUpdate.getImportance();
        if (importance != null)
            byId.setImportance(importance);
        String descr = taskUpdate.getDescr();
        if (descr != null)
            byId.setDescr(descr);
        List<TaskLogEntry> log = taskUpdate.getLog();
        if (log != null)
            byId.setDescr(descr);
        mySqlBridge.save((TaskModel)byId);

    }

    @Override
    protected String nextId() {
        return null;
    }

    @Override
    public void flush() {
    }

    @Override
    public List<Task> findByPredicate(com.querydsl.core.types.Predicate predicate) {
        Iterable<Task> all = mySqlBridge.findAll(predicate);
        return (List<Task>) all;
    }

}
