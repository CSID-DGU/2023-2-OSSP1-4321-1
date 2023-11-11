package com.AkobotWeb.AutoInput;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;

// 데이터 구조 해시 테이블
// 클래스로도 구현할 수 있음
// FIXME: 클래스와 연결 관계는 이후 변경될 수 있음.

import java.util.HashMap;
import java.util.Map;



// 아래는 클래스 구현

// 단과대학 클래스
class College {
    String collegeID; // 단과대학을 식별 키
    String collegeName; // 단과대학 이름
    String campus; // 서울 or 바이오메디 캠퍼스
    LinkedList<Department> departments; // 학부 리스트

    public College(String collegeID, String collegeName, String campus) {
        this.collegeID = collegeID;
        this.collegeName = collegeName;
        this.campus = campus;
        this.departments = new LinkedList<>();
    }

    public void addDepartment(Department department) {
        this.departments.add(department);
    }
}

// 학부 클래스
class Department {
    String deptID; // 학부를 식별하기 위한 키
    String collegueID; // 단과대학 외래키
    String deptName; // 학부 이름
    LinkedList<Major> majors; // 전공 리스트

    public Department(String deptID, String collegueID, String deptName) {
        this.deptID = deptID;
        this.collegueID = collegueID;
        this.deptName = deptName;
        this.majors = new LinkedList<>();
    }

    public void addMajor(Major major) {
        this.majors.add(major);
    }
}

// 전공 클래스
class Major {
    String majorID; // 전공 키
    String deptID; // 학부 식별 외래키
    String collegueID; // 단과대학 식별 외래키
    String majorName; // 전공 이름
    LinkedList<RecruitmentDivision> divisions; // 모집단위 리스트

    public Major(String majorID, String deptID, String collegueID, String majorName) {
        this.majorID = majorID;
        this.deptID = deptID;
        this.collegueID = collegueID;
        this.majorName = majorName;
        this.divisions = new LinkedList<>();
    }

    public void addRecruitmentDivision(RecruitmentDivision division) {
        this.divisions.add(division);
    }
}

// 모집단위 클래스
class RecruitmentDivision {
    String divisionID; // 모집단위 식별 키
    String divType; // 모집단위 계열(자연, 인문, 예체능)
    LinkedList<Recruitment> recruitments; // 모집 전형 리스트

    public RecruitmentDivision(String divisionID, String divType) {
        this.divisionID = divisionID;
        this.divType = divType;
        this.recruitments = new LinkedList<>();
    }

    public void addRecruitment(Recruitment recruitment) {
        this.recruitments.add(recruitment);
    }
}

// 모집 전형 클래스
class Recruitment {
    String recruitmentID; // 모집 전형 키
    String entranceType; // 모집 전형
    int quota; // 정원
    int additionalQuota; // 정원외
    String qualification; // 지원자격

    public Recruitment(String recruitmentID, String entranceType, int quota, int additionalQuota, String qualification) {
        this.recruitmentID = recruitmentID;
        this.entranceType = entranceType;
        this.quota = quota;
        this.additionalQuota = additionalQuota;
        this.qualification = qualification;
    }
}




// CsvToTab 클래스
public class CsvToTab {
    // CSV 파일을 읽어 문자열로 반환하는 메소드
    // CSV 파일을 읽어 문자열로 반환하는 메소드
    public void readCsv(File file) throws IOException {
        StringBuilder csvData = new StringBuilder();

        try (BufferedReader br = new BufferedReader(new FileReader(file))) {
            String line;
            while ((line = br.readLine()) != null) {
                csvData.append(line).append("\n");
            }
        }

    }

    // 문자열을 기반으로 데이터를 쪼개서 해시 테이블 및 연결리스트에 담도록 하는 메소드
    public void processParsing(String csvContent) {
        String[] rows = csvContent.split("\n");


        for (String row : rows) {
            // 어떻게 할지는 모르겠지만 아무튼,서류를 분류하도록 해서(아마 문자열 match로 일일히 하는 방법 뿐인 듯)
            // FIXME: 챕터명등의 케이스에 따라 Division 수행
            switch (Division.match()) {
                case 1:
                    // 전년대비 변경사항
                    break;
                case 2:
                    // 수시모집
                    break;
                case 3:
                    // 정시모집
                    break;
                case 4:
                    // 논술
                    break;
                case 5:
                    //....
                    break;
                case 6:
                    // 회의 필요할 듯?
                    break;
                case 7:
                    break;
                case 8:
                    break;
                //...

            }
        }
    }
    /* 사용을 위한 main 함수
    public static void main(String[] args) {
        CsvToTab csvToTab = new CsvToTab();
        csvToTab.processCollegesAndDivisions(new File("your_csv_file_path.csv"));
    }
     */
}
