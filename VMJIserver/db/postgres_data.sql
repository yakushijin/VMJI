-----------�X�L�[�}�ڑ�
set search_path to S_Main;

-----------�e�e�[�u���쐬
DROP TABLE IF EXISTS M_User CASCADE;
CREATE TABLE M_User(
UserId INTEGER NOT NULL DEFAULT nextval('M_User_UserId_seq'),
LoginId VARCHAR(50) NOT NULL,
Password bytea NOT NULL,
UserName VARCHAR(50)  NOT NULL,
AdminFlag boolean DEFAULT '0',
Remarks VARCHAR(400),
GrantUserCreate boolean DEFAULT '0',
GrantUserUpdate boolean DEFAULT '0',
GrantUserDelete boolean DEFAULT '0',
GrantUserAuthBrowse boolean DEFAULT '0',
GrantGuestOsAllGet boolean DEFAULT '0',
GrantGuestOsStart boolean DEFAULT '0',
GrantGuestOsStop boolean DEFAULT '0',
GrantGuestOsAuthBrowse boolean DEFAULT '0',
GrantGuestOsUpdate boolean DEFAULT '0',
GrantHostOsCreate boolean DEFAULT '0',
GrantHostOsUpdate boolean DEFAULT '0',
GrantHostOsDelete boolean DEFAULT '0',
PRIMARY KEY (UserId),
CONSTRAINT UQ__M_User__UserId UNIQUE (UserId),
CONSTRAINT UQ__M_User__LoginId UNIQUE (LoginId)
);

DROP TABLE IF EXISTS M_GuestOs CASCADE;
CREATE TABLE M_GuestOs(
GuestOsId INTEGER NOT NULL DEFAULT nextval('M_GuestOs_GuestOsId_seq'),
Uuid VARCHAR(40) NOT NULL,
GuestOsName VARCHAR(50) NOT NULL,
Status VARCHAR(20),
HostOsId INTEGER NOT NULL,
GuestOsHostName VARCHAR(50) NOT NULL DEFAULT '',
Os VARCHAR(50) NOT NULL DEFAULT '',
Ip VARCHAR(20) NOT NULL DEFAULT '',
LanIp VARCHAR(20) NOT NULL DEFAULT '',
VlanId VARCHAR(5) NOT NULL DEFAULT '',
LoginUser VARCHAR(20) NOT NULL DEFAULT '',
LoginPassword VARCHAR(20) NOT NULL DEFAULT '',
LoginPort VARCHAR(20) NOT NULL DEFAULT '',
Kanshi boolean,
Cpu INTEGER NOT NULL,
Mem INTEGER NOT NULL,
Disk INTEGER NOT NULL,
Remarks VARCHAR(400) NOT NULL DEFAULT '',
UserId INTEGER NOT NULL,
PRIMARY KEY (GuestOsId),
CONSTRAINT UQ__M_GuestOs__Uuid UNIQUE (GuestOsId),
CONSTRAINT FK__M_GuestOs__UserId FOREIGN KEY (UserId) REFERENCES M_User (UserId)
);

DROP TABLE IF EXISTS M_HostOs CASCADE;
CREATE TABLE M_HostOs(
HostOsId INTEGER NOT NULL DEFAULT nextval('M_HostOs_HostOsId_seq'),
HostName VARCHAR(20) NOT NULL,
Ip VARCHAR(50) NOT NULL,
Cpu INTEGER NOT NULL,
Mem INTEGER NOT NULL,
Disk INTEGER NOT NULL,
Remarks VARCHAR(400),
PRIMARY KEY (HostOsId)
);

DROP TABLE IF EXISTS M_OperationContents CASCADE;
CREATE TABLE M_OperationContents(
OperationId INTEGER NOT NULL,
OperationContents VARCHAR(50),
PRIMARY KEY (OperationId)
);

DROP TABLE IF EXISTS T_OperationLog CASCADE;
CREATE TABLE T_OperationLog(
LogId INTEGER NOT NULL DEFAULT nextval('T_OperationLog_LogId_seq'),
UserId INTEGER NOT NULL,
GuestOsName VARCHAR(50),
OperationDate timestamp NOT NULL,
OperationId INTEGER NOT NULL,
PRIMARY KEY (LogId),
CONSTRAINT FK__T_OperationLog__UserId FOREIGN KEY (UserId) REFERENCES M_User (UserId),
CONSTRAINT FK__T_OperationLog__OperationId FOREIGN KEY (OperationId) REFERENCES M_OperationContents (OperationId)
);

DROP TABLE IF EXISTS T_Information CASCADE;
CREATE TABLE T_Information(
InfoId INTEGER NOT NULL DEFAULT nextval('T_Information_InfoId_seq'),
InfoDate timestamp NOT NULL,
InfoLevel INTEGER NOT NULL,
InfoAuthority INTEGER NOT NULL,
InfoContents VARCHAR(200) NOT NULL,
PRIMARY KEY (InfoId)
);

-----------�f�[�^����
--�C���t�H���[�V����
insert into T_Information values(nextval('T_Information_InfoId_seq'),NOW(),1,2,'�V�X�e�������[�X');

--���[�U�����[�U���A�p�X���[�h�͓K�X�C�ӂ̂��̂ɕύX���邱��
--�V�X�e�����[�U���K���쐬����
insert into M_User values(999999,'system',pgp_sym_encrypt('pass','TnmKFq3LzD!g'),'system','1','SystemUser�B���O�C���֎~�B','1','1','1','1','1','1','1','1','1','1','1','1');
--�Ǘ��҃��[�U���쐬���Ȃ��Ă����v�����쐬���邱�Ƃ𐄏�
insert into M_User values(nextval('M_User_UserId_seq'),'admin',pgp_sym_encrypt('admin','TnmKFq3LzD!g'),'�Ǘ��҃��[�U','1','�Ǘ���');

--�z�X�g��IP�A�h���X��z�X�g����K�X���ɉ������`�ŕύX����
insert into M_HostOs values(nextval('M_HostOs_HostOsId_seq'),'HostName1','localhost','16','32','500','Remarks1');

--����}�X�^
insert into M_OperationContents values(101,'���O�C��');
insert into M_OperationContents values(102,'���O�A�E�g');
insert into M_OperationContents values(201,'�쐬');
insert into M_OperationContents values(202,'�X�V');
insert into M_OperationContents values(203,'�폜');
insert into M_OperationContents values(301,'�擾');
insert into M_OperationContents values(401,'�N��');
insert into M_OperationContents values(402,'��~');

--���샍�O
insert into T_OperationLog values(nextval('T_OperationLog_LogId_seq'),999999,'system',NOW(),201);


GRANT USAGE ON SCHEMA S_Main TO app_user;
GRANT SELECT,INSERT,UPDATE,DELETE ON ALL TABLES IN SCHEMA S_Main TO app_user;
GRANT ALL ON ALL SEQUENCES IN SCHEMA S_Main TO app_user; 
