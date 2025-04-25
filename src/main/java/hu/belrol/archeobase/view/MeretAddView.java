package hu.belrol.archeobase.view;

import hu.belrol.archeobase.meret.Meret;
import hu.belrol.archeobase.meret.MeretController;
import hu.belrol.archeobase.meret.Mertekegyseg;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.net.URL;
import java.sql.SQLException;
import java.util.List;
import java.util.ResourceBundle;

public class MeretAddView implements Initializable {
    public Text meretText;
    public Text meretTipusText;
    public Text mertekegysegText;
    public Spinner<Integer> meretSpinner;
    public ComboBox<String> meretTipusCB;
    public RadioButton cmRadio;
    public RadioButton mRadio;
    public Button saveMeret;
    private List<Meret> meretHolder;
    private ToggleGroup toggleGroup;
    private Stage stage;

    public void save(ActionEvent event) {
        Meret meret = new Meret();
        meret.setMeret(meretSpinner.getValue());
        meret.setMeretTipus(meretTipusCB.getValue());
        meret.setMertekegyseg(toggleGroup.getSelectedToggle() == cmRadio ? Mertekegyseg.CM : Mertekegyseg.M);
        meretHolder.add(meret);
        stage.close();
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        meretSpinner.setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(1, 1000));
        meretSpinner.setEditable(true);
        meretTipusCB.setEditable(true);
        try {
            meretTipusCB.setItems(FXCollections.observableArrayList(MeretController.getInstance().findAttributes("meret_tipus")));
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        toggleGroup = new ToggleGroup();
        cmRadio.setToggleGroup(toggleGroup);
        mRadio.setToggleGroup(toggleGroup);
        toggleGroup.selectToggle(cmRadio);
    }

    public void setMeretHolder(List<Meret> holder) {
        meretHolder = holder;
    }

    public void setStage(Stage stage) {
        this.stage = stage;
    }
}
