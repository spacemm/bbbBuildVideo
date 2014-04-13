#!/bin/bash
s=$1
p=$2
name=$3
lenght=$4
resol=$5
cd $2
ffmpeg -v quiet -y -loop 1 -i /tmp/black'$resol'.png -t $lenght -pix_fmt yuv420p -vcodec libx264 $p/$s"_"$name
cd -

