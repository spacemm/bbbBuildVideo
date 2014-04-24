#!/bin/bash
id=$1
count=$2
dir=/tmp/$id
res=$dir/webcams.mp4
full_wav=$3
wav=`basename $full_wav`
name=`echo $wav | awk -F '.' '{print $1}'`

#ffmpeg -v quiet -y -i $dir/left.mp4 -vf "movie='$dir/chat/chat.mp4' [mv]; [in][mv] overlay=800:0" $dir/top.mp4
#ffmpeg -v quiet -y -i $dir/top.mp4 -vf "pad=1120:840:0:0:black" $dir/notlast.mp4

if [[ "$count" == "0" ]];then
    cp $dir/top.mp4 $dir/result.mp4
elif [[ "$count" == "1" ]];then
    ffmpeg -v quiet -y -i $dir/slides/output.mp4 -vf "pad=1920:1080:0:0:gray" $dir/left.mp4
    ffmpeg -v quiet -y -i $dir/RR_0.mp4 -vf "pad=640:1080:0:0:gray" $dir/r-c.mp4
    ffmpeg -v quiet -y -i $dir/r-c.mp4 -vf "movie='$dir/chat/chat.mp4' [mv]; [in][mv] overlay=0:490" $dir/r.mp4
    ffmpeg -v quiet -y -i $dir/left.mp4 -vf "movie='$dir/r.mp4' [mv]; [in][mv] overlay=1280:0" $dir/newres.mp4
    #cp $dir/RR_0.mp4 $res
    let k="480"
elif [[ "$count" == "2" ]];then
    #files=$(find $dir  -maxdepth 1 -type f -name RR_\*.mp4)
    #param=`echo $files|sed -e 's/ / -i /g'`
    param="RR_0.mp4 -i RR_1.mp4"
    ffmpeg  -v quiet -y -i $param -filter_complex "[0:v:0]pad=iw*2:ih[bg]; [bg][1:v:0]overlay=w" $res
    ffmpeg -v quiet -y -i $res -vf "pad=1920:1080:0:0:gray" $dir/left.mp4
    ffmpeg -v quiet -y -i $dir/left.mp4 -vf "movie='$dir/slides/output.mp4' [mv]; [in][mv] overlay=640:0" $dir/a-c.mp4
    ffmpeg -v quiet -y -i $dir/a-c.mp4 -vf "movie='$dir/chat/chat.mp4' [mv]; [in][mv] overlay=1600:0" $dir/newres.mp4
    let k="240"
elif [[ "$count" == "3" ]];then
    #files=$(find $dir  -maxdepth 1 -type f -name RR_\*.mp4)
    #param=`echo $files|sed -e 's/ / -i /g'`
    ffmpeg -y -i RR_0.mp4 -i RR_1.mp4 -i RR_2.mp4 -filter_complex "[0:v]pad=iw*2:ih*2[tl];[tl][1:v]overlay=w[tr];[tr][2:v]overlay=0:h" k.mp4
       ffmpeg -v quiet -y -i $res -vf "pad=1920:1080:0:0:gray" $dir/left.mp4
    ffmpeg -v quiet -y -i $dir/left.mp4 -vf "movie='$dir/slides/output.mp4' [mv]; [in][mv] overlay=640:0" $dir/a-c.mp4
    ffmpeg -v quiet -y -i $dir/a-c.mp4 -vf "movie='$dir/chat/chat.mp4' [mv]; [in][mv] overlay=1600:0" $dir/newres.mp4
    #param="RR_0.mp4 -i RR_1.mp4 -i RR_2.mp4"
    #ffmpeg -v quiet -y -i $param -filter_complex "[0:v]pad=iw*3:ih[l];[l][1:v]overlay=w[c];[c][2:v]overlay=w*2" $res
    let k="240"
