package com.example.demo.post;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.example.demo.board.Board;
import com.example.demo.board.BoardServiceImpl;
import com.example.demo.category.CategoryService;
import com.example.demo.user.User;
import com.example.demo.user.UserService;

@Controller
@RequestMapping("/board")
public class PostController {

	@Autowired
	BoardServiceImpl boardService;
	@Autowired
	PostServiceImpl postService;
	@Autowired
	UserService userService;
	@Autowired
	CategoryService categoryService;
	
	@GetMapping(value = "/register")
	public String allPost(Model model) {
		List<Board> post = postService.getBoardList();
		model.addAttribute("post", post);
		return "board/posts";
	}
	
	
//  post write
	@GetMapping(value = "/register/new")
	public String writePost(Model model) {

		model.addAttribute("board", new Board());
		return "board/board_post";
	}
	
//  post register
	@PostMapping(value = "/register")
	public String registerBoard(@ModelAttribute("board") Board board, Model model) {

		postService.saveBoard(board);
		return "redirect:/board";
	}
}
