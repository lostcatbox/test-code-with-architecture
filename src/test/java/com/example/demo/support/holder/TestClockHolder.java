package com.example.demo.support.holder;

import com.example.demo.common.service.port.ClockHolder;

public class TestClockHolder implements ClockHolder {
    private final long timeLong;

    public TestClockHolder(long timeLong) {
        this.timeLong = timeLong;
    }

    @Override
    public long millis() {
        return timeLong;
    }
}
