package com.example.demo.service;

import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.prompt.ChatOptions;
import org.springframework.ai.embedding.EmbeddingModel;
import org.springframework.ai.vectorstore.VectorStore;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.advisor.ResumeAdvisor;
import com.example.demo.dao.ResumeDao;
import com.example.demo.dto.Resume;

import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Flux;

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

//--------------------------------------------------



  public String advivisorChain(String question) {
    String answer = chatClient.prompt()
        .user(question)
        .call()
        .content();
    return answer;
  }

  // ---------------------------------------------------
  // 기본 crud
  public String insertResume(Resume resume) {
    int result = resumeDao.insertResume(resume);
    if (result != 0) {
      return "success";
    }
    return "fail";

  }

  public Resume getResume(int resume_id) {
    Resume resume = resumeDao.selectResumeById(resume_id);
    return resume;
  }

  // update
  // delete

}
