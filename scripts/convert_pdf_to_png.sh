#!/bin/bash
id=$1
resolution=$2
#echo $path
d=/tmp/$id/slides/ 
cd $d ||exit 1
for dir in `find . -maxdepth 1 -type d| egrep -v '^\.$'`;do
    cd $dir
    mkdir -p res
    mkdir -p tmp
    di=`pwd`
    echo $di
    k=`find $di -name \*swf|wc -l`
    for num in `seq $k` ; do 
        pdf2svg `find . -name \*pdf` tmp/$num.svg $num
        convert -resize $resolution -gravity center -extent $resolution  tmp/$num.svg res/`echo $num-1|bc`.png
     done
     cd ..
done
