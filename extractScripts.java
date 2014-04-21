import java.io.*;
import java.util.List;

public class extractScripts {


    public static void doIt(String name, String tmpPath) {

        InputStream inputStream = null;
        OutputStream outputStream = null;

        try {
            // read this file into InputStream
            String fullName = "scripts/" + name;
            String outName = tmpPath + "/" + name;
            inputStream = bbbBuildVideo.class.getResourceAsStream(fullName);
            // write the inputStream to a FileOutputStream
            outputStream = new FileOutputStream(new File(outName));

            int read = 0;
            byte[] bytes = new byte[1024];
            //System.out.println(bytes);
            //System.out.println(inputStream.read(bytes));

            try {
            while ((read = inputStream.read(bytes)) != -1) {
                outputStream.write(bytes, 0, read);
            }
            }
            catch (NullPointerException e) {
            e.printStackTrace();
        }

        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (inputStream != null) {
                try {
                    inputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if (outputStream != null) {
                try {
                    // outputStream.flush();
                    outputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }

            }
        }


    }
    public static void all(List<String> scripts,String tmpPath){
        for ( String entry : scripts ){
            extractScripts.doIt(entry, tmpPath);
        }
    }
}

