package com.example.Task1.data;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.List;

@Repository
@Transactional
public interface WorkerRepository extends JpaRepository<Worker, Long> {
    Worker findByName(String name);
    List<Worker> findByDepartment_Id(Long departmentId);
    Integer deleteAllByDepartment_Id(Long departmentId);
}
