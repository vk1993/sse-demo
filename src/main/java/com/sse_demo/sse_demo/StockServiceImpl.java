package com.sse_demo.sse_demo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class StockServiceImpl implements StockService {

    @Autowired
    RestTemplate restTemplate;

    // scope: we can add caching pull
    @Override
    public StockData getStockData() {
        return restTemplate.getForObject("http://localhost:8080/stock", StockData.class);
    }
}