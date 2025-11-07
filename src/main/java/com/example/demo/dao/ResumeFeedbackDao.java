package com.example.demo.dao;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.example.demo.dto.resumefeedback.ResumeFeedback;



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