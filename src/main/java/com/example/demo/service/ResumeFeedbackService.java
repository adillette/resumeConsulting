package com.example.demo.service;

import java.util.List;
import java.util.Map;

import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.prompt.Prompt;
import org.springframework.ai.chat.prompt.PromptTemplate;
import org.springframework.ai.converter.BeanOutputConverter;
import org.springframework.stereotype.Service;

import com.example.demo.dao.ResumeFeedbackDao;
import com.example.demo.dto.resumefeedback.ResumeFeedback;
import com.example.demo.dto.resumefeedback.ResumeFeedbackRequest;
import com.example.demo.dto.resumefeedback.ResumeFeedbackResponse;
import com.fasterxml.jackson.databind.ObjectMapper;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class ResumeFeedbackService {

  private ChatClient chatClient;
  private ResumeFeedbackDao feedbackDao;
  private ObjectMapper objectMapper;

  public ResumeFeedbackService(ChatClient.Builder chatClientBuilder,
      ResumeFeedbackDao feedbackDao,
      ObjectMapper objectMapper) {
    this.chatClient = chatClientBuilder.build();
    this.feedbackDao = feedbackDao;
    this.objectMapper = objectMapper;
  }

  public ResumeFeedbackResponse analyzeResume(ResumeFeedbackRequest request){
    log.info("ai 분석 시작...");
    try{
      BeanOutputConverter<ResumeFeedbackResponse> outputConverter = new BeanOutputConverter<>(ResumeFeedbackResponse.class);

      String promptText="""
          당신은 전문 이력서 컨설턴트입니다.
                다음 이력서를 분석하고 평가해주세요.
                
                [이력서 내용]
                {content}
                
                {targetJobInfo}
                
                [분석 기준]
                1. 경력 기술: 수치와 성과 중심인가?
                2. 직무 적합성: 목표 직무와 연관성이 있는가?
                3. 작성 품질: 문법, 맞춤법, 전문성이 적절한가?
                
                [응답 형식]
                다음 JSON 형식으로 응답해주세요:
                {format}

          """;

          PromptTemplate promptTemplate = new PromptTemplate(promptText) ;

          String targetJobInfo = request.getTargetJob()!=null
          ? "[목표 직무]\n" + request.getTargetJob()
                : "";
          
                Prompt prompt = promptTemplate.create(Map.of("content",request.getContent(),
                "targetJobInfo",request.getTargetJob(),"format",outputConverter.getFormat()));

                String aiResponse = chatClient.prompt(prompt)
                  .call()
                  .content();

                  log.info("ai 응답 수신 완료");
                  log.debug("AI RawResponse: {}", aiResponse);
                  //JSON 을 객체로 변환
                  ResumeFeedbackResponse result = outputConverter.convert(aiResponse);
                  return result;


    }catch (Exception e){
       log.error("AI 분석 중 에러 발생", e);
       throw new RuntimeException("이력서 분석에 실패했습니다: " + e.getMessage(),e);
    }
  }

  public ResumeFeedback saveFeedback(int resumeId, int userId, ResumeFeedbackResponse feedbackResult) {

    log.info("피드백 저장 시작...");
    try {

      ResumeFeedback feedback = new ResumeFeedback();

      feedback.setResumeId(resumeId);
      feedback.setUserId(userId);
      feedback.setFeedbackType("initial");
      feedback.setAnalysisVersion("gpt-4o-mini");

      // 점수 정보
      feedback.setTotalScore(feedbackResult.getTotalScore());
      feedback.setScores(objectMapper.writeValueAsString(feedbackResult.getScores()));

      // 강점/약점/개선점 (List를 JSONB 문자열로 변환)
      feedback.setStrengthsFromList(feedbackResult.getStrengths());
      feedback.setWeaknessesFromList(feedbackResult.getWeaknesses());
      feedback.setImprovements(
          objectMapper.writeValueAsString(feedbackResult.getImprovements()));

      // AI 요약 및 추천 액션
      feedback.setSummary(feedbackResult.getSummary());
      feedback.setRecommendedActionsFromList(feedbackResult.getRecommendedActions());

      // DB 저장
      int result = feedbackDao.insertFeedback(feedback);

      if (result <= 0) {
        throw new RuntimeException("피드백 저장 실패");
      }

      log.info("피드백 저장 완료: feedbackId={}", feedback.getFeedbackId());
      return feedback;

    } catch (Exception e) {
      log.error("피드백 저장 중 에러 발생", e);
      throw new RuntimeException("피드백 저장에 실패했습니다: " + e.getMessage(), e);
    }
  }

  /**
   * 이력서별 피드백 목록 조회
   */
  public List<ResumeFeedback> getFeedbacksByResumeId(int resumeId) {
    return feedbackDao.selectFeedbacksByResumeId(resumeId);
  }

}
