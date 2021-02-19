package com.example.Task1.service;


import com.example.Task1.data.Department;
import com.example.Task1.data.DepartmentRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

@RunWith(SpringRunner.class)
public class DepartmentServiceTest {
    @Mock
    private DepartmentRepository departmentRepo;
    @Mock
    private WorkerService workerService;
    @InjectMocks
    private DepartmentService departmentService;


    @Test
    public void findDepartmentTest(){
        Department department = new Department(1001L,"HR Department");
        Mockito.when(departmentRepo.findById(1001L)).thenReturn(java.util.Optional.of(department));

        Department found = departmentService.findDepartment(1001L);

        assertTrue(department.getName().equals(found.getName()));
    }

    @Test
    public void findAllDepartmentsTest(){
        Department department = new Department(1001L,"HR Department");
        Department department1 = new Department(1002L,"Dev Department");
        Department department2 = new Department(1003L,"Finance Department");
        Mockito.when(departmentService.findDepartments()).thenReturn(
                Arrays.asList(new Department[]{department,department1,department2}));


        List<Department> found = departmentRepo.findAll();

        assertEquals(3,found.size());
    }

    @Test
    public void saveDepartmentTest(){
        Department department = new Department("HR Department");
        Mockito.when(departmentRepo.save(department)).thenReturn(department);

        Department saved = departmentService.saveDepartment(department);

        assertTrue(department.getName().equals(saved.getName()));
    }

    @Test
    public void updateDepartmentTest(){
        Department department = new Department(1001L,"HR Department");
        Mockito.when(departmentRepo.save(department)).thenReturn(department);
        Mockito.when(departmentRepo.findById(1001L)).thenReturn(java.util.Optional.of(department));

        Department saved = departmentService.updateDepartment(department);

        assertTrue(department.getName().equals(saved.getName()));
    }

    @Test
    public void deleteDepartmentTest(){
        Department department = new Department(1001L,"HR Department");
        Mockito.when(departmentRepo.findById(1001L)).thenReturn(java.util.Optional.of(department));

        Department deleted = departmentService.deleteDepartment(department.getId());

        assertTrue(department.getName().equals(deleted.getName()));
    }

    @Test
    public void deleteAllDepartmentsTest(){

        assertTrue(departmentService.deleteAllDepartments());
    }

}
