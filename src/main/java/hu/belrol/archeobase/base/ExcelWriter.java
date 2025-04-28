package hu.belrol.archeobase.base;

import hu.belrol.archeobase.asatas.Asatas;
import hu.belrol.archeobase.asatas.AsatasDao;
import hu.belrol.archeobase.meret.Meret;
import hu.belrol.archeobase.meret.MeretController;
import hu.belrol.archeobase.strat.Strat;
import javafx.scene.control.Alert;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.FileOutputStream;
import java.io.IOException;
import java.sql.SQLException;
import java.util.List;
import java.util.stream.Collectors;

public class ExcelWriter {
    private static ExcelWriter instance = null;

    private ExcelWriter() {
    }

    public static ExcelWriter getInstance() {
        if (instance == null) {
            instance = new ExcelWriter();
        }
        return instance;
    }

    public void writeNewFile(List<Strat> data) throws SQLException, IOException {
        XSSFWorkbook workbook = new XSSFWorkbook();
        XSSFSheet sheet = workbook.createSheet();
        int idx;
        int width = 4500;
        sheet.setColumnWidth(0, 2000);
        sheet.setColumnWidth(1, 2000);
        sheet.setColumnWidth(9, 3000);


        for (idx = 2; idx < 9; idx++) {
            sheet.setColumnWidth(idx, width);

        }

        writeHeader(sheet);
        fillTable(sheet, data);
        Asatas asatas = AsatasDao.getInstance().findById(data.get(0).getAsatasId());
        FileOutputStream fos = new FileOutputStream(asatas.getMegnevezes() + ".xlsx");
        workbook.write(fos);

        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Generálás");
        alert.setHeaderText(null);
        alert.setContentText("Excel (" + asatas.getMegnevezes() + ".xlsx" + ") sikeresen generálva!");
        alert.showAndWait();
    }

    public void writeHeader(Sheet sheet) {
        int idx = 0;
        List<String> headers = List.of("SNR", "OBNR", "SNR típus", "SNR állapot", "SNR kor", "SNR korszak",
                "SNR méret", "Feltételes dat.", "Leírás"
        );
        Row row = sheet.createRow(0);

        for (String header : headers) {
            Cell cell = row.createCell(idx++);
            cell.setCellValue(header);
            cell.setCellStyle(bold(sheet.getWorkbook()));
        }

    }

    private void fillTable(XSSFSheet sheet, List<Strat> data) throws SQLException {
        int rowIdx = 1;
        for (Strat strat : data) {
            Row row = sheet.createRow(rowIdx++);
            int cellIdx = 0;
            row.createCell(cellIdx++).setCellValue(strat.getSNR());
            row.createCell(cellIdx++).setCellValue((strat.getOBNR() == 0 ? "" : String.valueOf(strat.getOBNR())));
            row.createCell(cellIdx++).setCellValue(strat.getTipus());
            row.createCell(cellIdx++).setCellValue(strat.getAllapot());
            row.createCell(cellIdx++).setCellValue(strat.getKor());
            row.createCell(cellIdx++).setCellValue(strat.getKorszak());
            row.createCell(cellIdx++).setCellValue(MeretController.getInstance().getAll(strat.getId())
                    .stream().map(Meret::toString).collect(Collectors.joining("\n")));
            row.createCell(cellIdx++).setCellValue(strat.isFeltDat() ? "Igen" : "Nem");
            row.createCell(cellIdx).setCellValue(strat.getLeiras());
        }

    }

    private CellStyle bold(Workbook workbook) {
        CellStyle cellStyle = workbook.createCellStyle();
        Font font = workbook.createFont();
        font.setBold(true);
        cellStyle.setFont(font);
        return cellStyle;
    }
}
