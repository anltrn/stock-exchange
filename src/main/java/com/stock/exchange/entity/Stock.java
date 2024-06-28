package com.stock.exchange.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.UpdateTimestamp;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@Entity
@Table(name = "STOCK", indexes = {   @Index(name = "idx_stock_name", columnList = "name") })
@AllArgsConstructor
@NoArgsConstructor
public class Stock {
    @Id
    @Column
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String name;

    @Column(nullable = false)
    private String description;

    @Column
    private BigDecimal currentPrice;

    @UpdateTimestamp
    private LocalDateTime lastUpdate;

    @ManyToMany(mappedBy = "stocks")
    @JsonIgnoreProperties("stocks")
    private Set<StockExchange> stockExchanges = new HashSet<>();

    public void addToStockExchange(StockExchange stockExchange) {
        this.stockExchanges.add(stockExchange);
        stockExchange.getStocks().add(this);
    }

    public void removeFromStockExchange(StockExchange stockExchange) {
        this.stockExchanges.remove(stockExchange);
        stockExchange.getStocks().remove(this);
    }

    @PreRemove
    private void removeStocksFromExchanges() {
        for (StockExchange stockExchange : stockExchanges) {
            stockExchange.getStocks().remove(this);
        }
    }

}
