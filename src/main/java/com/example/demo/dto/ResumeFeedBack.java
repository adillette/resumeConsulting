package com.example.demo.dto;

import java.sql.Timestamp;

import lombok.Data;

@Data
public class ResumeFeedBack {
  
   private int feedbackId;  // ⚠️ 추가 필요: PK
    //** feedback_id (SERIAL PRIMARY KEY)
    
    private int resumeId;  // ⚠️ 추가 필요: resumes 테이블 FK
    //** 어떤 이력서의 피드백인지 식별


  //점수 관련
  private int resumeTotalScore;
  private int resumeScores;

  //강점/ 약점/개선점
  private String resumeStrength;
  private String resumeWeakness;
  private String resumeImprovements;

  //세부 분석
  private String resumeSentenceFeedbacks;
  private String resumeKeywordAnalysis;
  private String resumeCareerAnalysis;
  private String resumeJobMatch;

  //AI요약/ 액션
  private String summary;
  private String[] recommended_actions;

  //피드백 정보
  private String feedback_type;//initial, revision, final
  
  //생성 수정일
  private Timestamp resumeCreated_at;
  private Timestamp resumeupdated_at;



}
