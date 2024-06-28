package com.stock.exchange;

import com.stock.exchange.dao.StockExchangeRepository;
import com.stock.exchange.dao.StockRepository;
import com.stock.exchange.entity.Stock;
import com.stock.exchange.entity.StockExchange;
import com.stock.exchange.model.StockCreateRequest;
import com.stock.exchange.model.StockUpdateRequest;
import com.stock.exchange.service.impl.StockServiceImpl;
import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.util.Optional;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class StockServiceImplTest {

    @Mock
    private StockRepository stockRepository;

    @Mock
    private StockExchangeRepository stockExchangeRepository;

    @InjectMocks
    private StockServiceImpl stockService;

    @Test
    @DisplayName("Should delete stock successfully when stock is found")
    public void testDeleteStock_WhenStockFound() {
        Stock stock = new Stock();
        stock.setId(1L);
        StockExchange stockExchange = new StockExchange();
        stockExchange.setName("BIST");
        stock.setStockExchanges(Set.of(stockExchange));

        when(stockRepository.findById(1L)).thenReturn(Optional.of(stock));

        stockService.deleteStock(1L);

        // Verify
        verify(stockRepository, times(1)).findById(1L);
        verify(stockRepository, times(1)).deleteById(1L);
        verify(stockExchangeRepository, times(1)).saveAll(Set.of(stockExchange));
    }

    @Test
    @DisplayName("Should throw EntityNotFoundException when stock is not found")
    public void testDeleteStock_WhenStockNotFound() {
        when(stockRepository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(EntityNotFoundException.class, () -> stockService.deleteStock(1L));

        // Verify
        verify(stockRepository, times(1)).findById(1L);
        verify(stockRepository, never()).deleteById(anyLong());
        verify(stockExchangeRepository, never()).saveAll(any());
    }

    @Test
    @DisplayName("Should update stock price successfully when stock is found")
    public void testUpdateStockPrice_WhenStockFound() {
        StockUpdateRequest request = new StockUpdateRequest();
        request.setId(1L);
        request.setPrice(BigDecimal.valueOf(150.0));

        Stock stock = new Stock();
        stock.setId(1L);

        when(stockRepository.findById(1L)).thenReturn(Optional.of(stock));

        stockService.updateStockPrice(request);

        // Verify
        verify(stockRepository, times(1)).findById(1L);
        verify(stockRepository, times(1)).save(stock);
    }

    @Test
    @DisplayName("Should throw EntityNotFoundException when stock is not found for update")
    public void testUpdateStockPrice_WhenStockNotFound() {
        StockUpdateRequest request = new StockUpdateRequest();
        request.setId(1L);
        request.setPrice(BigDecimal.valueOf(150.0));

        when(stockRepository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(EntityNotFoundException.class, () -> stockService.updateStockPrice(request));

        // Verify
        verify(stockRepository, times(1)).findById(1L);
        verify(stockRepository, never()).save(any());
    }

    @Test
    @DisplayName("Should create stock successfully")
    public void testCreateStock() {
        StockCreateRequest request = new StockCreateRequest();
        request.setName("KOCHL");
        request.setDescription("KOC Holding Inc.");
        request.setCurrentPrice(BigDecimal.valueOf(122));

        Stock savedStock = new Stock();
        savedStock.setId(1L);
        savedStock.setName(request.getName());
        savedStock.setDescription(request.getDescription());
        savedStock.setCurrentPrice(request.getCurrentPrice());

        when(stockRepository.save(any(Stock.class))).thenReturn(savedStock);

        Stock result = stockService.createStock(request);

        assertNotNull(result);
        assertEquals(1L, result.getId());
        assertEquals("KOCHL", result.getName());
        assertEquals("KOC Holding Inc.", result.getDescription());
        assertEquals(BigDecimal.valueOf(122), result.getCurrentPrice());

        // Verify
        verify(stockRepository, times(1)).save(any(Stock.class));
    }
}
