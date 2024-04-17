package com.example.demo.board;

import java.sql.Date;

import org.springframework.web.multipart.MultipartFile;

import com.example.demo.category.Category;
import com.example.demo.user.User;

import lombok.Data;

@Data
public class Board {

	private Integer postID;
    private String authorName;
    private String authorLink;
    private String postName;
    private String content;
    private int visitCnt;
    private Date writeDate;
    private Date endDate;
    private Date eventStart;
    private Date eventEnd;
    
    private User user;
    private Category category;

    private String userID;
    private Integer categoryID;
    
    private String categoryName;
	
    private Integer fileID;
    private Integer costID;
    
    private MultipartFile file;
}
