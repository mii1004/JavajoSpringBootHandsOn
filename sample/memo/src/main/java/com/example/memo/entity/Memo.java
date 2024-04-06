package com.example.memo.entity;

import org.springframework.data.annotation.Id;
import java.time.LocalDateTime;

public record Memo(@Id Long id,
                   String body,
                   Long user_id,
                   LocalDateTime created_at,
                   LocalDateTime updated_at) {
}