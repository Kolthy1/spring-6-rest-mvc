package kolthy.springframework.spring6restmvc.services;

import kolthy.springframework.spring6restmvc.model.CustomerDTO;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.time.LocalDateTime;
import java.util.*;

@Service
public class CustomerServiceImpl implements CustomerService {
    private final Map<UUID, CustomerDTO> customerMap;

    public CustomerServiceImpl() {
        this.customerMap = new HashMap<>();

        CustomerDTO customer1 = CustomerDTO.builder()
                .customerName("John Doe")
                .id(UUID.randomUUID())
                .createdDate(LocalDateTime.now())
                .lastModifiedDate(LocalDateTime.now())
                .build();

        CustomerDTO customer2 = CustomerDTO.builder()
                .customerName("Jane Smith")
                .id(UUID.randomUUID())
                .createdDate(LocalDateTime.now().minusDays(10))
                .lastModifiedDate(LocalDateTime.now().minusDays(5))
                .build();

        CustomerDTO customer3 = CustomerDTO.builder()
                .customerName("Alice Johnson")
                .id(UUID.randomUUID())
                .createdDate(LocalDateTime.now().minusMonths(1))
                .lastModifiedDate(LocalDateTime.now().minusDays(1))
                .build();

        customerMap.put(customer1.getId(),customer1);
        customerMap.put(customer2.getId(),customer2);
        customerMap.put(customer3.getId(),customer3);


    }

    @Override
    public List<CustomerDTO> listCustomers() {
        return new ArrayList<>(customerMap.values());
    }

    @Override
    public Optional<CustomerDTO> getById(UUID id) {
        return Optional.of(customerMap.get(id));
    }

    @Override
    public CustomerDTO saveNewCustomer(CustomerDTO customer) {
        CustomerDTO savedCustomer = CustomerDTO.builder()
                .customerName(customer.getCustomerName())
                .id(UUID.randomUUID())
                .createdDate(LocalDateTime.now())
                .lastModifiedDate(LocalDateTime.now())
                .build();

        customerMap.put(customer.getId(),customer);

        return savedCustomer;
    }

    @Override
    public void updateCustomerById(UUID customerId, CustomerDTO customer) {
        CustomerDTO existingCustomer = customerMap.get(customerId);
        existingCustomer.setCustomerName(customer.getCustomerName());
        existingCustomer.setLastModifiedDate(LocalDateTime.now());

        customerMap.put(customerId,existingCustomer);
    }

    @Override
    public void deleteCustomer(UUID customerId) {
        customerMap.remove(customerId);
    }

    @Override
    public void patchCustomerById(UUID customerId, CustomerDTO customer) {
        CustomerDTO existingCustomer = customerMap.get(customerId);

        if(StringUtils.hasText(customer.getCustomerName()))
            existingCustomer.setCustomerName(customer.getCustomerName());

    }
}
