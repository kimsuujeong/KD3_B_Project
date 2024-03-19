package com.example.demo.board;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

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
	
//	post detail 
	@GetMapping("/board/{postID}")
    public String showPostDetail(@PathVariable(name="postID") Integer postID, Model model) {
        Board post = (Board) boardService.getPostById(postID);
        
//        System.out.println("컨트롤러" + post.getCategoryName());
        model.addAttribute("post", post);
        return "board_detail";
    }
}
