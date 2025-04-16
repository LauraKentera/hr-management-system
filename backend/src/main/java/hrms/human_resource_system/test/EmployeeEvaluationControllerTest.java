package main.java.hrms.human_resource_system.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import main.java.hrms.human_resource_system.model.EmployeeEvaluation;
import main.java.hrms.human_resource_system.service.EmployeeEvaluationService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(EmployeeEvaluationController.class)
public class EmployeeEvaluationControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private EmployeeEvaluationService service;

    @Autowired
    private ObjectMapper objectMapper;

    private EmployeeEvaluation sampleEvaluation;

    @BeforeEach
    public void setup() {
        sampleEvaluation = new EmployeeEvaluation();
        sampleEvaluation.setId(1);
        sampleEvaluation.setUserId(101);
        sampleEvaluation.setEvaluatorName("Jane Doe");
        sampleEvaluation.setComments("Great job");
        sampleEvaluation.setScore(95.0);
        sampleEvaluation.setEvaluationDate(LocalDate.of(2023, 5, 1));
    }

    @Test
    public void testGetAllEvaluations() throws Exception {
        List<EmployeeEvaluation> list = Arrays.asList(sampleEvaluation);
        Mockito.when(service.getAll()).thenReturn(list);

        mockMvc.perform(get("/api/employee-evaluations"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].evaluatorName").value("Jane Doe"));
    }

    @Test
    public void testGetEvaluationById() throws Exception {
        Mockito.when(service.getById(1)).thenReturn(sampleEvaluation);

        mockMvc.perform(get("/api/employee-evaluations/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.comments").value("Great job"));
    }

    @Test
    public void testCreateEvaluation() throws Exception {
        Mockito.when(service.insert(any(EmployeeEvaluation.class))).then(invocation -> {
            EmployeeEvaluation eval = invocation.getArgument(0);
            eval.setId(2);
            return eval;
        });

        mockMvc.perform(post("/api/employee-evaluations")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(sampleEvaluation)))
                .andExpect(status().isCreated());
    }

    @Test
    public void testUpdateEvaluation() throws Exception {
        Mockito.when(service.update(any(EmployeeEvaluation.class))).thenReturn(sampleEvaluation);

        mockMvc.perform(put("/api/employee-evaluations/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(sampleEvaluation)))
                .andExpect(status().isOk());
    }

    @Test
    public void testDeleteEvaluation() throws Exception {
        mockMvc.perform(delete("/api/employee-evaluations/1"))
                .andExpect(status().isNoContent());
    }
}
