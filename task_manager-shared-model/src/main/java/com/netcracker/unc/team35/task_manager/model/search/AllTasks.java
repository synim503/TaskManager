package com.netcracker.unc.team35.task_manager.model.search;

import com.netcracker.unc.team35.task_manager.model.QTaskModel;

public class AllTasks extends AbstractPredicate {
    @Override
    public com.querydsl.core.types.Predicate qdslPredicate() {
        return QTaskModel.taskModel.isNotNull();
    }
}
