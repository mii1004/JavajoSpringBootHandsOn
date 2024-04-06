package com.example.memo.dto;

import java.time.LocalDateTime;

public record MemoResponse(Long id,
                           LocalDateTime createdAt,
                           String body,
                           User user) {

    public record User(
            Long id,
            String emailAddress,
            String userName) {
    }
}
