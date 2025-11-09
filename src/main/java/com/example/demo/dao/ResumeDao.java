package com.example.demo.dao;

import org.apache.ibatis.annotations.Mapper;

import com.example.demo.dto.resume.Resume;

/*
 CREATE TABLE resume (
    resume_id SERIAL PRIMARY KEY,
    user_id INTEGER NOT NULL, -- users 테이블이 있다고 가정
    
    -- 기본 정보
    title VARCHAR(200),
    file_url TEXT,
    
    -- JSONB 필드들 (모든 배열을 JSONB로 통일)
    education JSONB,      -- 학력 정보 배열
    experience JSONB,     -- 경력 정보 배열 
    projects JSONB,       -- 프로젝트 정보 배열
    certifications JSONB, -- 자격증 정보 배열
    awards JSONB,         -- 수상 경력 배열
    summary TEXT,         -- 자기소개 또는 이력 요약
    skills JSONB,         -- 보유 기술 배열
    languages JSONB,      -- 언어 능력 배열
    
    -- 생성/수정일
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

 */

@Mapper
public interface ResumeDao {
    public int insertResume(Resume resume);
    public int updateResume(Resume resume);
    public Resume selectResumeById(int resume_id);
    public int delete(int resume_id);

  
} 
