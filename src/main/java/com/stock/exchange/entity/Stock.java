package com.stock.exchange.entity;

import jakarta.persistence.*;
import lombok.Data;
import org.hibernate.annotations.UpdateTimestamp;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@Entity
@Table(name ="STOCK")
public class Stock {
    @Id
    @Column
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private Long id;

    @Column
    private String name;

    @Column
    private String description;

    @Column
    private BigDecimal currentPrice;

    @UpdateTimestamp
    private LocalDateTime lastUpdate;
}
