package com.example.demo.service;

import org.springframework.ai.chat.client.ChatClient;
// import org.springframework.ai.chat.prompt.ChatOptions;
// import org.springframework.ai.embedding.EmbeddingModel;
// import org.springframework.ai.vectorstore.VectorStore;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.advisor.ResumeAdvisor;
import com.example.demo.dao.ResumeDao;
import com.example.demo.dto.resume.Resume;
import com.example.demo.dto.resume.ResumeRequest;
import com.example.demo.dto.resumefeedback.ResumeFeedback;
import com.example.demo.dto.resumefeedback.ResumeFeedbackResponse;

import lombok.extern.slf4j.Slf4j;
// import reactor.core.publisher.Flux;

@Service
@Slf4j
public class ResumeService {
  @Autowired
  private ResumeDao resumeDao;
  
  private ChatClient chatClient;

  public ResumeService(ChatClient.Builder chatClientBuilder) {
    this.chatClient = chatClientBuilder
        .defaultAdvisors(new ResumeAdvisor(0))
        .build();
  }


  // @Autowired
  // private EmbeddingModel embeddingModel;// text-embedding-3-small

  // @Autowired
  // private VectorStore vectorStore;
 
  // public Flux<String> generateStreamResumeCoach(String question) {
  //   Flux<String> fluxstring = chatClient.prompt()
  //       .system("입력한 양식에 맞추어서 한국어로 답변해야합니다.")
  //       .user(question)
  //       .options(ChatOptions.builder()
  //           .model("gpt-4o-mini")
  //           .maxTokens(300)
  //           .temperature(0.4).build())
  //       .stream()
  //       .content();

  //   return fluxstring;
  // }




  public ResumeFeedback saveResumeFeedback(int resumeId, int userId, ResumeFeedbackResponse feedbackResult){
    ResumeFeedback feedback = new ResumeFeedback();

    feedback.setTotalScore(feedbackResult.getTotalScore());

     return feedback;
  }




  // ---------------------------------------------------
  // 기본 crud
  public int insertResume(Resume resume) {
    int result = resumeDao.insertResume(resume);
    if (result <= 0) {
      return -1;
    }
    log.info("이력서 등록 완료: resumeId={}",resume.getResumeId());
    int newResumeNumber =  resume.getResumeId();
      return  newResumeNumber ;

  

  }
  //조회
      public Resume getResume(int resumeId) {
        
        log.info("이력서 조회: resumeId={}", resumeId);
        
        Resume resume = resumeDao.selectResumeById(resumeId);
        
        if (resume == null) {
            log.warn("이력서를 찾을 수 없음: resumeId={}", resumeId);
        }
        
        return resume;
    }

  // update

    public String updatResume(Resume resume){
      log.info("이력서 수정: resumeId={}",resume.getResumeId());
      int result = resumeDao.updateResume(resume);

      if (result <= 0) {
          return "fail";
      }
      log.info("이력서 수정완료: resumeId={}",resume.getResumeId());
      
      String message = String.format("수정된 이력서 ", resume.getResumeId());
      return message;
    }
  // delete

  public int deleteResume(int resumeId){
    log.info("이력서 삭제: resumeId={}", resumeId);

    int result = resumeDao.delete(resumeId);

    if(result<=0){
      return -1;
    }
    log.info("이력서 삭제 완료: resumeId={}", resumeId);
    return result;
  }

}
