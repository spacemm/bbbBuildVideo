#!/bin/sh
id=$1
server=$2
tmp=/tmp/$id
mkdir -p $tmp/slides
scp -r root@$server:/var/bigbluebutton/dump/$id.dump $tmp
scp -r root@$server:/usr/share/red5/webapps/video/streams/$id/* $tmp
scp -r root@$server:/var/bigbluebutton/$id/$id/* $tmp/slides/
scp -r root@$server:/var/freeswitch/meetings/$id* $tmp
convert -size 320x240 xc:white /tmp/black320x240.png
convert -size 640x480 xc:white /tmp/black640x480.png


