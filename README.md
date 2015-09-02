#JDBC Realm and Form Based Authentication with GlassFish 4.1 and Java EE 7

 - jdk1.8.0_60 
 - GlassFish Server 4.1
 - NetBeans IDE 8.0.2

##新規プロジェクト
新規プロジェクト -> Maven -> Web アプリケーション ->
 
プロジェクト名: jdbc-realm-form-auth -> グループID: monac

プロジェクト実行 -> HelloWorld!

##Maven の設定ファイルを書き換え

プロジェクト・ファイル -> pom.xml

artifactId の要素を javaee-web-api から javaee-api に変更

    <artifactId>javaee-api</artifactId>

Java EE 7 の Web アプリケーション内で Concurrency Utilities for
EE (Web Profile に含まれない機能)を利用できるようになる。

##JavaServer Faces の有効化

プロジェクト -> プロパティ -> フレームワーク追加 -> JavaServer Faces

web.xml と index.html が追加される

web.xml の `<session-timeout>` の値はデフォルトで 30分

新規プロジェクト作成時に自動生成された index.html は削除

##CDI の設定

新規 -> コンテキストと依存性 -> beans.xml (CDI 構成ファイル)

beans.xml の beans-discovery-mode の値を "annotated" から "all" に書き換え

これで、@Named 以外のアノテーションでもインジェクションできるようになる

##GlassFish のリクエスト・エンコーディングの修正

WEB-INF -> 新規 -> GlassFish -> GlassFish ディスクリプタ

glassfish-web.xml ファイル内に次の行を追加

    <parameter-encoding default-charset="UTF-8" />

##データベースにテーブルを作成

データベースを作成接続して次のSQL文を実行

    create table users (
    	username varchar(128) NOT NULL CONSTRAINT USERS_PK PRIMARY KEY,
    	email varchar(128) NOT NULL,
    	password varchar(128) NOT NULL
    );
    
    create table groups (
    	username varchar(128) NOT NULL,
    	groupid varchar(128) NOT NULL,
    	CONSTRAINT GROUPS_PK PRIMARY KEY(username, groupid),
    	CONSTRAINT USERS_FK FOREIGN KEY(username)
    	REFERENCES users(username) ON DELETE CASCADE ON UPDATE RESTRICT
    );

##JPA のエンティティを作成

データベースにユーザ情報、グループ情報を登録・削除するためにO/RマッピングのJava Persistence API を使用

データベースのテーブルから JPA の Entity クラスを自動作成

プロジェクト -> 新規 -> 持続性 -> データベースからのエンティティ・クラス

テーブルの中から GROUPS と USERS を追加

パッケージ名は 「（プロジェクト名）.entities」 で入力し「JAXB 注釈を生成
(J):」のチェックを外して実行

「Users.java」、「Groups.java」、「GroupsPK.java」の３つのエンティティクラスが自動生成される

##Enterprise JavaBeans の作成

ユーザを管理（登録・削除）するためのビジネスロジックを EJB で作成

新規 -> エンタープライズJavaBeans -> セッションBean

EJB 名を UserRegistManager にして、パッケージ名は 「（プロジェクト名）.ejb」と入力して作成

ユーザをDB に登録するためのビジネスロジックを実装したコードを書く

UserRegistManager にユーザ検索とユーザ削除を行なうためのロジックも追記する

##メッセージ・ダイジェスト生成器の作成

パスワードの内容は平文ではなく、メッセージ・ダイジェスト（ハッシュ値）で保存するため、ダイジェスト・アルゴリズムとしてSHA-256 を使う

SHA-256 のダイジェストを生成するためのユーティリティ・クラスを作成

新規 -> Java -> Javaクラス

クラス名は「SHA256Encoder」と入力し、パッケージ「（プロジェクト名）.DigestUtil」と入力して作成

ハッシュ値にエンコードするコードを実装する

##CDI (JSF 管理対象Bean)の作成

EJB のビジネス・ロジックを作成したので、JSF のバッキング・ビーンであるCDI を作成する

ここでは管理用の画面からユーザ名、メールアドレス、パスワード、グループ名が入力される事を想定し、それぞれのフィールドを用意し、入力された値からEJB のビジネス・ロジックを呼び出すコードを実装する

新規 -> JavaServer Faces -> JSF 管理対象Bean

クラス名(N)を「RegistPage」、パッケージ名は「（プロジェクト名）.cdis」で、スコープは「request」に設定して作成

ユーザ名、メールアドレス、パスワード、グループ名が画面から入力される事を想定し、４つのフィールドを用意

フィールドを追加した後、フィールドを隠蔽する

リファクタリング -> フィールドをカプセル化

「取得メソッドを作成」、「設定メソッドを作成」の全チェックボックスをチェックして getter/setter を自動生成

次に、EJB (UserRegistManager)のビジネスロジック(createUserAndGroup)を呼び出しユーザ登録するメソッドを追加

@EJB のアノテーションを付加し、UserRegistManager のEJB をインジェクション

Web ページから入力された各情報を取得し、createUserAndGroup()メソッドに値を渡す

同様に削除用のJSF 管理対象Bean も「RemovePage.java」として作成

##ユーザ管理用Web ページ(JSF Facelets)の作成

管理用のWeb ページは「user-manage」ディレクトリ配下にそれぞれ「regist.xhtml」、「remove.xhtml」を作成

新規 -> フォルダ -> user-manage

user-manage -> JavaServer Faces -> JSFページ

regist.xhtml、reg-success.xhtml、remove.xhtml、rem-success.xhtml をそれぞれ作成・実装

index.xhtml も登録削除ページに飛べるようにリンクを書く

##ユーザ登録・削除機能の試行

プロジェクト実行して、ユーザ登録画面へ

ここで、各フィールドに下記を入力し「ユーザ登録」ボタンを押下

ユーザ名: admin
メールアドレス: admin
パスワード: admin
所属グループ: admin

