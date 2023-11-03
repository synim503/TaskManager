package com.netcracker.unc.team35.task_manager.model.repository;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.netcracker.unc.team35.task_manager.model.AbstractTaskRepository;
import com.netcracker.unc.team35.task_manager.model.Importance;
import com.netcracker.unc.team35.task_manager.model.Task;
import com.querydsl.collections.FunctionalHelpers;
import com.querydsl.core.types.Predicate;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;
/*
* Класс файловой базы данных
* */
public class FileSystemTaskRepository extends AbstractTaskRepository {
    private final String fileName;
    private long maxId;
    /*
    * Конструктор класса, принимающий путь до Json файла базы данных*/
    @JsonCreator
    public FileSystemTaskRepository(String path) {
        fileName = path;
        String content = null;
        try {
            content = Files.lines(Paths.get(fileName)).reduce("", String::concat);
        } catch (IOException e) {
            e.printStackTrace();
        }
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            objectMapper.registerModule(new JavaTimeModule());
            tasks = objectMapper.readValue(content, new TypeReference<HashMap<String, Task>>() {
            });
            for (Map.Entry<String, Task> entry : tasks.entrySet()) {
                entry.getValue().setId(Long.valueOf(entry.getKey()));
            }

            Optional<Long> reduce = tasks.values().stream()
                    .map(Task::getId)
                    .filter(Objects::nonNull)
                    .map(String.class::cast)
                    .map(Long::parseLong)
                    .reduce(Long::max);
            maxId = reduce.orElse(1L);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
    }
    /*
    * Метод возвращающий все задачи в колекции
    * */
    @Override
    public Collection<Task> getAllTasks() {
        return Collections.unmodifiableList(new ArrayList<>(tasks.values()));
    }
    /*
     * Метод увеличивающий поле класса "maxId"
     * */

    @Override
    public Task add(final String descr, final LocalDateTime dateTime, final Importance importance) {
        Task add = super.add(descr, dateTime, importance);
        add.setId(Long.valueOf(nextId()));
        return add;
    }

    @Override
    protected String nextId() {
        return Long.toString(maxId + 1);
    }
    /*
     * Метод записывает данные из колекции задач класса в файл базы данных
     * */
    @Override
    public void flush() {

        String json = null;
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.registerModule(new JavaTimeModule());
        try {
            json = objectMapper.findAndRegisterModules().writeValueAsString(tasks);
            Files.write(Paths.get(fileName), Collections.singleton(json), StandardCharsets.UTF_8);
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
    /*
     * Метод поиска задач в колекции
     * */
    @Override
    public List<Task> findByPredicate(Predicate predicate) {
        return getAllTasks().stream().filter(FunctionalHelpers.wrap(predicate)).collect(Collectors.toList());
    }

}
