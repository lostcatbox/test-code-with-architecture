package com.example.demo.user.support;

import java.util.UUID;

public class UUIDHolderImpl implements UUIDHolder {
    @Override
    public String randomUUID() {
        return UUID.randomUUID().toString();
    }
}
