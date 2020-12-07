package com.dspiranec.todo.command;

import com.dspiranec.todo.enums.Priority;
import com.sun.istack.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class TaskCommand {

    private Long id;
    private String name;
    private String date;
    private Priority priority;

}
