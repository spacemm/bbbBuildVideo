#!/bin/bash
id=$1
pushd /tmp/$id/slides
find . -name $id"_"1\*.mp4 -exec echo file '{}' \; > files.list
cat files.list |sort > files.list.sort
ffmpeg -y -f concat -i files.list.sort -c copy output.mp4
popd



