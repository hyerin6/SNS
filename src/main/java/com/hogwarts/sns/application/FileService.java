package com.hogwarts.sns.application;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import com.hogwarts.sns.domain.response.FileInfo;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Component
public class FileService {

	private final AwsS3Service s3Service;

	public FileInfo uploadFile(MultipartFile file) {
		String fileName = createFileName(file);
		String filePath = s3Service.uploadFile(file, fileName);
		return new FileInfo(fileName, filePath);
	}

	public List<FileInfo> uploadFiles(List<MultipartFile> files) {
		List<FileInfo> fileInfos = new ArrayList<>();

		for (MultipartFile file : files) {
			String fileName = createFileName(file);
			String filePath = s3Service.uploadFile(file, fileName);
			fileInfos.add(new FileInfo(fileName, filePath));
		}

		return fileInfos;
	}
	
	public void deleteFiles(List<FileInfo> fileInfos) {
		for (FileInfo info : fileInfos) {
			s3Service.deleteS3Object(info.getFileName());
		}
	}

	private String createFileName(MultipartFile file) {
		String extension = StringUtils.getFilenameExtension(file.getOriginalFilename());
		String fileName = UUID.randomUUID().toString();
		return fileName + "." + extension;
	}

}
