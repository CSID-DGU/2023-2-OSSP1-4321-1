package com.AkobotWeb.AutoInput;
import java.io.File;
import java.io.IOException;

public class Csvcombine {
    public class CsvCombine {
        public void sendFile() throws IOException {
            File file = new File("여기에 통합한 파일명 및 루트 입력");
            CsvToTab csvToTab = new CsvToTab();
            csvToTab.readCsv(file);
        }
    }
}
