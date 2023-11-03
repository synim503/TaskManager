package com.netcracker.unc.team35.task_manager.model.search;

import com.netcracker.unc.team35.task_manager.model.Task;
import com.querydsl.collections.FunctionalHelpers;

import java.util.function.Predicate;

public abstract class AbstractPredicate implements Predicate<Task>, IQuerydslPredicate {
    public boolean test(Task task) {
        return FunctionalHelpers.wrap(qdslPredicate()).test(task);
    }

    @Override
    public abstract com.querydsl.core.types.Predicate qdslPredicate();
}
