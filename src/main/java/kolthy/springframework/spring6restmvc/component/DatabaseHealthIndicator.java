package kolthy.springframework.spring6restmvc.component;

import kolthy.springframework.spring6restmvc.repositories.BeerRepository;
import kolthy.springframework.spring6restmvc.repositories.CustomerRepository;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.actuate.health.Health;
import org.springframework.boot.actuate.health.HealthIndicator;
import org.springframework.stereotype.Component;

@Component
@Getter
@Setter
public class DatabaseHealthIndicator implements HealthIndicator {
    private final BeerRepository beerRepository;
    private final CustomerRepository customerRepository;


    public DatabaseHealthIndicator(BeerRepository beerRepository, CustomerRepository customerRepository) {
        this.beerRepository = beerRepository;
        this.customerRepository = customerRepository;
    }

    @Override
    public Health health() {
        try {
            long beerCount = beerRepository.count();
            long customerCount = customerRepository.count();

            return Health.up()
                    .withDetail("Beer Repository", "Accessible")
                    .withDetail("Beer Count", beerCount)
                    .withDetail("Customer Repository", "Accessible")
                    .withDetail("Customer Count", customerCount)
                    .build();
        } catch (Exception e) {
            return Health.down()
                    .withDetail("Error", "Could not access repositories")
                    .withException(e)
                    .build();
        }
    }
}
