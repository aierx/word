package icu.aierx.word.util;

import icu.aierx.word.model.WordVO;
import lombok.SneakyThrows;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.File;
import java.io.FileInputStream;
import java.util.ArrayList;
import java.util.List;

/**
 * @author aleiw
 */
public class CommonUtil {

    /**
     * 依据：一个中文占两个字节，一个英文占一个字节。故若是字符长度与字节长度相等，则判断为英文。否则为中文。
     *
     * @param p 字段
     * @return t
     */
    public static boolean isEnglish(String p) {
        byte[] bytes = p.getBytes();
        //i为字节长度
        int i = bytes.length;
        //j为字符长度
        int j = p.length();
        return i == j;
    }


    @SneakyThrows
    public static List<WordVO> readExcel(File fis, String category, int id) {
        Workbook wb = new XSSFWorkbook(fis);
        Sheet sheet = wb.getSheetAt(0);
        int count = 1;
        List<WordVO> words = new ArrayList<>();
        int seq = id * 100000;
        int index = 0;
        while (sheet.getRow(count) != null) {
            index++;
            Row row = sheet.getRow(count);
            String word = row.getCell(0) != null ? row.getCell(0).getStringCellValue() : "";
            String accent = row.getCell(1) != null ? row.getCell(1).getStringCellValue() : "";
            String meanCN = row.getCell(2) != null ? row.getCell(2).getStringCellValue() : "";;
            words.add(new WordVO(seq+index, word, accent, meanCN, "",0, category));
            count++;
        }
        return words;
    }

    @SneakyThrows
    public static List<WordVO> readExcel() {
        FileInputStream fis = new FileInputStream("C:\\Users\\aleiw\\Desktop\\模板.xlsx");
        Workbook wb = new XSSFWorkbook(fis);
        Sheet sheet = wb.getSheetAt(0);
        int count = 1;
        List<WordVO> words = new ArrayList<>();
        while (sheet.getRow(count) != null) {
            Row row = sheet.getRow(count);
            int topic = (int) row.getCell(0).getNumericCellValue();
            String word = row.getCell(1) != null ? row.getCell(1).getStringCellValue() : "";
            String accent = row.getCell(2) != null ? row.getCell(2).getStringCellValue() : "";
            String meanCN = row.getCell(3) != null ? row.getCell(3).getStringCellValue() : "";
            String freq = row.getCell(4) != null ? String.valueOf(row.getCell(4).getNumericCellValue()) : "0";
            int wordLength = row.getCell(5) != null ? (int) row.getCell(5).getNumericCellValue() : 0;
            words.add(new WordVO(topic, word, accent, meanCN, freq, wordLength));
            count++;
        }
        return words;
    }
}
