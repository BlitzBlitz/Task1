package com.example.Task1.controller;

import com.example.Task1.data.Department;
import com.example.Task1.data.Worker;
import com.example.Task1.service.WorkerService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.Arrays;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@WebMvcTest(WorkerController.class)
public class WorkerControllerTest {

    Logger logger = LoggerFactory.getLogger(WorkerController.class);

    @MockBean
    private WorkerService workerService;

    @Autowired
    MockMvc mockMvc;

    @Test
    public void retrieveWorkerTest() throws Exception{
        Department department = new Department(1001L,"HR");
        Worker worker = new Worker(2001L,"Mike",333.22,department);

        Mockito.when(workerService.findWorker(2001L)).thenReturn(worker);

        RequestBuilder requestBuilder = MockMvcRequestBuilders.get("/departments/1001/workers/2001")
                .accept(MediaType.APPLICATION_JSON);
        MvcResult result = mockMvc.perform(requestBuilder).andReturn();
        assertTrue(result.getResponse().getContentAsString().contains("Mike"));
    }

    @Test
    public void retrieveAllWorkersTest() throws Exception{
        Department department = new Department(1001L,"HR");
        Worker worker = new Worker(2001L,"Mike",333.22,department);
        Worker worker1 = new Worker(2002L,"Mario",1333.22,department);


        Department department1 = new Department(1001L,"Marketing");
        Worker worker2 = new Worker(2003L,"Luigi",2333.22,department1);

        Mockito.when(workerService.findWorkers(1001L)).thenReturn(
                Arrays.asList(new Worker[]{worker,worker1}));

        RequestBuilder requestBuilder = MockMvcRequestBuilders.get("/departments/1001/workers")
                .accept(MediaType.APPLICATION_JSON);
        MvcResult result = mockMvc.perform(requestBuilder).andReturn();

        assertTrue(result.getResponse().getContentAsString().contains("Mike"));
        assertTrue(result.getResponse().getContentAsString().contains("Mario"));
        assertFalse(result.getResponse().getContentAsString().contains("Luigi"));

    }

    @Test
    public void addWorkerTest() throws Exception{
        Department department = new Department(1001L,"HR");
        Worker worker = new Worker(2001L,"Mikey",333.22,department);
        Mockito.when(workerService.saveWorker(Mockito.any())).thenReturn(worker);

        RequestBuilder requestBuilder = MockMvcRequestBuilders.post("/departments/1001/workers")
                .accept(MediaType.APPLICATION_JSON)
                .content("{\"id\": 2001, \"name\": \"Mikey\", \"salary\": 333.22," +
                        " \"department\": {\"id\": 1001, \"name\": \"HR\"} }")
                .contentType(MediaType.APPLICATION_JSON);
        MvcResult result = mockMvc.perform(requestBuilder).andReturn();
        MockHttpServletResponse response = result.getResponse();
        assertEquals(HttpStatus.CREATED.value(), response.getStatus());
    }

    @Test
    public void updateWorkerTest() throws Exception {
        Department department = new Department(1001L,"HR");
        Worker worker = new Worker(2001L,"Mikey",333.22,department);
        Mockito.when(workerService.updateWorker(Mockito.any())).thenReturn(worker);

        RequestBuilder requestBuilder = MockMvcRequestBuilders.put("/departments/1001/workers/2001")
                .accept(MediaType.APPLICATION_JSON)
                .content("{\"id\": 2001, \"name\": \"Mikey-Updated\", \"salary\": 333.22," +
                        " \"department\": {\"id\": 1001, \"name\": \"HR\"} }")
                .contentType(MediaType.APPLICATION_JSON);
        MvcResult result = mockMvc.perform(requestBuilder).andReturn();
        MockHttpServletResponse response = result.getResponse();
        assertEquals(HttpStatus.OK.value(), response.getStatus());
    }

    @Test
    public void deleteWorkerTest() throws Exception{
        Department department = new Department(1001L,"HR");
        Worker worker = new Worker(2001L,"Mikey",333.22,department);
        Mockito.when(workerService.deleteWorker(2001L)).thenReturn(worker);

        RequestBuilder requestBuilder = MockMvcRequestBuilders.delete("/departments/1001/workers/2001");
        MvcResult result = mockMvc.perform(requestBuilder).andReturn();
        MockHttpServletResponse response = result.getResponse();
        assertEquals(HttpStatus.OK.value(), response.getStatus());
    }

    @Test
    public void deleteAllWorkers() throws Exception{
        RequestBuilder requestBuilder = MockMvcRequestBuilders.delete("/departments/1001/workers");
        MvcResult result = mockMvc.perform(requestBuilder).andReturn();
        MockHttpServletResponse response = result.getResponse();
        assertEquals(HttpStatus.OK.value(), response.getStatus());
    }

}
