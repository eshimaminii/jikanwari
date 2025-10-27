# トップ｜私の時間割

このリポジトリは Java Servlet/JSP で実装された「時間割/予定管理」アプリです。DAO 層（`UsersDAO`/`EventsDAO`/`ColorDAO`）とサーブレット、JSP による典型的な MVC 構成になっています。

## 主な機能（ファイル名からの推定）
- 単発予定のCRUD / ログイン/ログアウト / メインメニュー/ダッシュボード / ユーザー登録（新規/確認/完了） / 週間予定の作成/確認/完了 / 予定カラー管理 / セッション管理（ログアウト）
- カラー選択（`ColorDAO`）
- JSTL を用いた繰り返しレンダリング（`<c:forEach>` など）
- `request.getContextPath()` による安全なリンク生成（検出回数: 43 箇所）

## プロジェクト構成（抜粋）
```
└─ jikanwari
   └─ src
      └─ main
         ├─ java
         │  ├─ dao
         │  │  ├─ ColorDAO.java
         │  │  ├─ DBManager.java
         │  │  ├─ EventsDAO.java
         │  │  ├─ UsersDAO.java
         │  │  └─ WeekdayDAO.java
         │  ├─ model
         │  │  ├─ Color.java
         │  │  ├─ Event.java
         │  │  ├─ User.java
         │  │  └─ Weekday.java
         │  ├─ service
         │  │  ├─ EventEditService.java
         │  │  ├─ EventService.java
         │  │  ├─ ScheduleDisplayService.java
         │  │  ├─ UserLoginService.java
         │  │  ├─ UserRegistrationService.java
         │  │  ├─ WeeklyDisplayService.java
         │  │  └─ WeeklyEventService.java
         │  └─ servlet
         │     ├─ EventAddServlet.java
         │     ├─ EventDeleteServlet.java
         │     ├─ EventEditServlet.java
         │     ├─ LoginServlet.java
         │     ├─ LogoutServlet.java
         │     ├─ MainServlet.java
         │     ├─ NewUserServlet.java
         │     ├─ WeeklyEventEditServlet.java
         │     ├─ WeeklyEventServlet.java
         │     └─ WelcomeServlet.java
         └─ webapp
            ├─ common
            │  ├─ footer.jsp
            │  ├─ header.jsp
            │  └─ header_plain.jsp
            ├─ css
            │  ├─ common.css
            │  └─ mainMenu.css
            ├─ images
            │  ├─ button_l.png
            │  ├─ button_m.png
            │  ├─ button_p.png
            │  ├─ button_s.png
            │  ├─ home.png
            │  └─ logout.png
            ├─ META-INF
            │  └─ MANIFEST.MF
            └─ WEB-INF
               ├─ jsp
               │  ├─ eventAddComplete.jsp
               │  ├─ eventAddConfirm.jsp
               │  ├─ eventAddForm.jsp
               │  ├─ eventDeleteComplete.jsp
               │  ├─ eventDeleteConfirm.jsp
               │  ├─ eventEditComplete.jsp
               │  ├─ eventEditConfirm.jsp
               │  ├─ eventEditForm.jsp
               │  ├─ index.jsp
               │  ├─ login.jsp
               │  ├─ loginComplete.jsp
               │  ├─ logout.jsp
               │  ├─ main.jsp
               │  ├─ newUserComplete.jsp
               │  ├─ newUserConfirm.jsp
               │  ├─ newUserForm.jsp
               │  ├─ weeklyEvent.jsp
               │  ├─ weeklyEventComplete.jsp
               │  ├─ weeklyEventConfirm.jsp
               │  └─ weeklyEventForm.jsp
               └─ lib
                  ├─ jakarta.servlet.jsp.jstl-3.0.1.jar
                  ├─ jakarta.servlet.jsp.jstl-api-3.0.0.jar
                  ├─ json-20250517.jar
                  ├─ mysql-connector-j-8.0.33.jar
                  └─ mysql-connector-java-8.0.23.jar
```

## サーブレット / ルーティング
- `EventAddServlet` → `/EventAddServlet` 〔jikanwari/src/main/java/servlet/EventAddServlet.java〕
- `EventDeleteServlet` → `/EventDeleteServlet` 〔jikanwari/src/main/java/servlet/EventDeleteServlet.java〕
- `EventEditServlet` → `/EventEditServlet` 〔jikanwari/src/main/java/servlet/EventEditServlet.java〕
- `LoginServlet` → `/LoginServlet` 〔jikanwari/src/main/java/servlet/LoginServlet.java〕
- `LogoutServlet` → `/LogoutServlet` 〔jikanwari/src/main/java/servlet/LogoutServlet.java〕
- `MainServlet` → `/MainServlet` 〔jikanwari/src/main/java/servlet/MainServlet.java〕
- `NewUserServlet` → `/NewUserServlet` 〔jikanwari/src/main/java/servlet/NewUserServlet.java〕
- `WeeklyEventEditServlet` → `/WeeklyEventEditServlet` 〔jikanwari/src/main/java/servlet/WeeklyEventEditServlet.java〕
- `WeeklyEventServlet` → `/WeeklyEventServlet` 〔jikanwari/src/main/java/servlet/WeeklyEventServlet.java〕
- `WelcomeServlet` → `/WelcomeServlet` 〔jikanwari/src/main/java/servlet/WelcomeServlet.java〕

