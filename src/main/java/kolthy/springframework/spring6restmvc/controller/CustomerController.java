package kolthy.springframework.spring6restmvc.controller;

import kolthy.springframework.spring6restmvc.model.CustomerDTO;
import kolthy.springframework.spring6restmvc.services.CustomerService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
/*
@AllArgsConstructor
@RestController
public class CustomerController {
    private final CustomerService customerService;
    public static final String CUSTOMER_PATH = "/api/v1/customer";
    public static final String CUSTOMER_PATH_ID = CUSTOMER_PATH + "/{customerId}";

    @PatchMapping(CUSTOMER_PATH_ID)
    public ResponseEntity patchCustomerById(@PathVariable("customerId") UUID customerId, @RequestBody CustomerDTO customer) {
        customerService.patchCustomerById(customerId, customer);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @DeleteMapping(CUSTOMER_PATH_ID)
    public ResponseEntity deleteById(@PathVariable("customerId") UUID customerId) {
        customerService.deleteCustomer(customerId);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @PutMapping(CUSTOMER_PATH_ID)
    public ResponseEntity updateCustomerById(@PathVariable("customerId") UUID customerId, @RequestBody CustomerDTO customer) {
        customerService.updateCustomerById(customerId, customer);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @PostMapping(CUSTOMER_PATH)
    public ResponseEntity postCustomer(@RequestBody CustomerDTO customer) {

        CustomerDTO savedCustomer = customerService.saveNewCustomer(customer);

        HttpHeaders headers = new HttpHeaders();
        headers.add("Location",CUSTOMER_PATH + customer.getId().toString());

        return new ResponseEntity<>(headers,HttpStatus.CREATED);
    }

    @GetMapping(value = CUSTOMER_PATH)
    public List<CustomerDTO> listCustomers() {
        return customerService.listCustomers();
    }

    @GetMapping(value = CUSTOMER_PATH_ID)
    public CustomerDTO getById(@PathVariable("customerId") UUID customerId) {
        return customerService.getById(customerId).orElseThrow(NotFoundException::new);
    }



}


 */