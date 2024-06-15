package com.example.community.upload.infrastructure.s3;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "cloud.aws.s3")
public record S3Properties(
	String bucketName,
	String accessKey,
	String secretKey,
	String region,
	String imageUrl
) {
}
