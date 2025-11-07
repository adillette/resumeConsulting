package com.example.demo.dto.resumefeedback;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;

@Data
public class ResumeFeedbackRequest {
    private Integer resumeId;    // 이력서 ID (선택사항, 신규 작성시 null)
    private Integer userId;      // 사용자 ID (필수)
    private String content;      // 분석할 이력서 내용 (필수)
    private String targetJob;    // 목표 직무 (선택사항)

}
