package Configuration;

/**
 * @author Robin Duda
 *
 * Configuration File.
 */
public class Config {
    public static final byte[] SERVER_SECRET = "!!!!!!!!!!!server_secret!!!!!!!!!!".getBytes();
    public static final int WEB_PORT = 7670;
    public static final String CONNECTION_STRING = "mongodb://localhost:27017/";
    public static final String DB_NAME = "vote";
    public static final String SERVER_NAME = "receiver.server";
}
