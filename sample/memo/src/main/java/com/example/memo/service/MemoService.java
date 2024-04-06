package com.example.memo.service;

import com.example.memo.dto.MemoRequest;
import com.example.memo.dto.MemoResponse;
import com.example.memo.entity.Memo;
import com.example.memo.entity.User;
import com.example.memo.repository.MemoRepository;
import com.example.memo.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class MemoService {

    private final MemoRepository memoRepository;
    private final UserRepository userRepository;

    public MemoService(MemoRepository memoRepository, UserRepository userRepository) {
        this.memoRepository = memoRepository;
        this.userRepository = userRepository;
    }

    public List<MemoResponse> getMemos() {
        Iterable<Memo> memos = memoRepository.findAll();
        List<MemoResponse> response = new ArrayList<>();
        for (Memo memo : memos) {
            Optional<User> user = userRepository.findById(memo.user_id());
            response.add(new MemoResponse(
                    memo.id(),
                    memo.created_at(),
                    memo.body(),
                    user.map(u -> new MemoResponse.User(u.id(), u.emailAddress(), u.name())).orElse(null)
            ));
        }
        return response;
    }

    public MemoResponse createMemo(MemoRequest request) {
        Memo memo = memoRepository.save(new Memo(null,
                request.body(),
                request.userId(),
                LocalDateTime.now(),
                LocalDateTime.now()));

        Optional<User> user = userRepository.findById(request.userId());
        return new MemoResponse(
                memo.id(),
                memo.created_at(),
                memo.body(),
                user.map(u -> new MemoResponse.User(u.id(), u.emailAddress(), u.name())).orElse(null)
        );
    }
}
