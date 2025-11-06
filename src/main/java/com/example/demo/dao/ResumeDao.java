package com.example.demo.dao;

import org.apache.ibatis.annotations.Mapper;
/*
 
CREATE TABLE resumes (
    resume_id SERIAL PRIMARY KEY,
     user_id INTEGER NOT NULL REFERENCES users(user_id) ON DELETE CASCADE,
    
    resume_title VARCHAR(200),
   
    file_url TEXT,
    
    education TEXT,
   
    experience TEXT,
   
    projects TEXT,
    
    certifications TEXT,
    
    awards TEXT,
   
    links TEXT,
    
    summary TEXT,
   
    skills TEXT,
  
    languages TEXT,
   
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
   
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
    
);

 */

import com.example.demo.dto.Resume;



@Mapper
public interface ResumeDao {

    public int insertResume(Resume resume);
    public int updateResume(Resume resume);
    public Resume selectResumeById(int resume_id);
    public int delete(int resume_id);

  
} 
