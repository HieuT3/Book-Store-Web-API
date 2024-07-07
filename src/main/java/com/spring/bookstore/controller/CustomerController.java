package com.spring.bookstore.controller;

import com.spring.bookstore.dto.CustomerProfileDto;
import com.spring.bookstore.entity.Customer;
import com.spring.bookstore.entity.Users;
import com.spring.bookstore.event.OnRegisterCompleteEvent;
import com.spring.bookstore.service.CustomerService;
import jakarta.persistence.EntityNotFoundException;
import jakarta.servlet.http.HttpServletRequest;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/customer")
@AllArgsConstructor
public class CustomerController {

    private CustomerService customerService;
    private ApplicationEventPublisher applicationEventPublisher;
    private ModelMapper modelMapper;

    @PreAuthorize("isAuthenticated()")
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

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping
    public ResponseEntity<List<CustomerProfileDto>> getAllCustomers() {
        return ResponseEntity.ok(this.customerService.getAllCustomers());
    }

    @PostMapping
    public ResponseEntity<?> registerCustomer(@RequestBody Customer customer, HttpServletRequest request) {
        try {
            CustomerProfileDto savedCustomer = this.customerService.registerCustomer(customer);

            String appURL = request.getContextPath();
            Users users = this.modelMapper.map(savedCustomer, Users.class);
            this.applicationEventPublisher.publishEvent(new OnRegisterCompleteEvent(users, appURL));
            return new ResponseEntity<>("You have registered successfully but your account is not active", HttpStatus.CREATED);
        } catch (IllegalArgumentException e) {
            e.printStackTrace();
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @PreAuthorize("isAuthenticated()")
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

    @PreAuthorize("hasRole('ADMIN')")
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
