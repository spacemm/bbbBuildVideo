#!/bin/bash
time=$1
path=$2
numb=$3
numb=`echo $numb| sed -r 's/0/1/g'`
name=`find $path/img -name slide-$numb.jpg`
mkdir -p $path/res
ffmpeg -v quiet -y -loop 1 -i $name -t $time -pix_fmt yuv420p $path/res/`basename $name| sed -e 's/jpg/mp4/g'`
