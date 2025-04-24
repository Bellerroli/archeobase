package hu.belrol.archeobase.base;

import hu.belrol.archeobase.db.DatabaseManager;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

public class Utility {
    private static Utility instance;

    private Utility() {
    }

    public static Utility getInstance() {
        if (instance == null) {
            instance = new Utility();
        }
        return instance;
    }

    public void initDB() throws IOException, SQLException {
        String initScript = new String(getClass().getClassLoader().getResourceAsStream("initAsatas.sql").readAllBytes(),
                StandardCharsets.UTF_8);
        String initScript2 = new String(getClass().getClassLoader().getResourceAsStream("initStrat.sql").readAllBytes(),
                StandardCharsets.UTF_8);
        String initScript3 = new String(getClass().getClassLoader().getResourceAsStream("initMeret.sql").readAllBytes(),
                StandardCharsets.UTF_8);

        Connection connection = DatabaseManager.getConnection();
        Statement statement = connection.createStatement();
        statement.execute(initScript);
        statement.execute(initScript2);
        statement.execute(initScript3);

        statement.close();
        connection.close();
    }
}
