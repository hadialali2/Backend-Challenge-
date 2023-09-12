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
import com.example.demo2.collection.EmployeeCollection;
import com.example.demo2.repository.CompanyRepository;
import com.example.demo2.repository.EmployeeRepository;

@RestController
public class EmployeeController {

    @Autowired
    private EmployeeRepository employeeRepository;

    @Autowired
    private CompanyRepository companyRepository;

    // get all employees (get request)
    @GetMapping("/employees")
    public ResponseEntity<?> getAllEmployees() {
        List<EmployeeCollection> Employees = employeeRepository.findAll();
        if(Employees.size() > 0) {
            return new ResponseEntity<List<EmployeeCollection>>(Employees,HttpStatus.OK);
        } else {
            return new ResponseEntity<>("no Employees found", HttpStatus.NOT_FOUND);
        }
    }

    // create new company (post request)
    @PostMapping("/employees")
    public ResponseEntity<?> createEmployee(@RequestBody EmployeeCollection employee) {
        try {
            // Retrieve the company by its ID
            CompanyCollection company = companyRepository.findById(employee.getCompany().getId()).orElseThrow(() -> new RuntimeException("Company not found"));
            employee.setCreatedAt(new Date(System.currentTimeMillis()));
            employee.setCompany(company);
            employeeRepository.save(employee);
            return new ResponseEntity<EmployeeCollection>(employee, HttpStatus.OK);
        } catch (Exception e) {
            // TODO: handle exception
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    // Update an existing company
@PutMapping("/employees/{id}")
public ResponseEntity<?> updateEmployee(@PathVariable String id, @RequestBody EmployeeCollection updatedEmployee) {

    try {
        Optional<EmployeeCollection> existingEmployee = employeeRepository.findById(id);

        if (existingEmployee.isPresent()) {
            EmployeeCollection employee = existingEmployee.get();
            employee.setName(updatedEmployee.getName());
            employee.setPosition(updatedEmployee.getPosition());
            employee.setUpdatedAt(new Date(System.currentTimeMillis()));
            CompanyCollection company = companyRepository.findById(employee.getCompany().getId()).orElseThrow(() -> new RuntimeException("Company not found"));
            employee.setCompany(company);
            // Update other fields as needed
            employeeRepository.save(employee);
            return new ResponseEntity<EmployeeCollection>(employee, HttpStatus.OK);
        } else {
            return new ResponseEntity<>("employee not found", HttpStatus.NOT_FOUND);
        }
    } catch (Exception e) {
        return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
// Delete a employee by ID (delete method)
@DeleteMapping("/employees/{id}")
public ResponseEntity<?> deleteEmployee(@PathVariable String id) {
    try {
        Optional<EmployeeCollection> employeeToDelete = employeeRepository.findById(id);

        if (employeeToDelete.isPresent()) {
            employeeRepository.deleteById(id);
            return new ResponseEntity<>("employee deleted successfully", HttpStatus.OK);
        } else {
            return new ResponseEntity<>("employee not found", HttpStatus.NOT_FOUND);
        }
    } catch (Exception e) {
        return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
    }
}

}

