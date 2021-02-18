package com.example.Task1.data;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Entity
public class Worker {
    @Id
    @GeneratedValue
    @NotNull
    private Long id;
    @Size(min = 5)
    private String name;
    private Double salary;

    @ManyToOne
    @JsonIgnoreProperties("workers")
    @NotNull
    private Department department;

    public Worker() {
    }

    public Worker(Long id,String name, Double salary, Department department) {
        this.id = id;
        this.name = name;
        this.salary = salary;
        this.department = department;

    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Double getSalary() {
        return salary;
    }

    public void setSalary(Double salary) {
        this.salary = salary;
    }

    public Department getDepartment() {
        return department;
    }

    public void setDepartment(Department department) {
        this.department = department;
    }

    @Override
    public String toString() {
        return "Worker{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", salary=" + salary +
                ", department=" + department.getName() +
                '}';
    }
}
