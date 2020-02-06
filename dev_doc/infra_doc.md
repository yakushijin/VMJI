# プログラム実行サーバ構築
## docker基盤構築
dockerを動かす基盤はそれぞれの環境にあった形で自由に実装する形とする。（docker基盤の環境依存でコンテナの挙動には影響がでない為）  
以下は開発用として、バーチャルボックス上のCentos7にdocker基盤を構築する例となる。  
※バーチャルボックスのインストールおよびゲストOSのCentos7のインストールまでは手順を割愛。  
本番環境のdocker基盤を作成する場合は適宜本番のハードウェア、ネットワークを考慮し実装する。

### バーチャルボックスのネットワーク設定
NAT接続とし、ポートフォワーディングすることでローカルPCのホストOSからゲストOSにアクセスする形とする  

1. 対象のゲストOSを選択し、右クリック＞設定＞ネットワークを選択
1. アダプター1タブ内で、高度を押下し、ポートフォワーディングを選択
1. 以下を設定

|名前 |プロトコル |ホストIP |ホストポート |ゲストIP |ゲストポート |
|---|---|---|---|---|---|
|db|TCP||5432|10.0.2.30|5432|
|https|TCP||443|10.0.2.30|443|
|ssh|TCP||22|10.0.2.20|22|
|agent1|TCP||8001|10.0.2.41|8001|
|agent2|TCP||8002|10.0.2.41|8002|

### OS構築
```
#環境ごとの変数設定
Nic=enp0s3
Host=centos7_docker_dev
#ネットワーク設定
nmcli connection modify $Nic connection.autoconnect yes
systemctl restart network
#SElinux無効
sed -i "s/\(^SELINUX=\).*/\1disabled/" /etc/selinux/config
#ファイルディスクリプタ変更
echo "* soft nofile 65536" >> /etc/security/limits.conf
echo "* hard nofile 65536" >> /etc/security/limits.conf
#カーネルパラメータ変更
echo "vm.swappiness = 10" >> /etc/sysctl.conf
echo "net.core.somaxconn = 1024" >> /etc/sysctl.conf
echo "net.ipv4.ip_forward = 1" >> /etc/sysctl.conf
#hostname変更
echo $Host > /etc/hostname
#history設定変更
sed -i -e "s/HISTSIZE=1000/#HISTSIZE=1000/g" /etc/profile
sed -i -e "/#HISTSIZE=1000/a HISTSIZE=10000" /etc/profile
echo "HISTTIMEFORMAT='%F %T '" >> /etc/profile
echo "unset HISTCONTROL" >> /etc/profile
echo "export HISTSIZE HISTTIMEFORMAT" >> /etc/profile
#FW設定変更
sed -i -e "/ssh/a <port protocol=\"tcp\" port=\"443\"/>"  /etc/firewalld/zones/public.xml
sed -i -e "/dhcpv6-client/d" /etc/firewalld/zones/public.xml
firewall-cmd --direct --add-rule ipv4 nat POSTROUTING 0 -o $Nic -j MASQUERADE
#yumアップデート
yum update -y
#再起動
reboot
```
### MW構築
```
#環境ごとの変数設定
Nic0=enp0s3
Nic0Segment=10.0.2.0/24
Nic0Addr=10.0.2.20
Nic0Gateway=10.0.2.2
Nic0Dns=8.8.8.8

#dockerインストール
yum install -y yum-utils
yum-config-manager --add-repo https://download.docker.com/linux/centos/docker-ce.repo
yum install -y docker-ce docker-ce-cli containerd.io

systemctl enable docker
systemctl start docker

echo "TYPE=Bridge" >> /etc/sysconfig/network-scripts/ifcfg-br0
echo "BOOTPROTO=static" >> /etc/sysconfig/network-scripts/ifcfg-br0
echo "DEVICE=br0" >> /etc/sysconfig/network-scripts/ifcfg-br0
echo "IPADDR=$Nic0Addr" >> /etc/sysconfig/network-scripts/ifcfg-br0
echo "NETMASK=255.255.255.0" >> /etc/sysconfig/network-scripts/ifcfg-br0
echo "GATEWAY=$Nic0Gateway" >> /etc/sysconfig/network-scripts/ifcfg-br0
echo "DNS1=$Nic0Dns" >> /etc/sysconfig/network-scripts/ifcfg-br0
echo "ONBOOT=yes" >> /etc/sysconfig/network-scripts/ifcfg-br0

rm -f /etc/sysconfig/network-scripts/ifcfg-$Nic0

echo "DEVICE=$Nic0" >> /etc/sysconfig/network-scripts/ifcfg-$Nic0
echo "ONBOOT=yes" >> /etc/sysconfig/network-scripts/ifcfg-$Nic0
echo "BOOTPROTO=none" >> /etc/sysconfig/network-scripts/ifcfg-$Nic0
echo "TYPE=ethernet" >> /etc/sysconfig/network-scripts/ifcfg-$Nic0
echo "BRIDGE=br0" >> /etc/sysconfig/network-scripts/ifcfg-$Nic0

systemctl restart network
systemctl restart docker

#dockerネットワーク設定
docker network create \
         -d bridge \
         --subnet $Nic0Segment \
         --gateway $Nic0Addr \
         -o 'com.docker.network.bridge.enable_icc=true' \
         -o 'com.docker.network.bridge.enable_ip_masquerade=false' \
         -o 'com.docker.network.bridge.host_binding_ipv4=0.0.0.0' \
         -o 'com.docker.network.bridge.name=br0' \
         -o 'com.docker.network.driver.mtu=1500' \
         br0_docker

#docker再起動
systemctl restart docker
```

