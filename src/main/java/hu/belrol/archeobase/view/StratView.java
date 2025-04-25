package hu.belrol.archeobase.view;

import hu.belrol.archeobase.App;
import hu.belrol.archeobase.asatas.Asatas;
import hu.belrol.archeobase.base.ExcelWriter;
import hu.belrol.archeobase.meret.MeretController;
import hu.belrol.archeobase.strat.Strat;
import hu.belrol.archeobase.strat.StratController;
import hu.belrol.archeobase.strat.StratDao;
import hu.belrol.archeobase.strat.StratDto;
import javafx.beans.property.ReadOnlyStringWrapper;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableRow;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.text.Text;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.ResourceBundle;
import java.util.stream.Collectors;

public class StratView implements Initializable {
    @FXML
    public TableView<StratDto> table;
    @FXML
    public TableColumn<StratDto, Integer> SNRColumn;
    public TableColumn<StratDto, Integer> OBNRColumn;
    public TableColumn<StratDto, String> tipusColumn;
    public TableColumn<StratDto, String> korColumn;
    public TableColumn<StratDto, String> korszakColumn;
    public TableColumn<StratDto, String> allapotColumn;
    public TableColumn<StratDto, String> meretColumn;
    public TableColumn<StratDto, String> feltDatColumn;
    public Text fileAbsolutePath;
    private Asatas chosenAsatas;
    private FileChooser fileChooser = new FileChooser();
    private File chosenExcel;
    private Stage stage;

    public StratView() {
    }

    public void setChosenAsatas(Asatas chosenAsatas) throws SQLException {
        this.chosenAsatas = chosenAsatas;
        refreshTable();
    }

    @FXML
    private void refreshTable() throws SQLException {
        table.getItems().setAll(
                StratController.getInstance().findAllStratDtoByAsatasId(chosenAsatas.getId())
        );
        table.refresh();
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        SNRColumn.setCellValueFactory(new PropertyValueFactory<>("SNR"));
        OBNRColumn.setCellValueFactory(new PropertyValueFactory<>("OBNR"));
        tipusColumn.setCellValueFactory(new PropertyValueFactory<>("tipus"));
        allapotColumn.setCellValueFactory(new PropertyValueFactory<>("allapot"));
        korColumn.setCellValueFactory(new PropertyValueFactory<>("kor"));
        korszakColumn.setCellValueFactory(new PropertyValueFactory<>("korszak"));
        meretColumn.setCellValueFactory(cellValue -> {
            StratDto value = cellValue.getValue();
            return new ReadOnlyStringWrapper(value.getMeretList().stream()
                    .map(meret -> meret.getMeretTipus() + ": " + meret.getMeret() + " " + meret.getMertekegyseg())
                    .collect(Collectors.joining("\n")));
        });
        feltDatColumn.setCellValueFactory(cellValue -> new ReadOnlyStringWrapper(cellValue.getValue().isFeltDat() ? "Igen" : "Nem"));
        table.setRowFactory(tv -> {
            TableRow<StratDto> row = new TableRow<>();
            row.setOnMouseClicked(event -> {
                if (event.getClickCount() == 2 && !row.isEmpty()) {
                    try {
                        openSNR();
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                }
            });
            return row;
        });
    }

    public void openSNR() throws IOException {
        StratDto strat = table.getSelectionModel().getSelectedItem();
        if (strat == null) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Hiba");
            alert.setHeaderText(null);
            alert.setContentText("Válassz stratot!");
            alert.showAndWait();
            return;
        }
        FXMLLoader loader = App.getLoader("stratInsert");
        Parent root = loader.load();
        StratInsertView controller = loader.getController();
        controller.setUpdateable(strat);
        controller.setAsatas(chosenAsatas);

        Stage stage = new Stage();
        controller.setStage(stage);
        stage.setTitle("ArcheoBase - SNR módosítás");
        stage.setScene(new Scene(root));
        stage.show();
    }

    public void openNewSNR(ActionEvent event) throws IOException {
        FXMLLoader loader = App.getLoader("stratInsert");
        Parent root = loader.load();
        StratInsertView controller = loader.getController();
        controller.setAsatas(chosenAsatas);
        Stage stage = new Stage();
        controller.setStage(stage);
        stage.setTitle("ArcheoBase - SNR módosítás");
        stage.setScene(new Scene(root));
        stage.show();
    }

    public void loadExcel(ActionEvent event) {
        if (chosenExcel == null) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Hiba");
            alert.setHeaderText(null);
            alert.setContentText("Válassz ki egy xlsx fájlt először!");
            alert.showAndWait();
            return;
        }

        try (XSSFWorkbook workbook = new XSSFWorkbook(chosenExcel)) {
            XSSFSheet sheet = workbook.getSheetAt(0);
            Iterator<Row> rowIterator = sheet.rowIterator();
            Row row = rowIterator.next();
            if (!row.getCell(1).getStringCellValue().trim().equalsIgnoreCase("SNR") ||
                    !row.getCell(0).getStringCellValue().trim().equalsIgnoreCase("OBNR") ||
                    !row.getCell(2).getStringCellValue().trim().equalsIgnoreCase("objektum típusa") ||
                    !row.getCell(3).getStringCellValue().trim().equalsIgnoreCase("régészeti korszak")) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Hiba");
                alert.setHeaderText(null);
                alert.setContentText("A táblázat beosztása nem megfelelő! A fejléc sorrendje: 'SNR, OBNR, objektum típusa, régészeti korszak'");
                alert.showAndWait();
                chosenExcel = null;
                return;
            }
            List<Strat> stratList = new ArrayList<>();
            while (rowIterator.hasNext()) {
                row = rowIterator.next();
                Strat strat = new Strat();
                strat.setSNR((int) row.getCell(1).getNumericCellValue());
                strat.setOBNR((int) row.getCell(0).getNumericCellValue());
                strat.setTipus(row.getCell(2).getStringCellValue());
                strat.setKor(row.getCell(3).getStringCellValue());
                strat.setAsatasId(chosenAsatas.getId());
                stratList.add(strat);
            }

            for (Strat strat : stratList) {
                StratDao.getInstance().insertPartialStrat(strat);
            }
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Hiba");
            alert.setHeaderText(null);
            alert.setContentText("Sikeres betöltés!");
            alert.showAndWait();
            refreshTable();
        } catch (IOException | InvalidFormatException e) {
            throw new RuntimeException(e);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void chooseFile(ActionEvent event) {
        File file = fileChooser.showOpenDialog(stage);
        if (file == null) return;
        String[] split = file.getName().split("\\.");
        if (!split[split.length - 1].equals("xlsx")) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Hiba");
            alert.setHeaderText(null);
            alert.setContentText("A fájl nem xlsx kiterjesztésű!");
            alert.showAndWait();
            return;
        }
        chosenExcel = file;
        fileAbsolutePath.setText(file.getAbsolutePath());
    }

    public void setStage(Stage stage) {
        this.stage = stage;
    }

    public void generateExcel(ActionEvent event) throws SQLException, IOException {
        ExcelWriter.getInstance().writeNewFile(StratController.getInstance().findByAsatasId(chosenAsatas.getId()));
    }

    public void deleteSNR(ActionEvent event) throws SQLException {
        StratDto strat = table.getSelectionModel().getSelectedItem();
        if (strat == null) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Hiba");
            alert.setHeaderText(null);
            alert.setContentText("Válassz stratot!");
            alert.showAndWait();
            return;
        }

        MeretController.getInstance().deleteAll(strat.getId());
        StratController.getInstance().delete(strat.getId());
        refreshTable();
    }
}
