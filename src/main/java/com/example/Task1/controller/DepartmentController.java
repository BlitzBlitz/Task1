package com.example.Task1.controller;

import com.example.Task1.data.Department;
import com.example.Task1.service.DepartmentService;
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
        return ResponseEntity.created(location).build();
    }

    @GetMapping("/departments")
    public List<Department> retrieveDepartments() {
        List<Department> list = departmentService.findDepartments();
        return list;
    }

    @GetMapping("/departments/{departmentId}")
    public ResponseEntity<Department> retrieveDepartment(@PathVariable Long departmentId){
        Department department = departmentService.findDepartment(departmentId);
        if(department == null){
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok().body(department);
    }

    @PutMapping(value = "/departments")
    public ResponseEntity<Department> updateDepartment(@Valid @RequestBody Department updatedDepartment, BindingResult result)
            throws ValidationException{
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
        Department department = departmentService.deleteDepartment(departmentId);
        if(department == null){
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok().build();
    }

    @DeleteMapping(value = "/departments")
    public ResponseEntity<Department> deleteAllDepartments() {
        departmentService.deleteAllDepartments();
        return ResponseEntity.ok().build();
    }



}
