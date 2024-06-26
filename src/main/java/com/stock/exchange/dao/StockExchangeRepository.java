package com.stock.exchange.dao;

import com.stock.exchange.entity.StockExchange;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface StockExchangeRepository extends JpaRepository<StockExchange,Long> {
}
