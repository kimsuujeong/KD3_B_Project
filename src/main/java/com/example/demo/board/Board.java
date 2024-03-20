package com.example.demo.board;

import java.sql.Date;

import com.example.demo.category.Category;
import com.example.demo.user.User;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
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
    
    private User user_userID;
    private Category category_categoryID;

    private String categoryName;
	
}
