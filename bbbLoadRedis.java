import redis.clients.jedis.Jedis;

import java.util.List;
import java.util.Map;
import java.util.Set;

public class bbbLoadRedis {
    private static Jedis cli;

    /**
     * constructor: connect to Redis server and authorization
     *
     * @param host
     * @param port
     */
    public static void RedisConnector(String host, int port) {
        cli = new Jedis(host, port);
        //cli.auth(password);
        try {
            cli.connect();
        } catch (Exception ex) {
            ex.printStackTrace(System.err);
        }
    }

    public static Set<String> getAllKeys(String begin) {
        return cli.keys(begin + "*");
    }

    /**
     * calculate keys count (eg count of active sessions)
     *
     * @return
     */
    public static long getKeysCount() {
        return cli.dbSize();
    }

    private static long num_events_for(String id) {
        return (cli.llen("meeting:" + id + ":recordings"));
    }

    public static List<String> events_for(String id) {
        return (cli.lrange("meeting:" + id + ":recordings", 0, num_events_for(id)));
    }

    public static Map<String, String> events_info_for(String id, String event) {
        return (cli.hgetAll("recording:" + id + ":" + event));
    }
}