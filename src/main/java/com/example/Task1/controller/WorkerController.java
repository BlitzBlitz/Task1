package com.example.Task1.controller;

import com.example.Task1.data.Worker;
import com.example.Task1.service.WorkerService;
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

    @Autowired
    WorkerService workerService;

    @PostMapping("/departments/{departmentId}/workers")
    public ResponseEntity<Worker> addWorker(@Valid @RequestBody Worker newWorker, BindingResult result) throws ValidationException {
        if(result.hasErrors()){
            throw new ValidationException();
        }
        Worker worker = workerService.saveWorker(newWorker);

        if (worker == null) {
            return ResponseEntity.noContent().build();
        }
        URI location = ServletUriComponentsBuilder.fromCurrentRequest().path(
                "/{id}").buildAndExpand(worker.getId()).toUri();
        return ResponseEntity.created(location).build();
    }

    @GetMapping("/departments/{departmentId}/workers")
    public List<Worker> retrieveAllWorkers(@PathVariable Long departmentId) {
        return workerService.findWorkers(departmentId);
    }

    @GetMapping("/departments/{departmentId}/workers/{workerId}")
    public ResponseEntity<Worker> retrieveWorker(@PathVariable Long workerId){
        Worker worker = workerService.findWorker(workerId);
        if(workerId == null){
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok().body(worker);
    }

    @PutMapping(value = "/departments/{departmentId}/workers/{workerId}")
    public ResponseEntity<Worker> updateWorker(@Valid @RequestBody Worker updatedWorker, BindingResult result)
            throws ValidationException{
        if(result.hasErrors()){
            throw new ValidationException();
        }
        if(workerService.updateWorker(updatedWorker) == null){
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(updatedWorker);
    }

    @DeleteMapping(value = "/departments/{departmentId}/workers/{workerId}")
    public ResponseEntity<Worker> deleteWorker(@PathVariable Long workerId) {
        Worker worker = workerService.deleteWorker(workerId);
        if(worker == null){
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(worker);
    }

    @DeleteMapping(value = "/departments/{departmentId}/workers")
    public ResponseEntity<Worker> deleteAllWorkers(@PathVariable Long departmentId) {
        workerService.deleteAllWorkersByDepId(departmentId);
        return ResponseEntity.ok().build();
    }



}
