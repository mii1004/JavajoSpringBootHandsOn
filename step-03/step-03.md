# Step3 APIを実装する

## `GET /memos` を実装する
### 1. レスポンスDTOを作成する
`src/main/java/com/example/memo/dto` ディレクトリに `MemoResponse.java` ファイルを作成し、`MemoResponse` レコードクラスを定義します。
```java
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
```

### 2. serviceクラスを作成する
`src/main/java/com/example/memo/service` ディレクトリに `MemoService.java` ファイルを作成し、`MemoService` クラスを定義します。

```java
package com.example.memo.service;

public class MemoService {
}
```

`MemoService`クラスをDIコンテナに登録するため `@Service` アノテーションを付与します。

```java
package com.example.memo.service;

import org.springframework.stereotype.Service;

@Service
public class MemoService {
}
```

`MemoRepository` および `UserRepository` のフィールドを定義し、コンストラクタでDIコンテナからオブジェクトを受け取って代入します。

```java
package com.example.memo.service;

import com.example.memo.repository.MemoRepository;
import com.example.memo.repository.UserRepository;
import org.springframework.stereotype.Service;

@Service
public class MemoService {
    
    private final MemoRepository memoRepository;
    private final UserRepository userRepository;

    // コンストラクタが1つなので @Autowired は省略
    public MemoService(MemoRepository memoRepository, UserRepository userRepository) {
        this.memoRepository = memoRepository;
        this.userRepository = userRepository;
    }
    
}
```

### 3. serviceクラスのメソッドを実装する
`MemoRepository` からメモの一覧を取得し、`UserRepository` から取得したユーザー情報と合わせて返却する `getMemos` メソッドを実装します。

```java
package com.example.memo.service;

import com.example.memo.dto.MemoResponse;
import com.example.memo.entity.Memo;
import com.example.memo.entity.User;
import com.example.memo.repository.MemoRepository;
import com.example.memo.repository.UserRepository;
import org.springframework.stereotype.Service;

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
}

```

### 4. controllerを作成する
`src/main/java/com/example/memo/controller` ディレクトリに `MemoController.java` ファイルを作成し、`MemoController` クラスを定義します。

```java
package com.example.memo.controller;

public class MemoController {
}
```

`MemoController` クラスに `@RestController` アノテーションを付与します。  
`@RestController` を付与することで、HTTPリクエストを受け取ったり、メソッドの戻り値としてResponseBodyにあたる値を返却してJSONに変換してもらうことなどができます。

```java
package com.example.memo.controller;

import org.springframework.web.bind.annotation.RestController;

@RestController
public class MemoController {
}
```

`MemoService` のフィールドを定義し、コンストラクタでDIコンテナからオブジェクトを受け取って代入します。
```java
package com.example.memo.controller;

import com.example.memo.service.MemoService;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class MemoController {
    
    private final MemoService memoService;

    public MemoController(MemoService memoService) {
        this.memoService = memoService;
    }
    
}
```

### 5. controllerクラスのメソッドを実装する
`GET /memos` に対応するメソッドを実装し、先ほど作成したserviceクラスのメソッドを呼び出します。  
`@GetMapping("/memos")` のアノテーションを付与することで、 `/memos` へのGETリクエストがあった場合に本メソッドが呼び出されるようになります。

```java
package com.example.memo.controller;

import com.example.memo.dto.MemoResponse;
import com.example.memo.service.MemoService;
import org.springframework.web.bind.annotation.GetMapping;
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
}
```

### 6. `GET /memos` を実行する
Spring Bootアプリケーションを起動し、ブラウザまたはターミナル等からAPIを実行します。
- ブラウザの場合 `http://localhost:8080/memos`
- curlコマンドの場合 `$ curl -X 'GET' 'http://localhost:8080/memos'`

確認できたらアプリケーションを終了しておきます。

## `POST /memos` を実装する

### 1. リクエストDTOを作成する
`src/main/java/com/example/memo/dto` ディレクトリに `MemoRequest.java` ファイルを作成し、`MemoRequest` レコードクラスを定義します。
```java
package com.example.memo.dto;

public record MemoRequest(String body, Long userId) {
}
```

### 2. serviceクラスのメソッドを実装する
`MemoService` クラスに、引数で受け取った `MemoRequest` をもとに `memo` テーブルにレコードを登録するメソッドを実装します。

```java
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
```

### 3. controllerクラスのメソッドを実装する
`MemoController` クラスに、`POST /memos` に対応するメソッドを実装します。  
引数の `MemoRequest` に `@RequestBody` アノテーションを付与し、HTTPリクエストのRequestBodyを `MemoRequest` にマッピングして受け取ります。
```java
@PostMapping("/memos")
public MemoResponse createMemo(@RequestBody MemoRequest request) {
    return memoService.createMemo(request);
}
```

### 6. `POST /memos` を実行する
Spring Bootアプリケーションを起動し、ターミナル等からAPIを実行します。
- curlコマンドの場合
    ```
    $ curl -X 'POST' \
      'http://localhost:8080/memos' \
      -H 'Content-Type: application/json' \
      -d '{
      "body": "メモ本文を入力",
      "userId": 1
    }'
    ```

確認できたらアプリケーションを終了しておきます。
