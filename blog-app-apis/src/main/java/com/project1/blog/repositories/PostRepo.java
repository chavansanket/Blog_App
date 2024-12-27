package com.project1.blog.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.project1.blog.entities.Post;
import com.project1.blog.entities.User;
import com.project1.blog.entities.Category;


public interface PostRepo extends JpaRepository<Post, Integer>{

	
	List<Post> findByUser(User user);
	List<Post> findByCategory(Category category);
	
//	@Query("select p from Post where p.title like :key")
//	List<Post> serachByTitle(@Param("key") String title);
//	

}
