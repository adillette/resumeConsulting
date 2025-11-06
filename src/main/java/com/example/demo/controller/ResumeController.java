package com.example.demo.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.dto.Resume;
import com.example.demo.service.ResumeService;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;


@RestController
@RequestMapping("/resume")
public class ResumeController {
  @Autowired
  private ResumeService resumeService;

  @PostMapping(value = "/advisor-chain"
  ,consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE
  ,produces = MediaType.TEXT_PLAIN_VALUE
  )
  
  public String advisorChain(@RequestBody String question) {
     String answer = resumeService.advivisorChain(question);
     return answer;
  }
  



  @PostMapping("/insertResume")
  public String insertResume(@RequestBody Resume resume) {
   String result= resumeService.insertResume(resume);
   return result;
  }

  
}
