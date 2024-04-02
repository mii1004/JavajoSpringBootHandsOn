# Step2 DBにアクセスする

## H2 Databaseを有効にする

### 1. `application.properties` に設定を追加する
`src/main/resources/application.properties`ファイルを開き、以下の設定を追加します。

```properties
spring.datasource.url=jdbc:h2:mem:testdb;DB_CLOSE_DELAY=-1;DB_CLOSE_ON_EXIT=FALSE
spring.datasource.driverClassName=org.h2.Driver
spring.datasource.username=sa
spring.datasource.password=password
spring.jpa.database-platform=org.hibernate.dialect.H2Dialect

spring.h2.console.enabled=true
spring.h2.console.path=/h2-console
```

### 2. SQLファイルを追加する
`src/main/resources` に、 `schema.sql` および `data.sql` を追加します。

### 3. H2 Consoleに接続する
Spring Bootアプリケーションを起動し、ブラウザから `http://localhost:8080/h2-console` にアクセスします。  
`application.properties` で設定したログイン情報を入力してDBに接続し、データが参照できることを確認します。  
確認できたらアプリケーションを終了しておきます。

## repositoryを実装する

### 1. entityを作成する
`src/main/java/com/example/memo/entity` ディレクトリに `Memo.java` および `User.java` ファイルを作成します。

```java
package com.example.memo.entity;

import org.springframework.data.annotation.Id;
import java.time.LocalDateTime;

public record Memo(@Id Long id,
                    String body,
                    Long user_id,
                    LocalDateTime created_at,
                    LocalDateTime updated_at) {
}
```

```java
package com.example.memo.entity;

import org.springframework.data.annotation.Id;

public record User(@Id Long id,
                    String name,
                    String emailAddress) {
}
```

### 2. repositoryを作成する
`src/main/java/com/example/demo/repository` ディレクトリに `MemoRepository.java` および `UserRepository.java` ファイルを作成します。

```java
package com.example.demo.repository;

import com.example.demo.entity.Memo;
import org.springframework.data.repository.CrudRepository;

public interface MemoRepository extends CrudRepository<Memo, Long> {
}
```

```java
package com.example.demo.repository;

import com.example.demo.entity.User;
import org.springframework.data.repository.CrudRepository;

public interface UserRepository extends CrudRepository<User, Long> {
}
```
