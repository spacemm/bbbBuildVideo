/**
 * Created by space on 23/02/14.
 */

import javax.json.Json;
import javax.json.stream.JsonParser;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.StringReader;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;

/**
 * Created by space on 2/21/14.
 */

public class getDataFromJson {

    public static ArrayList<HashMap<String, String>> getLOG(String id) {
        try {
            String pathToDump = "/tmp/" + id + "/" + id + ".dump";
            List<String> lines = Files.readAllLines(Paths.get(pathToDump), Charset.forName("UTF-8"));
            ArrayList<HashMap<String, String>> result = new ArrayList<>();

            for (String line : lines) {
                line = line.replaceFirst(".*value\\\":\\{", "");
                line = "{" + line.replaceFirst("\\}.\\\"size.*", "") + "}";
                if (!line.matches(".*type\":\"list.*")) {


                    //System.out.println("LINE :: "+line);

                    //LinkedList<HashMap<String,String>> eventInfo = new LinkedList<HashMap<String, String>>();
                    HashMap<String, String> map = new HashMap<>();
                    StringReader reader = new StringReader(line);
                    String name = "";
                    String value = "";
                    javax.json.stream.JsonParser parser = Json.createParser(reader);
                    while (parser.hasNext()) {
                        JsonParser.Event event = parser.next();
                        //System.out.println("event :: "+event);
                        switch (event) {
                            case KEY_NAME:
                                name = parser.getString();
                                //System.out.print("name :: "+parser.getString());
                                break;
                            case VALUE_STRING:
                                value = parser.getString();
                                //System.out.println("value :: "+parser.getString());

                                break;

                        }
                        if (!name.equals("") && !value.equals("")) {
                            map.put(name, value);
                            //System.out.println("NAME :: "+ name + " VALUE :: "+ value);
                            //System.out.println("__________________________________________________________________");
                            //eventInfo.add(map);
                            name = "";
                            value = "";
                        }
                    }

                    //System.out.println(map);
                    if (!result.contains(map) && map.containsKey("timestamp")) {
                        result.add(map);
                    }
                }
            }
            return sortList(result);
            /*return result;*/
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static ArrayList sortList(ArrayList l) {
        Collections.sort(l, new Comparator<HashMap<String, String>>() {
            public int compare(HashMap<String, String> one, HashMap<String, String> two) {
                //System.out.println(one.get("timestamp")+two.get("timestamp"));
                return one.get("timestamp").compareTo(two.get("timestamp"));
            }
        });
        return l;
    }



}
