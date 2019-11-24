package com.ecommerce.microcommerce.dao;

import com.ecommerce.microcommerce.model.Employee;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Repository;

import com.mongodb.WriteResult;
import com.mongodb.client.result.UpdateResult;

@Repository
public class EmployeeRepositoryCustomImpl implements EmployeeRepositoryCustom {

    @Autowired
    MongoTemplate mongoTemplate;

    public EmployeeRepositoryCustomImpl()
    {}
 
    public long getMaxEmpId() {
        Query query = new Query();
        query.with(new Sort(Sort.Direction.DESC, "id"));
        query.limit(1);
        Employee maxObject = mongoTemplate.findOne(query, Employee.class);
        if (maxObject == null) {
            return 0L;
        }
        return maxObject.getId();
    }

    @Override
    public long updateEmployee(String empNo, String fullName, Date hireDate) {
        Query query = new Query(Criteria.where("empNo").is(empNo));
        Update update = new Update();
        update.set("fullName", fullName);
        update.set("hireDate", hireDate);

        WriteResult result = mongoTemplate.updateFirst(query, update, Employee.class);
        if (result != null) {
            return result.getN();
        }

        return 0;
    }

}