package com.example.demo.dao;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.example.demo.dto.resumefeedback.ResumeFeedback;
/*
 -- 2. Resume Feedback 테이블 생성 (resume_feedbacks → resume_feedback 변경)
CREATE TABLE resume_feedback (
    feedback_id SERIAL PRIMARY KEY,
    resume_id INTEGER NOT NULL REFERENCES resume(resume_id) ON DELETE CASCADE, -- 테이블명 변경
    user_id INTEGER NOT NULL, -- users 테이블이 있다고 가정
    
    -- 점수 관련
    total_score DECIMAL(3,1),  -- 이력서 종합 점수 (0.0 ~ 10.0)
    scores JSONB,              -- 항목별 점수 (내용, 구조, 명확성 등)
    
    -- 강점/약점/개선점 (모두 JSONB로 통일)
    strengths JSONB,           -- 강점 목록
    weaknesses JSONB,          -- 약점 목록
    improvements JSONB,        -- 개선 제안 목록 (우선순위, 예시 포함)
    
    -- 세부 분석
    sentence_feedback JSONB,  -- 문장별 피드백
    keyword_analysis JSONB,    -- 키워드 매칭 분석
    career_analysis JSONB,     -- 경력 기간, 공백, 연속성 분석
    job_match JSONB,           -- 지원 직무 적합성 분석
    
    -- AI 요약/액션
    summary TEXT,              -- AI의 종합 평가 및 조언
    recommended_actions JSONB, -- 개선 우선순위 또는 액션 목록
    
    -- 피드백 정보
    feedback_type VARCHAR(50) DEFAULT 'initial', -- initial, revision, final
    analysis_version VARCHAR(20),                 -- AI 분석 모델 버전
    
    -- 생성/수정일
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

 */


@Mapper
public interface ResumeFeedbackDao {
    // 기본 CRUD
    int insertFeedback(ResumeFeedback feedback);
    ResumeFeedback selectFeedbackById(int feedbackId);
    List<ResumeFeedback> selectFeedbacksByResumeId(int resumeId);
    List<ResumeFeedback> selectFeedbacksByUserId(int userId);
    int updateFeedback(ResumeFeedback feedback);
    int deleteFeedback(int feedbackId);
    
    // 추가 조회
    ResumeFeedback selectLatestFeedbackByResumeId(int resumeId);
    List<ResumeFeedback> selectFeedbacksByType(String feedbackType);
    int countFeedbacks(Integer resumeId);
    
}