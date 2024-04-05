# Step1 Spring Bootプロジェクトを作成する

## Spring Initializrでのプロジェクト設定

### 1. Spring Initializrのウェブサイトにアクセス
[Spring Initializr](https://start.spring.io) を開きます。

### 2. プロジェクトの設定
下記の通りプロジェクトの詳細を設定します。

- **Project**: `Gradle - Groovy`
- **Language**: `Java`
- **Spring Boot**: `3.2.4`（デフォルトで最新が選択されているのでそのまま）
- **Project Metadata**:
  - **Group**: `com.example`
  - **Artifact**: `memo`
  - **Name**: （自動入力されるのでそのまま）
  - **Description**: 任意でプロジェクトの説明を入力
  - **Package name**: （自動入力されるのでそのまま）
- **Packaging**: `Jar`
- **Java**: `21`

### 3. 依存関係の追加
画面右上の`ADD DEPENDENCIES`ボタンをクリックし、下記のライブラリを追加します。

- `Spring Web`
- `Spring Data JDBC`
- `H2 Database`

### 4. プロジェクトの生成とダウンロード
設定が完了したら、画面下部の`GENERATE`ボタンをクリックしてプロジェクトを作成し、ダウンロードします。

## プロジェクトのセットアップと実行
### 1. プロジェクトの展開
ダウンロードしたzipファイルを展開し、IDEでプロジェクトを開きます。

### 2. ビルドの実行
IDEまたはコマンドラインからプロジェクトをビルドします。

- Mac, Linuxの場合: `$ ./gradlew build`
- Windowsの場合: `gradlew.bat build`

### 3. Spring Bootアプリケーションの起動
ビルドが成功したら、IDEまたはコマンドラインからSpring Bootアプリケーションを起動します。

- Mac, Linuxの場合: `$ ./gradlew bootRun`
- Windowsの場合: `> gradlew.bat bootRun`

### 4. アプリケーションの動作確認
ブラウザを開いて `http://localhost:8080` にアクセスし、404エラーが返ることを確認します。  
確認できたらアプリケーションを終了しておきます。
