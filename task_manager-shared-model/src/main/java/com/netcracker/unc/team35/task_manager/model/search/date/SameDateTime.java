package com.netcracker.unc.team35.task_manager.model.search.date;

import com.netcracker.unc.team35.task_manager.model.QTaskModel;
import com.netcracker.unc.team35.task_manager.model.search.AbstractPredicate;

import java.time.LocalDateTime;

public class SameDateTime extends AbstractPredicate {
    private LocalDateTime dueDateTimeFrom;
    private LocalDateTime dueDateTimeTo;

    public SameDateTime(LocalDateTime dueDateTimeFrom, LocalDateTime dueDateTimeTo) {
        this.dueDateTimeFrom = dueDateTimeFrom;
        this.dueDateTimeTo = dueDateTimeTo;
    }

    public com.querydsl.core.types.Predicate qdslPredicate() {
        return QTaskModel.taskModel.dueDate.after(dueDateTimeFrom).and(
            QTaskModel.taskModel.dueDate.before(dueDateTimeTo)
        );
    }
}
