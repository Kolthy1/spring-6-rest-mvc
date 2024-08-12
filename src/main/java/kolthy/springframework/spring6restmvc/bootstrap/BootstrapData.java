package kolthy.springframework.spring6restmvc.bootstrap;

import kolthy.springframework.spring6restmvc.entities.Beer;
import kolthy.springframework.spring6restmvc.entities.Customer;
import kolthy.springframework.spring6restmvc.model.BeerCSVRecord;
import kolthy.springframework.spring6restmvc.model.BeerDTO;
import kolthy.springframework.spring6restmvc.model.BeerStyle;
import kolthy.springframework.spring6restmvc.model.CustomerDTO;
import kolthy.springframework.spring6restmvc.repositories.BeerRepository;
import kolthy.springframework.spring6restmvc.repositories.CustomerRepository;
import kolthy.springframework.spring6restmvc.services.BeerCsvService;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.ResourceUtils;

import java.io.File;
import java.io.FileNotFoundException;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;

@Transactional
@Component
@RequiredArgsConstructor
public class BootstrapData implements CommandLineRunner {
    private final BeerRepository beerRepository;
    private final CustomerRepository customerRepository;
    private final BeerCsvService beerCsvService;

    @Override
    public void run(String... args) throws Exception {
        loadBeerData();
        loadCsvData();
        loadCustomerData();
    }

    private void loadCsvData() throws FileNotFoundException {
        if (beerRepository.count() < 10){
            File file = ResourceUtils.getFile("classpath:csvdata/beers.csv");

            List<BeerCSVRecord> recs = beerCsvService.convertCSV(file);

            recs.forEach(beerCSVRecord -> {
                BeerStyle beerStyle = switch (beerCSVRecord.getStyle()) {
                    case "American Pale Lager" -> BeerStyle.LAGER;
                    case "American Pale Ale (APA)", "American Black Ale", "Belgian Dark Ale", "American Blonde Ale" ->
                            BeerStyle.ALE;
                    case "American IPA", "American Double / Imperial IPA", "Belgian IPA" -> BeerStyle.IPA;
                    case "American Porter" -> BeerStyle.PORTER;
                    case "Oatmeal Stout", "American Stout" -> BeerStyle.STOUT;
                    case "Saison / Farmhouse Ale" -> BeerStyle.SAISON;
                    case "Fruit / Vegetable Beer", "Winter Warmer", "Berliner Weissbier" -> BeerStyle.WHEAT;
                    case "English Pale Ale" -> BeerStyle.PALE_ALE;
                    default -> BeerStyle.PILSNER;
                };

                beerRepository.save(Beer.builder()
                        .beerName(StringUtils.abbreviate(beerCSVRecord.getBeer(), 50))
                        .beerStyle(beerStyle)
                        .price(BigDecimal.TEN)
                        .upc(beerCSVRecord.getRow().toString())
                        .quantityOnHand(beerCSVRecord.getCount())
                        .build());
            });
        }}

    private void loadBeerData() {
        if(beerRepository.count() == 0) {
            Beer beer1 = Beer.builder()
                    .beerName("Name")
                    .upc("12356")
                    .price(new BigDecimal(12))
                    .quantityOnHand(11)
                    .createdDate(LocalDateTime.now())
                    .updateDate(LocalDateTime.now())
                    .build();

            Beer beer2 = Beer.builder()
                    .beerName("Name")
                    .upc("12356")
                    .price(new BigDecimal(12))
                    .quantityOnHand(11)
                    .createdDate(LocalDateTime.now())
                    .updateDate(LocalDateTime.now())
                    .build();

            Beer beer3 = Beer.builder()
                    .beerName("Name")
                    .upc("12356")
                    .price(new BigDecimal(12))
                    .quantityOnHand(11)
                    .createdDate(LocalDateTime.now())
                    .updateDate(LocalDateTime.now())
                    .build();

            beerRepository.save(beer1);
            beerRepository.save(beer2);
            beerRepository.save(beer3);
        }
    }

    private void loadCustomerData() {
        if(customerRepository.count() == 0) {
            Customer customer1 = Customer.builder()
                    .customerName("John Doe")
                    .createdDate(LocalDateTime.now())
                    .lastModifiedDate(LocalDateTime.now())
                    .build();

            Customer customer2 = Customer.builder()
                    .customerName("Jane Smith")
                    .createdDate(LocalDateTime.now().minusDays(10))
                    .lastModifiedDate(LocalDateTime.now().minusDays(5))
                    .build();

            Customer customer3 = Customer.builder()
                    .customerName("Alice Johnson")
                    .createdDate(LocalDateTime.now().minusMonths(1))
                    .lastModifiedDate(LocalDateTime.now().minusDays(1))
                    .build();
            customerRepository.saveAll(Arrays.asList(customer1,customer2,customer3));
        }



    }
}
