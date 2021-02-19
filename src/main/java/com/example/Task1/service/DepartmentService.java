package com.example.Task1.service;

import com.example.Task1.data.Department;
import com.example.Task1.data.DepartmentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DepartmentService {

    @Autowired
    DepartmentRepository departmentRepo;

    @Autowired
    WorkerService workerService;

    public Department saveDepartment(Department department){
        if(departmentRepo.findByName(department.getName()) != null){
            return null;
        }
        return  departmentRepo.save(department);
    }

    public List<Department> findDepartments() {
        List<Department> departments = departmentRepo.findAll();
        return departments;
    }

    public Department findDepartment(Long departmentId) {
        return departmentRepo.findById(departmentId).orElse(null);
    }

    public Department updateDepartment(Department department) {
        if(departmentRepo.findById(department.getId()).orElse(null) == null){
            return null;
        }
        return departmentRepo.save(department);
    }

    public Department deleteDepartment(Long departmentId) {
        Department department = departmentRepo.findById(departmentId).orElse(null);
        if(department == null){
            return null;
        }
        workerService.deleteAllWorkersByDepId(departmentId);
        departmentRepo.delete(department);
        return department;
    }

    public boolean deleteAllDepartments() {
        workerService.deleteAllWorkers();
        departmentRepo.deleteAll();
        return true;
    }
}
