package com.example.demo.post;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
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
		return "/TestHtml/board/board_post";
	}
	
//  post save
	@PostMapping(value = "/register")
	public String registerBoard(@ModelAttribute("board") Board board, Model model) {

		postService.saveBoard(board);
		return "redirect:/board";
	}
	
//  post delete
	@GetMapping(value = "/delete/{postID}")
	public String delete(@PathVariable(name = "postID") Integer postID) {
		postService.delete(postID);
		return "redirect:/board";
	}
	
//  post modify (수정)
	@GetMapping(value = "/modify/{postID}")
	public String modifyPost(Board board, @PathVariable(name = "postID") Integer postID, Model model) {
		
		Board board_temp = boardService.getPostById(postID);
		
        board.setPostName(board_temp.getPostName());
        board.setContent(board_temp.getContent());
		board.setAuthorName(board_temp.getAuthorName());
		board.setAuthorLink(board_temp.getAuthorLink());
		board.setCategory_categoryID(board_temp.getCategory_categoryID());
		board.setUser_userID(board_temp.getUser_userID());
		board.setEventStart(board_temp.getEventStart());
		board.setEventEnd(board_temp.getEventEnd());
		
		model.addAttribute("board", board);
		model.addAttribute("postID", postID);
		
		return "/TestHtml/board/board_modify";
	}
	
	@PostMapping(value = "/modify/{postID}")
	public String updatePost(@PathVariable(name = "postID") Integer postID, @ModelAttribute("board") Board board) {
		
		postService.updateBoard(board);
		return "redirect:/board";
	}
}
