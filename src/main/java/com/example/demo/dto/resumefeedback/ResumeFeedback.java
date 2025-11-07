package com.example.demo.dto.resumefeedback;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import lombok.Data;

@Data
public class ResumeFeedback{
    // 기본키/외래키
    private int feedbackId;      // feedback_id (PK)
    private int resumeId;        // resume_id (FK)
    private int userId;          // user_id (FK)
    
    // 점수 관련
    private Double totalScore;   // total_score (0.0 ~ 10.0)
    private String scores;          // scores JSONB (항목별 점수)
    
    // 강점/약점/개선점 (JSONB 필드들)
    private String strengths;       // 강점 목록
    private String weaknesses;      // 약점 목록
    private String improvements;    // 개선 제안 목록
    
    // 세부 분석 (JSONB 필드들)
    private String sentenceFeedbacks;  // 문장별 피드백
    private String keywordAnalysis;    // 키워드 매칭 분석
    private String careerAnalysis;     // 경력 기간, 공백, 연속성 분석
    private String jobMatch;           // 지원 직무 적합성 분석
    
    // AI 요약/액션
    private String summary;            // AI의 종합 평가 및 조언
    private String recommendedActions; // 개선 우선순위 또는 액션 목록 (JSONB)
    
    // 피드백 정보
    private String feedbackType;      // feedback_type (initial, revision, final)
    private String analysisVersion;   // analysis_version (AI 분석 모델 버전)
    
    // 생성/수정일
    private Timestamp createdAt;      // 생성 시각
    private Timestamp updatedAt;      // 수정 시각
    
    // ===== JSONB 처리 유틸리티 메소드들 =====
    
    private static final ObjectMapper objectMapper = new ObjectMapper();
    
    /**
     * strengths JSONB를 List<String>으로 변환
     */
    public List<String> getStrengthsAsList() {
        if (strengths == null || strengths.trim().isEmpty()) {
            return new ArrayList<>();
        }
        try {
            return objectMapper.readValue(strengths, new TypeReference<List<String>>() {});
        } catch (Exception e) {
            System.err.println("Strengths JSONB 파싱 에러: " + e.getMessage());
            return new ArrayList<>();
        }
    }
    
    /**
     * List<String>을 strengths JSONB로 설정
     */
    public void setStrengthsFromList(List<String> strengthsList) {
        if (strengthsList == null) {
            this.strengths = "[]";
            return;
        }
        try {
            this.strengths = objectMapper.writeValueAsString(strengthsList);
        } catch (Exception e) {
            System.err.println("Strengths JSONB 변환 에러: " + e.getMessage());
            this.strengths = "[]";
        }
    }
    
    /**
     * weaknesses JSONB를 List<String>으로 변환
     */
    public List<String> getWeaknessesAsList() {
        if (weaknesses == null || weaknesses.trim().isEmpty()) {
            return new ArrayList<>();
        }
        try {
            return objectMapper.readValue(weaknesses, new TypeReference<List<String>>() {});
        } catch (Exception e) {
            System.err.println("Weaknesses JSONB 파싱 에러: " + e.getMessage());
            return new ArrayList<>();
        }
    }
    
    /**
     * List<String>을 weaknesses JSONB로 설정
     */
    public void setWeaknessesFromList(List<String> weaknessesList) {
        if (weaknessesList == null) {
            this.weaknesses = "[]";
            return;
        }
        try {
            this.weaknesses = objectMapper.writeValueAsString(weaknessesList);
        } catch (Exception e) {
            System.err.println("Weaknesses JSONB 변환 에러: " + e.getMessage());
            this.weaknesses = "[]";
        }
    }
    
    /**
     * recommendedActions JSONB를 List<String>으로 변환
     */
    public List<String> getRecommendedActionsAsList() {
        if (recommendedActions == null || recommendedActions.trim().isEmpty()) {
            return new ArrayList<>();
        }
        try {
            return objectMapper.readValue(recommendedActions, new TypeReference<List<String>>() {});
        } catch (Exception e) {
            System.err.println("RecommendedActions JSONB 파싱 에러: " + e.getMessage());
            return new ArrayList<>();
        }
    }
    
    /**
     * List<String>을 recommendedActions JSONB로 설정
     */
    public void setRecommendedActionsFromList(List<String> actionsList) {
        if (actionsList == null) {
            this.recommendedActions = "[]";
            return;
        }
        try {
            this.recommendedActions = objectMapper.writeValueAsString(actionsList);
        } catch (Exception e) {
            System.err.println("RecommendedActions JSONB 변환 에러: " + e.getMessage());
            this.recommendedActions = "[]";
        }
    }
    
    /**
     * 강점 추가
     */
    public void addStrength(String strength) {
        List<String> strengthsList = getStrengthsAsList();
        if (!strengthsList.contains(strength)) {
            strengthsList.add(strength);
            setStrengthsFromList(strengthsList);
        }
    }
    
    /**
     * 약점 추가
     */
    public void addWeakness(String weakness) {
        List<String> weaknessesList = getWeaknessesAsList();
        if (!weaknessesList.contains(weakness)) {
            weaknessesList.add(weakness);
            setWeaknessesFromList(weaknessesList);
        }
    }
    
    /**
     * 추천 액션 추가
     */
    public void addRecommendedAction(String action) {
        List<String> actionsList = getRecommendedActionsAsList();
        if (!actionsList.contains(action)) {
            actionsList.add(action);
            setRecommendedActionsFromList(actionsList);
        }
    }
    
    /**
     * 특정 강점 포함 여부 확인
     */
    public boolean hasStrength(String strength) {
        return getStrengthsAsList().contains(strength);
    }
    
    /**
     * 특정 추천 액션 포함 여부 확인
     */
    public boolean hasRecommendedAction(String action) {
        return getRecommendedActionsAsList().contains(action);
    }
}
