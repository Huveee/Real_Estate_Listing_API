package com.example.CRUDApplicationRealEstate.service;

import com.example.CRUDApplicationRealEstate.config.RequestContext;
import com.example.CRUDApplicationRealEstate.model.Customer;
import com.example.CRUDApplicationRealEstate.repo.CustomerRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CustomerService {

    private final CustomerRepository customerRepository;
    private final AuditLogService auditLogService;
    private final RequestContext requestContext;

    // Get all the Customers
    public List<Customer> getAll() {
        return customerRepository.findByDeletedFalse();
    }

    // get the Customer with id
    public Customer getById(Long id) {
        return customerRepository.findByIdAndDeletedFalse(id)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND,
                        "User not found or has been deleted"
                ));
    }

    // creating new Customer
    public Customer create(Customer customer) {
        Customer savedCustomer = customerRepository.save(customer);

        // Add Audit log
        auditLogService.log(
                requestContext.getApiKey(),     // Global apı key
                "CREATE",
                "User",
                savedCustomer.getId(),
                "New user created"
        );

        return savedCustomer;
    }

// Delete the Customer (soft delete)

    public void delete(Long id) {
        Customer customer = customerRepository.findByIdAndDeletedFalse(id)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND,
                        "User not found or has already been deleted"
                ));

        customer.setDeleted(true);
        customerRepository.save(customer);

        auditLogService.log(
                requestContext.getApiKey(),
                "SOFT_DELETE",
                "User",
                customer.getId(),
                "User marked as deleted"
        );
    }

}
