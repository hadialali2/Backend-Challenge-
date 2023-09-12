package com.example.demo2.controller;

import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo2.collection.CompanyCollection;
import com.example.demo2.repository.CompanyRepository;

@RestController

public class CompanyController {
    @Autowired
    private CompanyRepository companyRepository;

    // get all companies
    @GetMapping("/companies")
    public ResponseEntity<?> getAllCompanies() {
        List<CompanyCollection> companies = companyRepository.findAll();
        if(companies.size() > 0) {
            return new ResponseEntity<List<CompanyCollection>>(companies,HttpStatus.OK);
        } else {
            return new ResponseEntity<>("no companies avalibale", HttpStatus.NOT_FOUND);
        }
    }
    // create new company
    @PostMapping("/companies")
    public ResponseEntity<?> createCompany(@RequestBody CompanyCollection company) {
        try {
            company.setCreatedAt(new Date(System.currentTimeMillis()));
            companyRepository.save(company);
            return new ResponseEntity<CompanyCollection>(company, HttpStatus.OK);
        } catch (Exception e) {
            // TODO: handle exception
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }
    // Update an existing company
@PutMapping("/companies/{id}")
public ResponseEntity<?> updateCompany(@PathVariable String id, @RequestBody CompanyCollection updatedCompany) {
    try {
        Optional<CompanyCollection> existingCompany = companyRepository.findById(id);

        if (existingCompany.isPresent()) {
            CompanyCollection company = existingCompany.get();
            company.setName(updatedCompany.getName());
            // Update other fields as needed
            companyRepository.save(company);
            return new ResponseEntity<CompanyCollection>(company, HttpStatus.OK);
        } else {
            return new ResponseEntity<>("Company not found", HttpStatus.NOT_FOUND);
        }
    } catch (Exception e) {
        return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
// Delete a company by ID
@DeleteMapping("/companies/{id}")
public ResponseEntity<?> deleteCompany(@PathVariable String id) {
    try {
        Optional<CompanyCollection> companyToDelete = companyRepository.findById(id);

        if (companyToDelete.isPresent()) {
            companyRepository.deleteById(id);
            return new ResponseEntity<>("Company deleted successfully", HttpStatus.OK);
        } else {
            return new ResponseEntity<>("Company not found", HttpStatus.NOT_FOUND);
        }
    } catch (Exception e) {
        return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
    }
}


}
