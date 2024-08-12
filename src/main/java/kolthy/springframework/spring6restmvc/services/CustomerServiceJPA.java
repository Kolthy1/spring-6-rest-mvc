package kolthy.springframework.spring6restmvc.services;

import kolthy.springframework.spring6restmvc.mappers.BeerMapperImpl;
import kolthy.springframework.spring6restmvc.mappers.CustomerMapper;
import kolthy.springframework.spring6restmvc.model.CustomerDTO;
import kolthy.springframework.spring6restmvc.repositories.BeerRepository;
import kolthy.springframework.spring6restmvc.repositories.CustomerRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;


@Service
@Primary
@RequiredArgsConstructor
public class CustomerServiceJPA implements CustomerService {
    private final CustomerRepository customerRepository;
    private final CustomerMapper customerMapper;
    private final BeerRepository beerRepository;
    private final BeerMapperImpl beerMapperImpl;

    @Override
    public List<CustomerDTO> listCustomers() {
        return null;
    }

    @Override
    public Optional<CustomerDTO> getById(UUID id) {
        return Optional.empty();
    }

    @Override
    public CustomerDTO saveNewCustomer(CustomerDTO customer) {
        return null;
    }

    @Override
    public void updateCustomerById(UUID customerId, CustomerDTO customer) {

    }

    @Override
    public void deleteCustomer(UUID customerId) {

    }

    @Override
    public void patchCustomerById(UUID customerId, CustomerDTO customer) {

    }
}
