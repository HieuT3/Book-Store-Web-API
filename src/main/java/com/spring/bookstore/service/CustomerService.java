package com.spring.bookstore.service;

import com.spring.bookstore.dto.CustomerProfileDto;
import com.spring.bookstore.entity.Customer;
import com.spring.bookstore.entity.RoleEnum;
import com.spring.bookstore.repository.CustomerRepository;
import com.spring.bookstore.repository.RoleRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class CustomerService {

    private CustomerRepository customerRepository;
    private RoleRepository roleRepository;
    private PasswordEncoder passwordEncoder;
    private ModelMapper modelMapper;

    public CustomerProfileDto getCustomerById(int customerId) {
        Customer customer = this.customerRepository.findById(customerId)
                .orElseThrow(
                        () -> new EntityNotFoundException("The customer with id " + customerId + " not found!")
                );
        return this.modelMapper.map(customer, CustomerProfileDto.class);
    }

    public List<CustomerProfileDto> getAllCustomers() {
        List<Customer> customers = this.customerRepository.findAll();
        return customers.stream().map((customer) -> this.modelMapper.map(customer, CustomerProfileDto.class)).toList();
    }

    public CustomerProfileDto registerCustomer(Customer customer) {
        boolean check = this.customerRepository.existsByEmail(customer.getEmail());
        if(check) {
            throw new IllegalArgumentException("The email already exists!");
        }
        customer.setPassword(this.passwordEncoder.encode(customer.getPassword()));
        customer.setRole(this.roleRepository.findByName(RoleEnum.USER).get());

        return this.modelMapper.map(this.customerRepository.save(customer), CustomerProfileDto.class);
    }

    public CustomerProfileDto updateProfile(int userId, CustomerProfileDto customerProfileDto) {
        Customer customer = this.customerRepository.findById(userId).get();
        customer.setFullName(customerProfileDto.getFullName());
        customer.setPhone(customerProfileDto.getPhone());
        customer.setAddress(customerProfileDto.getAddress());
        return this.modelMapper.map(this.customerRepository.save(customer), CustomerProfileDto.class);
    }

    public CustomerProfileDto changePassword(int userId, String password) {
        Customer customer = this.customerRepository.findById(userId).orElseThrow(
                () -> new EntityNotFoundException("The customer with id " + userId + " not found!")
        );
        customer.setPassword(this.passwordEncoder.encode(password));
        return this.modelMapper.map(this.customerRepository.save(customer), CustomerProfileDto.class);
    }

    public void deleteCustomer(int customerId) {
        this.customerRepository.findById(customerId)
                .orElseThrow(
                        () -> new EntityNotFoundException("The customer with id " + customerId + " not found!")
                );
        this.customerRepository.deleteById(customerId);
    }
}
