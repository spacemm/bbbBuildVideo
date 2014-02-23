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

public class testJSON {

    public static ArrayList<LinkedList<HashMap<String, String>>> getLOG(String[] args) {
        try {
            List<String> lines = Files.readAllLines(Paths.get("/home/space/1.dump"), Charset.forName("UTF-8"));
            ArrayList<LinkedList<HashMap<String, String>>> result = new ArrayList<>();

            for (String line : lines) {
                line = line.replaceFirst(".*value\\\":\\{", "");
                line = "{"+line.replaceFirst("\\}.\\\"size.*","")+"}";
                if(line.matches(".*type\":\"list.*")){
                    break;
                }

                System.out.println("LINE :: "+line);

                LinkedList<HashMap<String,String>> eventInfo = new LinkedList<HashMap<String, String>>();
                StringReader reader = new StringReader(line);
                String name = "";
                String value = "";
                javax.json.stream.JsonParser parser = Json.createParser(reader);
                while (parser.hasNext()) {
                    JsonParser.Event event = parser.next();
                    //System.out.println("event :: "+event);
                    HashMap<String, String> map = new HashMap<String, String>();
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
                    if (!name.equals("") && !value.equals("")){
                    map.put(name, value);
                    //System.out.println("NAME :: "+ name + " VALUE :: "+ value);
                    //System.out.println("__________________________________________________________________");
                    eventInfo.add(map);
                    name = "";
                    value = "";
                    }
                }
                System.out.println(eventInfo);
                result.add(eventInfo);

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