## web/dbサーバ構築
dockerコンテナを作成し、コンテナ上にログインし、作業を実施する。本番と開発及び検証環境は基本的に同じ構成となるが、開発環境独自の設定が必要なもは適宜記載の手順通りに設定をする。

### コンテナ作成（docker基盤での作業）
・dockerファイルを生成する
```
vi Dockerfile
=====================================
FROM centos:7

RUN rpm --import /etc/pki/rpm-gpg/RPM-GPG-KEY-CentOS-7

RUN yum -y update && yum clean all

RUN localedef -i ja_JP -f UTF-8 ja_JP.UTF-8
RUN echo 'LANG="ja_JP.UTF-8"' >  /etc/locale.conf
ENV LANG ja_JP.UTF-8

RUN echo 'ZONE="Asia/Tokyo"' > /etc/sysconfig/clock
RUN rm -f /etc/localtime
RUN ln -fs /usr/share/zoneinfo/Asia/Tokyo /etc/localtime

RUN sed -i -e"s/^tsflags=nodocs/\# tsflags=nodocs/" /etc/yum.conf

RUN yum -y install man
RUN yum -y install man-pages.noarch
RUN yum -y install man-pages-ja.noarch
=====================================
```
・dockerファイルからイメージをビルドする
```
docker build -t centos7_vmoperationserver:init .
```
・イメージからコンテナを生成する
```
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

### OS構築（コンテナ内での作業）
```
#必要なものをインストール
yum -y install firewalld openssh-server openssh-clients net-tools iproute traceroute
systemctl enable firewalld
systemctl enable sshd

#ファイルディスクリプタ変更
echo "* soft nofile 65536" >> /etc/security/limits.conf
echo "* hard nofile 65536" >> /etc/security/limits.conf
#カーネルパラメータ変更
echo "vm.swappiness = 10" >> /etc/sysctl.conf
echo "net.core.somaxconn = 1024" >> /etc/sysctl.conf

#ファイヤーウォール設定
firewall-cmd --add-service=https --zone=public --permanent
firewall-cmd --add-service=ssh --zone=public --permanent

systemctl restart firewalld
systemctl restart sshd

#DNS設定
echo "nameserver 8.8.8.8" > /etc/resolv.conf
```

### MW構築（コンテナ内での作業）
・apache設定
```
#インストール
yum install -y httpd mod_ssl
systemctl enable httpd

#設定
vi /etc/httpd/conf/httpd.conf

#管理用のメールアドレスの設定
#サーバがエラードキュメントを返す際に表示される管理用のメールアドレスの設定。不要の為、無効化。
ServerAdmin root@localhost
↓
#ServerAdmin root@localhost

