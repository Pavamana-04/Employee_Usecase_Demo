package com.example.employeeprocessor.model;

import jakarta.persistence.*;

@Entity
@Table(name = "employees")
public class Employee {
    @Id
    private Integer id;

    @Column(nullable = false)
    private String name;

    private String position;
    private String department;
    private Double salary;
    private String email;


    public Employee() {}


    public Employee(Integer id, String name, String position, String department, Double salary, String email) {
        this.id = id;
        this.name = name;
        this.position = position;
        this.department = department;
        this.salary = salary;
        this.email = email;
    }


    public Integer getId() { return id; }
    public void setId(Integer id) { this.id = id; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public String getPosition() { return position; }
    public void setPosition(String position) { this.position = position; }

    public String getDepartment() { return department; }
    public void setDepartment(String department) { this.department = department; }

    public Double getSalary() { return salary; }
    public void setSalary(Double salary) { this.salary = salary; }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    @Override
    public String toString() {
        return String.format("Employee{id=%d, name='%s', position='%s', department='%s', salary=%.2f, email='%s'}",
                id, name, position, department, salary, email);
    }
}