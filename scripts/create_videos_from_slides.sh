#!/bin/bash
id=$2
lenght=$1
text=$3
name=$4
mkdir -p /tmp/$id/slides
ffmpeg -v quiet -y -loop 1 -i $text -t $lenght -pix_fmt yuv420p /tmp/$id/slides/$id"_"$name.mp4

