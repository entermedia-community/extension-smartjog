#!/bin/sh

source=$1
dest=$2
user=$3
password=$4
host=$5

export ASPERA_SCP_PASS=$password
#-T to disable encryption
ascp -T $source $user@$host:$dest
