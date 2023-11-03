package com.netcracker.unc.team35.task_manager.model.search;

import com.netcracker.unc.team35.task_manager.model.QTaskModel;

public class ByNameLike extends AbstractPredicate {
    private String descriptionPart;

    public ByNameLike(String descriptionPart) {
        this.descriptionPart = descriptionPart;
    }

    public com.querydsl.core.types.Predicate qdslPredicate() {
        return QTaskModel.taskModel.descr.contains(descriptionPart);
    }
}
