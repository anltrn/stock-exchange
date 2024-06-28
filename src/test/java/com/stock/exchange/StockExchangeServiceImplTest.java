package com.stock.exchange;

import com.stock.exchange.dao.StockExchangeRepository;
import com.stock.exchange.dao.StockRepository;
import com.stock.exchange.entity.Stock;
import com.stock.exchange.entity.StockExchange;
import com.stock.exchange.service.impl.StockExchangeServiceImpl;
import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class StockExchangeServiceImplTest {

    @Mock
    private StockExchangeRepository stockExchangeRepository;

    @Mock
    private StockRepository stockRepository;

    @InjectMocks
    private StockExchangeServiceImpl stockExchangeService;

    @Test
    @DisplayName("Should delete stock from exchange successfully when stock and exchange are found")
    public void testDeleteStockFromExchange_WhenStockAndExchangeFound() {
        Stock stock = new Stock();
        StockExchange stockExchange = new StockExchange();
        stockExchange.getStocks().add(stock);

        when(stockRepository.findByName("TAB")).thenReturn(Optional.of(stock));
        when(stockExchangeRepository.findByName("BIST")).thenReturn(Optional.of(stockExchange));

        stockExchangeService.deleteStockFromExchange("BIST", "TAB");

        assertTrue(stockExchange.getStocks().isEmpty());
        // Verify
        verify(stockRepository, times(1)).findByName("TAB");
        verify(stockExchangeRepository, times(1)).findByName("BIST");
        verify(stockExchangeRepository, times(1)).save(stockExchange);
    }

    @Test
    @DisplayName("Should add stock to exchange successfully when stock and exchange are found")
    public void testAddStockToExchange_WhenStockAndExchangeFound() {
        Stock stock = new Stock();
        stock.setDescription("Tupras as");
        stock.setName("TUPRS");
        stock.setCurrentPrice(BigDecimal.valueOf(100));
        StockExchange stockExchange = new StockExchange();

        when(stockRepository.findByName("TUPRS")).thenReturn(Optional.of(stock));
        when(stockExchangeRepository.findByName("BIST")).thenReturn(Optional.of(stockExchange));

        stockExchangeService.addStockToExchange("BIST", "TUPRS");

        assertTrue(stockExchange.getStocks().contains(stock));
        // Verify
        verify(stockRepository, times(1)).findByName("TUPRS");
        verify(stockExchangeRepository, times(1)).findByName("BIST");
        verify(stockExchangeRepository, times(1)).save(stockExchange);
    }

    @Test
    @DisplayName("Should get all stocks by exchange name successfully when exchange is found")
    public void testGetAllStocksByExchangeName_WhenExchangeFound() {
        StockExchange stockExchange = new StockExchange();
        stockExchange.setName("BIST");
        Set<Stock> stockList = new HashSet<>();
        Stock stock = new Stock();
        stock.setDescription("Tab gida");
        stock.setName("TAB");
        stock.setCurrentPrice(BigDecimal.valueOf(100));
        stockList.add(stock);
        stock = new Stock();
        stock.setDescription("Turkcell");
        stock.setName("TCELL");
        stock.setCurrentPrice(BigDecimal.valueOf(44));
        stockList.add(stock);
        stockExchange.setStocks(stockList);

        when(stockExchangeRepository.findByName("BIST")).thenReturn(Optional.of(stockExchange));

        StockExchange result = stockExchangeService.getAllStocksByExchangeName("BIST");

        assertNotNull(result);
        assertEquals(stockExchange, result);

        // Verify
        verify(stockExchangeRepository, times(1)).findByName("BIST");
    }

    @Test
    @DisplayName("Should throw EntityNotFoundException when exchange is not found for getting all stocks")
    public void testGetAllStocksByExchangeName_WhenExchangeNotFound() {
        when(stockExchangeRepository.findByName("DENEME")).thenReturn(Optional.empty());

        assertThrows(EntityNotFoundException.class, () -> stockExchangeService.getAllStocksByExchangeName("DENEME"));

        // Verify
        verify(stockExchangeRepository, times(1)).findByName("DENEME");
    }

    @Test
    @DisplayName("Should create stock exchange successfully")
    public void testCreateStockExchange() {
        StockExchange stockExchange = new StockExchange();
        stockExchange.setName("BIST");
        stockExchange.setDescription("Borsa Istanbul");

        when(stockExchangeRepository.save(stockExchange)).thenReturn(stockExchange);

        StockExchange result = stockExchangeService.createStockExchange(stockExchange);

        assertNotNull(result);
        assertEquals(stockExchange, result);

        // Verify
        verify(stockExchangeRepository, times(1)).save(stockExchange);
    }
}
