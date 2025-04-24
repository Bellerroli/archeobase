package hu.belrol.archeobase;

import hu.belrol.archeobase.base.Utility;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.SQLException;


/**
 * JavaFX App
 */
public class App extends Application {
    private static Scene scene;
    private static String lastScene;

    @Override
    public void start(Stage stage) throws IOException {
        stage.setTitle("ArcheoBase - Ásatások");
        scene = new Scene(loadFXML("primary"), 640, 480);
        stage.setScene(scene);
        stage.show();
    }

    public static void setRoot(String fxml) throws IOException {
        lastScene = fxml;
        scene.setRoot(loadFXML(fxml));
    }

    public static Parent loadFXML(String fxml) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(App.class.getResource(fxml + ".fxml"));
        return fxmlLoader.load();
    }

    public static FXMLLoader getLoader(String fxml) throws IOException {
        return new FXMLLoader(App.class.getResource(fxml + ".fxml"));
    }

    public static void main(String[] args) throws SQLException, IOException {
        Utility.getInstance().initDB();
        launch();
    }

}