source ./serverconf/$1.txt

# 必要モジュールインストール
yum install -y unzip wget

#インストール
wget https://fedorapeople.org/groups/virt/virtio-win/virtio-win.repo -O /etc/yum.repos.d/virtio-win.repo
yum -y install libguestfs libvirt libvirt-client python-virtinst qemu-kvm virt-manager virt-top virt-viewer virt-who virt-install bridge-utils virtio-win 
;sleep 10
systemctl enable libvirtd
systemctl restart libvirtd

#VM格納ディレクトリ作成
mkdir /var/lib/libvirt/images/$KVMdir

#WAN用ブリッジ作成
echo "TYPE=Bridge" >> /etc/sysconfig/network-scripts/ifcfg-br0
echo "BOOTPROTO=static" >> /etc/sysconfig/network-scripts/ifcfg-br0
echo "DEVICE=br0" >> /etc/sysconfig/network-scripts/ifcfg-br0
echo "ONBOOT=yes" >> /etc/sysconfig/network-scripts/ifcfg-br0
echo "IPADDR=$IpAddr" >> /etc/sysconfig/network-scripts/ifcfg-br0
echo "NETMASK=255.255.255.0" >> /etc/sysconfig/network-scripts/ifcfg-br0
echo "GATEWAY=$GatewayOrDns" >> /etc/sysconfig/network-scripts/ifcfg-br0
echo "DNS1=$GatewayOrDns" >> /etc/sysconfig/network-scripts/ifcfg-br0
echo "ONBOOT=yes" >> /etc/sysconfig/network-scripts/ifcfg-br0

#既存ネットワーク変更
cp -p /etc/sysconfig/network-scripts/ifcfg-$Nic /etc/sysconfig/network-scripts/bk_ifcfg-$Nic
rm -f /etc/sysconfig/network-scripts/ifcfg-$Nic

echo "DEVICE=$Nic" >> /etc/sysconfig/network-scripts/ifcfg-$Nic
echo "ONBOOT=yes" >> /etc/sysconfig/network-scripts/ifcfg-$Nic
echo "BOOTPROTO=none" >> /etc/sysconfig/network-scripts/ifcfg-$Nic
echo "TYPE=ethernet" >> /etc/sysconfig/network-scripts/ifcfg-$Nic
echo "BRIDGE=br0" >> /etc/sysconfig/network-scripts/ifcfg-$Nic

#ブリッジ設定
echo "<network>" >> host-bridge.xml
echo "  <name>host-bridge</name>" >> host-bridge.xml
echo "  <forward mode=\"bridge\"/>" >> host-bridge.xml
echo "  <bridge name=\"br0\"/>" >> host-bridge.xml
echo "</network>" >> host-bridge.xml
virsh net-define --file host-bridge.xml
rm -f host-bridge.xml

#仮想ネットワーク定義
virsh net-autostart host-bridge
virsh net-start host-bridge

#ネットワーク、KVM再起動
systemctl restart network.service
systemctl restart libvirtd