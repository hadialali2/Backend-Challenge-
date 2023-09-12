package com.example.demo2.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;
import com.example.demo2.collection.EmployeeCollection;

@Repository
public interface EmployeeRepository extends MongoRepository<EmployeeCollection, String> {
    
}
