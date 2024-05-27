package com.example.demo.support.holder;

import com.example.demo.user.support.UUIDHolder;

public class TestUUIDHolder implements UUIDHolder {
    private final String uuid;

    public TestUUIDHolder(String uuid) {
        this.uuid = uuid;
    }

    @Override
    public String randomUUID() {
        return uuid;
    }
}
