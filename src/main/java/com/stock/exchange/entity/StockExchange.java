package com.stock.exchange.entity;

import jakarta.persistence.*;
import lombok.Data;
@Data
@Entity
@Table(name ="STOCK_EXCHANGE")
public class StockExchange {
    @Id
    @Column
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false,unique = true)
    private String name;

    @Column(nullable = false)
    private String description;

    @Column
    private boolean liveInMarket;
}
