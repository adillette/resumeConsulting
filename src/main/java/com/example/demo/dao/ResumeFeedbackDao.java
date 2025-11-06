package com.example.demo.dao;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.example.demo.dto.ResumeFeedback;



@Mapper
public interface ResumeFeedbackDao {
    int insertFeedback(ResumeFeedback feedback);
    
}