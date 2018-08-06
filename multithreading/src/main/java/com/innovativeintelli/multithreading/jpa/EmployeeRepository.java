package com.innovativeintelli.multithreading.jpa;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.innovativeintelli.multithreading.model.Employee;

public interface EmployeeRepository extends JpaRepository<Employee, Integer> {
	
           Employee findByEmpNo(int emp_no);
           
            List<Employee> findAll();
}
