package com.dspiranec.todo.service.impl;

import com.dspiranec.todo.command.TaskCommand;
import com.dspiranec.todo.dto.TaskDTO;
import com.dspiranec.todo.entity.Task;
import com.dspiranec.todo.exception.TaskNotFoundException;
import com.dspiranec.todo.repository.TaskRepository;
import com.dspiranec.todo.service.TaskService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class TaskServiceImpl implements TaskService {

    private final TaskRepository taskRepository;

    @Override
    public List<TaskDTO> findAll() {
        return taskRepository.findAll().stream().map(this::mapTaskToTaskDTO).collect(Collectors.toList());
    }

    @Override
    public TaskDTO findTaskById(final Long id) {
        return mapTaskToTaskDTO(taskRepository.findById(id).orElseThrow(() ->
            new TaskNotFoundException("Task with id " + id + " not found.")
        ));
    }

    @Override
    public TaskDTO createTask(TaskCommand taskCommand) {
        return mapTaskToTaskDTO(taskRepository.save(mapTaskCommandToCreateTask(taskCommand)));
    }

    @Override
    public TaskDTO updateTask(final TaskCommand taskCommand) {
        return mapTaskToTaskDTO(taskRepository.save(mapTaskCommandToUpdateTask(taskCommand)));
    }

    @Override
    public void deleteTask(final Long id) {
        taskRepository.deleteById(id);
    }

    private TaskDTO mapTaskToTaskDTO(final Task task){
        return TaskDTO.builder()
                .id(task.getId())
                .name(task.getName())
                .date(task.getDate())
                .priority(task.getPriority())
                .build();
    }

    private Task mapTaskCommandToUpdateTask(final TaskCommand taskCommand) {
        Task task = taskRepository.findById(taskCommand.getId()).orElseThrow(() ->
             new TaskNotFoundException("Task with id " + taskCommand.getId() + " not found.")
        );
        if (taskCommand.getName() != null) task.setName(taskCommand.getName());
        if (taskCommand.getDate() != null) task.setDate(LocalDate.parse(taskCommand.getDate(), DateTimeFormatter.ofPattern("dd.MM.yyyy.")));
        if (taskCommand.getPriority() != null) task.setPriority(taskCommand.getPriority());
        return task;
    }

    private Task mapTaskCommandToCreateTask(final TaskCommand taskCommand) {
        Task task = new Task();
        task.setId(taskCommand.getId());
        if (taskCommand.getName() != null) task.setName(taskCommand.getName());
        if (taskCommand.getDate() != null) task.setDate(LocalDate.parse(taskCommand.getDate(), DateTimeFormatter.ofPattern("dd.MM.yyyy.")));
        if (taskCommand.getPriority() != null) task.setPriority(taskCommand.getPriority());
        return task;}
}
