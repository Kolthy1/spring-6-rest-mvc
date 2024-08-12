package kolthy.springframework.spring6restmvc.services;

import kolthy.springframework.spring6restmvc.entities.Beer;
import kolthy.springframework.spring6restmvc.mappers.BeerMapper;
import kolthy.springframework.spring6restmvc.model.BeerDTO;
import kolthy.springframework.spring6restmvc.model.BeerStyle;
import kolthy.springframework.spring6restmvc.repositories.BeerRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Primary;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@Primary
@RequiredArgsConstructor
public class BeerServiceJPA implements BeerService {
    private final BeerRepository beerRepository;
    private final BeerMapper beerMapper;

    private static final int DEFAULT_PAGE = 0;
    private static final int DEFAULT_PAGE_SIZE = 25;

    //Aşağıdaki 2 fonksiyonda ne yaptığını anlamadım???
    @Override
    public Page<BeerDTO> listBeers(String beerName, BeerStyle beerStyle, Boolean showInventory, Integer pageNumber, Integer pageSize) {

        PageRequest pageRequest = buildPageRequest(pageNumber,pageSize);

        Page<Beer> beerPage;

        if(StringUtils.hasText(beerName) && beerStyle == null) {
            beerPage = listBeersByName(beerName,pageRequest);
        } else if(!StringUtils.hasText(beerName) && beerStyle !=null) {
            beerPage = listBeersByStyle(beerStyle, pageRequest);
        } else if(StringUtils.hasText(beerName) && beerStyle !=null) {
            beerPage = listBeersByNameAndStyle(beerName,beerStyle,pageRequest);
        }
        else {
            beerPage = beerRepository.findAll(pageRequest);
        }
        if(showInventory != null && !showInventory) {
            beerPage.forEach(beer -> beer.setQuantityOnHand(null));
        }
        return beerPage.map(beerMapper::beerToBeerDto);
    }

    public PageRequest buildPageRequest(Integer pageNumber, Integer pageSize) {
        int queryPageNumber;
        int queryPageSize;

        if(pageNumber != null && pageNumber > 0)
            queryPageNumber = pageNumber - 1;
        else
            queryPageNumber = DEFAULT_PAGE;
        if(pageSize == null)
            queryPageSize = DEFAULT_PAGE_SIZE;
        else {
            if(pageSize > 1000)
                queryPageSize = 1000;
            else
                queryPageSize = pageSize;
        }
        Sort sort = Sort.by(Sort.Order.asc("beerName"));

        return PageRequest.of(queryPageNumber,queryPageSize,sort);
    }

    private Page<Beer> listBeersByNameAndStyle(String beerName, BeerStyle beerStyle, Pageable pageable) {
        return beerRepository.findAllByBeerNameIsLikeIgnoreCaseAndBeerStyle("%"+beerName+"%", beerStyle, pageable);
    }


    public Page<Beer> listBeersByName(String beerName, Pageable pageable) {
        return beerRepository.findAllByBeerNameIsLikeIgnoreCase("%" + beerName + "%", pageable);
    }

    public Page<Beer> listBeersByStyle(BeerStyle beerStyle, Pageable pageable) {
        return beerRepository.findAllByBeerStyle(beerStyle, pageable);
    }

    @Override
    public Optional<BeerDTO> getBeerById(UUID id) {
        return Optional.ofNullable(beerMapper.beerToBeerDto(beerRepository.findById(id).orElse(null)));
    }

    @Override
    public BeerDTO saveNewBeer(BeerDTO beer) {
        return beerMapper.beerToBeerDto(beerRepository.save(beerMapper.beerDtoToBeer(beer)));
    }

    @Override
    public void updateBeerById(UUID beerId, BeerDTO beer) {
        beerRepository.findById(beerId).ifPresent(foundBeer->{
            foundBeer.setBeerName(beer.getBeerName());
            foundBeer.setBeerStyle(beer.getBeerStyle());
            foundBeer.setUpc(beer.getUpc());
            foundBeer.setPrice(beer.getPrice());
            beerRepository.save(foundBeer);
        });
    }

    @Override
    public void deleteBeer(UUID beerId) {
        beerRepository.deleteById(beerId);
    }

    @Override
    public void patchBeerById(UUID beerId, BeerDTO beer) {

    }
}
