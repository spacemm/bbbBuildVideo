import java.io.IOException;
import java.util.*;

public class bbbBuildVideo {
    public static void main(String[] args) throws InterruptedException {

        String id = null;
        if (System.getProperty("id") != null) {
            try {
                id = System.getProperty("id");
            } catch (Exception e) {
                System.out.println("You must specify the id!");
                return;
            }
        }

        String tmpPath = "/tmp/";
        String tmp = "/tmp/" + id + "/";
        String exe = "/bin/sh";
        String getdata = "getdata.sh";

        extractScripts.doIt(getdata, "/tmp/");

        String concatWebCamAndSlides = "concatWebCamAndSlides.sh";
        String concatwebcams = "concatwebcams.sh";
        String create_videos_from_text = "create_videos_from_text.sh";
        String create_videos_from_slides = "create_videos_from_slides.sh";
        String addblacktovideo = "addblacktovideo.sh";
        String concatwebcam = "concatwebcam.sh";
        String concatWebCamAndSlidesAndChat = "concatWebCamAndSlidesAndChat.sh";
        String concatChat = "concatChat.sh";
        String concatSlides = "concatSlides.sh";
        String convert_pdf_to_png = "convert_pdf_to_png.sh";
        String changeRESofVIDEO = "changeRESofVIDEO.sh";
        String buildcam="buidcam.sh";
        extractScripts.doIt(buildcam, "/tmp/");
        extractScripts.doIt(concatChat, "/tmp/");
        extractScripts.doIt(concatSlides, "/tmp/");
        extractScripts.doIt(create_videos_from_text, "/tmp/");
        extractScripts.doIt(addblacktovideo, "/tmp/");
        extractScripts.doIt(concatwebcam, "/tmp/");
        extractScripts.doIt(concatwebcams, "/tmp/");
        extractScripts.doIt(create_videos_from_slides, "/tmp/");
        extractScripts.doIt(convert_pdf_to_png, "/tmp/");
        extractScripts.doIt(changeRESofVIDEO, "/tmp/");


        try {
            runProcess.runNameTwoParams(getdata, exe, id, "192.168.11.221");
        } catch (IOException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        }
        ArrayList<HashMap<String, String>> dataList = getDataFromJson.getLOG(id);
        //System.out.println(l);
        ArrayList<HashMap<String, String>> sublist = new ArrayList<>();
        HashMap<String, ArrayList<HashMap<String, String>>> result = new HashMap<String, ArrayList<HashMap<String, String>>>();
        Long timestampOld = 0L;
        for (HashMap<String, String> map : dataList) {
            String timestamp = "";
            for (Map.Entry entry : map.entrySet()) {
                if (entry.getKey().toString().equalsIgnoreCase("timestamp")) {
                    timestamp = entry.getValue().toString();
                }
            }

            if ((Long.parseLong(timestamp) - timestampOld) > 5) {
                sublist.add(map);
                timestampOld = Long.parseLong(timestamp);

                //System.out.println("sublist.size :: " + sublist.size());
                //System.out.println("SUBLIST");
                //System.out.println(sublist);
                if (map.containsKey("filename") && map.containsValue("StopRecordingEvent")) {
                    System.out.println("sublist.size :: " + sublist.size());
                    System.out.println("WORK");
                    work(id, tmpPath, tmp, exe, concatwebcams, create_videos_from_text, create_videos_from_slides, addblacktovideo, concatwebcam, concatChat, concatSlides, convert_pdf_to_png, changeRESofVIDEO, sublist);
                    sublist.clear();
                }


            }
        }


}
        //splitlist(dataList);
        //System.out.println(dataList);
        //work(id, chat, presentation, videoStart, videoStop, startTime, stopTime, tmpPath, tmp, exe, concatwebcams, create_videos_from_text, create_videos_from_slides, addblacktovideo, concatwebcam, concatChat, concatSlides, convert_pdf_to_png, changeRESofVIDEO, dataList);
    //}


