package io.kr.inu.core.video.service;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.multipart.MultipartFile;

@Service
@RequiredArgsConstructor
public class VideoValidateService {

    private final RestTemplate restTemplate;

    public void validateHarmVideo(MultipartFile video) {
        // 요청할 API 엔드포인트 URL
        String apiUrl = "https://api.example.com/upload-file";

        // 멀티파트 요청 엔티티 생성
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.MULTIPART_FORM_DATA);

        // 멀티파트 폼 데이터 설정
        MultiValueMap<String, Object> formData = new LinkedMultiValueMap<>();
        formData.add("video", video);

        // 요청 엔티티 생성
        HttpEntity<MultiValueMap<String, Object>> requestEntity = new HttpEntity<>(formData, headers);

        // POST 요청 보내기
        Boolean responseData = restTemplate.postForObject(apiUrl, requestEntity, Boolean.class);

        if(Boolean.TRUE.equals(responseData)) {
            throw new IllegalArgumentException("유해 동영상입니다");
        }
    }
}
