package com.spring.bookstore.controller;

import com.spring.bookstore.dto.CustomerProfileDto;
import com.spring.bookstore.entity.Customer;
import com.spring.bookstore.service.CustomerService;
import jakarta.persistence.EntityNotFoundException;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/customer")
@AllArgsConstructor
public class CustomerController {

    private CustomerService customerService;

    @GetMapping("{id}")
    public ResponseEntity<?> getCustomer(@PathVariable("id") int customerId) {
        try {
            CustomerProfileDto customer = this.customerService.getCustomerById(customerId);
            return new ResponseEntity<>(customer, HttpStatus.FOUND);
        } catch (EntityNotFoundException e) {
            e.printStackTrace();
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping
    public ResponseEntity<List<CustomerProfileDto>> getAllCustomers() {
        return ResponseEntity.ok(this.customerService.getAllCustomers());
    }

    @PostMapping
    public ResponseEntity<?> registerCustomer(@RequestBody Customer customer) {
        try {
            CustomerProfileDto savedCustomer = this.customerService.registerCustomer(customer);
            return new ResponseEntity<>(savedCustomer, HttpStatus.CREATED);
        } catch (IllegalArgumentException e) {
            e.printStackTrace();
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @PutMapping("{id}")
    public ResponseEntity<?> updateCustomer(@PathVariable("id") int customerId,
                                            @RequestBody CustomerProfileDto customer) {
        try {
            CustomerProfileDto updatedCustomer = this.customerService.updateProfile(customerId, customer);
            return ResponseEntity.ok(updatedCustomer);
        } catch (EntityNotFoundException e) {
            e.printStackTrace();
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @DeleteMapping("{id}")
    public ResponseEntity<?> deleteCustomer(@PathVariable("id") int customerId) {
        try {
            this.customerService.deleteCustomer(customerId);
            return ResponseEntity.ok("Customer with id " + customerId + " has been deleted successfully!");
        } catch (EntityNotFoundException e) {
            e.printStackTrace();
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }
}