elif [[ "$count" == "4" ]];then
    #files=$(find $dir  -maxdepth 1 -type f -name RR_\*.mp4)
    #param=`echo $files|sed -e 's/ / -i /g'`
    ffmpeg -y -i RR_0.mp4 -i RR_1.mp4 -i RR_2.mp4 -i RR_3.mp4 -filter_complex "[0:v]pad=iw*2:ih*2[tl];[tl][1:v]overlay=w[tr];[tr][2:v]overlay=0:h[bl];[bl][3:v]overlay=w:h" k.mp4
       ffmpeg -v quiet -y -i $res -vf "pad=1920:1080:0:0:gray" $dir/left.mp4
    ffmpeg -v quiet -y -i $dir/left.mp4 -vf "movie='$dir/slides/output.mp4' [mv]; [in][mv] overlay=640:0" $dir/a-c.mp4
    ffmpeg -v quiet -y -i $dir/a-c.mp4 -vf "movie='$dir/chat/chat.mp4' [mv]; [in][mv] overlay=1600:0" $dir/newres.mp4
    #param="RR_0.mp4 -i RR_1.mp4 -i RR_2.mp4 -i RR_3.mp4"
    #ffmpeg -v quiet -y -i $param -filter_complex "[0:v]pad=iw*2:ih*2[tl];[tl][1:v]overlay=w[tr];[tr][2:v]overlay=0:h[bl];[bl][3:v]overlay=w:h" $res
    let k="240*2"
elif [[ "$count" == "5" ]];then
    #files=$(find $dir  -maxdepth 1 -type f -name RR_\*.mp4)
    #param=`echo $files|sed -e 's/ / -i /g'`
    ffmpeg -y -i RR_0.mp4 -i RR_1.mp4 -i RR_2.mp4 -i RR_3.mp4 -i RR_4.mp4 -filter_complex "[0:v]pad=iw*2:ih*3[tr];[tr][1:v]overlay=w[cl];[cl][2:v]overlay=0:h[bl];[bl][3:v]overlay=w:h[br];[br][4:v]overlay=0:h*2" k.mp4
       ffmpeg -v quiet -y -i $res -vf "pad=1920:1080:0:0:gray" $dir/left.mp4
    ffmpeg -v quiet -y -i $dir/left.mp4 -vf "movie='$dir/slides/output.mp4' [mv]; [in][mv] overlay=640:0" $dir/a-c.mp4
    ffmpeg -v quiet -y -i $dir/a-c.mp4 -vf "movie='$dir/chat/chat.mp4' [mv]; [in][mv] overlay=1600:0" $dir/newres.mp4
    #param="RR_0.mp4 -i RR_1.mp4 -i RR_2.mp4 -i RR_3.mp4 -i RR_4.mp4"
    #ffmpeg -v quiet -y -i $param -filter_complex "[0:v]pad=iw*3:ih*2[tl];[tl][1:v]overlay=w[tc];[tc][2:v]overlay=w*2[tr];[tr][3:v]overlay=0:h[bl];[bl][4:v]overlay=w:h" $res
    let k="240*2"
elif [[ "$count" == "6" ]];then
    #files=$(find $dir  -maxdepth 1 -type f -name RR_\*.mp4)
    #param=`echo $files|sed -e 's/ / -i /g'`
    ffmpeg -y -i RR_0.mp4 -i RR_1.mp4 -i RR_2.mp4 -i RR_3.mp4 -i RR_4.mp4 -i RR_5.mp4 -filter_complex "[0:v]pad=iw*2:ih*3[tr];[tr][1:v]overlay=w[cl];[cl][2:v]overlay=0:h[cr];[cr][3:v]overlay=w:h[bl];[bl][4:v]overlay=0:h*2[br];[br][5:v]overlay=w:h*2" k.mp4
       ffmpeg -v quiet -y -i $res -vf "pad=1920:1080:0:0:gray" $dir/left.mp4
    ffmpeg -v quiet -y -i $dir/left.mp4 -vf "movie='$dir/slides/output.mp4' [mv]; [in][mv] overlay=640:0" $dir/a-c.mp4
    ffmpeg -v quiet -y -i $dir/a-c.mp4 -vf "movie='$dir/chat/chat.mp4' [mv]; [in][mv] overlay=1600:0" $dir/newres.mp4
    #param="RR_0.mp4 -i RR_1.mp4 -i RR_2.mp4 -i RR_3.mp4 -i RR_4.mp4 -i RR_5.mp4"
    #ffmpeg  -v quiet -y -i $param -filter_complex "[0:v]pad=iw*3:ih*2[tl];[tl][1:v]overlay=w[tc];[tc][2:v]overlay=w*2[tr];[tr][3:v]overlay=0:h[bl];[bl][4:v]overlay=w:h[bc];[bc][5:v]overlay=w*2:h" $res
    let k="240*2"
