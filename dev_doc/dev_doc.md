# 開発環境構築
### ディレクトリ作成
Cドライブ直下にjavaディレクトリを作成する  
作成したjavaディレクトリ配下にdevディレクトリを作成する

### JDKダウンロード、インストール
Java SE Development Kitの8系（Windows x64を選択）  
https://www.oracle.com/technetwork/java/javase/downloads/jdk8-downloads-2133151.html  
インストーラにてインストールを実行する

### STSダウンロード、インストール
https://spring.io/tools  
Spring Tools 4 for Eclipse（Windows x64を選択）  
zipファイルを解凍し、ディレクトリをstsとリネームしC:\java配下に格納する

### mavenダウンロード、インストール
https://maven.apache.org/download.cgi  
Files欄のBinary zip archive、Linkのzipをダウンロードする  
zipファイルを解凍し、ディレクトリをmavenとリネームしC:\java配下に格納する

パスを設定する  
システム環境変数に新規で以下を追加  
・変数名
```
MAVEN_HOME
```
・値
```
C:\java\maven
```
システム環境変数の「Path」を編集し以下を追加する
```
%MAVEN_HOME%\bin
```  

### postgresダウンロード、インストール
（どうしてもwindows上のDBで開発したい人向け。後述するコンテナを使用した開発環境を推奨とする。コンテナを使用する場合は不要）  
https://www.postgresql.org/
Downloadタブ押下→windowsを押下→Download the installerを押下→10系の最新をダウンロードする  
ダウンロードしたインストーラを起動し、以下の設定以外はすべて次へを押下。  
①rootユーザのパスワードは任意のものを入力する。  
②ロケールはjapaneseを選択する

パスを設定する  
ユーザ環境変数の「Path」を編集し以下を追加する
```
C:\Program Files\PostgreSQL\10\bin
```  

※windowsクライアントツールはデフォルトで入るpgAdminが推奨だが、他ツールでも問題ないので慣れたものを使う

### コンテナイメージ取り込み
DBのリソースやwebのリソースはコンテナを使用したほうが効率がいいため、構築済みコンテナイメージを使用する。  
コンテナイメージを動かすdockerの環境は任意のもので構わない。  
以下手順はdocker動作環境が整い、docker動作OS上でコンテナイメージを取り込む手順となる。
```
#構築済みコンテナイメージ「centos7_vmoperationserver.tar」を設置したディレクトリにて以下を実行。
docker load < centos7_vmoperationserver.tar

#イメージIDの確認  
docker images
#確認したイメージIDを設定
imageId=

#コンテナに付与するIPアドレスを設定
dockerIP=

#コンテナ作成実施
docker create --hostname="centos7_VmOperationServer" --privileged -t --name centos7_vmoperationserver $imageId /sbin/init
docker network connect --ip $dockerIP br0_docker centos7_vmoperationserver

#コンテナ起動、接続
docker start centos7_vmoperationserver
docker exec -it centos7_vmoperationserver /bin/bash
```

コンテナイメージは本番環境を想定して作られたもののため、開発するためには以下を実行する
```
#ファイアウォール設定追加
firewall-cmd --add-port=5432/tcp --zone=public

systemctl restart firewalld

#postgresセキュリティ設定追加
vi /var/lib/pgsql/10/data/pg_hba.conf
==
host    all             all             0.0.0.0/0            md5
==

#listen設定追加
vi /var/lib/pgsql/10/data/postgresql.conf
==
listen_addresses = '*'
==

systemctl restart postgresql-10
```

# STS設定
### 日本語化
Pleiades プラグイン・ダウンロード欄のwindowsを選択  
http://mergedoc.osdn.jp/  

zipファイルを解凍し、pluginsフォルダ内の  
jp.sourceforge.mergedoc.pleiadesフォルダを以下に配置  
C:\java\sts\plugins  

featuresフォルダ内の  
jp.sourceforge.mergedoc.pleiadesフォルダを以下に配置  
C:\java\sts\features  

SpringToolSuite4.iniを編集  
（以下を追記）  
```
-Xverify:none
-javaagent:plugins/jp.sourceforge.mergedoc.pleiades/pleiades.jar
```
pleiades_X.X.X.zip内の"eclipse.exe -clean.cmd" をSTS.exeと同じディレクトリに配置  
"eclipse.exe -clean.cmd"を編集し、eclipse.exeをSpringToolSuite4.exeに書き換えて保存。  
"eclipse.exe -clean.cmd"をダブルクリックで実行  
ワークスペースを「C:\java\workspace」に変更し、  
「この選択を～」のチェックボックスにチェックを入れ起動を押下  

### デバッグ用サーバ設定  
1.  STS画面上部のメニューバーから、ヘルプ＞Eclips マーケットプレイスを選択  
1.  「Pivotal tc Server」と検索し、Eclips用 Pivotal tc Server 統合 XXXのインストールを押下  
1.  そのまま確認を押下し、使用条件の同意にチェックを入れ完了を押下  
1.  ウィンドウが閉じられインストールが実行される（画面右下にて進行状況が表示）
1.  インストールが終わると再起動しますかとポップアップされるのではいを押下。  
1.  STSが再起動されるとダッシュボードが出てくるので、ManageのIDE EXTENTIONSを選択  
1.  再度「Pivotal tc Server」を検索しPivotal tc Server Developer Editionにチェックを入れインストールを押下  
1.  そのまま次へを押下し、使用条件の同意にチェックを入れ完了を押下  
1.  インストールが終わると再起動しますかとポップアップされるのではいを押下。
1.  STS画面上部のメニューバーから、ウィンドウ＞ビューの表示＞その他＞サーバー＞サーバーを選択し開くを押下  
1.  「サーバ」ウィンドウが新たに表示されるので、「使用可能な～」の表示を押下  
1.  サーバのタイプを選択＞Pivotal＞Pivotal tc Server v4.0を選択し次へ  
1.  インストールディレクトリの参照を開き、C:\java 配下にできているpivotal-tc-server-developer-XXXを選択し次へ  
1.  そのまま次へを選択  
1.  任意の名前を付け、テンプレートの中のbaseにチェックを入れ完了を押下  

