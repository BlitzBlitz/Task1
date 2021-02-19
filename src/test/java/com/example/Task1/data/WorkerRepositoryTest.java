package com.example.Task1.data;


import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@DataJpaTest
public class WorkerRepositoryTest {

    @Autowired
    WorkerRepository workerRepository;

    @Test
    public void findByNameTest(){
        Worker worker = workerRepository.findByName("Klement");
        assertTrue(worker.getName().equals("Klement"));
    }

    @Test
    public void findByDepartment_IdTest(){
        List<Worker> workers = workerRepository.findByDepartment_Id(1001L);
        for(int i = 0; i < workers.size(); i++){
            assertEquals( new Long(1001L), workers.get(i).getDepartment().getId());
        }
    }

    @Test
    public void deleteAllByDepartment_IdTest(){
        workerRepository.deleteAllByDepartment_Id(1001L);
        List<Worker> workers = workerRepository.findByDepartment_Id(1001L);
        assertTrue(workers.isEmpty());
    }


}