    private static void work(String id, String tmpPath, String tmp, String exe, String concatwebcams, String create_videos_from_text, String create_videos_from_slides, String addblacktovideo, String concatwebcam, String concatChat, String concatSlides, String convert_pdf_to_png, String changeRESofVIDEO, ArrayList<HashMap<String, String>> dataList) throws InterruptedException {
        Map<Long, String> chat = new HashMap<Long, String>();
        Map<Long, String> presentation = new HashMap<Long, String>();
        Map<String, Long> videoStart = new HashMap<String, Long>();
        Map<String, Long> videoStop = new HashMap<String, Long>();
        Long startTime = (long) 0;
        Long stopTime = (long) 0;
        String wavFile = "";
        System.out.println(dataList.size());
        for (HashMap<String, String> map : dataList) {
            if(startTime==0){
              for (Map.Entry entry : map.entrySet()) {
                    if (entry.getKey().toString().equalsIgnoreCase("timestamp")) {
                        startTime = Long.valueOf(entry.getValue().toString());
                        System.out.println("time" + startTime);
                    }
            }
                chat.put(startTime, " ");
                presentation.put(startTime, "0");
            }
            //System.out.println(map);
            if (map.containsKey("filename")) {
                System.out.println("File::" + map);
                for (Map.Entry entry : map.entrySet()) {
                    if (entry.getKey().toString().equalsIgnoreCase("filename")) {
                        wavFile = entry.getValue().toString();
                        System.out.println("wav" + wavFile);
                    }
                }
                //chat.put(startTime, " ");
                //presentation.put(startTime, "0");
            }
            if (map.containsKey("eventName") && map.containsValue("StartRecordingEvent")) {
                System.out.println("MAP::" + map);
                for (Map.Entry entry : map.entrySet()) {
                    if (entry.getKey().toString().equalsIgnoreCase("timestamp")) {
                        startTime = Long.valueOf(entry.getValue().toString());
                        System.out.println("time" + startTime);
                    }
                }
                chat.put(startTime, " ");
                presentation.put(startTime, "0");
            }
            if (map.containsKey("module") && map.containsValue("WEBCAM") && map.containsValue("StartWebcamShareEvent")) {
                System.out.println(map);
                Long time = null;
                String message = "";
                for (Map.Entry entry : map.entrySet()) {
                    if (entry.getKey().toString().equalsIgnoreCase("timestamp")) {
                        time = Long.valueOf(entry.getValue().toString());
                        System.out.println("time" + time);
                    }
                    if (entry.getKey().toString().equalsIgnoreCase("stream")) {
                        message = entry.getValue().toString();
                        System.out.println("mes" + message);
                    }
                }
                videoStart.put(message, time);
            }
            if (map.containsKey("module") && map.containsValue("WEBCAM") && map.containsValue("StopWebcamShareEvent")) {
                System.out.println(map);
                Long time = null;
                String message = "";
                for (Map.Entry entry : map.entrySet()) {
                    if (entry.getKey().toString().equalsIgnoreCase("timestamp")) {
                        time = Long.valueOf(entry.getValue().toString());
                        System.out.println("time" + time);
                    }
                    if (entry.getKey().toString().equalsIgnoreCase("stream")) {
                        message = entry.getValue().toString();
                        System.out.println("mes" + message);
                    }
                }
                videoStop.put(message, time);
            }
            if (map.containsKey("module") && map.containsValue("CHAT")) {
                System.out.println(map);
                Long time = (long) 0;
                String message = "";
                String sender = "";
                for (Map.Entry entry : map.entrySet()) {
                    if (entry.getKey().toString().equalsIgnoreCase("timestamp")) {
                        time = Long.valueOf(entry.getValue().toString());
                        System.out.println("time" + time);
                    }
                    if (entry.getKey().toString().equalsIgnoreCase("sender")) {
                        sender = entry.getValue().toString() + " : ";
                        System.out.println("mes" + message);
                    }
                    if (entry.getKey().toString().equalsIgnoreCase("message")) {
                        message = entry.getValue().toString();
                        System.out.println("mes" + message);
                    }
                }
                chat.put(time, sender + message);
            }
            if (map.containsKey("module") && map.containsValue("PRESENTATION") && map.containsValue("ConversionCompletedEvent")) {
                Long time = (long) 0;
                String presentationName = "";
                for (Map.Entry entry : map.entrySet()) {
                    if (entry.getKey().toString().equalsIgnoreCase("timestamp")) {
                        time = Long.valueOf(entry.getValue().toString());
                        System.out.println("time" + time);
                    }
                    if (entry.getKey().toString().equalsIgnoreCase("presentationName")) {
                        presentationName = entry.getValue().toString();
                        System.out.println("pres" + presentationName);
                    }
                }
                presentation.put(time, presentationName);
            }
            if (map.containsKey("module") && map.containsValue("PRESENTATION") && map.containsValue("GotoSlideEvent")) {
                Long time = (long) 0;
                String slideN = "";
                for (Map.Entry entry : map.entrySet()) {
                    if (entry.getKey().toString().equalsIgnoreCase("timestamp")) {
                        time = Long.valueOf(entry.getValue().toString());
                        System.out.println("time" + time);
                    }
                    if (entry.getKey().toString().equalsIgnoreCase("slide")) {
                        slideN = entry.getValue().toString();
                        System.out.println("slide" + slideN);
                    }
                }
                presentation.put(time, slideN);
            }
            if (map.containsKey("eventName") && map.containsValue("StopRecordingEvent")) {
                System.out.println(map);
                Long time = null;
                for (Map.Entry entry : map.entrySet()) {
                    if (entry.getKey().toString().equalsIgnoreCase("timestamp")) {
                        stopTime = Long.valueOf(entry.getValue().toString());
                        System.out.println("time" + stopTime);
                    }
                }
                chat.put(stopTime, "StopRecording");
                presentation.put(stopTime, "0");
            }
        }
        Double totallong = (Double.valueOf(stopTime) - Double.valueOf(startTime)) / 1000.0;
        TreeMap<Long, String> sorted_presentation = new TreeMap<Long, String>(presentation);
        sorted_presentation.putAll(presentation);
        TreeMap<Long, String> sorted_chat = new TreeMap<Long, String>(chat);
        sorted_chat.putAll(chat);
        System.out.println("STARTTIME:::::::"+startTime+"STOPTIME:::::::::::"+stopTime);
        System.out.println("CHAT:::::::"+sorted_chat);
        System.out.println("VIDEOSTART:::::::"+videoStart);
        System.out.println("VIDEOSTOP:::::::"+videoStop);
        System.out.println("PRESENT:::::::"+sorted_presentation);
        List<Map<String, List<Long>>> slots = videoSlot.doIt(videoStart, videoStop, startTime, stopTime);
        System.out.print(slots);
        String resolutinWebcams="";
        String resolutionChat="";
        String resoloutionPdf="";
        if (slots.size()<2){
            resolutinWebcams="640x480";
            resolutionChat="640x590";
            resoloutionPdf="1280x1080";
            System.out.print(resolutinWebcams);
        }
        else {
            resolutinWebcams="320x240";
            resolutionChat="320x1080";
            resoloutionPdf="960x1080";
            System.out.print(resolutinWebcams);
        }
        try {
            runProcess.runNameTwoParams(changeRESofVIDEO, exe, id, resolutinWebcams);
        } catch (IOException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        }

        float lenght = (float) 0.0;
        long currentTime = (long) 0.0;
        String msg = "";
        for (Map.Entry entry : sorted_chat.entrySet()) {
            if (currentTime > 0.0) {
                lenght = Float.valueOf(Long.valueOf(entry.getKey().toString()) - currentTime);
                System.out.println(currentTime + " " + Long.valueOf(entry.getKey().toString()));
                System.out.println("len " + lenght / 1000.0 + "text " + entry.getValue());
                try {
                    List<String> args1 = new ArrayList<>();
                    args1.add(exe);
                    args1.add(tmpPath + create_videos_from_text);
                    args1.add(String.valueOf(lenght / 1000.0));
                    args1.add(tmp);
                    args1.add(msg);
                    args1.add(String.valueOf(currentTime));
                    args1.add(resolutionChat);
                    System.out.println(args1);
                    ProcessBuilder pb = new ProcessBuilder(args1);
                    Process p = pb.start();
                    p.waitFor();
                    System.out.println(args1);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            currentTime = Long.valueOf(entry.getKey().toString());
            Calendar calendar = Calendar.getInstance();
            calendar.setTimeInMillis((Long) entry.getKey());
            System.out.println("TIME HH" + calendar.get(Calendar.HOUR_OF_DAY) + ":" + calendar.get(Calendar.MINUTE));
            String hhmm = calendar.get(Calendar.HOUR_OF_DAY) + ":" + calendar.get(Calendar.MINUTE) + " ";
            if (entry.getKey() == startTime) {
                msg = msg + entry.getValue().toString() + "\n";
            } else {
                msg = msg + hhmm + entry.getValue().toString() + "\n";
            }
        }
        currentTime = 0;
        String slide = "0";
        System.out.println(sorted_presentation);
        String dir = tmp + "slides/" + "default";
        try {
            runProcess.runNameTwoParams(convert_pdf_to_png, exe, id, resoloutionPdf);
        } catch (IOException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        }
        for (Map.Entry entry : sorted_presentation.entrySet()) {
            try {
                int num = Integer.parseInt(String.valueOf(entry.getValue()));
                if (currentTime > 0.0) {
                    lenght = Float.valueOf(Long.valueOf(entry.getKey().toString()) - currentTime);
                    System.out.println(currentTime + " " + Long.valueOf(entry.getKey().toString()));
                    System.out.println("len " + lenght / 1000.0 + "dir" + dir + "slide " + entry.getValue());
                    List<String> args1 = new ArrayList<>();
                    args1.add(exe);
                    args1.add(tmpPath + create_videos_from_slides);
                    args1.add(String.valueOf(lenght / 1000.0));
                    args1.add(id);
                    args1.add(dir + "/res/" + entry.getValue() + ".png");
                    args1.add(String.valueOf(currentTime));
                    System.out.println(args1);
                    ProcessBuilder pb = new ProcessBuilder(args1);
                    Process p = pb.start();
                    p.waitFor();
                    slide = String.valueOf(entry.getValue());
                }

            } catch (NumberFormatException e) {
                lenght = Float.valueOf(Long.valueOf(entry.getKey().toString()) - currentTime);
                List<String> args1 = new ArrayList<>();
                args1.add(exe);
                args1.add(tmpPath + create_videos_from_slides);
                args1.add(String.valueOf(lenght / 1000.0));
                args1.add(id);
                args1.add(dir + "/res/" + slide + ".png");
                args1.add(String.valueOf(currentTime));
                System.out.println(args1);
                ProcessBuilder pb = new ProcessBuilder(args1);
                Process p = null;
                try {
                    p = pb.start();
                } catch (IOException e1) {
                    e1.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
                }
                p.waitFor();
                dir = tmp + "slides/" + entry.getValue();
                // not an integer!
            } catch (IOException e) {
                e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
            }
            currentTime = Long.valueOf(entry.getKey().toString());
        }
        //Add black to video from begin

        for (Map.Entry entry : videoStart.entrySet()) {
            String name = String.valueOf(entry.getKey());
            String lenghtS = String.valueOf((Long.valueOf(String.valueOf(entry.getValue())) - startTime) / 1000.0);
            if (Float.valueOf(lenghtS) >= 1.0) {
                List<String> args1 = new ArrayList<>();
                args1.add(exe);
                args1.add(tmpPath + addblacktovideo);
                args1.add("begin");
                args1.add(tmp);
                args1.add(name + ".mp4");
                args1.add(lenghtS);
                args1.add(resolutinWebcams);
                System.out.println(args1);
                ProcessBuilder pb = new ProcessBuilder(args1);
                Process p = null;
                try {
                    p = pb.start();
                } catch (IOException e) {
                    e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
                }
                p.waitFor();
            }
        }
        for (Map.Entry entry : videoStop.entrySet()) {
            System.out.println(entry);
            String name = String.valueOf(entry.getKey());
            String lenghtS = String.valueOf((stopTime - Long.valueOf(String.valueOf(entry.getValue()))) / 1000.0);
            if (Float.valueOf(lenghtS) >= 1.0) {
                List<String> args1 = new ArrayList<>();
                args1.add(exe);
                args1.add(tmpPath + addblacktovideo);
                args1.add("tail");
                args1.add(tmp);
                args1.add(name + ".mp4");
                args1.add(lenghtS);
                args1.add(resolutinWebcams);
                System.out.println(args1);
                ProcessBuilder pb = new ProcessBuilder(args1);
                Process p = null;
                try {
                    p = pb.start();
                } catch (IOException e) {
                    e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
                }
                p.waitFor();
            }
        }
        for (Map.Entry entry : videoStart.entrySet()) {
            String name = String.valueOf(entry.getKey());
            List<String> args1 = new ArrayList<>();
            args1.add(exe);
            args1.add(tmpPath + concatwebcam);
            args1.add(id);
            args1.add(name + ".mp4");
            args1.add(String.valueOf(totallong));
            System.out.println(args1);
            ProcessBuilder pb = new ProcessBuilder(args1);
            Process p = null;
            try {
                p = pb.start();
            } catch (IOException e) {
                e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
            }
            p.waitFor();
        }
        System.out.println("start:" + startTime + " stop:" + stopTime + " long:" + totallong);
        try {
            runProcess.runName(concatChat, id, exe);
        } catch (IOException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        }
        try {
            runProcess.runName(concatSlides, id, exe);
        } catch (IOException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        }

        if (slots.size() > 8) {

            System.out.println("Too much parallel videos");
            System.out.println(slots + "               " + slots.size());

            System.exit(1);
        }
        Integer k = 0;
        System.out.println(slots + "               " + slots.size());
        for (Map<String, List<Long>> slot : slots) {
            System.out.println(slot);
            Integer c = 0;
            for (Map.Entry entry : slot.entrySet()) {
                List<String> args1 = new ArrayList<>();
                System.out.println(entry);
                args1.add(exe);
                args1.add(tmpPath + "buidcam.sh");
                args1.add(id);
                args1.add(String.valueOf(c));
                args1.add(String.valueOf(entry.getKey()));
                long stop;
                if (((List<Long>) entry.getValue()).get(1) == null) {
                    stop = stopTime;
                } else {
                    stop = ((List<Long>) entry.getValue()).get(1);
                }
                long start;
                if (((List<Long>) entry.getValue()).get(0) == null) {
                    start = startTime;
                } else {
                    start = ((List<Long>) entry.getValue()).get(0);
                }
                args1.add(String.valueOf(stop / 1000 - start / 1000));
                System.out.println("args::" + args1);
                ProcessBuilder pb = new ProcessBuilder(args1);
                Process p = null;
                try {
                    p = pb.start();
                } catch (IOException e) {
                    e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
                }
                p.waitFor();
                c++;

            }
            List<String> args1 = new ArrayList<>();
            args1.add(exe);
            args1.add(tmpPath + "concatwebcam.sh");
            args1.add(id);
            args1.add("RR_" + String.valueOf(k));
            args1.add(String.valueOf(totallong));
            System.out.println(args1);
            ProcessBuilder pb = new ProcessBuilder(args1);
            Process p = null;
            try {
                p = pb.start();
            } catch (IOException e) {
                e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
            }
            p.waitFor();
            k++;

        }
        try {
            runProcess.runName3Params(concatwebcams, exe, id, String.valueOf(slots.size()), wavFile);
        } catch (IOException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        }
    }
}
