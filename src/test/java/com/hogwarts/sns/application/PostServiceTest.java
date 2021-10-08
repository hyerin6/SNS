package com.hogwarts.sns.application;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.web.multipart.MultipartFile;

import com.hogwarts.sns.domain.PostIndex;
import com.hogwarts.sns.presentation.request.CreatePostRequest;
import com.hogwarts.sns.presentation.request.PostSearchRequest;

@SpringBootTest
@ExtendWith(MockitoExtension.class)
class PostServiceTest {

	@Autowired
	PostService postService;

	@Test
	void getAllIndex() {
		List<MultipartFile> images = new ArrayList<>();

		List<CreatePostRequest> requests = List.of(
			new CreatePostRequest("나 우리", images),
			new CreatePostRequest("나의 우리나라", images),
			new CreatePostRequest("우주의 이야기", images),
			new CreatePostRequest("우유의 이야기", images),
			new CreatePostRequest("우기의 사랑", images),
			new CreatePostRequest("우리 은행", images),
			new CreatePostRequest("우리", images),
			new CreatePostRequest("우리나라", images),
			new CreatePostRequest("우주", images),
			new CreatePostRequest("우주의 나라", images)
		);

		for (CreatePostRequest p : requests) {
			postService.create(null, p);
		}

		PageRequest pageRequest = PageRequest.of(0, 5, Sort.by("id").descending());
		PostSearchRequest request = new PostSearchRequest("우");

		List<PostIndex> index = postService.getAllIndex(request, pageRequest);

		for (PostIndex i : index) {
			System.out.println(i.toString());
		}

	}

}