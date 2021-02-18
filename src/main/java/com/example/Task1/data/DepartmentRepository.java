package com.example.Task1.data;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;

@Repository
@Transactional
public interface DepartmentRepository extends JpaRepository<Department,Long> {
    Department findByName(String name);
}