#indexファイル参照の設定
#index.htmlが存在しない場合に、ディレクトリ内の一覧が表示されないように変更。
Options Indexes FollowSymLinks
↓
Options FollowSymLinks

#使用可能メソッドの設定
#PUT、DELETEメソッドのアクセス制御を行う。アクセスがあった際はすべて拒否する設定を追加。
※「<Directory "/var/www/html">」ディレクティブ内に追加
<Limit PUT DELETE>
    Order deny,allow
    Deny from all
</Limit>

#cgi-binフォルダの設定
#cgi-binフォルダは不要であり、無効化。
ScriptAlias /cgi-bin/ "/var/www/cgi-bin/"
↓
#ScriptAlias /cgi-bin/ "/var/www/cgi-bin/"

<Directory "/var/www/cgi-bin">			
    AllowOverride None			
    Options None			
    Require all granted			
</Directory>			
↓
#<Directory "/var/www/cgi-bin">
#    AllowOverride None
#    Options None
#    Require all granted
#</Directory>

#proxyPassの設定
#Apacheで受けたhttpdリクエストで指定のディレクトリパスを同サーバ内のtomcatに転送する設定を追加。
※最下層に追加
ProxyPass /VMJIserver/ ajp://localhost:8009/VMJIserver/

#TRACEメソッドの設定
#Cross Site Tracingの対策として、TRACEメソッドを無効化する為、設定を追加。
※最下層に追加
TraceEnable Off

#フッターライン表示の設定
#エラーメッセージなどを返す際のフッターラインを表示させない為、設定を追加。
※最下層に追加
ServerSignature Off

#apacheバージョン情報表示の設定
#レスポンスヘッダーにapacheのバージョンを表示させない為、設定を追加。
※最下層に追加
ServerTokens ProductOnly

#Etag無効化設定
#inode、PIDの情報が表示されないよう、Etagを無効化する設定を追加。
※最下層に追加
FileETag none
Header unset Etag

#リクエスト内容に関する各種制限の設定
#dos攻撃の対策として、リクエストBODYサイズ、リクエストヘッダフィールド数、リクエストヘッダサイズ、リクエストヘッダ行数の上限を指定する為、設定を追加。
※最下層に追加
LimitRequestBody 102400000
LimitRequestFields 50
LimitRequestFieldsize 8190
LimitRequestLine 8190

#X-Frame-Optionsの設定
#クリックジャッキング対策として、自身のサーバで生成されたiframeを、同一ドメイン内でのみ読み込みが可能になる設定を追加。
※追加
Header append X-FRAME-OPTIONS "SAMEORIGIN"

#Rewriteの設定（OPTIONSメソッドに関する設定）
#サーバで使用可能なhttpメソッドを表示させない為、OPTIONSメソッドがリクエストされた場合、リクエスト内容を書き換え無効化し、403を返す設定を追加。
※追加
RewriteEngine On
RewriteCond %{REQUEST_METHOD} ^OPTIONS
RewriteRule .* - [F]

#ssl設定
mkdir -p /etc/httpd/ssl
chmod 700 /etc/httpd/ssl
cd /etc/httpd/ssl
openssl genrsa -out cert.key 2048
openssl req -subj '/CN=common_name.example.com/O=ORGANIZATION/C=JP' -new -key cert.key > cert.csr
openssl x509 -in cert.csr -days 3650 -req -signkey cert.key > cert.crt
```
・tomcat設定
```
#インストール
yum install -y tomcat unzip
systemctl enable tomcat

#設定
vi /etc/tomcat/server.xml

#tomcatのhttp通信の設定
#apacheからの接続を8009で行い、不要な為無効化。

<Connector port="8080" protocol="HTTP/1.1"									
           connectionTimeout="20000"									
           redirectPort="8443" />									
↓
<!--
<Connector port="8080" protocol="HTTP/1.1"
           connectionTimeout="20000"
           redirectPort="8443" />
-->

#自動デプロイの設定
#誤操作のリスクやパフォーマンスを考慮し、自動デプロイはさせない為、無効化する設定に変更。
unpackWARs="true" autoDeploy="true">
↓
unpackWARs="true" autoDeploy="false" errorReportValveClass="">

