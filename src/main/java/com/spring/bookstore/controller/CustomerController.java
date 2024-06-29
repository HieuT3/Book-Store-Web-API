package com.spring.bookstore.controller;

import com.spring.bookstore.entity.Customer;
import com.spring.bookstore.service.CustomerService;
import jakarta.persistence.EntityNotFoundException;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/cus")
@AllArgsConstructor
public class CustomerController {

    private CustomerService customerService;

    @GetMapping("{id}")
    public ResponseEntity<?> getCustomer(@PathVariable("id") int customerId) {
        try {
            Customer customer = this.customerService.getCustomerById(customerId);
            return new ResponseEntity<>(customer, HttpStatus.FOUND);
        } catch (EntityNotFoundException e) {
            e.printStackTrace();
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping
    public ResponseEntity<List<Customer>> getAllCustomers() {
        return ResponseEntity.ok(this.customerService.getAllCustomers());
    }

    @PostMapping
    public ResponseEntity<?> registerCustomer(@RequestBody Customer customer) {
        try {
            Customer savedCustomer = this.customerService.registerCustomer(customer);
            return new ResponseEntity<>(savedCustomer, HttpStatus.CREATED);
        } catch (IllegalArgumentException e) {
            e.printStackTrace();
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @PutMapping("{id}/update")
    public ResponseEntity<?> updateCustomer(@PathVariable("id") int customerId,
                                            @RequestBody Customer customer) {
        try {
            Customer updatedCustomer = this.customerService.updateCustomer(customerId, customer);
            return ResponseEntity.ok(updatedCustomer);
        } catch (EntityNotFoundException e) {
            e.printStackTrace();
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @DeleteMapping("{id}/delete")
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
