package kolthy.springframework.spring6restmvc.mappers;

import kolthy.springframework.spring6restmvc.entities.Customer;
import kolthy.springframework.spring6restmvc.model.CustomerDTO;
import org.mapstruct.Mapper;

@Mapper
public interface CustomerMapper {
    Customer customerDtoToCustomer(CustomerDTO customerDTO);
    CustomerDTO customerToCustomerDto(Customer customer);
}
