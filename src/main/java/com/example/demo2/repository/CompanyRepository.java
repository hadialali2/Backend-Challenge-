package com.example.demo2.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.example.demo2.collection.CompanyCollection;

@Repository
public interface CompanyRepository extends MongoRepository<CompanyCollection, String> {
    
}
