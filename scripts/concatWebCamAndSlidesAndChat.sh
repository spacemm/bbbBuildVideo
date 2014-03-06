#!/bin/bash
p=$1
web=$p/res/video.mp4
chat=$p/chat/chat.mp4
#ffmpeg -v quiet -y -i $web  -vf "pad=iw:2*ih [left]; movie='$chat' [right]; [left][right] overlay=0:main_h/2" $path/res.mp4
ffmpeg -v quiet -y -i $web -vf "pad=1120:600:0:0:black" $path/res/left_wide.mp4
ffmpeg -v quiet -y -i $path/res/left_wide.mp4 -vf "movie='$chat' [mv]; [in][mv] overlay=640:0" $path/combined_video.mp4



