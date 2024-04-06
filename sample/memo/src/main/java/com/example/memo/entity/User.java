package com.example.memo.entity;

import org.springframework.data.annotation.Id;

public record User(@Id Long id,
                   String name,
                   String emailAddress) {
}