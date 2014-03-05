import java.util.*;


public class videoSlot {
    public static List<Map<String, List<Long>>> doIt(Map<String, Long> videoStart, Map<String, Long> videoStop, Long startTime, Long stopTime) {
        List<Map<String, List<Long>>> slots = new ArrayList<>();
        while (videoStart.size() > 0) {
            Map<String, List<Long>> sVideoStart = new LinkedHashMap<String, List<Long>>();
            sVideoStart = sortMap.sortByValue(videoStart);
            //System.out.println(sVideoStart);
            Map<String, List<Long>> video = new HashMap<String, List<Long>>();
            for (Map.Entry entry : sVideoStart.entrySet()) {
                List<Long> times = new ArrayList<>();
                times.add((Long) entry.getValue());
                times.add(videoStop.get(entry.getKey()));
                video.put((String) entry.getKey(), times);
            }
            System.out.println(video);
            Map<String, List<Long>> temp = new LinkedHashMap<String, List<Long>>();
            Long last = Long.valueOf(0);
            Long lastTime = Long.valueOf(0);
            Integer c = 0;
            for (Map.Entry entry : sVideoStart.entrySet()) {
                if (c == 0) {
                    List<Long> times = new ArrayList<>();
                    times.add((Long) startTime);
                    times.add((Long) entry.getValue());
                    temp.put("start", times);
                    //last= videoStop.get(entry.getKey());
                    //lastTime=videoStop.get(entry.getKey());
                }
                System.out.println(temp.containsKey(entry.getKey()));
                if (!temp.containsKey(entry.getKey())) {
                    Long start = (Long) entry.getValue();
                    System.out.println("last " + last);
                    System.out.println(start);
                    if (last < start) {
                        List<Long> times = new ArrayList<>();
                        times.add(last);
                        times.add((Long) entry.getValue());
                        List<Long> times2 = new ArrayList<>();
                        times2.add((Long) entry.getValue());
                        times2.add(videoStop.get(entry.getKey()));
                        last = videoStop.get(entry.getKey());
                        System.out.println(times);
                        temp.put((String) entry.getKey(), times2);
                        temp.put("mid" + entry.getKey(), times);
                        videoStart.remove(entry.getKey());
                        lastTime = videoStop.get(entry.getKey());
                    }
                }
                c++;
            }
            List<Long> times = new ArrayList<>();
            times.add(lastTime);
            times.add(stopTime);
            temp.put("end", times);
            System.out.println("videostop" + videoStop);
            System.out.println(videoStart);
            System.out.println(temp);
            slots.add(temp);
        }
        System.out.println(slots);
        return slots;

    }
}
