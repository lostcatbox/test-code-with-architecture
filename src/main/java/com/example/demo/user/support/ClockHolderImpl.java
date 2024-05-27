package com.example.demo.user.support;

import java.time.Clock;

public class ClockHolderImpl implements ClockHolder {
    @Override
    public long millis() {
        return Clock.systemUTC().millis();
    }
}
