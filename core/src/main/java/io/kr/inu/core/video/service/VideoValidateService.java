package io.kr.inu.core.video.service;

import io.kr.inu.core.redis.HarmfulVideoReqDto;
import io.kr.inu.core.redis.RedisPubService;
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

    private final RedisPubService redisPubService;

    public void validateHarmVideo(HarmfulVideoReqDto dto) {
        redisPubService.pubMsgChannel(dto);
    }
}
