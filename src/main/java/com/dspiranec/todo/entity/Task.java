package com.dspiranec.todo.entity;

import com.dspiranec.todo.enums.Priority;
import lombok.*;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

@EqualsAndHashCode
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "task")
public class Task {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name  = "task_name")
    private String name;

    @Column(name  = "task_date")
    private LocalDate date;

    @Enumerated(EnumType.STRING)
    @Column(name  = "task_priority")
    private Priority priority;



    public Task(Long id, String name, String date, String priority) {
        this.id = id;
        this.name = name;
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy.");
        this.date = LocalDate.parse(date, formatter);
        this.priority = Priority.valueOf(priority);
    }

    public Task(Long id, String name, String priority) {
        this.id = id;
        this.name = name;
        this.priority = Priority.valueOf(priority);
    }
}
