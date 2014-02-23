#!/bin/bash
path=$1
#echo $path
mkdir -p $path/img
for i in `find $path -name \*.png` ; do convert -resize 640x480 -flatten $i $path/img/`basename $i| sed 's/png/jpg/g'` ; done
