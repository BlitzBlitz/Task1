package com.example.Task1.controller;

import com.example.Task1.data.Department;
import com.example.Task1.service.DepartmentService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
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

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

@RunWith(SpringRunner.class)
@WebMvcTest(DepartmentController.class)
public class DepartmentControllerTest {

    @MockBean
    private DepartmentService departmentService;

    @Autowired
    MockMvc mockMvc;

    @Test
    public void retrieveDepartmentTest() throws Exception{
        Department department = new Department("HR Department");
        Mockito.when(departmentService.findDepartment(1001L)).thenReturn(department);

        RequestBuilder requestBuilder = MockMvcRequestBuilders.get("/departments/1001")
                .accept(MediaType.APPLICATION_JSON);
        MvcResult result = mockMvc.perform(requestBuilder).andReturn();
        assertTrue(result.getResponse().getContentAsString().contains("HR Department"));
    }

    @Test
    public void retrieveDepartmentsTest()throws Exception{
        Department department = new Department("HR Department");
        Department department1 = new Department("Dev Department");
        Department department2 = new Department("Finance Department");
        Mockito.when(departmentService.findDepartments()).thenReturn(
                Arrays.asList(new Department[]{department,department1,department2}));

        RequestBuilder requestBuilder = MockMvcRequestBuilders.get("/departments")
                .accept(MediaType.APPLICATION_JSON);
        MvcResult result = mockMvc.perform(requestBuilder).andReturn();
        assertTrue(result.getResponse().getContentAsString().contains("HR Department"));
        assertTrue(result.getResponse().getContentAsString().contains("Dev Department"));
        assertTrue(result.getResponse().getContentAsString().contains("Finance Department"));

    }

    @Test
    public void addDepartmentTest() throws Exception{
        Department department = new Department(1111L,"HR2 Department");
        Mockito.when(departmentService.saveDepartment(Mockito.any())).thenReturn(department);
        RequestBuilder requestBuilder = MockMvcRequestBuilders.post("/departments")
                .accept(MediaType.APPLICATION_JSON)
                .content("{\"id\": 1111, \"name\": \"HR2 Department\"}").contentType(MediaType.APPLICATION_JSON);
        MvcResult result = mockMvc.perform(requestBuilder).andReturn();
        MockHttpServletResponse response = result.getResponse();
        assertEquals(HttpStatus.CREATED.value(), response.getStatus());
    }

    @Test
    public void updateDepartmentTest() throws Exception {
        Department department = new Department(1111L,"HR2 Department");
        Mockito.when(departmentService.updateDepartment(Mockito.any())).thenReturn(department);
        RequestBuilder requestBuilder = MockMvcRequestBuilders.put("/departments")
                .accept(MediaType.APPLICATION_JSON)
                .content("{\"id\": 1111, \"name\": \"HR2 Department\"}").contentType(MediaType.APPLICATION_JSON);
        MvcResult result = mockMvc.perform(requestBuilder).andReturn();
        MockHttpServletResponse response = result.getResponse();
        assertEquals(HttpStatus.OK.value(), response.getStatus());
    }


    @Test
    public void deleteDepartmentTest() throws Exception{
        Department department = new Department(1111L,"HR2 Department");
        Mockito.when(departmentService.deleteDepartment(1111L)).thenReturn(department);
        RequestBuilder requestBuilder = MockMvcRequestBuilders.delete("/departments/1111");
        MvcResult result = mockMvc.perform(requestBuilder).andReturn();
        MockHttpServletResponse response = result.getResponse();
        assertEquals(HttpStatus.OK.value(), response.getStatus());
    }

    @Test
    public void deleteAllDepartments() throws Exception{
        RequestBuilder requestBuilder = MockMvcRequestBuilders.delete("/departments");
        MvcResult result = mockMvc.perform(requestBuilder).andReturn();
        MockHttpServletResponse response = result.getResponse();
        assertEquals(HttpStatus.OK.value(), response.getStatus());
    }
}
