package com.hogwarts.sns.application;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.CannedAccessControlList;
import com.amazonaws.services.s3.model.DeleteObjectRequest;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.PutObjectRequest;
import com.hogwarts.sns.presentation.exception.e5xx.FileUploadException;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Component
public class AwsS3Service {

	private final AmazonS3 amazonS3;

	@Value("${cloud.aws.s3.bucket}")
	private String bucket;

	public String uploadFile(MultipartFile file, String fileName) {
		try {
			ObjectMetadata metaData = new ObjectMetadata();
			metaData.setContentLength(file.getBytes().length);
			amazonS3.putObject(new PutObjectRequest(bucket, fileName, file.getInputStream(), metaData)
				.withCannedAcl(CannedAccessControlList.PublicRead));
		} catch (Exception e) {
			throw new FileUploadException(e);
		}

		return getUrl(bucket, fileName);
	}

	private String getUrl(String path, String fileName) {
		return amazonS3.getUrl(path, fileName).toString();
	}

	public void deleteS3Object(String name) {
		amazonS3.deleteObject(new DeleteObjectRequest(bucket, name));
	}

}
