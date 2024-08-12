package kolthy.springframework.spring6restmvc.services;

import kolthy.springframework.spring6restmvc.model.CustomerDTO;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface CustomerService {
    public List<CustomerDTO> listCustomers();
    public Optional<CustomerDTO> getById(UUID id);
    public CustomerDTO saveNewCustomer(CustomerDTO customer);
    public void updateCustomerById(UUID customerId, CustomerDTO customer);
    public void deleteCustomer(UUID customerId);

    void patchCustomerById(UUID customerId, CustomerDTO customer);
}
