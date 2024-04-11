package com.example.demo.file;

import java.sql.Date;

import org.springframework.web.multipart.MultipartFile;

import com.example.demo.user.User;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UploadFile {

	private String name;
	private MultipartFile file;
	public String uri;
	
	private Integer fileID;
    private String path;
    private String oriName;
    private String saveName;
    private String userID;
    private Date lastUpdateDate;
    
    private User user;
}
