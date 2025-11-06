package com.example.demo.dto;

import java.sql.Timestamp;

import lombok.Data;

@Data
public class Resume {
  private int resume_id;

  private int userId;

  private String resumeTitle;
  private String fileUrl;
  private String education;
  private String experience;
  private String projects;
  private String certifications;
  private String awards;
  private String links;
  private String summary;
  private String skills;
  private String languages;

    private Timestamp  createdAt;  // ⚠️ 추가 권장
    //** created_at 컬럼 매핑
    
    private Timestamp updatedAt;  // ⚠️ 추가 권장
    //** updated_at 컬럼 매핑

 
}
