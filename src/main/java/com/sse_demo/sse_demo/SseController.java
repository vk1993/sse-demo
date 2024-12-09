package com.sse_demo.sse_demo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicBoolean;

@RestController
public class SseController {

    private final ExecutorService executorService = Executors.newFixedThreadPool(10); // Adjust thread pool size as needed

    @Autowired
    private StockService stockService; // Inject your StockService here

    public SseController(StockService stockService) {
        this.stockService = stockService;
    }

    @GetMapping(value = "/stream", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    public SseEmitter stream() {
        SseEmitter emitter = new SseEmitter(Long.MAX_VALUE);
        AtomicBoolean isComplete = new AtomicBoolean(false);

        executorService.execute(() -> {
            try {
                while (!isComplete.get()) {
                    // Fetch stock data from cache or database
                    StockData stockData = stockService.getStockData();
                    System.out.println("send new events");
                    emitter.send(stockData);
                }
            } catch (Exception e) {
                emitter.completeWithError(e);
                isComplete.set(true);
            } finally {
                emitter.complete();
                isComplete.set(true);
            }
        });

        emitter.onCompletion(() -> isComplete.set(true));
        emitter.onTimeout(() -> isComplete.set(true));

        return emitter;
    }
}