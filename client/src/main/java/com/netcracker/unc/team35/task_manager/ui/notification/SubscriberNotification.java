package com.netcracker.unc.team35.task_manager.ui.notification;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.netcracker.unc.team35.task_manager.model.TaskModel;
import com.netcracker.unc.team35.task_manager.ui.network.WebClientApi;
import com.netcracker.unc.team35.task_manager.ui.network.sse_client.EventStreamAdapter;
import com.netcracker.unc.team35.task_manager.ui.network.sse_client.HttpEventStreamClient;

import java.net.http.HttpResponse;
import java.util.ArrayList;
import java.util.List;

/**
 * The class that implements the logic of receiving information about new notifications from the server
 * @author unc 21-22
 * @version 1.0
 */
public class SubscriberNotification extends Thread {
    /**
     * The Worker notification.
     */
    WorkerNotification workerNotification;

    /**
     * Instantiates a new Subscriber notification.
     */
    public SubscriberNotification() {
         this.workerNotification = new WorkerNotification();
         workerNotification.start();
    }

    /**
     * The main thread method, which contains the logic for subscribing to the server, to receive notifications
     */
    @Override
    public void run() {
            HttpEventStreamClient client = new HttpEventStreamClient(WebClientApi.NOTIFI_API_URL, new EventStreamAdapter() {
                @Override
                public void onClose(HttpEventStreamClient client, HttpResponse<Void> response) {
                    System.out.println("SSE Client closed");
                }

                @Override
                public void onEvent(HttpEventStreamClient client, HttpEventStreamClient.Event event) throws JsonProcessingException {
                    ObjectMapper objectMapper = new ObjectMapper().findAndRegisterModules();
                    TaskModel tasks = objectMapper.readValue(event.getData(), TaskModel.class);
                    List<TaskModel> listTask = new ArrayList<>();
                    listTask.add(tasks);
                    workerNotification.add(listTask);
                }

            });

                client.start().join();
    }
}
