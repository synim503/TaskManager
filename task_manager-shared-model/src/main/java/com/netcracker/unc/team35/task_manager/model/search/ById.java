package com.netcracker.unc.team35.task_manager.model.search;

import com.netcracker.unc.team35.task_manager.model.QTaskModel;

public class ById extends AbstractPredicate {
    private Long id;

    public ById(Long id) {
        this.id = id;
    }

    public com.querydsl.core.types.Predicate qdslPredicate() {
        return QTaskModel.taskModel.id.eq(id);
    }
}
