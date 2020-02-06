# 概要
このプログラムは仮想マシンを管理する為のwebインターフェースを提供するもの。基本機能は動くがまだ未完成。
自宅のgitlabサーバが吹き飛んだのでgithubにバックアップ用のリポジトリとしてプッシュ。

# ドキュメント、ソース
gitクライアントを入れた状態で、任意のディレクトリ（ここではユーザディレクトリ配下）にて以下を実行し各ファイルをダウンロードする。

- 環境設定
```
gitdir=~/git
myname="[半角自分の名前]"
mymailaddress="[自分のメールアドレス]"
clonehost=https://github.com/yakushijin/VMJI.git

```
- git設定、ダウンロード
```
mkdir $gitdir
cd $gitdir
git init
git config --global user.email $mymailaddress
git config --global user.name $myname
git config --global http.sslVerify false
git config --global core.autocrlf false
git clone $clonehost
cd VMJI
```
- ドキュメント関連ディレクトリ  
doc  
- ソース関連ディレクトリ  
VMJIagent  
VMJIserver

## ドキュメントの内容
- システムの概要、操作方法など  
manual.md
- 開発関連のドキュメント  
dev_doc.md
- インフラ関連のドキュメント  
infra_doc.md
