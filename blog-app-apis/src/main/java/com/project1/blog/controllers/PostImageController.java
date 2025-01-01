package com.project1.blog.controllers;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;


@RestController
@RequestMapping("/apis/v1/posts/images")
public class PostImageController {
	
	private final Path updloadDirectory = Paths.get("uploads");
	
	public PostImageController() throws IOException {
		//Create upload directory if it doesn't exist
		if (!Files.exists(updloadDirectory)) {
			Files.createDirectories(updloadDirectory);
		}
	}
	
	@PostMapping("/upload")
	public ResponseEntity<String> uploadImage(@RequestParam("file") MultipartFile file){
		try {
			//validate file
			if(file.isEmpty()) {
				return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("File is empty!");
			}
			String  fileName = StringUtils.cleanPath(file.getOriginalFilename());
			Path targetPath = updloadDirectory.resolve(fileName);
			
			//Save the file
			Files.copy(file.getInputStream(), targetPath);
			
			return ResponseEntity.ok("Image updloaded successfully: " + fileName);
			
		}catch (Exception e) {
			// TODO: handle exception
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error uploading image: "+ e.getMessage());
		}
	}
	
	@GetMapping("/{fileName}")
	public ResponseEntity<Resource> getImage(@PathVariable String fileName) {
		
		try {
			Path filePath = updloadDirectory.resolve(fileName).normalize();
			Resource resource = new UrlResource(filePath.toUri());
			
//sending in binary format	//image is sent as a binary file with an appropriate content type, such as image/jpeg, image/png, etc.
			if(resource.exists()) {
	            // Determine the content type
	            String contentType = Files.probeContentType(filePath);

	            if (contentType == null) {
	                contentType = "application/octet-stream";
	            }

	            return ResponseEntity.ok()
	                    .contentType(MediaType.parseMediaType(contentType))
	                    .header(HttpHeaders.CONTENT_DISPOSITION, "inline; filename=\"" + resource.getFilename() + "\"")
	                    .body(resource);
			} else {
				return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
			} 
			
////sending in json format			//file is being treated as binary data and encoded into JSON instead of being directly served as a raw image
//			if(resource.exists()) {
//				return ResponseEntity.ok()
//						.header(HttpHeaders.CONTENT_DISPOSITION, "inline; filename=\"" + resource.getFilename() + "\"")
//						.body(resource);
//			} else {
//				return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
//			} 
			
		} catch (Exception e) {
			
			// TODO: handle exception
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
		}
	}
	
	
	
}
