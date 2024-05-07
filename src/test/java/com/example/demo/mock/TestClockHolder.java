package com.example.demo.mock;

import com.example.demo.common.service.port.ClockHolder;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
public class TestClockHolder implements ClockHolder {
    private final long testTime;
    @Override
    public long millis() {
        return testTime;
    }
}
