package com.example.memo.controller;

import com.example.memo.dto.MemoRequest;
import com.example.memo.dto.MemoResponse;
import com.example.memo.service.MemoService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class MemoController {

    private final MemoService memoService;

    public MemoController(MemoService memoService) {
        this.memoService = memoService;
    }

    @GetMapping("/memos")
    public List<MemoResponse> getAllMemos() {
        return memoService.getMemos();
    }

    @PostMapping("/memos")
    public MemoResponse createMemo(@RequestBody MemoRequest request) {
        return memoService.createMemo(request);
    }
}
