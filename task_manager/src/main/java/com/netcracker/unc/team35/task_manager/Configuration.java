package com.netcracker.unc.team35.task_manager;

import com.netcracker.unc.team35.task_manager.logic.Controller;
import com.netcracker.unc.team35.task_manager.model.DataBaseBridge;
import com.netcracker.unc.team35.task_manager.model.ITaskRepository;
import com.netcracker.unc.team35.task_manager.model.repository.DataBaseTaskRepository;
import com.netcracker.unc.team35.task_manager.model.repository.FileSystemTaskRepository;
import com.netcracker.unc.team35.task_manager.model.scheduler.INotifier;
import com.netcracker.unc.team35.task_manager.model.scheduler.Scheduler;
import com.netcracker.unc.team35.task_manager.model.scheduler.WebSocketNotifier;
import com.netcracker.unc.team35.task_manager.server.api.controller.SseService;
import com.netcracker.unc.team35.task_manager.view.OutputStreavTaskView;
import com.netcracker.unc.team35.task_manager.view.TasksOutput;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import javax.persistence.EntityManagerFactory;
import java.util.Timer;

@org.springframework.context.annotation.Configuration
public class Configuration {
    @Autowired
    SseService sseService;
    @Autowired
    DataBaseBridge dataBaseBridge;
    @Autowired
    EntityManagerFactory entityManagerFactory;

    @Bean
    public Controller controller() {
        ITaskRepository repository = repository();
        Scheduler scheduler = scheduler();
        Timer timer = new Timer(true);
        timer.scheduleAtFixedRate(scheduler, 0, 60 * 1000);
        return new Controller(repository, tasksOutput(), scheduler);
    }

    @Bean
    public ITaskRepository repository() {
        return //new FileSystemTaskRepository("DB.json");//
            new DataBaseTaskRepository(dataBaseBridge);
    }

    @Bean
    public TasksOutput tasksOutput() {
        return new OutputStreavTaskView(System.out);
    }

    @Bean
    public Scheduler scheduler() {
        return new Scheduler(repository(), notifier());
    }

    @Bean
    public INotifier notifier() {
        return new WebSocketNotifier(sseService);
    }

    @Bean
    public SseEmitter sseEmitter() {
        return sseService.getEmitter();
    }
}
