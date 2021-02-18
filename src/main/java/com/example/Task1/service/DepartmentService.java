package com.example.Task1.service;

import com.example.Task1.Task1Application;
import com.example.Task1.data.Department;
import com.example.Task1.data.DepartmentRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DepartmentService {

    private Logger logger = LoggerFactory.getLogger(Task1Application.class);

    @Autowired
    DepartmentRepository departmentRepo;

    @Autowired
    WorkerService workerService;

    public Department saveDepartment(Department department){
        if(departmentRepo.findByName(department.getName()) != null){
            logger.error("{} already exists", department);
            return null;
        }else {
            logger.info("saving... {}", department);
        }
        return  departmentRepo.save(department);
    }

    public List<Department> findDepartments() {
        List<Department> departments = departmentRepo.findAll();
        if(departments.isEmpty()){
            logger.info("No department found");
            return departments;
        }else {
            logger.info("Departments  {} retrieved ", departments);
        }
        return departments;
    }

    public Department findDepartment(Long departmentId) {
        Department department = departmentRepo.findById(departmentId).orElse(null);
        if(department == null){
            logger.info("department with id -> {} does not exist", departmentId);
        }else {
            logger.info("department with id -> {} found", departmentId);
        }
        return department;
    }

    public Department updateDepartment(Department department) {
        if(departmentRepo.findById(department.getId()).orElse(null) == null){
            logger.info("Department to be updated with id -> {} does not exist", department.getId());
            return null;
        }
        Department updatedDepartment = departmentRepo.save(department);
        if(updatedDepartment == null){
            logger.info("Department -> {} not updated successfully", department);
        }else {
            logger.info("Department -> {} updated successfully", department);
        }
        return updatedDepartment;
    }

    public Department deleteDepartment(Long departmentId) {
        Department department = departmentRepo.findById(departmentId).orElse(null);
        if(department == null){
            logger.info("Department to be deleted with id -> {} does not exist", departmentId);
            return null;
        }
        logger.info("Deleting department`s workers {}...", department.getWorkers());
        workerService.deleteAllWorkersByDepId(departmentId);
        logger.info("Deleting department {}...", department);
        departmentRepo.delete(department);
        return department;
    }

    public boolean deleteAllDepartments() {
        logger.info("Deleting all departments` workers...");
        workerService.deleteAllWorkers();
        logger.info("Deleting all departments...");
        departmentRepo.deleteAll();
        return true;
    }
}
