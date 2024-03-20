package com.example.demo.board;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.example.demo.category.Category;
import com.example.demo.user.User;
import com.example.demo.user.UserService;

@Controller
public class BoardController {

	@Autowired
	BoardService boardService;
	
	@Autowired
	UserService userService;
	
//	main page
	@GetMapping("/")
	public String main(Model model) {
		List<Board> posts = boardService.getAllPosts();
        model.addAttribute("posts", posts);
		return "main";
	}
	
//	board page
	@GetMapping("/board")
    public String showBoard(@RequestParam(name = "categoryId", required = false) Integer categoryId, Model model) {
//		all post view
		List<Board> posts = boardService.getAllPosts(); 
	    for (Board post : posts) {
//	    	post author userID
	    	String userID = post.getUser_userID().getUserID();
	    	User user = userService.getUserById(post.getUser_userID());
	        post.setUser_userID(user); 
	    }

        model.addAttribute("posts", posts);
        return "board";
    }
	
//  post write
	@GetMapping(value = "/board/post")
	public String writePost(@RequestParam(value = "postID", required=false) Integer postID, Model model) {
		if (postID == null) {
			model.addAttribute("board", new Board());
		} else {
			Board board = boardService.getBoardDetail(postID);
			if (board == null) {
				return "redirect:/board";
			}
			model.addAttribute("board", board);
		}

		return "board_post";
	}
	
//  post register
	@PostMapping(value = "/board/register")
	public String registerBoard(@ModelAttribute Board board, Model model) {
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
		boardService.saveBoard(board);
		
		return "redirect:/board";
	}
	
//	post detail 
	@GetMapping("/board/{postID}")
    public String showPostDetail(@PathVariable(name="postID") Integer postID, Model model) {
        Board post = (Board) boardService.getPostById(postID);
        
//        System.out.println("컨트롤러" + post.getCategoryName());
        model.addAttribute("post", post);
        return "board_detail";
    }
}
