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
        return mapTaskToTaskDTO(taskRepository.save(prepareTaskCommandForCreate(taskCommand)));
    }

    @Override
    public void deleteTask(final Long id) { taskRepository.deleteById(id); }

    private TaskDTO mapTaskToTaskDTO(final Task task){
        return TaskDTO.builder()
                .id(task.getId())
                .name(task.getName())
                .date(task.getDate())
                .priority(task.getPriority())
                .build();
    }

    private Task prepareTaskCommandForCreate(final TaskCommand taskCommand) {
        Task task = new Task();
        if (taskCommand.getName() != null) task.setName(taskCommand.getName());
        if (taskCommand.getDate() != null && !taskCommand.getDate().equals("")) task.setDate(LocalDate.parse(taskCommand.getDate(), DateTimeFormatter.ofPattern("yyy-MM-dd")));
        if (taskCommand.getPriority() != null) task.setPriority(taskCommand.getPriority());
        return task;}
}
