package kolthy.springframework.spring6restmvc.services;

import kolthy.springframework.spring6restmvc.model.BeerDTO;
import kolthy.springframework.spring6restmvc.model.BeerStyle;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.*;

@Slf4j
@Service
public class BeerServiceImpl implements BeerService {

    private final Map<UUID, BeerDTO> beerMap;

    public BeerServiceImpl() {
        this.beerMap = new HashMap<>();

        BeerDTO beer1 = BeerDTO.builder()
                .id(UUID.randomUUID())
                .version(1)
                .beerName("Name")
                .beerStyle(BeerStyle.IPA)
                .upc("12356")
                .price(new BigDecimal(12))
                .quantityOnHand(11)
                .createdDate(LocalDateTime.now())
                .updateDate(LocalDateTime.now())
                .build();

        BeerDTO beer2 = BeerDTO.builder()
                .id(UUID.randomUUID())
                .version(1)
                .beerName("Name")
                .upc("12356")
                .beerStyle(BeerStyle.IPA)
                .price(new BigDecimal(12))
                .quantityOnHand(11)
                .createdDate(LocalDateTime.now())
                .updateDate(LocalDateTime.now())
                .build();

        BeerDTO beer3 = BeerDTO.builder()
                .id(UUID.randomUUID())
                .version(1)
                .beerName("Name")
                .beerStyle(BeerStyle.IPA)
                .upc("12356")
                .price(new BigDecimal(12))
                .quantityOnHand(11)
                .createdDate(LocalDateTime.now())
                .updateDate(LocalDateTime.now())
                .build();

        beerMap.put(beer1.getId(),beer1);
        beerMap.put(beer2.getId(),beer2);
        beerMap.put(beer3.getId(),beer3);

    }


    @Override
    public Page<BeerDTO> listBeers(String beerName, BeerStyle beerStyle, Boolean showInventory, Integer pageNumber, Integer pageSize) {
        return new PageImpl<>(new ArrayList<>(beerMap.values()));
    }

    @Override
    public Optional<BeerDTO> getBeerById(UUID id) {
        log.debug("Get beer id in service. ID: "+ id.toString());

        return Optional.of(beerMap.get(id));
    }

    @Override
    public BeerDTO saveNewBeer(BeerDTO beer) {
        BeerDTO savedBeer = BeerDTO.builder()
                .id(UUID.randomUUID())
                .createdDate(LocalDateTime.now())
                .updateDate(LocalDateTime.now())
                .beerName(beer.getBeerName())
                .beerStyle(beer.getBeerStyle())
                .quantityOnHand(beer.getQuantityOnHand())
                .upc(beer.getUpc())
                .price(beer.getPrice())
                .build();

        beerMap.put(savedBeer.getId(),savedBeer);

        return savedBeer;
    }

    @Override
    public void updateBeerById(UUID beerId, BeerDTO beer) {

        BeerDTO existingBeer = beerMap.get(beerId);
        BeerDTO existing = beerMap. get (beerId);
        existing.setBeerName(beer.getBeerName());
        existing.setPrice(beer.getPrice());
        existing.setUpc(beer.getUpc());
        existing.setQuantityOnHand(beer.getQuantityOnHand());

         beerMap.put(existingBeer.getId(),existingBeer);
    }

    @Override
    public void deleteBeer(UUID beerId) {
        beerMap.remove(beerId);
    }

    @Override
    public void patchBeerById(UUID beerId, BeerDTO beer) {
        BeerDTO existingBeer = beerMap.get(beerId);

        if(StringUtils.hasText(beer.getBeerName()))
            existingBeer.setBeerName(beer.getBeerName());
        if(beer.getBeerStyle() != null)
            existingBeer.setBeerStyle(beer.getBeerStyle());
        if(beer.getPrice() != null)
            existingBeer.setPrice(beer.getPrice());
        if(beer.getQuantityOnHand() != null)
            existingBeer.setQuantityOnHand(beer.getQuantityOnHand());
        if(StringUtils.hasText(beer.getUpc()))
            existingBeer.setUpc(beer.getUpc());
    }
}