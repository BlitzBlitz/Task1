package com.example.Task1.data;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.ArrayList;
import java.util.List;

@Entity
public class Department {
    @Id
    @GeneratedValue
    @NotNull(message = "Id must not be null")
    private Long id;
    @Size(min = 5, message = "Name should be more than 5 letters")
    private String name;

    @OneToMany(mappedBy = "department")
    private List<Worker> workers = new ArrayList<>();

    public Department() {
    }

    public Department(@Size(min = 5, message = "Name should be more than 5 letters") String name) {
        this.name = name;
    }

    public Department(@NotNull(message = "Id must not be null") Long id,
                      @Size(min = 5, message = "Name should be more than 5 letters") String name) {
        this.id = id;
        this.name = name;
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

    public List<Worker> getWorkers() {
        return workers;
    }

    public void addWorker(Worker worker) {
        this.workers.add(worker);
    }

    public void removeWorker(Worker worker) {
        this.workers.remove(worker);
    }

    @Override
    public String toString() {
        return "Department{" +
                "id=" + id +
                ", name='" + name + '\'' +
                '}';
    }
}
