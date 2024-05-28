package com.example.demo.common.util.impl;

import com.example.demo.common.util.UUidCreate;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
public class UUidCreateImpl implements UUidCreate {
    @Override
    public String random() {
        return UUID.randomUUID().toString();
    }
}
