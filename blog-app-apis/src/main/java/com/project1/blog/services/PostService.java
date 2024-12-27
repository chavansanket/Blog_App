package com.project1.blog.services;

import java.util.List;

import com.project1.blog.payloads.PostDto;

public interface PostService {
	
	//create 
	PostDto createPost(PostDto postDto,Integer userId,Integer categoryId);

	//update 
	PostDto updatePost(PostDto postDto, Integer postId);

	// delete
	void deletePost(Integer postId);
	
//	//getAll
//	PostResponse getAllPosts(Integer pageNumber, Integer pageSize, String sortBy, String sortDir);
	List<PostDto> getAllPosts();
	
	//get
	PostDto getPost(Integer postId);
	
	//getPostOf category
	List<PostDto> getPostsByCategory(Integer catId);
	
	//getPostOf user
	List<PostDto> getPostsByUser(Integer userId);
	
//	//search posts
//	List<PostDto> searchPosts(String keyword);
	
}
