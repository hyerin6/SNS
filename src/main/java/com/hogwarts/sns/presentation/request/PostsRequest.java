package com.hogwarts.sns.presentation.request;

public class PostsRequest {
	
	private Long lastPostId;

	public Long getLastPostId() {
		if (this.lastPostId == null) {
			return Long.MIN_VALUE;
		}
		return this.lastPostId;
	}

}
