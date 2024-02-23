package com.kernel360.utils.file;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class S3BucketPath {
    private final String modulePath;
    private final String domainPath;
    private final String customPath;
}
