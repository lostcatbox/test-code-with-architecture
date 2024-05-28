package com.example.demo.common.util.impl;

import com.example.demo.common.util.TimeUtil;
import org.springframework.stereotype.Component;

@Component
public class TimeUtilImpl implements TimeUtil {

    @Override
    public long thisTime() {
        return System.currentTimeMillis();
    }
}
