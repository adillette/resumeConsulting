package com.example.demo.advisor;

import org.springframework.ai.chat.client.ChatClientRequest;
import org.springframework.ai.chat.client.ChatClientResponse;
import org.springframework.ai.chat.client.advisor.api.CallAdvisor;
import org.springframework.ai.chat.client.advisor.api.CallAdvisorChain;
import org.springframework.ai.chat.messages.UserMessage;
import org.springframework.ai.chat.prompt.Prompt;
import org.springframework.core.Ordered;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class ResumeAdvisor implements CallAdvisor {

  private int order;

  public ResumeAdvisor(int order) {
    this.order = order;
  }

  public static final String RESUME_FORMAT_CONTEXT = "resumeFormatContext";

  private static final String DEFAULT_RESUME_FORMAT = """
      [이력서 작성 기준]
      당신은 전문 이력서 컨설턴트입니다.

      [분석 기준]
      1. 경력 기술: 수치와 성과 중심인가?
      2. 직무 적합성: 목표 직무와 연관성이 있는가?
      3. 작성 품질: 문법, 맞춤법, 전문성이 적절한가?

      [피드백 형식]
      강점, 약점, 작성 팁을 제공하세요.
      """;
  
  @Override
  public String getName() {

    return this.getClass().getSimpleName();
  }

  @Override
  public int getOrder() {

    return Ordered.HIGHEST_PRECEDENCE + 1;
  }

  @Override
  public ChatClientResponse adviseCall(ChatClientRequest chatClientRequest,
      CallAdvisorChain callAdvisorChain) {
    ChatClientRequest mutatedRequest = injectResumeFormat(chatClientRequest);

    ChatClientResponse chatClientResponse = callAdvisorChain.nextCall(mutatedRequest);

    return chatClientResponse;

  }

  private ChatClientRequest injectResumeFormat(ChatClientRequest chatClientRequest) {

    // 프롬프트 만들기
    Prompt prevPrompt = chatClientRequest.prompt();
    Prompt newPrompt = prevPrompt.augmentUserMessage(userMessage -> {
      return UserMessage.builder()
          .text(DEFAULT_RESUME_FORMAT + "\n\n" + userMessage.getText())
          .build();
    });
    ChatClientRequest newChatClientRequest = chatClientRequest.mutate()
        .prompt(newPrompt)
        .build();

    return newChatClientRequest;
  }

}
