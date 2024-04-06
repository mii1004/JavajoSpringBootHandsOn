package com.example.memo.repository;

import com.example.memo.entity.Memo;
import org.springframework.data.repository.CrudRepository;

public interface MemoRepository extends CrudRepository<Memo, Long> {
}
