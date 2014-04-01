#!/bin/bash
id=$1
n=$2
name=$3
long=$4
cd /tmp/$id
mid=`echo $name | awk -F "__" '{print $1}'`
echo $mid
if [[ "$name" == "start" ]] || [[ "$mid" == "mid" ]] || [[ "$name" == "end" ]] ; then
    ffmpeg -v quiet -y -loop 1 -i /tmp/black.png -t $long -pix_fmt yuv420p -vcodec libx264 $n".mp4"
else
    ffmpeg -y  -an -q:v 2  -i $name".flv" -s 320x240 -r 25 $n".mp4"
fi
cd ..
