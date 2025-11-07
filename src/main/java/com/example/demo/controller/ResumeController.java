package com.example.demo.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.dto.resume.Resume;
import com.example.demo.dto.resume.ResumeRequest;
import com.example.demo.dto.resumefeedback.ResumeFeedback;
import com.example.demo.dto.resumefeedback.ResumeFeedbackRequest;
import com.example.demo.dto.resumefeedback.ResumeFeedbackResponse;
import com.example.demo.service.ResumeFeedbackService;
import com.example.demo.service.ResumeService;

import lombok.extern.slf4j.Slf4j;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@Slf4j
@RestController
@RequestMapping("/ai/resume")
public class ResumeController {

  @Autowired
  private ResumeService resumeService;
  
  @Autowired
  private ResumeFeedbackService feedbackService;

  @PostMapping(value = "/advisor-chain"
  ,consumes = MediaType.APPLICATION_JSON_VALUE
  ,produces = MediaType.APPLICATION_JSON_VALUE
  )
    public Map<String,Object> analyzeResume(@RequestBody ResumeFeedbackRequest request) {

      Map<String, Object> response = new HashMap<>();
    try {
      log.info("=== 이력서 코칭 요청 시작 ===");
    log.info("Request: resumeId={}, userId={}", request.getResumeId(), request.getUserId());
        

    ResumeFeedbackResponse feedbackResult = feedbackService.analyzeResume(request);

    ResumeFeedback feedback = feedbackService.saveFeedback(request.getResumeId(),
     request.getUserId(), feedbackResult);
     response.put("success", true);
     response.put("feedbackId", feedback.getFeedbackId());
     response.put("feedback", feedbackResult);

     log.info("=== 이력서 코칭 완료 === feedbackId={}", feedback.getFeedbackId());

    } catch (Exception e) {
       log.error("이력서 코칭 중 에러 발생", e);
      response.put("success", false);
      response.put("error", e.getMessage());
    }
    return response;
    
}

  @PostMapping(value = "/create",
                 consumes = MediaType.APPLICATION_JSON_VALUE,
                 produces = MediaType.APPLICATION_JSON_VALUE)
  public Map<String,Object> create(@RequestBody Resume resume) {

    log.info("=== 이력서 등록 요청 ===");
    log.info("Resume title: {}", resume.getTitle());
    Map<String, Object> response = new HashMap<>();

    int result= resumeService.insertResume(resume);
    if(result>0){
   
   response.put("success",true);
    response.put("resumeId", result);
    response.put("message", "이력서가 성공적으로 등록되었습니다.");
  }else{
     log.error("이력서 등록 중 에러 발생");
            response.put("success", false);
          
  }
  return response;

  }

    //   /**
    //  * 이력서 수정
    //  * POST http://localhost:8081/api/resume/update/{resumeId}
    //  * Content-Type: application/json
    //  */
    // @PostMapping(value = "/update/{resumeId}",
    //              consumes = MediaType.APPLICATION_JSON_VALUE,
    //              produces = MediaType.APPLICATION_JSON_VALUE)
    // public Map<String, Object> updateResume(@PathVariable int resumeId, 
    //                                        @RequestBody Resume resume) {
        
    //     log.info("=== 이력서 수정 요청 === resumeId={}", resumeId);
        
    //     Map<String, Object> response = new HashMap<>();
        
    //     try {
    //         resume.setResumeId(resumeId);
    //         resumeService.updateResume(resume);
            
    //         response.put("success", true);
    //         response.put("message", "이력서가 수정되었습니다.");
            
    //     } catch (Exception e) {
    //         log.error("이력서 수정 중 에러 발생", e);
    //         response.put("success", false);
    //         response.put("error", e.getMessage());
    //     }
        
    //     return response;
    // }

    // /**
    //  * 이력서 삭제
    //  * POST http://localhost:8081/api/resume/delete/{resumeId}
    //  */
    // @PostMapping(value = "/delete/{resumeId}",
    //              produces = MediaType.APPLICATION_JSON_VALUE)
    // public Map<String, Object> deleteResume(@PathVariable int resumeId) {
        
    //     log.info("=== 이력서 삭제 요청 === resumeId={}", resumeId);
        
    //     Map<String, Object> response = new HashMap<>();
        
    //     try {
    //         resumeService.deleteResume(resumeId);
            
    //         response.put("success", true);
    //         response.put("message", "이력서가 삭제되었습니다.");
            
    //     } catch (Exception e) {
    //         log.error("이력서 삭제 중 에러 발생", e);
    //         response.put("success", false);
    //         response.put("error", e.getMessage());
    //     }
        
    //     return response;
    // }

    // /**
    //  * 피드백 조회 (단건)
    //  * GET http://localhost:8081/api/resume/feedback/{feedbackId}
    //  */
    // @GetMapping(value = "/feedback/{feedbackId}",
    //             produces = MediaType.APPLICATION_JSON_VALUE)
    // public Map<String, Object> getFeedbackDetail(@PathVariable int feedbackId) {
        
    //     log.info("=== 피드백 조회 === feedbackId={}", feedbackId);
        
    //     Map<String, Object> response = new HashMap<>();
        
    //     try {
    //         ResumeFeedback feedback = coachingService.getFeedbackById(feedbackId);
            
    //         if (feedback == null) {
    //             response.put("success", false);
    //             response.put("error", "피드백을 찾을 수 없습니다.");
    //         } else {
    //             response.put("success", true);
    //             response.put("feedback", feedback);
    //         }
            
    //     } catch (Exception e) {
    //         log.error("피드백 조회 중 에러 발생", e);
    //         response.put("success", false);
    //         response.put("error", e.getMessage());
    //     }
        
    //     return response;
    // }

    // /**
    //  * 피드백 목록 조회 (이력서별)
    //  * GET http://localhost:8081/api/resume/{resumeId}/feedbacks
    //  */
    // @GetMapping(value = "/{resumeId}/feedbacks",
    //             produces = MediaType.APPLICATION_JSON_VALUE)
    // public Map<String, Object> getFeedbackList(@PathVariable int resumeId) {
        
    //     log.info("=== 피드백 목록 조회 === resumeId={}", resumeId);
        
    //     Map<String, Object> response = new HashMap<>();
        
    //     try {
    //         List<ResumeFeedback> feedbacks = coachingService.getFeedbacksByResumeId(resumeId);
            
    //         response.put("success", true);
    //         response.put("count", feedbacks.size());
    //         response.put("feedbacks", feedbacks);
            
    //     } catch (Exception e) {
    //         log.error("피드백 조회 중 에러 발생", e);
    //         response.put("success", false);
    //         response.put("error", e.getMessage());
    //     }
        
    //     return response;
    // }

    // /**
    //  * 사용자별 이력서 목록 조회
    //  * GET http://localhost:8081/api/resume/list/{userId}
    //  */
    // @GetMapping(value = "/list/{userId}",
    //             produces = MediaType.APPLICATION_JSON_VALUE)
    // public Map<String, Object> getResumeList(@PathVariable int userId) {
        
    //     log.info("=== 사용자 이력서 목록 조회 === userId={}", userId);
        
    //     Map<String, Object> response = new HashMap<>();
        
    //     try {
    //         List<Resume> resumes = resumeService.getResumesByUserId(userId);
            
    //         response.put("success", true);
    //         response.put("count", resumes.size());
    //         response.put("resumes", resumes);
            
    //     } catch (Exception e) {
    //         log.error("이력서 목록 조회 중 에러 발생", e);
    //         response.put("success", false);
    //         response.put("error", e.getMessage());
    //     }
        
    //     return response;
    // }

  
}
