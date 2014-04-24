#!/bin/bash
path=$2
lenght=$1
text=$3
name=$4
res=$5
mkdir -p $path/chat
cat << EOF > $path/chat/text.txt
$text
EOF
tr -d '\r' < $path/chat/text.txt > $path/chat/ready_text.txt
cat $path/chat/ready_text.txt |convert \
    -size $res \
    -font DejaVu-Sans \
    -pointsize 12 \
    -fill black \
    -gravity SouthWest \
    caption:@- \
    $path/chat/image.png
 ffmpeg -v quiet -y -loop 1 -i $path/chat/image.png -t $lenght -pix_fmt yuv420p $path/chat/$name.mp4

#    -draw "text 0,0 '$text'" \
