package io.kr.inu.webclient.api.chat;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Slf4j
public class ChatHandler extends TextWebSocketHandler {

    private final Map<String, WebSocketSession> sessions = new ConcurrentHashMap<>();

    @Override
    public void afterConnectionEstablished(WebSocketSession session) throws Exception {
        //TODO: 토큰 기반으로 통신하는 기능 구현
        String userId = session.getUri().getQuery().split("=")[1];
        sessions.put(userId, session);
        log.info("User connected: " + userId);
    }

    @Override
    protected void handleTextMessage(WebSocketSession session, TextMessage message) throws Exception {
        String payload = message.getPayload();
        log.info("Received message: " + payload);

        try {
            // JSON 파싱
            ObjectMapper objectMapper = new ObjectMapper();
            JsonNode jsonNode = objectMapper.readTree(payload);

            // JSON 데이터에서 값 추출
            String toUser = jsonNode.get("to").asText();
            String chatMessage = jsonNode.get("message").asText();

            // 대상 사용자 세션 찾기
            WebSocketSession receiverSession = sessions.get(toUser);
            if (receiverSession != null && receiverSession.isOpen()) {
                receiverSession.sendMessage(new TextMessage(chatMessage));
            } else {
                log.info("User not connected: " + toUser);
            }
        } catch (Exception e) {
            log.error("Error processing message: " + payload, e);
            session.close(); // 잘못된 메시지로 인해 세션 닫기
        }
    }

    @Override
    public void afterConnectionClosed(WebSocketSession session, CloseStatus status) throws Exception {
        sessions.values().remove(session);
        log.info("User disconnected");
    }
}
