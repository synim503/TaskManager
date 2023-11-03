package com.netcracker.unc.team35.task_manager.model.search.date;

import com.netcracker.unc.team35.task_manager.model.QTaskModel;
import com.netcracker.unc.team35.task_manager.model.search.AbstractPredicate;

import java.time.LocalDateTime;

public class EarlierThan extends AbstractPredicate {
    private LocalDateTime dueDateTime;

    public EarlierThan(LocalDateTime dueDateTime) {
        this.dueDateTime = dueDateTime;
    }

    public com.querydsl.core.types.Predicate qdslPredicate() {
        return QTaskModel.taskModel.dueDate.before(dueDateTime);
    }
}
