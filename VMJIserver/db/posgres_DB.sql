--�ڑ�
psql -U postgres

/*�ȉ��͊e�평��������ۂɎg�p
--DB�폜
DROP DATABASE kvmdatabase;
--�X�L�[�}�폜
DROP SCHEMA S_Main CASCADE;
--���[�U�폜
DROP ROLE app_user;
DROP ROLE admin_user;
*/

--���[�U�쐬
CREATE ROLE app_user WITH LOGIN PASSWORD 'K6Y7wh9A5)a|';
CREATE ROLE admin_user WITH LOGIN PASSWORD 'Zu2N$h7SdSvp';

--�f�[�^�x�[�X�쐬
CREATE DATABASE kvmdatabase
    WITH 
    OWNER = admin_user
    ENCODING = 'UTF8'
    TABLESPACE = pg_default
    CONNECTION LIMIT = -1;
--��windows�̏ꍇ
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

--�ڑ��؂�ւ�    
\q
psql -U admin_user kvmdatabase

--�X�L�[�}�쐬
CREATE SCHEMA S_Main AUTHORIZATION admin_user;

--�g�����W���[���L����
\q
psql -U postgres kvmdatabase
CREATE EXTENSION pgcrypto WITH SCHEMA s_main;

--�ڑ��؂�ւ�  
\q  
psql -U admin_user kvmdatabase
set search_path to S_Main;

--�����t�^
GRANT USAGE ON SCHEMA S_Main TO app_user;
GRANT SELECT,INSERT,UPDATE,DELETE ON ALL TABLES IN SCHEMA S_Main TO app_user;
GRANT ALL ON ALL SEQUENCES IN SCHEMA S_Main TO app_user; 

--�V�[�P���X�쐬
CREATE SEQUENCE M_User_UserId_seq;
CREATE SEQUENCE M_GuestOs_GuestOsId_seq;
CREATE SEQUENCE M_HostOs_HostOsId_seq;
CREATE SEQUENCE T_OperationLog_LogId_seq;
CREATE SEQUENCE T_Information_InfoId_seq;

