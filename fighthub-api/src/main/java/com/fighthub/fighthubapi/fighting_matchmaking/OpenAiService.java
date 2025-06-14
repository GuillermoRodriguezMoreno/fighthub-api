package com.fighthub.fighthubapi.fighting_matchmaking;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.util.List;
import java.util.Map;

@Service
public class OpenAiService {

    private final WebClient client;
    private final ObjectMapper objectMapper;

    public OpenAiService(WebClient openAiWebClient, ObjectMapper objectMapper) {
        this.client = openAiWebClient;
        this.objectMapper = objectMapper;
    }

    public Mono<String> sendText(String text) {
        Map<String, Object> body = Map.of(
                "model", "gpt-4.1-nano",
                "messages", List.of(
                        Map.of("role", "user", "content", text)
                )
        );

        return client.post()
                .uri("/chat/completions")
                .bodyValue(body)
                .retrieve()
                .bodyToMono(Map.class)
                .map(resp -> {
                    List<?> choices = (List<?>) resp.get("choices");
                    if (choices != null && !choices.isEmpty()) {
                        Map<?,?> first = (Map<?,?>) choices.get(0);
                        Map<?,?> message = (Map<?,?>) first.get("message");
                        return (String) message.get("content");
                    }
                    return "No response";
                });
    }

    public Mono<List<OpponentRank>> rankOpponents(String targetJson, String opponentsJson) {
        String systemMsg = """
            You are an expert analyst in mixed martial arts.
            You must compare the target fighter with each potential opponent
            based on all their stats and attributes (weight, height, win/loss record, fighting styles, division, etc.).
            Return only a JSON array of objects with fields id, name, and score
            (a number between 0.0 and 1.0), sorted in descending order of compatibility.
      """;

        String userMsg = """
      Target: %s

      Opponents: %s
      """.formatted(targetJson, opponentsJson);

        Map<String,Object> body = Map.of(
                "model", "gpt-4.1-nano",
                "messages", List.of(
                        Map.of("role","system", "content", systemMsg),
                        Map.of("role","user",   "content", userMsg)
                ),
                "temperature", 0.0
        );

        return client.post()
                .uri("/chat/completions")
                .bodyValue(body)
                .retrieve()
                .bodyToMono(Map.class)
                .handle((resp, sink) -> {
                    try {
                        Object choice = ((List<?>) resp.get("choices")).get(0);
                        if (!(choice instanceof Map<?, ?> choiceMap)) {
                            sink.error(new RuntimeException("Wrong format in 'choices[0]': " + choice));
                            return;
                        }

                        Object message = choiceMap.get("message");
                        if (!(message instanceof Map<?, ?> messageMap)) {
                            sink.error(new RuntimeException("Wrong format in 'message': " + message));
                            return;
                        }

                        Object content = messageMap.get("content");
                        if (!(content instanceof String contentStr)) {
                            sink.error(new RuntimeException("Content is not a String: " + content));
                            return;
                        }

                        JsonNode contentNode = objectMapper.readTree(contentStr);
                        if (!contentNode.isArray()) {
                            sink.error(new RuntimeException("Content is not an array JSON: " + contentStr));
                            return;
                        }

                        List<OpponentRank> result = objectMapper.readerForListOf(OpponentRank.class)
                                .readValue(contentNode);

                        sink.next(result);

                    } catch (Exception e) {
                        sink.error(new RuntimeException("Error parsing response from OpenAI", e));
                    }
                });
    }

    public String toJson(Object obj) {
        try {
            return objectMapper.writeValueAsString(obj);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }
}