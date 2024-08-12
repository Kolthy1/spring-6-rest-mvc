package kolthy.springframework.spring6restmvc.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import kolthy.springframework.spring6restmvc.model.BeerDTO;
import kolthy.springframework.spring6restmvc.model.CustomerDTO;
import kolthy.springframework.spring6restmvc.services.BeerServiceImpl;
import kolthy.springframework.spring6restmvc.services.CustomerService;
import kolthy.springframework.spring6restmvc.services.CustomerServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static kolthy.springframework.spring6restmvc.controller.CustomerController.CUSTOMER_PATH;
import static kolthy.springframework.spring6restmvc.controller.CustomerController.CUSTOMER_PATH_ID;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.mockito.ArgumentMatchers.matches;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.willThrow;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.hamcrest.core.Is.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(CustomerController.class) // Specify the controller under test
class CustomerControllerTest {

    @Autowired
    MockMvc mockMvc;

    @Autowired
    ObjectMapper objectMapper;

    @MockBean
    CustomerService customerService;

    CustomerServiceImpl customerServiceImpl;
    @Autowired
    private ObjectMapper jacksonObjectMapper;

    @Captor
    ArgumentCaptor<UUID> uuidArgumentCaptor;

    @Captor
    ArgumentCaptor<CustomerDTO> customerDTOArgumentCaptor;

    @BeforeEach
    void setUp() {
        customerServiceImpl = new CustomerServiceImpl();
    }

    @Test
    void testPatchCustomer() throws Exception {
        CustomerDTO customerDTO = customerServiceImpl.listCustomers().get(0);

        Map<String, Object> customerMap = new HashMap<>();
        customerMap.put("customerName", "New Name");

        mockMvc.perform(patch(CUSTOMER_PATH_ID, customerDTO.getId()) // Pass the customer ID
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(customerMap)))
                .andExpect(status().isNoContent());

        verify(customerService).patchCustomerById(uuidArgumentCaptor.capture(), customerDTOArgumentCaptor.capture());
        assertThat(customerDTO.getId()).isEqualTo(uuidArgumentCaptor.getValue());
        assertThat(customerMap.get("customerName")).isEqualTo(customerDTOArgumentCaptor.getValue().getCustomerName());
    }

    @Test
    void testDeleteCustomer() throws Exception {
        CustomerDTO customerDTO = customerServiceImpl.listCustomers().get(0);

        mockMvc.perform(delete(CUSTOMER_PATH_ID, customerDTO.getId(), customerDTO.getId()) // Pass the customer ID
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isNoContent());

        verify(customerService).deleteCustomer(uuidArgumentCaptor.capture());
        assertThat(customerDTO.getId()).isEqualTo(uuidArgumentCaptor.getValue());
    }

    @Test
    void testUpdateCustomer() throws Exception {
        CustomerDTO customerDTO = customerServiceImpl.listCustomers().get(0);

        mockMvc.perform(put(CUSTOMER_PATH_ID, customerDTO.getId())
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(customerDTO)))
                .andExpect(status().isNoContent());

        verify(customerService).updateCustomerById(any(UUID.class),any(CustomerDTO.class));

    }

    @Test
    void testCreateCustomers() throws JsonProcessingException {
        CustomerDTO customerDTO = customerServiceImpl.listCustomers().get(0);
        System.out.println(objectMapper.writeValueAsString(customerDTO));
    }


/*
    @Test
    void testListCustomers() throws Exception {
        // Correctly set up the mock to return the list of customers
        given(customerService.listCustomers()).willReturn(customerServiceImpl.listCustomers());

        mockMvc.perform(get(CUSTOMER_PATH)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.length()", is(3)));
    }


 */
    @Test
    void getCustomerByIdNotFound() throws Exception {
        // Setup to throw an exception if the customer is not found
        given(customerService.getById(any(UUID.class))).willReturn(Optional.empty());

        mockMvc.perform(get("/api/v1/customer/" + UUID.randomUUID()) // Pass a random UUID to simulate not found
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }

    @Test
    void getCustomerById() throws Exception {
        CustomerDTO customerDTO = customerServiceImpl.listCustomers().get(0);

        // Mock the behavior of getById to return the test customer
        given(customerService.getById(customerDTO.getId())).willReturn(Optional.of(customerDTO));

        mockMvc.perform(get(CUSTOMER_PATH_ID, customerDTO.getId())
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.id", is(customerDTO.getId().toString())))
                .andExpect(jsonPath("$.customerName", is(customerDTO.getCustomerName())));
    }
}