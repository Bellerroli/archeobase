package hu.belrol.archeobase.view;

import hu.belrol.archeobase.App;
import hu.belrol.archeobase.asatas.Asatas;
import hu.belrol.archeobase.asatas.AsatasController;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class AsatasView implements Initializable {

    @FXML
    public TextField megnevezesField;
    public Button ujAsatasButton;
    @FXML
    public TableView<Asatas> table;
    @FXML
    public TableColumn<Asatas, String> megnevezesColumn;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        megnevezesColumn.setCellValueFactory(new PropertyValueFactory("megnevezes"));
        try {
            table.getItems().setAll(
                    AsatasController.getInstance().getAll()
            );
            table.refresh();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @FXML
    public void addAsatas(ActionEvent event) throws SQLException {
        if (megnevezesField.getText().isEmpty()) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Hiba");
            alert.setContentText("Az ásatásnak megnevezése kell, hogy legyen!");
            alert.showAndWait();
            return;
        }
        Asatas asatas = new Asatas();
        asatas.setMegnevezes(megnevezesField.getText());
        AsatasController.getInstance().insert(asatas);
        table.getItems().setAll(
                AsatasController.getInstance().getAll()
        );
        table.refresh();
    }

    @FXML
    public void megnyit(ActionEvent event) throws SQLException, IOException {
        Asatas chosen = table.getSelectionModel().getSelectedItem();
        if (chosen == null) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Hiba");
            alert.setContentText("Válassz ki egy ásatást először!");
            alert.showAndWait();
            return;
        }
        FXMLLoader loader = App.getLoader("stratList");
        Parent root = loader.load();
        StratView controller = loader.getController();
        controller.setChosenAsatas(chosen);
        Stage stage = new Stage();
        controller.setStage(stage);
        stage.setTitle("ArcheoBase - " + chosen.getMegnevezes() + " SNR");
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }
}
