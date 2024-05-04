package io.kr.inu.infra.s3;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.ObjectMetadata;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;

@Service
@RequiredArgsConstructor
@Transactional
@Slf4j
public class VideoS3Repository {

    private final AmazonS3 amazonS3;
    @Value("${cloud.aws.s3.bucket}")
    private String bucket;
    private static final String BASIC_PROFILE_IMAGE = "https://studyhub-s3.s3.ap-northeast-2.amazonaws.com/avatar_l%401x.png";

    public String saveVideo(MultipartDto multipartDto) {
        String originalFilename = multipartDto.getOriginalFileName();

        ObjectMetadata metadata = new ObjectMetadata();
        metadata.setContentLength(multipartDto.getFileSize());
        metadata.setContentType(multipartDto.getContentType());

        amazonS3.putObject(bucket, originalFilename, multipartDto.getInputStream(), metadata);
        return amazonS3.getUrl(bucket, originalFilename).toString();
    }

    public String saveVideoByStream(String fileName, File file) throws FileNotFoundException {
        log.info("saveVideoByStream 시작");
        ObjectMetadata metadata = new ObjectMetadata();
        metadata.setContentLength(file.length());
        metadata.setContentType("image/jpeg");

        amazonS3.putObject(bucket, fileName, new FileInputStream(file), metadata);
        log.info("S3 저장완료");
        log.info(amazonS3.getUrl(bucket, fileName).toString());
        return amazonS3.getUrl(bucket, fileName).toString();
    }

}
