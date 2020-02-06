--接続
psql -U postgres

/*以下は各種初期化する際に使用
--DB削除
DROP DATABASE kvmdatabase;
--スキーマ削除
DROP SCHEMA S_Main CASCADE;
--ユーザ削除
DROP ROLE app_user;
DROP ROLE admin_user;
*/

--ユーザ作成
CREATE ROLE app_user WITH LOGIN PASSWORD 'K6Y7wh9A5)a|';
CREATE ROLE admin_user WITH LOGIN PASSWORD 'Zu2N$h7SdSvp';

--データベース作成
CREATE DATABASE kvmdatabase
    WITH 
    OWNER = admin_user
    ENCODING = 'UTF8'
    TABLESPACE = pg_default
    CONNECTION LIMIT = -1;
--※windowsの場合
/*
CREATE DATABASE kvmdatabase
    WITH 
    OWNER = admin_user
    ENCODING = 'UTF8'
    LC_COLLATE = 'Japanese_Japan.932'
    LC_CTYPE = 'Japanese_Japan.932'
    TABLESPACE = pg_default
    CONNECTION LIMIT = -1;
*/

--接続切り替え    
\q
psql -U admin_user kvmdatabase

--スキーマ作成
CREATE SCHEMA S_Main AUTHORIZATION admin_user;

--拡張モジュール有効化
\q
psql -U postgres kvmdatabase
CREATE EXTENSION pgcrypto WITH SCHEMA s_main;

--接続切り替え  
\q  
psql -U admin_user kvmdatabase
set search_path to S_Main;

--権限付与
GRANT USAGE ON SCHEMA S_Main TO app_user;
GRANT SELECT,INSERT,UPDATE,DELETE ON ALL TABLES IN SCHEMA S_Main TO app_user;
GRANT ALL ON ALL SEQUENCES IN SCHEMA S_Main TO app_user; 

--シーケンス作成
CREATE SEQUENCE M_User_UserId_seq;
CREATE SEQUENCE M_GuestOs_GuestOsId_seq;
CREATE SEQUENCE M_HostOs_HostOsId_seq;
CREATE SEQUENCE T_OperationLog_LogId_seq;
CREATE SEQUENCE T_Information_InfoId_seq;

