package com.project1.blog.services.impl;

import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.project1.blog.entities.User;
import com.project1.blog.exceptions.ResourceNotFoundException;
import com.project1.blog.payloads.UserDto;
import com.project1.blog.repositories.UserRepo;
import com.project1.blog.services.UserService;

@Service("userService")
public class UserServiceImpl implements UserService{
	
	@Autowired
	private UserRepo userRepo;
	
	@Autowired
	private ModelMapper modelMapper;

	@Override
	public UserDto createUser(UserDto userDto) {
		// TODO Auto-generated method stub
		User user = this.userDtoToUser(userDto);
		User  savedUser= userRepo.save(user);
		UserDto saveUserDto =  this.userToDto(savedUser);
		
		return saveUserDto;
	}

	@Override
	public UserDto updateUser(UserDto userDto, Integer userId) {
		User user2 = this.userRepo.findById(userId).orElseThrow(() -> new ResourceNotFoundException("User", " Id ", userId));
		
		user2.setEmail(userDto.getEmail());
		user2.setName(userDto.getName());
		user2.setPassword(userDto.getPassword());
		user2.setAbout(userDto.getAbout());
		
		User updatedUser = this.userRepo.save(user2);
		
		return this.userToDto(updatedUser);
	}

	@Override
	public UserDto getUserById(Integer userId) {
		User user = this.userRepo.findById(userId).orElseThrow(()-> new ResourceNotFoundException("User", "Id", userId));
		
		UserDto userDto = this.userToDto(user);
		
		return userDto;
	}

	@Override
	public List<UserDto> getAllUsers() {
		List<User> userList = this.userRepo.findAll();
		
		
		List<UserDto> userDtos = userList.stream().map((user)->this.userToDto(user)).collect(Collectors.toList());
		

		return userDtos;
	}

	@Override
	public void deleteUser(Integer userId) {
		User user = this.userRepo.findById(userId).orElseThrow(()-> new ResourceNotFoundException("User", "Id", userId));
		this.userRepo.delete(user);
		
	}
	
	private User userDtoToUser (UserDto uDto) {
		
		User user = this.modelMapper.map(uDto, User.class);
		
//		User user = new User();
//		user.setId(uDto.getId());
//		user.setName(uDto.getName());
//		user.setEmail(uDto.getEmail());
//		user.setPassword(uDto.getPassword());
//		user.setAbout(uDto.getAbout());
		
		
		return user;
	}
	
	public UserDto userToDto (User user) {
		UserDto userDto = this.modelMapper.map(user, UserDto.class);
//		UserDto userDto = new UserDto();
//		userDto.setId(user.getId());
//		userDto.setName(user.getName());
//		userDto.setEmail(user.getEmail());
//		userDto.setPassword(user.getPassword());
//		userDto.setAbout(user.getAbout());
		
		return userDto;
	}

}
