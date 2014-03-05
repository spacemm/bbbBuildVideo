import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;


public class runProcess {

    private static void runIt(List<String> args1) throws IOException, InterruptedException {
        ProcessBuilder pb = new ProcessBuilder(args1);
        File encodingFile = new File("/tmp/.encoding");
        encodingFile.createNewFile();
        pb.redirectErrorStream(true);
        pb.redirectInput(ProcessBuilder.Redirect.PIPE); //optional, default behavior
        pb.redirectOutput(encodingFile);
        //pb.redirectOutput(ProcessBuilder.Redirect.INHERIT);
        Process p;
        //pb.inheritIO();
        p = pb.start();
        p.waitFor();
        encodingFile.delete();
    }

    public static void runName(String name, String id, String exe) throws IOException, InterruptedException {
        List<String> args1 = new ArrayList<>();
        String tmpPath = "/tmp/";
        args1.add(exe);
        args1.add(tmpPath + name);
        args1.add(id);
        System.out.println(args1);
        runIt(args1);
    }

    public static void runNameTwoParams(String name, String exe, String id, String param) throws IOException, InterruptedException {
        List<String> args1 = new ArrayList<>();
        String tmpPath = "/tmp/";
        args1.add(exe);
        args1.add(tmpPath + name);
        args1.add(id);
        args1.add(param);
        System.out.println(args1);
        runIt(args1);
    }

}