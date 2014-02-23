#!/bin/bash
id=$1
res=$2
cd /tmp/$id
for i in `find . -name \*flv`;do
   ffmpeg -y  -an -q:v 2  -i $i -s $res -r 25 `echo $i|sed -e 's/\.flv/\.mp4/g'`
done
