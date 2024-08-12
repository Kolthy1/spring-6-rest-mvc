package kolthy.springframework.spring6restmvc.repositories;

import kolthy.springframework.spring6restmvc.bootstrap.BootstrapData;
import kolthy.springframework.spring6restmvc.entities.Beer;
import kolthy.springframework.spring6restmvc.model.BeerStyle;
import kolthy.springframework.spring6restmvc.services.BeerCsvServiceImpl;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;
import org.springframework.data.domain.Page;

import java.math.BigDecimal;
import java.util.List;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;


@DataJpaTest
@Import({BootstrapData.class, BeerCsvServiceImpl.class})
class BeerRepositoryTest {
    @Autowired
    BeerRepository beerRepository;

    @Test
    void testGetBeerListByName() {
        Page<Beer> list = beerRepository.findAllByBeerNameIsLikeIgnoreCase("%IPA%", null);

        assertThat(list.getContent().size()).isEqualTo(336);
    }

    @Test
    void testSaveBeer() {
        Beer savedBeer = beerRepository.save(Beer.builder()
                .beerName("abc")
                .beerStyle(BeerStyle.IPA)
                .upc("123")
                .price(new BigDecimal(12))
                .build());

        beerRepository.flush();

        assertThat(savedBeer).isNotNull();
        assertThat(savedBeer.getId()).isNotNull();
    }
}

