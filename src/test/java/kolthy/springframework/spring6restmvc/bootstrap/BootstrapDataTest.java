package kolthy.springframework.spring6restmvc.bootstrap;

import kolthy.springframework.spring6restmvc.repositories.BeerRepository;
import kolthy.springframework.spring6restmvc.repositories.CustomerRepository;
import kolthy.springframework.spring6restmvc.services.BeerCsvService;
import kolthy.springframework.spring6restmvc.services.BeerCsvServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
@DataJpaTest
@Import(BeerCsvServiceImpl.class)
class BootstrapDataTest {

    @Autowired
    BeerRepository beerRepository;
    @Autowired
    CustomerRepository customerRepository;
    @Autowired
    BeerCsvService csvService;

    BootstrapData bootstrapData;

    @BeforeEach
    void setUp() {
        bootstrapData = new BootstrapData(beerRepository,customerRepository,csvService);
    }

    @Test
    void run() throws Exception {
    bootstrapData.run(null);

    assertThat(beerRepository.count()).isEqualTo(2413);
    assertThat(customerRepository.count()).isEqualTo(3);

    }
}