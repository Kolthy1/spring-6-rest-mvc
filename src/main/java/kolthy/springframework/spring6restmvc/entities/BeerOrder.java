package kolthy.springframework.spring6restmvc.entities;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.annotations.UpdateTimestamp;
import org.hibernate.type.SqlTypes;

import java.sql.Timestamp;
import java.util.Set;
import java.util.UUID;

@Entity
@Getter
@Setter
@NoArgsConstructor
@Builder
public class BeerOrder {

    @Id
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(name = "UUID", strategy = "org.hibernate.id.UUIDGenerator")
    @JdbcTypeCode(SqlTypes.CHAR)
    @Column(length = 36, columnDefinition = "varchar(36)",updatable = false,nullable = false)
    private UUID id;

    @Version
    private Long version;

    @CreationTimestamp
    @Column(updatable = false)
    private Timestamp createdDate;

    @UpdateTimestamp
    private Timestamp lastModifiedDate;

    public BeerOrder(UUID id, Long version, Timestamp createdDate, Timestamp lastModifiedDate, String customerRef, Set<BeerOrderLine> beerOrderLines, Customer customer,BeerOrderShipment beerOrderShipment) {
        this.id = id;
        this.version = version;
        this.createdDate = createdDate;
        this.lastModifiedDate = lastModifiedDate;
        this.customerRef = customerRef;
        this.beerOrderLines = beerOrderLines;
        this.setCustomer(customer);
        this.beerOrderShipment = beerOrderShipment;
    }

    public boolean isNew() {
        return (this.id == null);
    }

    private String customerRef;

    @OneToMany(mappedBy = "beerOrder")
    private Set<BeerOrderLine> beerOrderLines;

    public BeerOrder(Customer customer) {
        this.customer = customer;
        customer.getBeerOrders().add(this);
    }

    @ManyToOne
    private Customer customer;


    //Bu cascade kısmının da ne olduğunu anlamadım???
    @OneToOne(cascade = CascadeType.PERSIST)
    BeerOrderShipment beerOrderShipment;

    //Bu 2 methodu da anlamadım ???
    public void setCustomer(Customer customer) {
        this.customer = customer;
        customer.getBeerOrders().add(this);
    }
    public void setBeerOrderShipment(BeerOrderShipment beerOrderShipment) {
        this.beerOrderShipment = beerOrderShipment;
        beerOrderShipment.setBeerOrder(this);
    }

}
