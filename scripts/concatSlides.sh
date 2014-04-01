#!/bin/bash
id=$1
pushd /tmp/$id/slides
rm -rf output.mp4
find . -name $id"_"1\*.mp4 -exec echo file '{}' \; > files.list
cat files.list |sort > files.list.sort
ffmpeg -v quiet -y -f concat -i files.list.sort -c copy output.mp4
rm `ls *mp4 | grep -v 'output.mp4'`
rm files.list files.list.sort
popd



