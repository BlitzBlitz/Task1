package com.example.Task1.service;


import com.example.Task1.data.Department;
import com.example.Task1.data.DepartmentRepository;
import com.example.Task1.data.Worker;
import com.example.Task1.data.WorkerRepository;
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
public class WorkerServiceTest {
    @Mock
    private WorkerRepository workerRepo;
    @Mock
    private DepartmentRepository departmentRepo;
    @InjectMocks
    WorkerService workerService;

    @Test
    public void findWorkerTest(){
        Department department = new Department(1001L,"HR Department");
        Worker worker = new Worker(2001L, "Mikee", 333.33,department);
        Mockito.when(workerRepo.findById(2001L)).thenReturn(java.util.Optional.of(worker));

        Worker found = workerService.findWorker(2001L);
        assertEquals(found.getId(),worker.getId());
    }

    @Test
    public void findAllWorkersTest(){
        Department department = new Department(1001L,"HR Department");
        Worker worker = new Worker(2001L, "Mikee", 333.33,department);
        Worker worker1 = new Worker(2002L, "Spike", 3133.33,department);
        Worker worker2 = new Worker(2003L, "Alike", 3433.33,department);
        Mockito.when(departmentRepo.findById(1001L)).thenReturn(java.util.Optional.of(department));
        Mockito.when(workerRepo.findByDepartment_Id(department.getId())).thenReturn(
                Arrays.asList(new Worker[]{worker,worker1,worker2}));

        List<Worker> found = workerService.findWorkers(1001L);

        assertEquals(3,found.size());
    }

    @Test
    public void saveWorkerTest(){
        Department department = new Department(1001L,"HR Department");
        Worker worker = new Worker(2001L, "Mikee", 333.33,department);
        Mockito.when(departmentRepo.findById(department.getId())).thenReturn(java.util.Optional.of(department));
        Mockito.when(workerRepo.findByName(worker.getName())).thenReturn(null);
        Mockito.when(workerRepo.save(worker)).thenReturn(worker);

        Worker saved = workerService.saveWorker(worker);

        assertTrue(worker.getName().equals(saved.getName()));
    }

    @Test
    public void updateWorkerTest(){
        Department department = new Department(1001L,"HR Department");
        Worker worker = new Worker(2001L, "Mikee", 333.33,department);
        Worker workerUpdated = new Worker(2001L, "Mikee-Updated", 333.33,department);
        Mockito.when(workerRepo.findById(worker.getId())).thenReturn(java.util.Optional.of(worker));
        Mockito.when(workerRepo.save(workerUpdated)).thenReturn(workerUpdated);


        Worker updated = workerService.updateWorker(workerUpdated);

        assertTrue(updated.getName().contains("Updated"));
    }

    @Test
    public void deleteWorker(){
        Department department = new Department(1001L,"HR Department");
        Worker worker = new Worker(2001L, "Mikee", 333.33,department);
        Mockito.when(workerRepo.findById(2001L)).thenReturn(java.util.Optional.of(worker));

        Worker deleted = workerService.deleteWorker(worker.getId());

        assertTrue(deleted.getName().equals(worker.getName()));

    }

    @Test
    public void deleteAllWorkers(){
        assertTrue(workerService.deleteAllWorkers());
    }

    @Test
    public void deleteAllWorkersByDepId(){
        Mockito.when(workerRepo.deleteAllByDepartment_Id(1001L)).thenReturn(3);
        assertEquals(java.util.Optional.of(3).get(),workerService.deleteAllWorkersByDepId(1001L));
    }
}
