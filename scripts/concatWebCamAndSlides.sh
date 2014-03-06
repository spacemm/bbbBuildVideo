#!/bin/bash
id=$1
web=`find $path -name webcams.webm`
slides=/tmp/$id/res/output.mp4
ffmpeg -v quiet -y -i $web  -vf "pad=iw:2*ih [top]; movie='$slides' [bottom]; [top][bottom] overlay=0:main_h/2" /tmp/$id/res/video.mp4

