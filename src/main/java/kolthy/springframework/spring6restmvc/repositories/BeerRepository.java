package kolthy.springframework.spring6restmvc.repositories;

import kolthy.springframework.spring6restmvc.entities.Beer;
import kolthy.springframework.spring6restmvc.model.BeerStyle;
import org.springframework.data.domain.Page;
import org.springframework.data.jpa.repository.JpaRepository;

import java.awt.print.Pageable;
import java.util.UUID;

public interface BeerRepository extends JpaRepository<Beer, UUID> {
    Page<Beer> findAllByBeerNameIsLikeIgnoreCase(String beerName, org.springframework.data.domain.Pageable pageable);

    Page<Beer> findAllByBeerStyle(BeerStyle beerStyle, org.springframework.data.domain.Pageable pageable);

    Page<Beer> findAllByBeerNameIsLikeIgnoreCaseAndBeerStyle(String beerName, BeerStyle beerStyle, org.springframework.data.domain.Pageable pageable);
}
