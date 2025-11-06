package com.example.demo.dto;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JavaType;

import lombok.Data;


@Data
public class Resume {

    // 기본키/외래키
    private int resumeId;        // resume_id (PK)
    private int userId;          // user_id (FK)
    
    // 기본 정보
    private String title;        // 이력서 제목
    private String fileUrl;      // 업로드된 파일 URL
    
    // JSONB 필드들 (String으로 매핑)
    private String education;      // 학력 정보 배열
    private String experience;     // 경력 정보 배열  
    private String projects;       // 프로젝트 정보 배열
    private String certifications; // 자격증 정보 배열
    private String awards;         // 수상 경력 배열
    private String skills;         // 보유 기술 배열
    private String languages;      // 언어 능력 배열
    
    // 텍스트 필드
    private String summary;      // 자기소개 또는 이력 요약
    
    // 생성/수정일
    private Timestamp createdAt;  // 생성 시각
    private Timestamp updatedAt;  // 수정 시각
    
    // ===== JSONB 처리 유틸리티 메소드들 =====
    
    private static final ObjectMapper objectMapper = new ObjectMapper();
    
    /**
     * skills JSONB를 List<String>으로 변환
     */
    public List<String> getSkillsAsList() {
        if (skills == null || skills.trim().isEmpty()) {
            return new ArrayList<>();
        }
        try {
            return objectMapper.readValue(skills, new TypeReference<List<String>>() {});
        } catch (Exception e) {
            System.err.println("Skills JSONB 파싱 에러: " + e.getMessage());
            return new ArrayList<>();
        }
    }
    
    /**
     * List<String>을 skills JSONB로 설정
     */
    public void setSkillsFromList(List<String> skillsList) {
        if (skillsList == null) {
            this.skills = "[]";
            return;
        }
        try {
            this.skills = objectMapper.writeValueAsString(skillsList);
        } catch (Exception e) {
            System.err.println("Skills JSONB 변환 에러: " + e.getMessage());
            this.skills = "[]";
        }
    }
    
    /**
     * education JSONB를 List<Object>로 변환
     */
    public List<Object> getEducationAsList() {
        if (education == null || education.trim().isEmpty()) {
            return new ArrayList<>();
        }
        try {
            return objectMapper.readValue(education, new TypeReference<List<Object>>() {});
        } catch (Exception e) {
            System.err.println("Education JSONB 파싱 에러: " + e.getMessage());
            return new ArrayList<>();
        }
    }
    
    /**
     * experience JSONB를 List<Object>로 변환
     */
    public List<Object> getExperienceAsList() {
        if (experience == null || experience.trim().isEmpty()) {
            return new ArrayList<>();
        }
        try {
            return objectMapper.readValue(experience, new TypeReference<List<Object>>() {});
        } catch (Exception e) {
            System.err.println("Experience JSONB 파싱 에러: " + e.getMessage());
            return new ArrayList<>();
        }
    }
    
    /**
     * 기술 스택 추가
     */
    public void addSkill(String skill) {
        List<String> skillsList = getSkillsAsList();
        if (!skillsList.contains(skill)) {
            skillsList.add(skill);
            setSkillsFromList(skillsList);
        }
    }
    
    /**
     * 특정 기술 보유 여부 확인
     */
    public boolean hasSkill(String skill) {
        return getSkillsAsList().contains(skill);
    }
}
