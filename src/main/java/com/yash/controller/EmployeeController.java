package com.yash.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.yash.exception.ResourceNotFoundException;
import com.yash.model.Employee;
import com.yash.repository.EmployeeRepository;

@CrossOrigin("*")
@RestController
public class EmployeeController {

	@Autowired
	private EmployeeRepository employeeRepository;

	@GetMapping("/get/employee")
	public List<Employee> getAllEmployees() {
		return employeeRepository.findAll();
	}

	@PostMapping("/save/employee")
	public ResponseEntity<Employee> saveEmployee(@RequestBody Employee employee) {
		Employee employee1 = employeeRepository.save(employee);
		return new ResponseEntity<>(employee1, HttpStatus.OK);
	}

	@GetMapping("/getid/{id}")
	public ResponseEntity<Employee> getEmployeeById(@PathVariable long id) {
		Employee employee = employeeRepository.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("Employee not exist with id:" + id));

		return ResponseEntity.ok(employee);
	}

	@PutMapping("/update/{id}")
	public ResponseEntity<Employee> updateEmployee(@PathVariable long id, @RequestBody Employee employeeDetails) {
		Employee updateEmployee = employeeRepository.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("Employee not exist with id:" + id));
		updateEmployee.setName(employeeDetails.getName());
		updateEmployee.setdOJ(employeeDetails.getdOJ());
		updateEmployee.setTotalExp(employeeDetails.getTotalExp());
		updateEmployee.setDesignation(employeeDetails.getDesignation());
		updateEmployee.setSkills(employeeDetails.getSkills());
		employeeRepository.save(updateEmployee);

		return ResponseEntity.ok(updateEmployee);
	}

	@DeleteMapping("/delete/{id}")
	public ResponseEntity<HttpStatus> deleteEmployee(@PathVariable long id) {
		Employee employee = employeeRepository.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("Employee not exist with id:" + id));
		employeeRepository.delete(employee);
		return new ResponseEntity<>(HttpStatus.NO_CONTENT);
	}
}
