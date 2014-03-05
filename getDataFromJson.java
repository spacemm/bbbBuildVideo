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
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;

/**
 * Created by space on 2/21/14.
 */

public class getDataFromJson {

    public static LinkedList<HashMap<String, String>> getLOG(String id) {
        try {
            String pathToDump = "/tmp/" + id + "/" + id + ".dump";
            List<String> lines = Files.readAllLines(Paths.get(pathToDump), Charset.forName("UTF-8"));
            LinkedList<HashMap<String, String>> result = new LinkedList<>();

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
                    result.add(map);
                }
            }
            return result;
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
}
