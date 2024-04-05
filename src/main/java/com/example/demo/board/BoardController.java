package com.example.demo.board;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.example.demo.user.UserService;

@Controller
public class BoardController {

	@Autowired
	BoardService boardService;

//	@Autowired
	UserService userService;

//	test main page
	@GetMapping("/")
	public String main(Model model) {
		List<Board> posts = boardService.getAllPosts();
		model.addAttribute("posts", posts);
		return "board/Main";
	}

//	board page
	@GetMapping("/board")
	public String showBoard(Model model, Pageable page) {
//		all post view
		Page<Board> posts = this.boardService.getList(page);

		model.addAttribute("posts", posts);
		return "/TestHtml/board/board";
	}

//	board page separate categoryId
	@GetMapping("/board/{categoryID}")
	public String showBoard(@PathVariable(name = "categoryID") Integer categoryID, 
							Model model, Pageable page) {
//		all post view
		Page<Board> posts = this.boardService.getPostsByCategoryId(categoryID, page);

		model.addAttribute("posts", posts);
		return "/TestHtml/board/board";
	}

//	post detail 
	@GetMapping("/board/{categoryID}/{postID}")
	public String showPostDetail(@PathVariable(name = "postID") Integer postID, Model model) {
		Board post = (Board) boardService.getPostById(postID);
		boardService.visitCnt(postID);
//        System.out.println("컨트롤러" + post.getCategoryName());
		model.addAttribute("post", post);
		return "/TestHtml/board/board_detail";
	}

	// 전체 게시물 검색
	@RequestMapping("/board/search")
	public String search_board(Model model, @ModelAttribute Search search, Pageable pageable) {
		Page<Board> searchPost = boardService.search(search, pageable);
		model.addAttribute("posts", searchPost);
			
		return "/TestHtml/board/board";
	}
		
	// 카테고리별 검색
	@RequestMapping("/board/{categoryID}/search")
	public String search(@PathVariable(name = "categoryID") Integer categoryID, Model model,
			@RequestParam(value="order", defaultValue="visitCnt") String order, 
			@ModelAttribute Search search, Pageable pageable) {
		Page<Board> searchPost = boardService.searchCtg(categoryID, search, order, pageable);
		model.addAttribute("posts", searchPost);

		return "/TestHtml/board/board";
	}
	
	
}
