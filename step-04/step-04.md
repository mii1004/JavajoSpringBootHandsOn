# Step4 テストを実行する

## 1. テストクラスを作成する
`src/test/java/com/example/memo/controller` ディレクトリに `MemoControllerTest.java` ファイルを作成します。

```java
package com.example.memo.controller;

class MemoControllerTest {
}
```

今回はcontroller, service, repositoryを通したテストを作成しようと思うので、アプリケーション起動時と同様にDIコンテナが使用できるよう、`@SpringBootTest` のアノテーションを付与します。

```java
package com.example.memo.controller;

import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class MemoControllerTest {
}
```

## 2. MockMvcの設定をする

テスト内で擬似的にHTTPリクエストを送るためにMockMvcという仕組みを使いたいので、 `@AutoConfigureMockMvc` のアノテーションを付与します。  
`MockMvc` のフィールドを定義して `@Autowired` を付与し、生成されたMockMvcオブジェクトをインジェクションします。

```java
package com.example.memo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;

@SpringBootTest
@AutoConfigureMockMvc
class MemoControllerTest {

    @Autowired
    private MockMvc mockMvc;
  
}
```

## 3. テストデータを作成する
`src/test/resources` ディレクトリに [`MemoControllerTest.sql`](MemoControllerTest.sql) ファイルを追加します。


テストの実行時にDBにテストデータがInsertされるよう、 `@Sql` アノテーションを付与します。  
また、テストメソッドの完了時にDBの状態をロールバックしたいので、 `@Transactional` アノテーションを付与します。
```java
package com.example.memo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

@SpringBootTest
@AutoConfigureMockMvc
@Sql("/MemoControllerTest.sql")
@Transactional
class MemoControllerTest {
    
    @Autowired
    private MockMvc mockMvc;
    
}
```

## 4. テストメソッドを実装する
MockMvcを使用してリクエストを送信し、レスポンスを検証するテストメソッドを実装します。

```java
package com.example.memo.controller;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.hamcrest.Matchers.*;

@SpringBootTest
@AutoConfigureMockMvc
@Sql("/MemoControllerTest.sql")
@Transactional
class MemoControllerTest {
    @Autowired
    private MockMvc mockMvc;
    
    @Test
    void getAllMemosTest() throws Exception {
        mockMvc.perform(get("/memos"))
              .andExpect(status().isOk())
              .andExpect(jsonPath("$", hasSize(2)))
              .andExpect(jsonPath("$[0].id", is(1)))
              .andExpect(jsonPath("$[0].body", is("takoyaki")))
              .andExpect(jsonPath("$[0].user.userName", is("tanaka")))
              .andExpect(jsonPath("$[0].user.emailAddress", is("tanaka@example.com")))
              .andExpect(jsonPath("$[1].id", is(2)))
              .andExpect(jsonPath("$[1].body", is("okonomiyaki")))
              .andExpect(jsonPath("$[1].user.userName", is("sato")))
              .andExpect(jsonPath("$[1].user.emailAddress", is("sato@example.com")));
    }
    
    @Test
    void createMemoTest() throws Exception {
        var request = """
              {
                "body": "dasimaki",
                "userId": 1
              }
              """;
        mockMvc.perform(post("/memos")
                      .contentType(MediaType.APPLICATION_JSON)
                      .content(request))
              .andExpect(status().isOk())
              .andExpect(jsonPath("$.body", is("dasimaki")))
              .andExpect(jsonPath("$.user.userName", is("tanaka")))
              .andExpect(jsonPath("$.user.emailAddress", is("tanaka@example.com")));
    }
}
```

## 4. テストを実行する
IDEまたはコマンドラインからテストを実行します。

- Mac, Linuxの場合: `$ ./gradlew test --tests "com.example.memo.controller.MemoControllerTest"`
- Windowsの場合: `> gradlew.bat test --tests "com.example.memo.controller.MemoControllerTest"`
