package com.ecommerce.microcommerce.dao;

import com.ecommerce.microcommerce.model.Employee;

import java.util.Date;
import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;
 
// This is an Interface.
// No need Annotation here
public interface EmployeeRepository extends MongoRepository<Employee, Long> { // Long: Type of Employee ID.
 
    Employee findByEmpNo(String empNo);
 
    List<Employee> findByFullNameLike(String fullName);
 
    List<Employee> findByHireDateGreaterThan(Date hireDate);
 
    // Supports native JSON query string
    @Query("{fullName:'?0'}")
    List<Employee> findCustomByFullName(String fullName);
 
}