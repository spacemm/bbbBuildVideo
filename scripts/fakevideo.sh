#!/bin/bash
p=$2
lenght=$4
cd $2
ffmpeg -v quiet -y -loop 1 -i /tmp/black640x480.png -t $lenght -pix_fmt yuv420p -vcodec libx264 $p/fake.mp4
cd -

