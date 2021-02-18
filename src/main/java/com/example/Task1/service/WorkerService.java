package com.example.Task1.service;

import com.example.Task1.Task1Application;
import com.example.Task1.data.Department;
import com.example.Task1.data.DepartmentRepository;
import com.example.Task1.data.Worker;
import com.example.Task1.data.WorkerRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Service
public class WorkerService {

    private Logger logger = LoggerFactory.getLogger(Task1Application.class);

    @Autowired
    WorkerRepository workerRepo;

    @Autowired
    DepartmentRepository departmentRepo;

    public Worker saveWorker(Worker newWorker) {
        if(newWorker.getDepartment() != null && departmentRepo.findById(newWorker.getDepartment().getId()).isEmpty()){
            logger.info("Department {} not found", newWorker.getDepartment());
            return null;
        }
        if(workerRepo.findByName(newWorker.getName()) != null){
            logger.info("Worker {} already exists", newWorker);
            return null;
        }
        Worker worker = workerRepo.save(newWorker);
        if(worker == null){
            logger.error("Worker {} not saved", newWorker);
        }else {
            logger.info("Workers of department {} saved", worker);
        }
        return worker;
    }

    @Transactional
    public List<Worker> findWorkers(Long departmentId) {
        Department department = departmentRepo.findById(departmentId).orElse(null);
        if(department == null){
            logger.info("Department {} not found", departmentId);
            return null;
        }
        List<Worker> workers = workerRepo.findByDepartment_Id(departmentId);
        if(workers.isEmpty()){
            logger.info("Workers of department {} not found", departmentId);
        }else {
            logger.info("Workers of department {} found", departmentId);
        }

        return workers;
    }

    public Worker findWorker(Long workerId) {
        Worker worker= workerRepo.findById(workerId).orElse(null);
        if(worker == null){
            logger.info("Worker {} not found", workerId);
        }else {
            logger.info("Worker {} found", workerId);
        }
        return worker;
    }

    public Worker updateWorker(Worker updatedWorker) {
        Worker found = workerRepo.findById(updatedWorker.getId()).orElse(null);
        if(found == null){
            logger.info("Worker {} not found", updatedWorker.getId());
            return null;
        }
        workerRepo.save(updatedWorker);
        logger.info("Worker {} updated successfully", updatedWorker);
        return updatedWorker;
    }

    public Worker deleteWorker(Long workerId) {
        Worker worker = workerRepo.findById(workerId).orElse(null);
        if(worker == null){
            logger.info("Worker {} not found", workerId);
            return null;
        }
        workerRepo.delete(worker);
        logger.info("Worker {} deleted successfully", workerId);
        return worker;
    }

    public boolean deleteAllWorkers() {
        workerRepo.deleteAll();
        logger.info("All workers deleted");
        return true;
    }

    public Integer deleteAllWorkersByDepId(Long departmentId) {
        Integer deleted = workerRepo.deleteAllByDepartment_Id(departmentId);
        if (deleted.equals(0)){
            logger.info("Department {} has no workers to be deleted", departmentId);
        }else {
            logger.info("Deleting all workers of department {}", departmentId);
        }
        return deleted;
    }


}
