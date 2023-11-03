package com.netcracker.unc.team35.task_manager.model.search;

import com.netcracker.unc.team35.task_manager.model.Importance;
import com.netcracker.unc.team35.task_manager.model.QTaskModel;

public class ByImportance extends AbstractPredicate {
    private Importance taskImportance;

    public ByImportance(Importance taskImportance) {
        this.taskImportance = taskImportance;
    }

    public com.querydsl.core.types.Predicate qdslPredicate() {
        return QTaskModel.taskModel.importance.eq(taskImportance);
    }
}