elif [[ "$count" == "7" ]];then
    ffmpeg -y -i RR_0.mp4 -i RR_1.mp4 -i RR_2.mp4 -i RR_3.mp4 -i RR_4.mp4 -i RR_5.mp4 -i RR_6.mp4 -filter_complex "[0:v]pad=iw*2:ih*4[tr];[tr][1:v]overlay=w[cl];[cl][2:v]overlay=0:h[cr];[cr][3:v]overlay=w:h[bl];[bl][4:v]overlay=0:h*2[br];[br][5:v]overlay=w:h*2[bbl];[bbl][6:v]overlay=0:h*3" k.mp4
       ffmpeg -v quiet -y -i $res -vf "pad=1920:1080:0:0:gray" $dir/left.mp4
    ffmpeg -v quiet -y -i $dir/left.mp4 -vf "movie='$dir/slides/output.mp4' [mv]; [in][mv] overlay=640:0" $dir/a-c.mp4
    ffmpeg -v quiet -y -i $dir/a-c.mp4 -vf "movie='$dir/chat/chat.mp4' [mv]; [in][mv] overlay=1600:0" $dir/newres.mp4
elif [[ "$count" == "8" ]];then
    ffmpeg -y -i RR_0.mp4 -i RR_1.mp4 -i RR_2.mp4 -i RR_3.mp4 -i RR_4.mp4 -i RR_5.mp4 -i RR_6.mp4 -i RR_7.mp4 -filter_complex "[0:v]pad=iw*2:ih*4[tr];[tr][1:v]overlay=w[cl];[cl][2:v]overlay=0:h[cr];[cr][3:v]overlay=w:h[bl];[bl][4:v]overlay=0:h*2[br];[br][5:v]overlay=w:h*2[bbl];[bbl][6:v]overlay=0:h*3[bbr];[bbr][7:v]overlay=w:h*3" k.mp4
       ffmpeg -v quiet -y -i $res -vf "pad=1920:1080:0:0:gray" $dir/left.mp4
    ffmpeg -v quiet -y -i $dir/left.mp4 -vf "movie='$dir/slides/output.mp4' [mv]; [in][mv] overlay=640:0" $dir/a-c.mp4
    ffmpeg -v quiet -y -i $dir/a-c.mp4 -vf "movie='$dir/chat/chat.mp4' [mv]; [in][mv] overlay=1600:0" $dir/newres.mp4
fi
let hi="600+$k"
#ffmpeg -v quiet -y -i $dir/top.mp4 -vf "pad=1120:$hi:0:0:white" $dir/notlast.mp4
#ffmpeg -v quiet -y -i $dir/notlast.mp4 -vf "movie='$res' [mv]; [in][mv] overlay=0:600" $dir/result.mp4
ffmpeg -v quiet -y -i $dir/newres.mp4 -i $dir/$wav $dir/$name'.mp4'
exit 1
rm $dir/RR_*.mp4
rm $dir/left.mp4
rm $dir/top.mp4
rm $dir/chat/chat.mp4
rm $dir/slides/output.mp4
rm $dir/notlast.mp4
rm $dir/result.mp4
rm $res






