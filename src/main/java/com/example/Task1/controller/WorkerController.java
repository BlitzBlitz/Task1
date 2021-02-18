package com.example.Task1.controller;

import com.example.Task1.Task1Application;
import com.example.Task1.data.Worker;
import com.example.Task1.service.WorkerService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.Valid;
import javax.validation.ValidationException;
import java.net.URI;
import java.util.List;

@RestController
public class WorkerController {

    private Logger logger = LoggerFactory.getLogger(Task1Application.class);
    @Autowired
    WorkerService workerService;

    @PostMapping("/departments/{departmentId}/workers")
    public ResponseEntity<Worker> addWorker(@Valid @RequestBody Worker newWorker, BindingResult result) throws ValidationException {
        if(result.hasErrors()){
            logger.error("Invalid entity ->{}, at fields {}", newWorker, result.getFieldErrors());
            throw new ValidationException();
        }
        logger.info("saving worker -> {}...", newWorker);
        Worker worker = workerService.saveWorker(newWorker);

        if (worker == null) {
            return ResponseEntity.noContent().build();
        }
        URI location = ServletUriComponentsBuilder.fromCurrentRequest().path(
                "/{id}").buildAndExpand(worker.getId()).toUri();
        logger.info("worker -> {} saved", newWorker);
        return ResponseEntity.created(location).build();
    }

    @GetMapping("/departments/{departmentId}/workers")
    public List<Worker> retrieveAllWorkers(@PathVariable Long departmentId) {
        logger.info("retrieving all workers for department -> {}...", departmentId);
        List<Worker> workers = workerService.findWorkers(departmentId);
        if(workers.isEmpty()){
            logger.info("no worker retried for department -> {}...", departmentId);
        }
        logger.info("all workers retried for department -> {}...", departmentId);
        return workers;
    }

    @GetMapping("/departments/{departmentId}/workers/{workerId}")
    public ResponseEntity<Worker> retrieveWorker(@PathVariable Long workerId){
        logger.info("retrieving worker -> {}", workerId);
        Worker worker = workerService.findWorker(workerId);
        if(workerId == null){
            return ResponseEntity.notFound().build();
        }
        logger.info("worker -> {} retrieved", workerId);
        return ResponseEntity.ok().body(worker);
    }

    @PutMapping(value = "/departments/{departmentId}/workers/{workerId}")
    public ResponseEntity<Worker> updateWorker(@Valid @RequestBody Worker updatedWorker, BindingResult result)
            throws ValidationException{
        logger.info("Updating worker -> {}", updatedWorker);
        if(result.hasErrors()){
            logger.info("Updated worker -> {} not valid", updatedWorker);
            throw new ValidationException();
        }
        if(workerService.updateWorker(updatedWorker) == null){
            return ResponseEntity.notFound().build();
        }
        logger.info("Worker -> {} updated", updatedWorker);
        return ResponseEntity.ok(updatedWorker);
    }

    @DeleteMapping(value = "/departments/{departmentId}/workers/{workerId}")
    public ResponseEntity<Worker> deleteWorker(@PathVariable Long workerId) {
        logger.info("Deleting worker -> {}", workerId);
        Worker worker = workerService.deleteWorker(workerId);
        if(worker == null){
            return ResponseEntity.notFound().build();
        }
        logger.info("Worker -> {} deleted successfully", workerId);
        return ResponseEntity.ok(worker);
    }

    @DeleteMapping(value = "/departments/{departmentId}/workers")
    public ResponseEntity<Worker> deleteAllWorkers(@PathVariable Long departmentId) {
        logger.info("Deleting workers of department -> {}", departmentId);
        workerService.deleteAllWorkersByDepId(departmentId);
        logger.info("Workers of department -> {} successfully deleted", departmentId);
        return ResponseEntity.ok().build();
    }



}
