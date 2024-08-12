package kolthy.springframework.spring6restmvc.mappers;

import kolthy.springframework.spring6restmvc.entities.Beer;
import kolthy.springframework.spring6restmvc.model.BeerDTO;
import org.mapstruct.Mapper;

import java.util.Optional;

@Mapper
public interface BeerMapper {
    Beer beerDtoToBeer(BeerDTO beerDTO);
    BeerDTO beerToBeerDto(Beer beer);
}
