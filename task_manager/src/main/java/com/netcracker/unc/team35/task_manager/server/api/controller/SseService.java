package com.netcracker.unc.team35.task_manager.server.api.controller;

import org.springframework.stereotype.Service;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import java.util.concurrent.atomic.AtomicReference;

@Service
public class SseService {
    AtomicReference<SseEmitter> emitter = new AtomicReference<>(new SseEmitter(-1L));
    public SseEmitter getEmitter() {
        return emitter.get();
    }
}
