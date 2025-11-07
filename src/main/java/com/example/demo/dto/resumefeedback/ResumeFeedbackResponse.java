package com.example.demo.dto.resumefeedback;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;

@Data
public class ResumeFeedbackResponse {
  
  private Double totalScore;

  private ScoreDetail scores;

  private List<String> strengths;

  private List<String> weaknesses;

  private List<ImprovementItem> improvements;

  private String summary;

   private List<String> recommendedActions;

  @Data
    public static class ScoreDetail {
        private Double content;        // 0.0 ~ 10.0
        private Double structure;
        private Double clarity;
        private Double completeness;
    }

     @Data
    public static class ImprovementItem {
        private Integer priority;
        private String action;
        private String example;
    }


}
