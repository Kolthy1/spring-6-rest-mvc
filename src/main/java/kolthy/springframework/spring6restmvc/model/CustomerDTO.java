package kolthy.springframework.spring6restmvc.model;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.UUID;
@Builder
@Data
public class CustomerDTO {
    private String customerName;
    private UUID id;
    private Float version;
    private LocalDateTime createdDate;
    private LocalDateTime lastModifiedDate;


}
