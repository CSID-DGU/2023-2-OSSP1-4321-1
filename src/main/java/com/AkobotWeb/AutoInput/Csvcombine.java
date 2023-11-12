package com.AkobotWeb.AutoInput;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import org.apache.commons.csv.*;


public class Csvcombine {
        public void main(String[] args) {
            // 합칠 CSV 파일 경로
            // 경로에 있는 모든 csv 파일명을 추출
            List<String> inputFiles = getAllCSVFilesInDirectory("path/to/directory"); // 디렉토리에 있는 모든 CSV 파일을 가져옴
            // 결과 파일 경로
            String outputFile = "mergedFile.csv";

            mergeCSVFiles(inputFiles, outputFile);
        }

        private List<String> getAllCSVFilesInDirectory(String directoryPath) {
            List<String> csvFiles = new ArrayList<>();

            File directory = new File(directoryPath);
            File[] files = directory.listFiles();

            if (files != null) {
                for (File file : files) {
                    if (file.isFile() && file.getName().endsWith(".csv")) {
                        csvFiles.add(file.getAbsolutePath());
                    }
                }
            }

            return csvFiles;
        }

        private void mergeCSVFiles(List<String> inputFiles, String outputFile) {
            try (CSVPrinter csvPrinter = new CSVPrinter(new FileWriter(outputFile), CSVFormat.DEFAULT)) {
                // 헤더를 포함하려면 첫 번째 파일의 첫 줄을 그대로 복사합니다.
                boolean headerCopied = false;

                for (String inputFile : inputFiles) {
                    try (CSVParser csvParser = new CSVParser(new FileReader(inputFile), CSVFormat.DEFAULT)) {
                        List<CSVRecord> records = csvParser.getRecords();

                        for (CSVRecord record : records) {
                            // 헤더 복사
                            if (!headerCopied) {
                                csvPrinter.printRecord(record);
                                headerCopied = true;
                            } else {
                                // 데이터 복사
                                csvPrinter.printRecord(record);
                            }
                        }
                    }
                }

                System.out.println("CSV 파일이 성공적으로 합쳐졌습니다. 결과 파일: " + outputFile);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        public void sendFile() throws IOException {
            File file = new File("여기에 통합한 파일명 및 루트 입력");
            CsvToTab csvToTab = new CsvToTab();
            csvToTab.readCsv(file);
        }
    }
