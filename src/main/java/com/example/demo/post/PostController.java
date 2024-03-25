package com.example.demo.post;

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
//  post write
	@GetMapping(value = "/post")
	public String writePost(@RequestParam(value = "postID", required=false) Integer postID, Model model) {
		if (postID == null) {
			model.addAttribute("board", new Board());
		} else {
			Board board = postService.getBoardDetail(postID);
			if (board == null) {
				return "redirect:/board";
			}
			model.addAttribute("board", board);
		}

		return "board_post";
	}
	
//  post register
	@PostMapping(value = "/register")
	public String registerBoard(@ModelAttribute("board") Board board, Model model,User userID,int categoryID) {
//		try {
//			boolean isRegistered = boardService.registerBoard(params);
//			if (isRegistered == false) {
//				// TODO => 게시글 등록에 실패하였다는 메시지를 전달
//			}
//		} catch (DataAccessException e) {
//			// TODO => 데이터베이스 처리 과정에 문제가 발생하였다는 메시지를 전달
//
//		} catch (Exception e) {
//			// TODO => 시스템에 문제가 발생하였다는 메시지를 전달
//		}

//		 User userID = board.getUser_userID();
//		 Category category = board.getCategory_categoryID();
//		    
//		    // 필요한 작업 수행 (예: 변환, 유효성 검사 등)
//		    
//		    // 변환된 값으로 다시 설정
//		 board.setUser_userID(userID);
//		 board.setCategory_categoryID(category);
//		    
		// 사용자 아이디를 기반으로 사용자 객체를 가져옴
//        User user = userService.findUserByUserID(userID);
//        
//        // 게시물에 사용자 객체 설정
//        board.setUser(user);
		postService.saveBoard(board);
		
//	    List<Category> categories = categoryService.getCategoryNameById(categoryID);
//		List<Board> boardList = boardService.getBoardList();
	    // 모델에 데이터 추가
//	    model.addAttribute("board", new Board());
//	    model.addAttribute("users", user);
//	    model.addAttribute("categories", categories);
//	    model.addAttribute("board", boardList);
		
		return "redirect:/board";
	}
}
