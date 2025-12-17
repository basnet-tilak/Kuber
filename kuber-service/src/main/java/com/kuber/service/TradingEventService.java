package com.kuber.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class TradingEventService {
    
    private final KafkaTemplate<String, Object> kafkaTemplate;
    
    public void publishTradingEvent(String topic, Object event) {
        try {
            kafkaTemplate.send(topic, event);
            log.info("Published trading event to topic: {}", topic);
        } catch (Exception e) {
            log.error("Failed to publish trading event to topic: {}", topic, e);
        }
    }
    
    public void publishOrderEvent(Object orderEvent) {
        publishTradingEvent("trading.orders", orderEvent);
    }
    
    public void publishTradeEvent(Object tradeEvent) {
        publishTradingEvent("trading.trades", tradeEvent);
    }
}