package com.AkobotWeb.AutoInput;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;

class UniversitySystem {
    static String generateID(){
        //1. 인풋별 ID 생성
        //2. 이미 ID가 있는 경우 해당 ID 반환
        return"상황에 맞는 ID";
    }
    private Map<String, College> colleges;
    private Map<String, Department> departments;
    private Map<String, Major> majors;
    private Map<String, RecruitmentDivision> divisions;
    private Map<String, Recruitment> recruitments;

    public UniversitySystem() {
        this.colleges = new HashMap<>();
        this.departments = new HashMap<>();
        this.majors = new HashMap<>();
        this.divisions = new HashMap<>();
        this.recruitments = new HashMap<>();
    }

    // Methods for adding and getting objects of each entity

    // Collegue 메소드
    public void addCollegue(College college) {
        colleges.put(generateID(), college);
    }

    public College getCollegue(String collegueID) {
        return colleges.get(collegueID);
    }

    // Department 메소드
    public void addDepartment(Department department) {
        departments.put(generateID(), department);
    }

    public Department getDepartment(String deptID) {
        return departments.get(deptID);
    }

    // Major 메소드
    public void addMajor(Major major) {
        majors.put(generateID(), major);
    }

    public Major getMajor(String majorID) {
        return majors.get(majorID);
    }

    // RecruitmentDivision 메소드
    public void addRecruitmentDivision(RecruitmentDivision division) {
        divisions.put(generateID(), division);
    }

    public RecruitmentDivision getRecruitmentDivision(String divisionID) {
        return divisions.get(divisionID);
    }

    // Recruitment 메소드
    public void addRecruitment(Recruitment recruitment) {
        recruitments.put(generateID(), recruitment);
    }

    public Recruitment getRecruitment(String recruitmentID) {
        return recruitments.get(recruitmentID);
    }

}


// 대충 데이터 쪼개는 클래스
public class Division {


    // 데이터 매핑을 위한 리스트 초기화
    //FIXME: 추가해야함, 데이터 구조 나오면 추가

    // division 종류를 나누는 static 함수
    // FIXME: 추가해야함. 회의 필요
    public static int match(){
    if("어떤 키워드" == "")return 0;
    else if("어떤 조건" =="" )return 0;
    else return 0;

    }

    // 문자열을 기반으로 처리하는 메서드1(예시)학생부종합전형
    public void 학생부종합(String csvContent) {

        String[] rows = csvContent.split("\n");

        for (String row : rows) {
            String[] values = row.split(",");//

            String collegeName = values[0];  // 단과대학
            String divisionName = values[1];  // 모집단위
            int recruitmentCount = Integer.parseInt(values[2]);  // 모집인원(명)

            // 단과대학이 처음 추가될 때만 id 생성
            if (!colleges.containsKey(collegeName)) {
                String collegeID = generateUniqueID(); // 새로운 id 생성 메서드 필요
                College college = new College(collegeID, collegeName, values[3]);  // 캠퍼스
                colleges.put(collegeName, college);
                collegeDivisions.put(collegeID, new LinkedList<>());
            }

            // 모집단위 객체 생성 및 연결
            RecruitmentDivision division = new RecruitmentDivision(generateUniqueID(), divisionName);
            collegeDivisions.get(colleges.get(collegeName).collegeID).add(division);
        }

        // 생성된 객체 출력 (예시)
        for (College college : colleges.values()) {
            System.out.println("College ID: " + college.collegeID + ", Name: " + college.collegeName + ", Campus: " + college.campus);
            System.out.println("Divisions:");
            for (RecruitmentDivision division : collegeDivisions.get(college.collegeID)) {
                System.out.println("  Division ID: " + division.divisionID + ", Type: " + division.divType);
            }
        }

}
