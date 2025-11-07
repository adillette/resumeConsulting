package com.example.demo.dto;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ResumeRequest {
   ResumeRequest(){

   }
   private String question;
    @Override
    public String toString() {
        return "ResumeRequest{question='" + question + "'}";
    }
   
}