## 画面（JSP）
- `jikanwari/src/main/webapp/WEB-INF/jsp/eventAddComplete.jsp`
- `jikanwari/src/main/webapp/WEB-INF/jsp/eventAddConfirm.jsp`
- `jikanwari/src/main/webapp/WEB-INF/jsp/eventAddForm.jsp`
- `jikanwari/src/main/webapp/WEB-INF/jsp/eventDeleteComplete.jsp`
- `jikanwari/src/main/webapp/WEB-INF/jsp/eventDeleteConfirm.jsp`
- `jikanwari/src/main/webapp/WEB-INF/jsp/eventEditComplete.jsp`
- `jikanwari/src/main/webapp/WEB-INF/jsp/eventEditConfirm.jsp`
- `jikanwari/src/main/webapp/WEB-INF/jsp/eventEditForm.jsp`
- `jikanwari/src/main/webapp/WEB-INF/jsp/index.jsp`
- `jikanwari/src/main/webapp/WEB-INF/jsp/login.jsp`
- `jikanwari/src/main/webapp/WEB-INF/jsp/loginComplete.jsp`
- `jikanwari/src/main/webapp/WEB-INF/jsp/logout.jsp`
- `jikanwari/src/main/webapp/WEB-INF/jsp/main.jsp`
- `jikanwari/src/main/webapp/WEB-INF/jsp/newUserComplete.jsp`
- `jikanwari/src/main/webapp/WEB-INF/jsp/newUserConfirm.jsp`
- `jikanwari/src/main/webapp/WEB-INF/jsp/newUserForm.jsp`
- `jikanwari/src/main/webapp/WEB-INF/jsp/weeklyEvent.jsp`
- `jikanwari/src/main/webapp/WEB-INF/jsp/weeklyEventComplete.jsp`
- `jikanwari/src/main/webapp/WEB-INF/jsp/weeklyEventConfirm.jsp`
- `jikanwari/src/main/webapp/WEB-INF/jsp/weeklyEventForm.jsp`
- `jikanwari/src/main/webapp/common/footer.jsp`
- `jikanwari/src/main/webapp/common/header.jsp`
- `jikanwari/src/main/webapp/common/header_plain.jsp`

## スタイル（CSS）
- `jikanwari/src/main/webapp/css/common.css`
- `jikanwari/src/main/webapp/css/mainMenu.css`

## JavaScript
_（JS は検出できませんでした）_

## SQL/DDL
_（SQL は検出できませんでした）_

## 使用テーブル（DAOのSQLから推定）
- **colors**: color, color_id 〔jikanwari/src/main/java/dao/ColorDAO.java〕
- **event_weekdays**: event_id, weekday_id 〔jikanwari/src/main/java/dao/EventsDAO.java〕
- **events**: color_id, date, delete_flag, description, e.event_id, e.title, event_id, repeat_flag, time, title, user_id, w.weekday 〔jikanwari/src/main/java/dao/EventsDAO.java〕
- **users**: birthday, name, password, user_id 〔jikanwari/src/main/java/dao/UsersDAO.java〕
- **weekdays**: weekday, weekday_id 〔jikanwari/src/main/java/dao/WeekdayDAO.java〕

## JSTL の使用
- 使用推定: あり
- `WEB-INF/lib` に JSTL JAR が同梱されているため、JSTL タグの利用が想定されます。

## 依存ライブラリ & 実行環境
### 実行環境
- Apache Tomcat 10 以降（Jakarta Servlet 5+ を想定）
- Java 17 推奨（実環境に合わせて調整）
- MySQL（`mysql-connector-j` が同梱されているため）

### 依存ライブラリ（同梱JAR）
_（`WEB-INF/lib` にJARは見当たりません）_

### ビルド/デプロイ
本プロジェクトは Maven/Gradle 定義が見当たりません（`pom.xml`/`build.gradle` 未検出）。Eclipse の *Dynamic Web Project* としてインポートし、Tomcat へデプロイしてください。

1. IDE で **File > Import > Existing Projects into Workspace** を選択し、`jikanwari` ディレクトリを指定
2. プロジェクトの **Java Build Path** で `src/main/webapp/WEB-INF/lib` の JAR をクラスパスに含める
3. **Servers** ビューから Tomcat を追加し、プロジェクトを *Add and Remove...* で割当
4. サーバを起動し、`http://localhost:8080/<コンテキスト名>/` にアクセス

### DB 設定
- ドライバ: com.mysql.cj.jdbc.Driver
- JDBC URL 検出: jdbc:mysql://localhost:3306/jikanwari?useSSL=false&serverTimezone=Asia/Tokyo

接続先は `DBManager` などのユーティリティで定義されています。環境変数や `context.xml` の `<Resource>` を経由する場合は、適宜変更してください。

## ライセンス
このプロジェクトには `LICENSE` が含まれていません。公開予定がある場合は、MIT などのライセンスファイルを追加してください。
