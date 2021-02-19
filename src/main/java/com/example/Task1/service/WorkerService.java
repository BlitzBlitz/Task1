package com.example.Task1.service;

import com.example.Task1.data.Department;
import com.example.Task1.data.DepartmentRepository;
import com.example.Task1.data.Worker;
import com.example.Task1.data.WorkerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Service
public class WorkerService {

    @Autowired
    WorkerRepository workerRepo;

    @Autowired
    DepartmentRepository departmentRepo;

    public Worker saveWorker(Worker newWorker) {
        if(newWorker.getDepartment() != null && departmentRepo.findById(newWorker.getDepartment().getId()).isEmpty()){
            return null;
        }
        if(workerRepo.findByName(newWorker.getName()) != null){
            return null;
        }
        return  workerRepo.save(newWorker);
    }

    @Transactional
    public List<Worker> findWorkers(Long departmentId) {
        Department department = departmentRepo.findById(departmentId).orElse(null);
        if(department == null){
            return null;
        }
        return workerRepo.findByDepartment_Id(departmentId);
    }

    public Worker findWorker(Long workerId) {
        return workerRepo.findById(workerId).orElse(null);
    }

    public Worker updateWorker(Worker updatedWorker) {
        Worker found = workerRepo.findById(updatedWorker.getId()).orElse(null);
        if(found == null){
            return null;
        }
        return workerRepo.save(updatedWorker);
    }

    public Worker deleteWorker(Long workerId) {
        Worker worker = workerRepo.findById(workerId).orElse(null);
        if(worker == null){
            return null;
        }
        workerRepo.delete(worker);
        return worker;
    }

    public boolean deleteAllWorkers() {
        workerRepo.deleteAll();
        return true;
    }

    public Integer deleteAllWorkersByDepId(Long departmentId) {
        return workerRepo.deleteAllByDepartment_Id(departmentId);
    }
}
