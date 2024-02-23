package com.kernel360.utils.file;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.CannedAccessControlList;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.PutObjectRequest;
import com.kernel360.code.common.CommonErrorCode;
import com.kernel360.exception.BusinessException;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.UUID;

@Component
@RequiredArgsConstructor
public class FileUtils {

    private final AmazonS3 amazonS3;

    @Value("${aws.s3.bucket.name}")
    private String bucketName;

    @Value("${aws.s3.bucket.url}")
    private String bucketUrl;

    @Value("${spring.profiles.active}")
    private String profile;

    public String upload(S3BucketPath s3BucketPath, MultipartFile multipartFile) {
        String filePath = makeFilePath(s3BucketPath);
        String filename = makeFileName();
        String fileExtension = getFileExtension(multipartFile.getOriginalFilename());
        String fileKey = String.join("", filePath, filename, fileExtension);

        ObjectMetadata metadata = new ObjectMetadata();
        metadata.setContentType(multipartFile.getContentType());
        metadata.setContentLength(multipartFile.getSize());

        try {
            amazonS3.putObject(
                    new PutObjectRequest(
                            bucketName,
                            fileKey,
                            multipartFile.getInputStream(),
                            metadata
                    ).withCannedAcl(CannedAccessControlList.PublicRead)
            );
        } catch (IOException e) {
            throw new BusinessException(CommonErrorCode.FAIL_FILE_UPLOAD);
        }

        return amazonS3.getUrl(bucketName, fileKey).toString();
    }

    private String makeFilePath(S3BucketPath s3BucketPath) {

        return String.join(
                "/",
                profile,
                s3BucketPath.getModulePath(),
                s3BucketPath.getDomainPath(),
                s3BucketPath.getCustomPath());
    }

    private String makeFileName() {

        return String.join(
                "-",
                LocalDate.now(ZoneId.of("Asia/Seoul")).toString(),
                UUID.randomUUID().toString());
    }

    private String getFileExtension(String originalFilename) {
        try {
            return originalFilename.substring(originalFilename.lastIndexOf("."));
        } catch (StringIndexOutOfBoundsException e) {
            throw new BusinessException(CommonErrorCode.INVALID_FILE_EXTENSION);
        }
    }

    public void delete(String fileUrl) {
        amazonS3.deleteObject(bucketName, fileUrl.split(bucketUrl)[1]);
    }
}
