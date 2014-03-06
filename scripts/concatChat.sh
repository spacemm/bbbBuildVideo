#!/bin/bash
id=$1
cd /tmp/$id/chat/
rm chat.mp4
find . -name \*.mp4 -exec echo file '{}' \; > files.list
cat files.list | sort > files.list.sort
ffmpeg -v quiet -y -f concat -i files.list.sort -c copy chat.mp4
cd -



