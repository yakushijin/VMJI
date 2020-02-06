source ./serverconf/$1.txt

#SElinux無効
sed -i "s/\(^SELINUX=\).*/\1disabled/" /etc/selinux/config

#ファイルディスクリプタ変更
echo "* soft nofile 65536" >> /etc/security/limits.conf
echo "* hard nofile 65536" >> /etc/security/limits.conf

#カーネルパラメータ変更
echo "vm.swappiness = 10" >> /etc/sysctl.conf
echo "net.core.somaxconn = 1024" >> /etc/sysctl.conf

#hostname変更
echo "$Host" > /etc/hostname

#ローカルネットワーク設定
nmcli connection modify $Nic ipv4.addresses "$IpAddr/24" ipv4.method manual
nmcli connection modify $Nic ipv4.gateway "$GatewayOrDns"
nmcli connection modify $Nic ipv4.dns "$GatewayOrDns"
nmcli connection modify $Nic connection.autoconnect yes

#history設定変更
sed -i -e "s/HISTSIZE=1000/#HISTSIZE=1000/g" /etc/profile
sed -i -e "/#HISTSIZE=1000/a HISTSIZE=10000" /etc/profile
echo "HISTTIMEFORMAT='%F %T '" >> /etc/profile
echo "unset HISTCONTROL" >> /etc/profile
echo "export HISTSIZE HISTTIMEFORMAT" >> /etc/profile

#yumアップデート
yum update -y;sleep 10

#再起動
reboot

