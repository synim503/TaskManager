package com.netcracker.unc.team35.task_manager.server.api.controller;

import com.netcracker.unc.team35.task_manager.logic.Controller;
import com.netcracker.unc.team35.task_manager.logic.commands.*;
import com.netcracker.unc.team35.task_manager.model.Importance;
import com.netcracker.unc.team35.task_manager.model.Task;
import com.netcracker.unc.team35.task_manager.model.TaskModel;
import com.netcracker.unc.team35.task_manager.model.search.AllTasks;
import com.netcracker.unc.team35.task_manager.model.search.ById;
import com.netcracker.unc.team35.task_manager.model.search.ByImportance;
import com.netcracker.unc.team35.task_manager.model.search.ByNameLike;
import com.netcracker.unc.team35.task_manager.model.search.date.EarlierThan;
import com.netcracker.unc.team35.task_manager.model.search.date.LaterThan;
import com.netcracker.unc.team35.task_manager.model.search.date.SameDateTime;
import com.querydsl.core.types.ExpressionUtils;
import com.querydsl.core.types.Predicate;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 * The controller that implements the service API
 * @author unc 21-22
 * @version 1.0
 */
@RestController
@RequestMapping("/api/v1/tasks")
public class TasksRestController {
    private final Controller controller;
    @Autowired
    private SseService sseService;

    /**
     * Instantiates a new Tasks rest controller.
     *
     * @param controller the controller
     */
    @Autowired
    public TasksRestController(Controller controller) {
        this.controller = controller;
    }

    /**
     * Gets notifications emitter.
     *
     * @return the notifications emitter
     */
    @RequestMapping(method = RequestMethod.GET, value = "/notifications")
    public SseEmitter getNotificationsEmitter() {
        return sseService.getEmitter();
    }

    /**
     * Gets task.
     * API request.
     *
     * @param id the id
     * @return the task
     */
    @RequestMapping(method = RequestMethod.GET,
            params = {"id"},
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Task> getTask(@RequestParam(value = "id") Long id) {
        TaskModel task;
        try {
            task = (TaskModel) ((List<Task>) controller.perform(new FindCommand(new ById(id).qdslPredicate()))).get(0);
        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
        return new ResponseEntity<>(task, HttpStatus.OK);
    }

    /**
     * Gets task by predicat.
     * API request.
     *
     * @param descr      the description
     * @param dueFrom    the due from
     * @param dueTo      the due to
     * @param importance the importance
     * @return the task by predicat
     */
    @RequestMapping(method = RequestMethod.GET,
            params = {"descr", "dueFrom", "dueTo", "importance"},
            produces = MediaType.APPLICATION_JSON_VALUE)
    @CrossOrigin(origins = "http://localhost:9999")
    public ResponseEntity<List<Task>> getTaskByPredicat(@RequestParam(value = "descr", required = false)
                                                                String descr,
                                                             @RequestParam(value = "dueFrom", required = false)
                                                        @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
                                                                LocalDateTime dueFrom,
                                                             @RequestParam(value = "dueTo", required = false)
                                                        @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
                                                                LocalDateTime dueTo,
                                                             @RequestParam(value = "importance", required = false)
                                                                Importance importance) {

        List<Task> tasks;
        try {
            tasks = (List<Task>) controller.perform(new FindCommand(buildSearchPredicate(descr, importance, dueFrom, dueTo)));
        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
        return new ResponseEntity<>(tasks, HttpStatus.OK);
    }


    /**
     * Add task.
     * API request.
     *
     * @param task the task
     * @return the response entity
     */
    @RequestMapping(
            method = RequestMethod.POST,
            value = "/add",
            produces = MediaType.TEXT_PLAIN_VALUE)
    public ResponseEntity<Long> addTask(@RequestBody TaskModel task) {
        Long taskId = null;
        try {
            taskId = (Long) controller.perform(
                    new AddTaskCommand(task.getDescr(), task.getDueDate(), task.getImportance()));
        } catch (Exception e) {
            ResponseEntity.noContent().build();
        }
        return new ResponseEntity<>(taskId, HttpStatus.OK);
    }


    /**
     * Postpone the deadline for the task.
     * API request.
     *
     * @param id     the id
     * @param period the period
     * @return the response entity
     */
    @RequestMapping(method = RequestMethod.PUT, value = "/postpone")
    @ResponseBody
    public ResponseEntity<String> postpone(@RequestParam Long id, @RequestParam Duration period) {
        try {
            controller.perform(new PostponeTaskCommand(id.toString(), period));
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body(e.getMessage());
        }
        return ResponseEntity.ok("postponed");
    }

    /**
     * Cancel the deadline for the task.
     * API request.
     *
     * @param id the id
     * @return the response entity
     */
    @RequestMapping(method = RequestMethod.PUT, value = "/cancel")
    @ResponseBody
    public ResponseEntity<String> cancel(@RequestParam Long id) {
        try {
            controller.perform(new CancelTaskCommand(id.toString()));
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body(e.getMessage());
        }
        return ResponseEntity.ok("cancelled");
    }


    /**
     * Complete the deadline for the task.
     * API request.
     *
     * @param id the id
     * @return the response entity
     */
    @RequestMapping(method = RequestMethod.PUT, value = "/complete")
    @ResponseBody
    public ResponseEntity<String> complete(@RequestParam Long id) {
        try {
            controller.perform(new CompleteTaskCommand(id.toString()));
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body(e.getMessage());
        }
        return ResponseEntity.ok("completed");
    }

    /**
     * Updating task information.
     * API request.
     * @param id         the id
     * @param descr      the description
     * @param dueDate    the due date
     * @param importance the importance
     * @return the response entity
     */
    @RequestMapping(method = RequestMethod.PUT, value = "/update")
    @ResponseBody
    public ResponseEntity<String> update(@RequestParam(value = "id", required = false)
                                                 Long id,
                                         @RequestParam(value = "descr")
                                                 String descr,
                                         @RequestParam(value = "dueDate")
                                         @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
                                                 LocalDateTime dueDate,
                                         @RequestParam(value = "importance")
                                                 Importance importance) {
        try {
            controller.perform(new UpdateCommand(id.toString(), descr, dueDate, importance));
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body(e.getMessage());
        }
        return ResponseEntity.ok("updated");
    }

    /**
     * Method collecting predicate by input string values.
     *
     * @param description
     * @param importance
     * @param datePeriodFromValid
     * @param datePeriodToValid
     */

    private com.querydsl.core.types.Predicate buildSearchPredicate(String description,
                                                 Importance importance,
                                                 LocalDateTime datePeriodFromValid,
                                                 LocalDateTime datePeriodToValid) {
        List<com.querydsl.core.types.Predicate> predicates = new ArrayList<>();

        if (StringUtils.isNotEmpty(description)) {
            predicates.add(new ByNameLike(description).qdslPredicate());
        }

        if (importance != null) {
            predicates.add(new ByImportance(importance).qdslPredicate());
        }

        if (datePeriodFromValid != null && datePeriodToValid == null) {
            predicates.add(new LaterThan(datePeriodFromValid).qdslPredicate());
        } else if (datePeriodFromValid == null && datePeriodToValid != null) {
            predicates.add(new EarlierThan(datePeriodToValid).qdslPredicate());
        } else if (datePeriodFromValid != null) {
            predicates.add(new SameDateTime(datePeriodFromValid, datePeriodToValid).qdslPredicate());
        }
        Predicate allTasks = new AllTasks().qdslPredicate();
        return predicates.stream().reduce(allTasks, ExpressionUtils::allOf);
    }
}
