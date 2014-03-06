#!/bin/bash
id=$1
name=$2
ln=$3
echo $name
cd /tmp/$id
#ffmpeg -v quiet -y -i "concat:begin_$name|$name|tail_$name" -c copy res_$name
rm -rf file.list
find . -name begin_$name -exec echo file '{}' \; >>  file.list
echo file $name >>  file.list
find . -name tail_$name -exec echo file '{}' \; >>  file.list
ffmpeg -v quiet -y -f concat -i file.list  -c copy res_$name
#rm -rf file.list
