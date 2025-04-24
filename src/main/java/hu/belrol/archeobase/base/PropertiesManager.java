package hu.belrol.archeobase.base;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class PropertiesManager {
    private static Properties properties;

    static {
        InputStream is = PropertiesManager.class.getClassLoader().getResourceAsStream("application.properties");
        properties = new Properties();
        try {
            properties.load(is);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static String getProperty(final String key) {
        return properties.getProperty(key);
    }

    public static int getIntProperty(final String key) {
        return Integer.parseInt(getProperty(key));
    }

}