#デフォルトエラーページの設定
#tomcatのデフォルトエラーページには改ざんの脆弱性やサーバ情報などが表示されるため、表示させない設定を追加。
unpackWARs="true" autoDeploy="true">
↓
unpackWARs="true" autoDeploy="false" errorReportValveClass="">

#バージョン情報表示設定
#tomcatのバージョン情報を非表示にする為、/usr/share/tomcat/lib/catalina.jar内の、org/apache/catalina/util/ServerInfo.propertiesの以下部分を削除。
server.info=Apache Tomcat/7.0.76
server.number=7.0.76.0
server.built=Oct 30 2017 10:21:55 UTC

server.info=
server.number=
server.built=

#アクセスログ設定変更
#logrotateを使用したローテーション時にログファイルにタイムスタンプを付けるため、出力時のアクセスログのファイル名にタイムスタンプを付けないよう変更
prefix="localhost_access_log." suffix=".txt"
pattern="%h %l %u %t &quot;%r&quot; %s %b" />
↓
prefix="localhost_access_log" suffix=".txt"
pattern="%h %l %u %t &quot;%r&quot; %s %b"
rotatable="false" />

#ログ設定
vi /etc/tomcat/logging.properties

#ログ出力ファイルの変更
#Tomcat Host Manager Web AppとTomcat Manager Web Appは使用しない為、それらのログを出力しないよう変更
handlers = 1catalina.org.apache.juli.FileHandler, 2localhost.org.apache.juli.FileHandler, 3manager.org.apache.juli.FileHandler, 4host-manager.org.apache.juli.FileHandler, java.util.logging.ConsoleHandler
↓
#handlers = 1catalina.org.apache.juli.FileHandler, 2localhost.org.apache.juli.FileHandler, 3manager.org.apache.juli.FileHandler, 4host-manager.org.apache.juli.FileHandler, java.util.logging.ConsoleHandler
handlers = 1catalina.org.apache.juli.FileHandler, 2localhost.org.apache.juli.FileHandler, java.util.logging.ConsoleHandler

#catalinaログの設定変更
#logrotateを使用したローテーション時にログファイルにタイムスタンプを付けるため、出力時のcatalinaログのファイル名にタイムスタンプを付けないよう変更
1catalina.org.apache.juli.FileHandler.prefix = catalina.
↓
1catalina.org.apache.juli.FileHandler.prefix = catalina
1catalina.org.apache.juli.FileHandler.rotatable = false

#localhostログの設定変更
#logrotateを使用したローテーション時にログファイルにタイムスタンプを付けるため、出力時のlocalhostログのファイル名にタイムスタンプを付けないよう変更
2localhost.org.apache.juli.FileHandler.prefix = localhost.
↓
2localhost.org.apache.juli.FileHandler.prefix = localhost
2localhost.org.apache.juli.FileHandler.rotatable = false
```
・postgres設定
```
#インストール
yum -y localinstall https://download.postgresql.org/pub/repos/yum/10/redhat/rhel-7-x86_64/pgdg-centos10-10-2.noarch.rpm
yum -y install postgresql10-server postgresql10-contrib
systemctl enable postgresql-10

#DB初期化
export PGSETUP_INITDB_OPTIONS="--encoding=UTF-8 --locale=ja_JP.UTF-8"
/usr/pgsql-10/bin/postgresql-10-setup initdb
systemctl start postgresql-10

#セキュリティ設定（1回目）
vi /var/lib/pgsql/10/data/pg_hba.conf
local   all             all                                     ident
↓
local   all             all                                     trust

host    all             all             127.0.0.1/32            md5
↓
host    all             all             localhost            trust

#再起動
systemctl restart postgresql-10

#postgresユーザパスワード設定
su - postgres
psql -U postgres
\password
jP9G3Wri
ALTER ROLE postgres WITH PASSWORD 'jP9G3Wri';
\q
exit

#セキュリティ設定（2回目）
vi /var/lib/pgsql/10/data/pg_hba.conf
local   all             all                                     trust
↓
local   all             all                                     md5

host    all             all             localhost            trust
↓
host    all             all             localhost            md5

#再起動
systemctl restart postgresql-10

#初回DB構築
以下ディレクトリにあるsqlファイルの内容を実施する  
kvm_webservice\VMJIserver\db

