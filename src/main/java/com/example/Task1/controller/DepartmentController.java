package com.example.Task1.controller;

import com.example.Task1.Task1Application;
import com.example.Task1.data.Department;
import com.example.Task1.service.DepartmentService;
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
public class DepartmentController {

    private Logger logger = LoggerFactory.getLogger(Task1Application.class);

    @Autowired
    DepartmentService departmentService;

    @PostMapping("/departments")
    public ResponseEntity<Department> addDepartment(@Valid @RequestBody Department newDepartment, BindingResult result)
            throws ValidationException {

        if(result.hasErrors()){
            throw new ValidationException();
        }

        Department department = departmentService.saveDepartment(newDepartment);

        if (department == null) {
            return ResponseEntity.noContent().build();
        }
        URI location = ServletUriComponentsBuilder.fromCurrentRequest().path(
                "/{id}").buildAndExpand(department.getId()).toUri();
        logger.info("{} saved successfully", newDepartment);
        return ResponseEntity.created(location).build();
    }

    @GetMapping("/departments")
    public List<Department> retrieveDepartments() {
        logger.info("Retrieving all departments...");
        return departmentService.findDepartments();
    }

    @GetMapping("/departments/{departmentId}")
    public ResponseEntity<Department> retrieveDepartment(@PathVariable Long departmentId){
        logger.info("Retrieving department with id -> {}", departmentId);
        Department department = departmentService.findDepartment(departmentId);
        if(department == null){
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok().body(department);
    }

    @PutMapping(value = "/departments")
    public ResponseEntity<Department> updateDepartment(@Valid @RequestBody Department updatedDepartment, BindingResult result)
            throws ValidationException{
        logger.info("Updating department with id {}", updatedDepartment.getId());
        if(result.hasErrors()){
            throw new ValidationException();
        }
        if(departmentService.updateDepartment(updatedDepartment) == null){
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(updatedDepartment);
    }

    @DeleteMapping(value = "/departments/{departmentId}")
    public ResponseEntity<Department> deleteDepartment(@PathVariable Long departmentId) {
        logger.info("Deleting department with id -> {}", departmentId);
        Department department = departmentService.deleteDepartment(departmentId);
        if(department == null){
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok().build();
    }

    @DeleteMapping(value = "/departments")
    public ResponseEntity<Department> deleteAllDepartments() {
        logger.info("Deleting all departments");
        departmentService.deleteAllDepartments();
        return ResponseEntity.ok().build();
    }



}
