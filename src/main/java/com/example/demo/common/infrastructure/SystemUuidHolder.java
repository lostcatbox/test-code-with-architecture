package com.example.demo.common.infrastructure;

import java.util.UUID;

public class SystemUuidHolder implements com.example.demo.common.service.port.UuidHolder {
    @Override
    public String random() {
        return UUID.randomUUID().toString();
    }
}
