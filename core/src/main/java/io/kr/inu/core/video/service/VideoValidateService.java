package io.kr.inu.core.video.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.io.InputStreamResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@Service
@RequiredArgsConstructor
@Slf4j
public class VideoValidateService {

    private final RestTemplate restTemplate;

    public void validateHarmVideo(MultipartFile video) throws IOException {
        // 요청할 API 엔드포인트 URL
        String apiUrl = "http://43.203.240.230:5000/process_video/sensitive_classify";

        // 로그 확인
        log.info("여기까지 들어옴1");

        // 멀티파트 요청 엔티티 생성
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.MULTIPART_FORM_DATA);
        log.info("여기까지 들어옴2");

        // 멀티파트 폼 데이터 설정
        MultiValueMap<String, Object> formData = new LinkedMultiValueMap<>();

        // 파일 메타데이터를 포함하는 Resource 생성
        Resource videoResource = new InputStreamResource(video.getInputStream()) {
            @Override
            public long contentLength() {
                return video.getSize();
            }

            @Override
            public String getFilename() {
                return video.getOriginalFilename();
            }
        };

        formData.add("video", videoResource); // 변경된 부분

        log.info("여기까지 들어옴3");
        // 요청 엔티티 생성
        HttpEntity<MultiValueMap<String, Object>> requestEntity = new HttpEntity<>(formData, headers);

        log.info("여기까지 들어옴4");

        // POST 요청 보내기
        RestTemplate restTemplate = new RestTemplate(); // restTemplate 인스턴스가 필요하다고 가정합니다.
        String responseData = restTemplate.postForObject(apiUrl, requestEntity, String.class);

        log.info("여기까지 들어옴5");
        // 응답 값 출력
        log.info("응답 값 출력 : " + responseData);
    }
}
