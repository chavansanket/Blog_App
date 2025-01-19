package com.project1.blog.services.impl;

//import java.io.Console;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

//import com.fasterxml.jackson.databind.ser.BeanPropertyWriter;
import com.project1.blog.entities.Category;
import com.project1.blog.entities.Post;
import com.project1.blog.entities.User;
import com.project1.blog.exceptions.ResourceNotFoundException;
import com.project1.blog.payloads.PostDto;
import com.project1.blog.repositories.CategoryRepo;
import com.project1.blog.repositories.PostRepo;
import com.project1.blog.repositories.UserRepo;
import com.project1.blog.services.PostService;

@Service
public class PostServiceImpl implements PostService {
	
	@Autowired
	private PostRepo postRepo;
	
	
	@Autowired
	private ModelMapper modelMapper;
	
	@Autowired
	private UserRepo userRepo;
	
	@Autowired
	private CategoryRepo categoryRepo;

	@Override
	public PostDto createPost(PostDto postDto, Integer userId, Integer categoryId) {
		Post post = modelMapper.map(postDto, Post.class);
		User user = userRepo.findById(userId).orElseThrow(()->new ResourceNotFoundException("User", "userId", userId));
		Category category = 
				categoryRepo.findById(categoryId).orElseThrow(()-> new ResourceNotFoundException("Category", "catId", categoryId));
		
		post.setUser(user);
		post.setCategory(category);
		post.setImageName("default.png");
		post.setAddedDate(new Date());
		System.out.println("post:  "+post.getAddedDate());
		
		Post savedPost = postRepo.save(post);
		PostDto savedPostDto = modelMapper.map(savedPost, PostDto.class);
		System.out.println("postDto: "+ savedPostDto.getAddedDate());
		return savedPostDto;
	}

	@Override
	public PostDto updatePost(PostDto postDto, Integer postId) {

		Post post = this.postRepo.findById(postId)
                .orElseThrow(() -> new ResourceNotFoundException("Post ", "post id", postId));
		
        post.setTitle(postDto.getTitle());
        post.setContent(postDto.getContent());
        post.setImageName(postDto.getImageName());

        Post updatedPost = this.postRepo.save(post);
        return this.modelMapper.map(updatedPost, PostDto.class);
	}

	@Override
	public void deletePost(Integer postId) {
		Post post = postRepo.findById(postId).orElseThrow(()->new ResourceNotFoundException("Post", "post_Id", postId));
		this.postRepo.delete(post);
	}

	@Override
	public List<PostDto> getAllPosts() {
		
		return null;
	}

	@Override
	public PostDto getPost(Integer postId) {
		Post post = this.postRepo.findById(postId)
                .orElseThrow(() -> new ResourceNotFoundException("Post", "post id", postId));
		System.out.println(post.getAddedDate());
        PostDto dtoPost=this.modelMapper.map(post, PostDto.class);
        System.out.println("dtoDate: "+dtoPost.getAddedDate());
        
		return dtoPost;
	}

	@Override
	public List<PostDto> getPostsByCategory(Integer catId) {
		Category cat= categoryRepo.findById(catId)
				.orElseThrow(()->new ResourceNotFoundException("Category", "catId", catId));
		
		List<Post> listPosts = postRepo.findByCategory(cat);
		List<PostDto> listPostDtos = listPosts.stream().map((t) -> modelMapper.map(t, PostDto.class)).collect(Collectors.toList());
		return listPostDtos;
	}

	@Override
	public List<PostDto> getPostsByUser(Integer userId) {
		 User user = this.userRepo.findById(userId)
	                .orElseThrow(() -> new ResourceNotFoundException("User ", "userId ", userId));
        List<Post> posts = this.postRepo.findByUser(user);

        List<PostDto> postDtos = posts.stream().map((post) -> this.modelMapper.map(post, PostDto.class))
                .collect(Collectors.toList());

        return postDtos;
	}

//	@Override
//	public List<PostDto> searchPosts(String keyword) {
//		List<Post> posts = postRepo.serachByTitle("%"+ keyword+ "%");
//		List<PostDto> postDtos = posts.stream().map((p)->modelMapper.map(p, PostDto.class)).collect(Collectors.toList());
//		return postDtos;
//	}

}
