package com.spring.bookstore.service;

import com.spring.bookstore.entity.Customer;
import com.spring.bookstore.entity.RoleEnum;
import com.spring.bookstore.repository.CustomerRepository;
import com.spring.bookstore.repository.RoleRepository;
import com.spring.bookstore.repository.UserRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.AllArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class CustomerService {

    private CustomerRepository customerRepository;
    private RoleRepository roleRepository;
    private PasswordEncoder passwordEncoder;

    public Customer getCustomerById(int customerId) {
        return this.customerRepository.findById(customerId)
                .orElseThrow(
                        () -> new EntityNotFoundException("The customer with id " + customerId + " not found!")
                );
    }

    public List<Customer> getAllCustomers() {
        return this.customerRepository.findAll();
    }

    public Customer registerCustomer(Customer customer) {
        boolean check = this.customerRepository.existsByEmail(customer.getEmail());
        if(check) {
            throw new IllegalArgumentException("The email already exists!");
        }
        customer.setPassword(this.passwordEncoder.encode(customer.getPassword()));
        customer.setRole(this.roleRepository.findByName(RoleEnum.USER).get());
        System.out.println(customer);
        return this.customerRepository.save(customer);
    }

    // Chua on, phai nghien cuu lai
    public Customer updateCustomer(int customerId, Customer customer) {
        Customer existsCustomer = this.customerRepository.findById(customerId)
                .orElseThrow(
                        () -> new EntityNotFoundException("The customer with id " + customerId + " not found!")
                );
        customer.setUserId(customerId);
        return this.customerRepository.save(customer);
    }

    public void deleteCustomer(int customerId) {
        this.customerRepository.findById(customerId)
                .orElseThrow(
                        () -> new EntityNotFoundException("The customer with id " + customerId + " not found!")
                );
        this.customerRepository.deleteById(customerId);
    }
}
