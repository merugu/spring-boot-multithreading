package com.innovativeintelli.multithreading.jpa;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.innovativeintelli.multithreading.model.Salary;
import com.innovativeintelli.multithreading.model.SalaryPK;

public interface SalaryRepository extends JpaRepository<Salary, Integer> {
	
	Optional<Salary> findById(SalaryPK id);

}
