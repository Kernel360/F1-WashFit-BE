package com.kernel360.utils.file;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.CannedAccessControlList;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.PutObjectRequest;
import com.kernel360.code.common.CommonErrorCode;
import com.kernel360.exception.BusinessException;
import lombok.RequiredArgsConstructor;
import org.apache.commons.io.FilenameUtils;
import org.apache.tika.Tika;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.List;
import java.util.UUID;

@Component
@RequiredArgsConstructor
public class FileUtils {

    private final AmazonS3 amazonS3;

    private final Tika tika = new Tika();

    @Value("${aws.s3.bucket.name}")
    private String bucketName;

    @Value("${spring.profiles.active}")
    private String profile;

    @Value("${module.name}")
    private String moduleName;

    public String upload(String path, MultipartFile multipartFile) {
        String filePath = makeFilePath(path);
        String filename = makeFileName();
        String fileExtension = FilenameUtils.getExtension(multipartFile.getOriginalFilename());
        String fileKey = String.join("", filePath, filename, "." + fileExtension);

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

        return fileKey;
    }

    private String makeFilePath(String path) {
        if (!path.endsWith("/")) {
            path += "/";
        }

        return String.join("/", profile, moduleName, path);
    }

    private String makeFileName() {

        return String.join(
                "-",
                LocalDate.now(ZoneId.of("Asia/Seoul")).toString(),
                UUID.randomUUID().toString());
    }

    public void isValidFileExtension(List<MultipartFile> files) {
        boolean isNotValid = files.stream()
                                  .anyMatch(file -> FilenameUtils.getExtension(file.getOriginalFilename()).isBlank());

        if (isNotValid) {
            throw new BusinessException(CommonErrorCode.INVALID_FILE_EXTENSION);
        }
    }

    public void isValidFileExtension(List<MultipartFile> files, List<String> allowedFileType) {
        isValidFileExtension(files);

        if (allowedFileType == null || allowedFileType.isEmpty()) {
            return;
        }

        boolean isNotValid = files.stream()
                                  .anyMatch(file -> {
                                      try {
                                          return !allowedFileType.contains(tika.detect(file.getInputStream()));
                                      } catch (IOException e) {
                                          throw new BusinessException(CommonErrorCode.FAIL_FILE_UPLOAD);
                                      }
                                  });

        if (isNotValid) {
            throw new BusinessException(CommonErrorCode.INVALID_FILE_EXTENSION);
        }
    }

    public void delete(String fileKey) {
        amazonS3.deleteObject(bucketName, fileKey);
    }
}
