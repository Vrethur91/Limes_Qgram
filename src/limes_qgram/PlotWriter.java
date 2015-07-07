package limes_qgram;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import static org.apache.poi.ss.usermodel.Cell.CELL_TYPE_NUMERIC;
import org.apache.poi.ss.usermodel.Row;

/**
 *
 * @author Bene
 */
public class PlotWriter {
    public static void writePlotSizeAgainstRuntime(ArrayList<Configuration> configList){
        try {
            HSSFWorkbook workbook = new HSSFWorkbook();
          
            //Basic
            HSSFSheet sheet = workbook.createSheet("Normal " + configList.get(0).getDataList().get(0).getTheta());
            
            Row row = sheet.createRow(0);
            row.createCell(0).setCellValue("Size");
            row.createCell(1).setCellValue("Over Theta");
            row.createCell(2).setCellValue("- -");
            row.createCell(3).setCellValue("+ -");
            row.createCell(4).setCellValue("- +");
            row.createCell(5).setCellValue("+ +");
            
            for(int i = 0; i < configList.get(0).getDataList().size();i++){
                row = sheet.createRow(i+1);
                row.createCell(0).setCellType(CELL_TYPE_NUMERIC);
                row.createCell(0).setCellValue(configList.get(0).getDataList().get(i).getSizeSample());
                row.createCell(1).setCellType(CELL_TYPE_NUMERIC);
                row.createCell(1).setCellValue(configList.get(0).getDataList().get(i).getOverTheta());
                for(int j = 0;j < 4;j++){
                    row.createCell(j+2).setCellType(CELL_TYPE_NUMERIC);
                    row.createCell(j+2).setCellValue(configList.get(j).getDataList().get(i).getTime());
                }
            }
            
            //Ends
            sheet = workbook.createSheet("Ends " + configList.get(4).getDataList().get(0).getTheta());

            row = sheet.createRow(0);
            row.createCell(0).setCellValue("Size");
            row.createCell(1).setCellValue("Over Theta");
            row.createCell(2).setCellValue("- -");
            row.createCell(3).setCellValue("+ -");
            row.createCell(4).setCellValue("- +");
            row.createCell(5).setCellValue("+ +");
            
            for(int i = 0; i < configList.get(4).getDataList().size();i++){
                row = sheet.createRow(i+1);
                row.createCell(0).setCellType(CELL_TYPE_NUMERIC);
                row.createCell(0).setCellValue(configList.get(4).getDataList().get(i).getSizeSample());
                row.createCell(1).setCellType(CELL_TYPE_NUMERIC);
                row.createCell(1).setCellValue(configList.get(4).getDataList().get(i).getOverTheta());
                for(int j = 0;j < 4;j++){
                    row.createCell(j+2).setCellType(CELL_TYPE_NUMERIC);
                    row.createCell(j+2).setCellValue(configList.get(j+4).getDataList().get(i).getTime());
                }
            }

            FileOutputStream fileOut = new FileOutputStream("SizeAgainstRuntime.xls");
            workbook.write(fileOut);
            fileOut.close();

        } catch (IOException ex) {
            Logger.getLogger(PlotWriter.class.getName()).log(Level.SEVERE, null, ex);
        } 
    }
    
    public static void writePlotThetaAgainstRuntime(ArrayList<Configuration> configList){
        try {
            HSSFWorkbook workbook = new HSSFWorkbook();
          
            //Basic
            HSSFSheet sheet = workbook.createSheet("Normal " + configList.get(0).getDataList().get(0).getSizeSample());
            
            Row row = sheet.createRow(0);
            row.createCell(0).setCellValue("Theta");
            row.createCell(1).setCellValue("Over Theta");
            row.createCell(2).setCellValue("- -");
            row.createCell(3).setCellValue("+ -");
            row.createCell(4).setCellValue("- +");
            row.createCell(5).setCellValue("+ +");
            
            for(int i = 0; i < configList.get(0).getDataList().size();i++){
                row = sheet.createRow(i+1);
                row.createCell(0).setCellType(CELL_TYPE_NUMERIC);
                row.createCell(0).setCellValue(configList.get(0).getDataList().get(i).getTheta());
                row.createCell(1).setCellType(CELL_TYPE_NUMERIC);
                row.createCell(1).setCellValue(configList.get(0).getDataList().get(i).getOverTheta());
                for(int j = 0;j < 4;j++){
                    row.createCell(j+2).setCellType(CELL_TYPE_NUMERIC);
                    row.createCell(j+2).setCellValue(configList.get(j).getDataList().get(i).getTime());
                }
            }
            
            //Ends
            sheet = workbook.createSheet("Ends " + configList.get(4).getDataList().get(0).getSizeSample());

            row = sheet.createRow(0);
            row.createCell(0).setCellValue("Theta");
            row.createCell(1).setCellValue("Over Theta");
            row.createCell(2).setCellValue("- -");
            row.createCell(3).setCellValue("+ -");
            row.createCell(4).setCellValue("- +");
            row.createCell(5).setCellValue("+ +");
            
            for(int i = 0; i < configList.get(4).getDataList().size();i++){
                row = sheet.createRow(i+1);
                row.createCell(0).setCellType(CELL_TYPE_NUMERIC);
                row.createCell(0).setCellValue(configList.get(4).getDataList().get(i).getTheta());
                row.createCell(1).setCellType(CELL_TYPE_NUMERIC);
                row.createCell(1).setCellValue(configList.get(4).getDataList().get(i).getOverTheta());
                for(int j = 0;j < 4;j++){
                    row.createCell(j+2).setCellType(CELL_TYPE_NUMERIC);
                    row.createCell(j+2).setCellValue(configList.get(j+4).getDataList().get(i).getTime());
                }
            }

            FileOutputStream fileOut = new FileOutputStream("ThetaAgainstRuntime.xls");
            workbook.write(fileOut);
            fileOut.close();

        } catch (IOException ex) {
            Logger.getLogger(PlotWriter.class.getName()).log(Level.SEVERE, null, ex);
        } 
    }
}