### プロジェクトインポート
※READMEに記載のとおりソースをダウンロードしている前提。  
①メインwebシステム  
STS画面上部のメニューバーから、ファイル＞インポート＞Maven＞既存Mavenプロジェクト＞次へ  
~/git/VMJIserverを選択し完了  
プロジェクトの自動インポート（mavenが必要パッケージをダウンロード、ビルド）が始まる（画面右下にて進行状況が表示）

②エージェント  
STS画面上部のメニューバーから、ファイル＞インポート＞一般＞既存プロジェクトをワークスペースへ＞次へ  
~/git/VMJIagentを選択し完了  

# デバッグ実行
### デバッグ環境準備
デバッグはメインwebシステムとエージェント共に動かさなければ実行できない  

①メインwebシステム  
サーバウィンドウ内にあるローカルサーバを右クリックし、追加及び除去を選択し、VMJIserverを追加し完了を押下  
再度右クリックし、デバッグを押下（windowsファイアウォールとディフェンダーの警告が出る場合、それぞれ許可を押下する）  
アクセスする http://localhost:8080/VMJIserver/top

②エージェント  
docker動作環境上でコンテナを動かす。以下はその手順となる。  
開発用のホストはアプリケーションのテストを考えると3つ程立てることが推奨。2つ目意向を作成する際はホスト名とIPは適宜違うものに変更すること。  
```
#構築済みコンテナイメージ「centos7_hostos_stub.tar」を設置したディレクトリにて以下を実行。
docker load < centos7_hostos_stub.tar

#イメージIDの確認  
docker images
#確認したイメージIDを設定
imageId=

#コンテナに付与するIPアドレスを設定
dockerIP=

#コンテナ作成実施
docker create --hostname="centos7_hostos_stub1" --privileged -t --name centos7_hostos_stub1 $imageId /sbin/init
docker network connect --ip $dockerIP br0_docker centos7_hostos_stub1

#コンテナ起動、接続
docker start centos7_hostos_stub1
docker exec -it centos7_hostos_stub1 /bin/bash
```

1. エクリプス上で、VMJIagentプロジェクトを右クリックし、エクスポート>java>JARファイルを選択、  
エクスポート先を任意の場所に指定し、ファイル名に「VMJIagent」と入力し、次へを押下。
そのまま次の画面も次へを押下し、「JARマニフェスト仕様」画面の、「アプリケーションのエントリー～...」欄にて、メインクラスを指定し、完了を押下する（VMJIagent.jarが生成される）
1. VMJIagent.jarを実行する
```
java -jar VMJIagent.jar &
```
デバッグ実行の場合。（API側のデバッグは環境整備が別途必要。まとまったら書く）
```
java -Xdebug -Xrunjdwp:transport=dt_socket,server=y,address=8000,suspend=y -jar HostOsApi.jar
```

## アプリケーション仕様
### VMJIserver  
###### 機能概要
1. web画面をクライアントに提供
1. ログイン認証を実施
1. ゲストOS、ホストOS、ユーザ情報、操作ログ情報を格納
1. 各ホストに存在するエージェントへ操作命令を実施

###### プログラム構成
1. VMJIserver\src\main\java\mainPackage  
mainクラス、共通、汎用的なプログラムを格納
1. VMJIserver\src\main\java\mainPackage\controller  
各コントローラを格納
1. VMJIserver\src\main\java\mainPackage\dao  
各DAOを格納
1. VMJIserver\src\main\java\mainPackage\entity  
各エンティティを格納
1. VMJIserver\src\main\java\mainPackage\JpaRepository  
各JPAリポジトリを格納
1. VMJIserver\src\main\java\apiInterface  
エージェントとの通信を行う各プログラムを格納
1. VMJIserver\src\main\java\SocketData  
エージェントとの通信を行う際に使用する各データオブジェクトを格納
1. VMJIserver\src\main\webapp\WEB-INF\view  
各画面にて使用するJSPを格納
1. VMJIserver\src\main\webapp\css  
各画面にて使用するCSSを格納
1. VMJIserver\src\main\webapp\js  
各画面にて使用するJSを格納
1. VMJIserver\src\main\resources  
spring関連各設定ファイルを格納。認証、DB接続、beanなど。

### VMJIagent
###### 機能概要
1. サーバより命令を受け取り各VMの操作を実施

###### プログラム構成
1. VMJIagent\src\mainPackage  
mainクラス、共通、汎用的なプログラムを格納
1. VMJIagent\src\Operation  
各VM操作を行うプログラムを格納
1. VMJIagent\src\ApiInterface  
webサーバとの通信を行う各プログラムを格納
1. VMJIagent\src\SocketData  
webサーバとの通信を行う際に使用する各データオブジェクトを格納
1. HostOsApi\src\Thread
各スレッド実行プログラムを格納
