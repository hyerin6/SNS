package com.hogwarts.sns.application;

import java.util.ArrayList;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.hogwarts.sns.domain.Image;
import com.hogwarts.sns.domain.Post;
import com.hogwarts.sns.persistence.ImageRepository;
import com.hogwarts.sns.presentation.response.FileInfo;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class ImageService {

	private final ImageRepository imageRepository;
	private final FileService fileService;

	@Transactional
	public void addImage(Post post, List<MultipartFile> files) {
		List<FileInfo> fileInfos = fileService.uploadFiles(files);
		List<Image> images = new ArrayList<>();

		for (FileInfo info : fileInfos) {
			images.add(new Image(post, info.getFileName(), info.getFilePath()));
		}

		imageRepository.saveAll(images);
	}

	public List<Image> getImage(Long postId) {
		return imageRepository.findByPostId(postId);
	}

	@Transactional
	public void delete(Long postId) {
		List<Image> images = getImage(postId);
		List<FileInfo> fileInfos = new ArrayList<>();

		for (Image image : images) {
			fileInfos.add(new FileInfo(image.getName(), image.getPath()));
		}

		fileService.deleteFiles(fileInfos);
		imageRepository.deleteAllByPostId(postId);
	}
}
