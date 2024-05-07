package com.example.demo.common.infrastructure;

import com.example.demo.common.service.port.ClockHolder;

import java.time.Clock;

public class SystemClockHolder implements ClockHolder {

    @Override
    public long millis() {
        return Clock.systemUTC().millis();
    };

}
