package com.netcracker.unc.team35.task_manager.model.scheduler;

import com.netcracker.unc.team35.task_manager.server.api.controller.SseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import java.io.IOException;

public class WebSocketNotifier implements INotifier {
    private SseService emitter;

    @Autowired
    public WebSocketNotifier(SseService emitter) {
        this.emitter = emitter;
    }

    @Override
    public void notify(TaskNotificationInfo taskNotificationInfo) {
        try {
            emitter.getEmitter().send(taskNotificationInfo);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
