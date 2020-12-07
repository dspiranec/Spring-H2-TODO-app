package com.dspiranec.todo.task;

import com.dspiranec.todo.service.TaskService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.web.JsonPath;
import org.springframework.http.MediaType;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;


import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class TaskControllerTest {

    @Autowired
    private MockMvc mockMvc;


    @Test
    void shouldGetAllTasksAndReturnOk() throws Exception {
        this.mockMvc.perform(get("/api/tasks"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON));
    }

    @Test
    void shouldGetTaskByIdAndReturnOk() throws Exception {

        this.mockMvc
                .perform(get("/api/tasks/1"))
                .andExpect(status().isNotFound())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON));
    }

    @Test
    void shouldFailToGetTaskByIdAndReturnNotFound() throws Exception {

        this.mockMvc
                .perform(get("/api/tasks/9"))
                .andExpect(status().isNotFound());
    }

    @Test
    void shouldSaveTaskAndReturnCreated() throws Exception {

        String taskCommand = "{\n"+
                "   \"name\": \"Napraviti test\",\n" +
                "   \"priority\": \"High\"\n" +
                "}";

        this.mockMvc.perform(MockMvcRequestBuilders.post("/api/tasks")
                .contentType(MediaType.APPLICATION_JSON)
                .content(taskCommand)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON));
    }

    @Test
    @DirtiesContext
    void shouldDeleteTaskAndReturnOk() throws Exception {

        this.mockMvc.perform(MockMvcRequestBuilders.delete("/tasks/1"))
                .andExpect(status().isNoContent());

    }

    @Test
    @DirtiesContext
    void shouldNotDeleteTaskAndReturnNotFound() throws Exception {

        this.mockMvc.perform(MockMvcRequestBuilders.delete("/api/tasks/9"))
                .andExpect(status().isNotFound());
    }

    @Test
    void shouldUpdateTaskAndReturnOk() throws Exception {

        String taskCommand = "{\n"+
                "   \"id\": \"1\",\n" +
                "   \"priority\": \"High\"\n" +
                "}";

        this.mockMvc.perform(MockMvcRequestBuilders.post("/api/tasks")
                .contentType(MediaType.APPLICATION_JSON)
                .content(taskCommand)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON));
    }

    @Test
    void shouldNotUpdateTaskAndReturnNotFound() throws Exception {

        String taskCommand = "{\n"+
                "   \"id\": \"9\",\n" +
                "   \"name\": \"Abc\"\n" +
                "}";

        this.mockMvc.perform(MockMvcRequestBuilders.post("/api/tasks")
                .contentType(MediaType.APPLICATION_JSON)
                .content(taskCommand)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON));
    }
}
