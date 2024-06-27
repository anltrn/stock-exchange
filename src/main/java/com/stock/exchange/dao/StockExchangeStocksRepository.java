package com.stock.exchange.dao;

import com.stock.exchange.entity.StockExchangeStocks;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;


@Repository
public interface StockExchangeStocksRepository extends JpaRepository<StockExchangeStocks,Long> {
    Optional<StockExchangeStocks> findByStockIdAndStockExchangeId(Long stockId, Long stockExchangeId);
    Optional<List<StockExchangeStocks>> findByStockExchangeId(Long stockExchangeId);
    @Query(value = "select stock_Id from stock_Exchange_stocks where stock_Exchange_Id = :stockExchangeId", nativeQuery = true)
    List<Long> findStockIdListByStockExchangeId(Long stockExchangeId);

    Optional<List<StockExchangeStocks>> findByStockId(Long stockId);
}