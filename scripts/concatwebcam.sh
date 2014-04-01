#!/bin/bash
id=$1
name=$2
ln=$3
echo $name
cd /tmp/$id
#ffmpeg -y -i "concat:begin_$name|$name|tail_$name" -c copy res_$name
rm -rf file.list
find . -name \?.mp4 -exec echo file '{}' \; >>  file.list
cat file.list | sort > file.list.sort
ffmpeg -v quiet -y -f concat -i file.list.sort  -c copy $name".mp4"  2>&1  >> /tmp/lllllllot
rm -rf ?.mp4
#rm -rf file.list
