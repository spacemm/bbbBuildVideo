#!/bin/bash
path=$1
res=$2
#echo $path
mkdir -p $path/img
for i in `find $path -name \*.png` ; do convert -resize $res -flatten $i $path/img/`basename $i| sed 's/png/jpg/g'` ; done
