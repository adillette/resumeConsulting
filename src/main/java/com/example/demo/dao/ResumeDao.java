package com.example.demo.dao;

import org.apache.ibatis.annotations.Mapper;

import com.example.demo.dto.resume.Resume;



@Mapper
public interface ResumeDao {
    public int insertResume(Resume resume);
    public int updateResume(Resume resume);
    public Resume selectResumeById(int resume_id);
    public int delete(int resume_id);

  
} 
