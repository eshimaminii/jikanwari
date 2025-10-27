# 🗓️ 私の時間割

**学校や日々の予定を管理できる JSP/Servlet ベースの時間割アプリケーション。**

## 🧭 概要
このアプリケーションは、**1日の予定を登録・編集・確認できる** Web アプリです。  
予定を**色分け**して可視化し、日常のスケジュールをわかりやすく管理できます。

### 主な機能
- 予定（タイトル・日時・メモ・色）の登録・編集・削除   
- ログイン・新規ユーザー登録機能  
- JavaScript によるインタラクティブな UI  
- 繰り返し予定・色分け管理  

## ⚙️ 開発環境
| 項目 | 使用技術 |
|------|-----------|
| 言語 | Java 17 |
| フレームワーク | JSP / Servlet |
| IDE | Eclipse |
| Web サーバ | Apache Tomcat 10 |
| データベース | MySQL |
| フロントエンド | HTML / CSS / JavaScript |

## 📁 プロジェクト構成
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
            │  ├─ logout.png
            │  └─ ER.png
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

## 📚 ドキュメント
Javadocを使用して自動生成したクラス仕様書を `doc/index.html` に含めています。

## 🗺️ ER図
![ER図](/ER.png)

## 💾 データベース設定
使用するデータベースは MySQL です。
以下の SQL を実行してデータベースとテーブルを作成してください。

```sql
CREATE DATABASE jikanwari;
USE jikanwari;
-- ユーザー情報テーブル
CREATE TABLE users (
    user_id VARCHAR(100) PRIMARY KEY, -- メールアドレス
    password VARCHAR(100),
    name VARCHAR(30),
    birthday DATE
);
-- カラー情報テーブル
CREATE TABLE colors (
    color_id VARCHAR(7) PRIMARY KEY,
    color VARCHAR(30)
);
INSERT INTO colors (color_id, color) VALUES
('#FFF700', 'イエロー'),
('#39FF14', 'グリーン'),
('#4666FF', 'ブルー'),
('#BC13FE', 'パープル'),
('#FF1493', 'ピンク');
-- イベント情報テーブル
CREATE TABLE events (
    event_id INT PRIMARY KEY,
    title VARCHAR(150),
    date DATE,
    time TIME,
    description VARCHAR(300),
    repeat_flag INT, -- 0:繰り返しなし, 1:繰り返しする
    color_id VARCHAR(7),
    delete_flag INT, -- 0:未削除, 1:削除
    user_id VARCHAR(100),
    minute INT,
    duration_minutes INT,
    FOREIGN KEY (color_id) REFERENCES colors(color_id),
    FOREIGN KEY (user_id) REFERENCES users(user_id)
);
-- 曜日テーブル
CREATE TABLE weekdays (
    weekday_id INT PRIMARY KEY,
    weekday VARCHAR(30)
);
INSERT INTO weekdays (weekday_id, weekday) VALUES
(1, '日曜日'),
(2, '月曜日'),
(3, '火曜日'),
(4, '水曜日'),
(5, '木曜日'),
(6, '金曜日'),
(7, '土曜日');
-- イベントと曜日の関連テーブル（繰り返し設定など）
CREATE TABLE event_weekdays (
    event_id INT,
    weekday_id INT,
    PRIMARY KEY (event_id, weekday_id),
    FOREIGN KEY (event_id) REFERENCES events(event_id),
    FOREIGN KEY (weekday_id) REFERENCES weekdays(weekday_id)
);
```

DB接続情報は `DBManager.java` 内で設定します。

```java
private static final String URL = "jdbc:mysql://localhost:3306/jikanwari";
private static final String USER = "root";
private static final String PASSWORD = "（あなたのパスワード）";
```

## 🚀 起動方法
1. Eclipse にプロジェクトをインポート  
   **[File] → [Import] → [Existing Projects into Workspace]**
2. Apache Tomcat を追加し、プロジェクトをデプロイ  
3. MySQL を起動し、上記の SQL を実行  
4. ブラウザでアクセス：  
   `http://localhost:8080/jikanwari`

## 🖥️ 画面構成
| 画面 | 説明 |
|------|------|
| ログインページ | ユーザー認証を行う |
| メインページ（main.jsp） | 当日の予定一覧を表示 |
| 予定登録ページ（eventAddForm.jsp） | 新規予定を登録 |
| 予定編集ページ（eventEditForm.jsp） | 登録済み予定を編集 |
| 週間表示ページ（weeklyEvent.jsp） | カレンダー形式で予定を確認 |

## 📦 使用ライブラリ
- jakarta.servlet.jsp.jstl-3.0.1.jar  
- jakarta.servlet.jsp.jstl-api-3.0.0.jar  
- mysql-connector-j-8.0.33.jar  
- json-20250517.jar  

## 🧠 今後の改善アイデア
- スマホ向けUIの最適化  
- 予定の通知設定  
- 色テーマのカスタマイズ  
- 予定検索・絞り込み機能  

## 👩‍💻 作者情報
Author: **eshimaminii**  
Language: Japanese / 日本語  

## 📝 ライセンス
本プロジェクトは学習・個人利用目的で自由に利用できます。  
商用利用の場合は、作者への連絡を推奨します。

