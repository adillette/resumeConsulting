package com.example.demo.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.dto.Resume;
import com.example.demo.dto.ResumeRequest;
import com.example.demo.service.ResumeService;

import lombok.extern.slf4j.Slf4j;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@Slf4j
@RestController
@RequestMapping("/resume")
public class ResumeController {




  @Autowired
  private ResumeService resumeService;

  @PostMapping(value = "/advisor-chain"
  ,consumes = MediaType.APPLICATION_JSON_VALUE
  ,produces = MediaType.TEXT_PLAIN_VALUE
  )
    public String advisorChain(@RequestBody ResumeRequest request) {

    log.info("insert 만들기 어렵댜{}",request.getQuestion());
    
    
    try {
      String answer = resumeService.advivisorChain(request.getQuestion());
      log.info("insert 만들기 어렵댜{}",answer);

      return answer;
    } catch (Exception e) {
       log.error("에러 발생: ", e);
      return "에러 발생: " + e.getMessage();
    }
    
  }
  



  @PostMapping("/insertResume")
  public String insertResume(@RequestBody Resume resume) {
   String result= resumeService.insertResume(resume);
   return result;
  }

  
}
