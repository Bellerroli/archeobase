package hu.belrol.archeobase.view;

import hu.belrol.archeobase.App;
import hu.belrol.archeobase.asatas.Asatas;
import hu.belrol.archeobase.meret.Meret;
import hu.belrol.archeobase.meret.MeretController;
import hu.belrol.archeobase.strat.StratController;
import hu.belrol.archeobase.strat.StratDto;
import hu.belrol.archeobase.strat.StratMapper;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import java.util.stream.Collectors;

public class StratInsertView implements Initializable {
    public Text SNRtext;
    public Text OBNRtext;
    public Text tipusText;
    public Text korText;
    public Text korszakText;
    public Text allapotText;
    public Text meretText;
    public Text meretTipusText;
    public Text mertekegysegText;
    public Text feltDatText;
    public Text leirasText;
    public Spinner<Integer> SNRtextField;
    public Spinner<Integer> OBNRtextField;
    public ComboBox<String> tipusCB;
    public ComboBox<String> korCB;
    public ComboBox<String> korszakCB;
    public ComboBox<String> allapotCB;
    public Spinner<Double> meretSpinner;
    public ComboBox<String> meretTipusCB;
    public ComboBox<String> mertekegysegCB;
    public TextArea leirasTA;
    public Button saveButton;
    public Button clearButton;
    public CheckBox feltDatCheckbox;
    public Text meretErtek;
    public Button meretAdd;
    public Button meretRefresh;
    private StratDto updateable;
    private Asatas asatas;
    private Stage stage;
    private StratController controller = StratController.getInstance();
    private List<Meret> meretHolder = new ArrayList<>();

    public void setUpdateable(StratDto updateable) {
        this.updateable = updateable;
        meretHolder = updateable.getMeretList();
        refreshFields();
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        try {
            tipusCB.setEditable(true);
            tipusCB.setItems(FXCollections.observableArrayList(controller.findAllAttributes("tipus")));
            korCB.setEditable(true);
            korCB.setItems(FXCollections.observableArrayList(controller.findAllAttributes("kor")));
            korszakCB.setEditable(true);
            korszakCB.setItems(FXCollections.observableArrayList(controller.findAllAttributes("korszak")));
            allapotCB.setItems(FXCollections.observableArrayList("feltárt", "részben feltárt", "feltáratlan"));
            SNRtextField.setEditable(true);
            SNRtextField.setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(1, 10000));
            OBNRtextField.setEditable(true);
            OBNRtextField.setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(0, 10000));

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void refreshFields() {
        SNRtextField.getValueFactory().setValue(updateable.getSNR());
        OBNRtextField.getValueFactory().setValue(updateable.getOBNR());
        tipusCB.setValue(updateable.getTipus());
        korCB.setValue(updateable.getKor());
        korszakCB.setValue(updateable.getKorszak());
        allapotCB.setValue(updateable.getAllapot());
        leirasTA.setText(updateable.getLeiras());
        feltDatCheckbox.setSelected(updateable.isFeltDat());
        meretErtek.setText(
                meretHolder.stream()
                        .map(Meret::toString)
                        .collect(Collectors.joining("\n"))
        );
    }

    public void save(ActionEvent event) throws SQLException {
        if (updateable != null) {
            updateStrat();
            return;
        }
        System.out.println("Save!");
        StratDto strat = new StratDto();
        setValues(strat);
        strat = StratMapper.getInstance().toDto(controller.insertStrat(StratMapper.getInstance().toStrat(strat)));
        for (Meret meret : meretHolder) {
            meret.setStratId(strat.getId());
            MeretController.getInstance().create(meret);
        }
        succesfulSaveAlert(strat);
    }

    private void updateStrat() throws SQLException {
        System.out.println("Update!");
        setValues(updateable);
        StratDto strat = StratMapper.getInstance().toDto(controller.updateStrat(StratMapper.getInstance().toStrat(updateable)));
        meretHolder.stream()
                .filter(meret -> meret.getId() == 0)
                .forEach(meret -> {
                    try {
                        meret.setStratId(strat.getId());
                        MeretController.getInstance().create(meret);
                    } catch (SQLException e) {
                        throw new RuntimeException(e);
                    }
                });
        succesfulSaveAlert(strat);
    }

    private void succesfulSaveAlert(StratDto strat) {
        if (strat != null && strat.getId() != 0) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Mentés");
            alert.setHeaderText(null);
            alert.setContentText("Sikeres mentés!");
            alert.showAndWait();
        }
        stage.close();
    }

    private void setValues(StratDto strat) {
        strat.setSNR(SNRtextField.getValue());
        strat.setOBNR(OBNRtextField.getValue());
        strat.setTipus(tipusCB.getValue());
        strat.setKor(korCB.getValue());
        strat.setKorszak(korszakCB.getValue());
        strat.setAllapot(allapotCB.getValue());
        strat.setLeiras(leirasTA.getText());
        strat.setFeltDat(feltDatCheckbox.isSelected());
        strat.setAsatasId(asatas.getId());
        strat.setMeretList(meretHolder);
    }

    public void setAsatas(Asatas asatas) {
        this.asatas = asatas;
    }

    public void setStage(Stage stage) {
        this.stage = stage;
    }

    public void openMeretAdd(ActionEvent event) throws IOException {
        Stage stage = new Stage();
        FXMLLoader loader = App.getLoader("meretAdd");
        Parent root = loader.load();
        MeretAddView meretAddView = loader.getController();
        meretAddView.setMeretHolder(meretHolder);
        meretAddView.setStage(stage);
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    public void refreshMeretText(ActionEvent event) {
        meretErtek.setText(
                meretHolder.stream()
                        .map(meret -> meret.getMeretTipus() + ": " + meret.getMeret() + " " + meret.getMertekegyseg())
                        .collect(Collectors.joining("\n"))
        );
    }
}
