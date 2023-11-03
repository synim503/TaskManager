package com.netcracker.unc.team35.task_manager.model;


import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

/**
 * The interface database bridge.
 * The interface is extended with other repositories to increase the functionality required to meet your goals
 */
@Repository
public interface DataBaseBridge extends CrudRepository<TaskModel, Long>, QuerydslPredicateExecutor<Task> {

}
