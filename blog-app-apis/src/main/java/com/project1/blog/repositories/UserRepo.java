package com.project1.blog.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.project1.blog.entities.User;

public interface UserRepo extends JpaRepository<User, Integer>{

}
