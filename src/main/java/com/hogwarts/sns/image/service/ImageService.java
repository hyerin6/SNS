package com.hogwarts.sns.image.service;

import java.util.ArrayList;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.hogwarts.sns.common.service.FileService;
import com.hogwarts.sns.image.domain.Image;
import com.hogwarts.sns.image.dto.FileInfo;
import com.hogwarts.sns.image.repository.ImageRepository;
import com.hogwarts.sns.post.domain.Post;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class ImageService {

	private final ImageRepository imageRepository;
	private final FileService fileService;

	public void addImage(Post post, MultipartFile file) {
		FileInfo fileInfo = fileService.uploadFile(file);
		Image image = new Image(post, fileInfo.getFileName(), fileInfo.getFilePath());
		imageRepository.save(image);
	}

	public void addImages(Post post, List<MultipartFile> files) {
		List<FileInfo> fileInfos = fileService.uploadFiles(files);
		List<Image> images = new ArrayList<>();

		for (FileInfo info : fileInfos) {
			images.add(new Image(post, info.getFileName(), info.getFilePath()));
		}

		imageRepository.saveAll(images);
	}

	public List<Image> getImages(Long postId) {
		return imageRepository.findByPostId(postId);
	}

	@Transactional
	public void delete(Long postId) {
		List<Image> images = getImages(postId);
		List<FileInfo> fileInfos = new ArrayList<>();

		for (Image image : images) {
			fileInfos.add(new FileInfo(image.getName(), image.getPath()));
		}

		fileService.deleteFiles(fileInfos);
		imageRepository.deleteAllByPostId(postId);
	}
}
