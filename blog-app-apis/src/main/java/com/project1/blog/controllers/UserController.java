package com.project1.blog.controllers;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.project1.blog.payloads.ApiResponse;
import com.project1.blog.payloads.UserDto;
import com.project1.blog.services.UserService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/apis/user")
public class UserController{
	
//	@Autowired
//	private UserService userService;
	
	
	private final UserService userService;
	
	public UserController(@Qualifier("userService") UserService userService) {
		this.userService = userService;
	}
	
	
	@PostMapping("/")
	public ResponseEntity<UserDto> createUser(@Valid @RequestBody UserDto userDto){
		UserDto createUserDto = this.userService.createUser(userDto);
		return new ResponseEntity<>(createUserDto, HttpStatus.CREATED);
	}
	
	@GetMapping("/getUser/{id}")
	public ResponseEntity<UserDto> getUser(@PathVariable Integer id){
		UserDto userDto = this.userService.getUserById(id);
		return new ResponseEntity<>(userDto, HttpStatus.OK);
	}
	
	@PutMapping("/updateUser/{id}")
	public ResponseEntity<UserDto> updateUser(@PathVariable Integer id, @RequestBody UserDto userDto){
		UserDto userdDto = this.userService.updateUser(userDto, id);
//		return new ResponseEntity<>(userdDto, HttpStatus.OK);
		return ResponseEntity.ok(userdDto);
	}
	
	@DeleteMapping("/deleteUser/{id}")
	public ResponseEntity<ApiResponse> deleteUser(@PathVariable Integer id){
//		System.out.println("hello");
//		System.out.println(id);
		this.userService.deleteUser(id);
		return new ResponseEntity<>(new ApiResponse( "User deleted successfully", true) , HttpStatus.OK);
	}
	
	@GetMapping("/allUsers")
	public ResponseEntity<List<UserDto>> getAllUser(){
		List<UserDto>  userDtos= this.userService.getAllUsers();
		return new ResponseEntity<>(userDtos, HttpStatus.OK);
	}
	
	
	
	


}
