package com.dspiranec.todo.service;

import com.dspiranec.todo.command.TaskCommand;
import com.dspiranec.todo.dto.TaskDTO;

import java.util.List;

public interface TaskService {
    List<TaskDTO> findAll();
    TaskDTO createTask(TaskCommand taskCommand);
    TaskDTO updateTask(TaskCommand taskCommand);
    TaskDTO findTaskById(Long id);
    void deleteTask(Long id);
}