```

### コンテンツ設置
（格納場所）  
/var/www/html/  
（格納資材）  
manual.html  
各.jpgファイル  

# デプロイ
### メインサーバ
※tomcatの自動デプロイは無効化してるので手動で実施する
```
#以下にwarを設置
/home/devuser/

※既存データが存在する場合消す
rm -fR /var/lib/tomcat/webapps/VMJIserver*

#コピー、解凍、権限設定
cp -p /home/devuser/VMJIserver.war /var/lib/tomcat/webapps/
cd /var/lib/tomcat/webapps/
unzip VMJIserver.war -d VMJIserver
chown -R tomcat:tomcat  VMJIserver

systemctl restart tomcat
```

# コンテナからイメージ作成（docker基盤での作業）
環境構築が完了後、開発用に使うコンテナイメージを作成する
```
docker commit centos7_vmoperationserver centos7_vmoperationserver:deploy
#イメージIDの確認  
docker images
#確認したイメージIDを設定
imageId=
docker save $imageId > centos7_vmoperationserver.tar
```

## 各ホストOSへの追加実装
web/DBサーバと通信する各ホストにもエージェントとなるjavaアプリケーションを実装する必要があるため、追加設定を実施する。  
開発環境の場合はdockerコンテナ上に、スタブとなる環境を実装する。

### エージェント実行環境追加（各ホストOS上での作業）
1. JDKをインストールする
```
yum -y install java-1.8.0-openjdk java-1.8.0-openjdk-devel
```
```
export JAVA_HOME=/usr/lib/jvm/java-1.8.0-openjdk
export PATH=$PATH:$JAVA_HOME/bin
export CLASSPATH=.:$JAVA_HOME/jre/lib:$JAVA_HOME/lib:$JAVA_HOME/lib/tools.jar
```
1. 資材を配置する
```
#格納場所作成
mkdir /opt/vmoperation/
```
（格納場所）  
/opt/vmoperation/  
（格納資材）  
VMJIagent.jar  
start  
destroy  
vminfo  
vminfo_unit  
```
#権限設定
chmod 500 /opt/vmoperation/*
chown root:root /opt/vmoperation/*
```
1. 実行
```
java -jar VMJIagent.jar &
```

### コンテナ作成（docker基盤での作業）※開発環境用
開発用のホストはアプリケーションのテストを考えると3つ程立てることが推奨。2つ目意向を作成する際はホスト名とIPは適宜違うものに変更すること。  
・イメージからコンテナを生成する※web/DB用のコンテナ作成時にビルドして作成したイメージを使用する。
```
#イメージIDの確認  
docker images
#確認したイメージIDを設定
imageId=

#コンテナに付与するIPアドレスを設定
dockerIP=

#コンテナ作成実施
docker create --hostname="centos7_hostos_stub" --privileged -t --name centos7_hostos_stub $imageId /sbin/init
docker network connect --ip $dockerIP br0_docker centos7_hostos_stub

#コンテナ起動、接続
docker start centos7_hostos_stub
docker exec -it centos7_hostos_stub /bin/bash
```

### OS構築（コンテナ内での作業）※開発環境用
```
#必要なものをインストール
yum -y install firewalld openssh-server openssh-clients net-tools iproute traceroute
systemctl enable firewalld
systemctl enable sshd
systemctl start firewalld
systemctl start sshd

#ファイヤーウォール設定
firewall-cmd --add-service=https --zone=public --permanent
firewall-cmd --add-service=ssh --zone=public --permanent
firewall-cmd --add-port=8001/tcp --zone=public --permanent
firewall-cmd --add-port=8002/tcp --zone=public --permanent
systemctl restart firewalld

#DNS設定
echo "nameserver 8.8.8.8" > /etc/resolv.conf
```

### エージェント追加設定（コンテナ内での作業）※開発環境用
手順は「エージェント実行環境追加」と同様な為割愛。  
ただし、格納資材はstub用の資材を置くこと。

### コンテナからイメージ作成（docker基盤での作業）※開発環境用
```
docker commit centos7_hostos_stub centos7_hostos_stub:init
#イメージIDの確認  
docker images
#確認したイメージIDを設定
imageId=
docker save $imageId > centos7_hostos_stub.tar
```
