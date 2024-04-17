package com.example.demo.post;

import java.sql.Date;

import org.springframework.web.multipart.MultipartFile;

import com.example.demo.user.User;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ImageFile {

	private String name;
	private MultipartFile file;
	public String uri;
	
	private Integer imageID;
	private String oriImName;
	private String saveImName;
    private String path;
    private Date createDate;
    private String userID;
    
    private User user;
	
}
