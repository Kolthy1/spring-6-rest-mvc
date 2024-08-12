package kolthy.springframework.spring6restmvc.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import kolthy.springframework.spring6restmvc.model.BeerDTO;
import kolthy.springframework.spring6restmvc.services.BeerService;
import kolthy.springframework.spring6restmvc.services.BeerServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

import static kolthy.springframework.spring6restmvc.controller.BeerController.BEER_PATH;
import static kolthy.springframework.spring6restmvc.controller.BeerController.BEER_PATH_ID;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;
import static org.hamcrest.core.Is.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


@WebMvcTest(BeerController.class)
class BeerControllerTest {

    @Autowired
    MockMvc mockMvc;

    @Autowired
    ObjectMapper objectMapper;

    @MockBean
    BeerService beerService;

    BeerServiceImpl beerServiceImpl;

    @Captor
    ArgumentCaptor<UUID> uuidArgumentCaptor;

    @Captor
    ArgumentCaptor<BeerDTO> beerDTOArgumentCaptor;

    @BeforeEach
    void setUp() {
        beerServiceImpl = new BeerServiceImpl();
    }

    @Test
    void testUpdateBeerBlankName() throws Exception {
        BeerDTO beerDTO = beerServiceImpl.listBeers(null, null, false, 1, 25).getContent().get(0);
        beerDTO.setBeerName("");
        mockMvc.perform(put(BEER_PATH_ID, beerDTO.getId())
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(beerDTO)))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.length()",is(1)));

    }

    @Test
    void testCreateBeerNullBeerName() throws Exception {
        // Create a BeerDTO with no name set
        BeerDTO beerDTO = BeerDTO.builder().build();

        // Mocking the behavior of beerService when saveNewBeer is called
        given(beerService.saveNewBeer(any(BeerDTO.class))).willReturn(beerServiceImpl.listBeers(null, null, false, 1, 25).getContent().get(1));

        // Perform the post request
        MvcResult mockMvcResult = mockMvc.perform(post(BEER_PATH)
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(beerDTO)))
                .andExpect(jsonPath("$.length()",is(6)))
                .andExpect(status().isBadRequest())
                .andReturn();

        System.out.println(mockMvcResult.getResponse().getContentAsString());
    }

    @Test
    void testPatchBeer() throws Exception {
        BeerDTO beerDTO = beerServiceImpl.listBeers(null, null, false, 1, 25).getContent().get(0);

        Map<String, Object> beerMap = new HashMap<>();
        beerMap.put("beerName", "New Name");

        mockMvc.perform(patch(BEER_PATH_ID, beerDTO.getId()).contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON).content(objectMapper.writeValueAsString(beerMap)))
                .andExpect(status().isNoContent());

        verify(beerService).patchBeerById(uuidArgumentCaptor.capture(),beerDTOArgumentCaptor.capture());
        assertThat(beerDTO.getId()).isEqualTo(uuidArgumentCaptor.getValue());
        assertThat(beerMap.get("beerName")).isEqualTo(beerDTOArgumentCaptor.getValue().getBeerName());
    }

    @Test
    void testDeleteBeer() throws Exception {
        BeerDTO beerDTO = beerServiceImpl.listBeers(null, null, false, 1, 25).getContent().get(0);

        mockMvc.perform(delete(BEER_PATH_ID, beerDTO.getId())
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isNoContent());
//Didn't understand ArgumentCaptor.
        verify(beerService).deleteBeer(uuidArgumentCaptor.capture());
        assertThat(beerDTO.getId()).isEqualTo(uuidArgumentCaptor.getValue());
    }

    @Test
    void testUpdateBeer() throws Exception {
        BeerDTO beerDTO = beerServiceImpl.listBeers(null, null, false, 1, 25).getContent().get(0);

        mockMvc.perform(put(BEER_PATH_ID, beerDTO.getId())
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(beerDTO)))
                .andExpect(status().isNoContent());

        verify(beerService).updateBeerById(any(UUID.class),any(BeerDTO.class));
        //Version that uses line below isn't working. Why?
        //verify(beerService).updateBeerById(beerDTO.getId(),any(BeerDTO.class));
    }

    @Test
    void testCreateNewBeer() throws JsonProcessingException {

        BeerDTO beerDTO = beerServiceImpl.listBeers(null, null, false, 1, 25).getContent().get(0);
        System.out.println(objectMapper.writeValueAsString(beerDTO));
    }
    @Test
    void testListBeers() throws Exception{
        given(beerService.listBeers(any(), any(), any(), any(), any()))
                .willReturn(beerServiceImpl.listBeers(null, null, false, 1, 25));

        mockMvc.perform(get(BEER_PATH)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.content.length()",is(3)));
    }



    @Test
    void getBeerByIdNotFound() throws Exception{
        given(beerService.getBeerById(any(UUID.class))).willReturn(Optional.empty());

        mockMvc.perform(get(BEER_PATH + "/",UUID.randomUUID()))
                .andExpect(status().isNotFound());
    }

    @Test
    void getBeerById() throws Exception {
        BeerDTO beerDTO = beerServiceImpl.listBeers(null, null, false, 1, 25).getContent().get(0);
        given(beerService.getBeerById(beerDTO.getId())).willReturn(Optional.of(beerDTO));

        mockMvc.perform(get(BEER_PATH_ID, beerDTO.getId())
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.id",is(beerDTO.getId().toString())))
                .andExpect(jsonPath("$.beerName",is(beerDTO.getBeerName())));
    }
}