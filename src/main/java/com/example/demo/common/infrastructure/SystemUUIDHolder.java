package com.example.demo.common.infrastructure;

import com.example.demo.common.service.port.UUIDHolder;

import java.util.UUID;

public class SystemUUIDHolder implements UUIDHolder {
    @Override
    public String randomUUID() {
        return UUID.randomUUID().toString();
    }
}
