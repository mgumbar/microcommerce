package com.ecommerce.microcommerce.model;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

@Document(collection = "Employee")
public class Employee {
 
    @Id
    private Long id;
 
    @Indexed(unique = true)
    @Field(value = "Emp_No")
    private String empNo;
 
    @Field(value = "Full_Name")
    private String fullName;
 
    @Field(value = "Hire_Date")
    private Date hireDate;

    public Employee()
    {}
 
    public Long getId() {
        return id;
    }
 
    public void setId(Long id) {
        this.id = id;
    }
 
    public String getEmpNo() {
        return empNo;
    }
 
    public void setEmpNo(String empNo) {
        this.empNo = empNo;
    }
 
    public String getFullName() {
        return fullName;
    }
 
    public void setFullName(String fullName) {
        this.fullName = fullName;
    }
 
    public Date getHireDate() {
        return hireDate;
    }
 
    public void setHireDate(Date hireDate) {
        this.hireDate = hireDate;
    }
 
    @Override
    public String toString() {
        return "id:" + this.id + ", empNo: " + empNo //
                + ", fullName: " + this.fullName + ", hireDate: " + this.hireDate;
    }
}
