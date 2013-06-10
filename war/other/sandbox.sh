#!/bin/bash

v=$(aa-autodep $2)
/etc/init.d/apparmor stop
p=${2//\//\.}
profilepath="/etc/apparmor.d/${p:1}"
if [ $1 -eq 0 ]
then
	if test -e $profilepath
	then
		rm $profilepath
	fi
else 
	if [ $1 -eq 1 ]
	then
		echo $profilepath
		if [ "$v"==*"already exists"* ]
		then
			rm $profilepath
		fi
		aa-autodep $2
		sed -i "s,},$3* rw\,\n}," $profilepath
		if [ $4 -eq 1 ]
		then
			sed -i "s,$2,$3*," $profilepath
		fi 
		aa-enforce $2 
	fi
fi
/etc/init.d/apparmor start
