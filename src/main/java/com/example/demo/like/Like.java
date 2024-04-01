package com.example.demo.like;

import com.example.demo.board.Board;
import com.example.demo.user.User;

import lombok.Data;

@Data
public class Like {

	private Integer likeID;
	private String userID;
	private Integer postID;
	private User user;
	private Board post;
	private String postName;
	private String authorName;
}
