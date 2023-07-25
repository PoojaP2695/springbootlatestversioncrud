package com.csi.controller;

import com.csi.exception.RecordNotFoundException;
import com.csi.model.Employee;
import com.csi.service.EmployeeServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api")
public class EmployeeController {

    @Autowired
    EmployeeServiceImpl employeeServiceImpl;

    @PostMapping("/savedata")
    public ResponseEntity<String> saveData(@RequestBody Employee employee) {
        employeeServiceImpl.saveData(employee);
        return ResponseEntity.ok("Save data successfully");
    }

    @GetMapping("/getdatabyid/{empId}")
    public Optional<Employee> getDataById(@PathVariable int empId) {
        return employeeServiceImpl.getDataById(empId);
    }

    @GetMapping("/getAlldata")
    public ResponseEntity<List<Employee>> getAllData() {
        return ResponseEntity.ok(employeeServiceImpl.getAllData());
    }

    @PutMapping("/updatedata/{empId}")
    public ResponseEntity<Employee> updateData(@PathVariable int empId, @RequestBody Employee employee) {
        Employee employee1 = employeeServiceImpl.getDataById(empId).orElseThrow(() -> new RecordNotFoundException("Employee Id does not exits"));

        employee1.setEmpContactNumber(employee.getEmpContactNumber());
        employee1.setEmpEmailId(employee.getEmpEmailId());
        employee1.setEmpDOB(employee.getEmpDOB());
        employee1.setEmpAddress(employee.getEmpAddress());
        employee1.setEmpName(employee.getEmpName());

        return new ResponseEntity<>(employeeServiceImpl.updateData(employee1), HttpStatus.CREATED);
    }

    @PatchMapping("/changeaddress/{empId}/{empAddress}")
    public ResponseEntity<Employee> changeAddress(@PathVariable int empId, @PathVariable String empAddress) {
        Employee employee1 = employeeServiceImpl.getDataById(empId).orElseThrow(() -> new RecordNotFoundException("Employee id does not exits"));

        employee1.setEmpAddress(empAddress);
        return ResponseEntity.ok(employeeServiceImpl.changeAddress(employee1));

    }

    @DeleteMapping("/deletedatabyid/{empId}")
    public ResponseEntity<String> deleteDataById(@PathVariable int empId) {
        employeeServiceImpl.deleteDataById(empId);
        return ResponseEntity.ok("Data deleted successfully");
    }

    @GetMapping("/welcome")
    public ResponseEntity<String> welcome(){
        return ResponseEntity.ok("Welcome to CSI");
    }
}
