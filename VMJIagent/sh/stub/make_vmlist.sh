#!/bin/bash
rm -f virshlist.txt

nic=eth0
vmquantity=3
localip=`ip -4 a show $nic | grep -oP '(?<=inet\s)\d+(\.\d+){3}'`
cutstring=','

for ((i=0 ; i<$vmquantity ; i++))
do

countmoji=_
guestosname=guestos_$i$countmoji$localip
id=`uuidgen`
status=実行中
cpu=$i
mem=$(($i * 2))
disk=$(($i * 10))

echo $guestosname$cutstring$id$cutstring$status$cutstring$cpu$cutstring$mem$cutstring$disk >> virshlist.txt

done
